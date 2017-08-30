package uk.ac.imperial.icfootballfantasy.view;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import uk.ac.imperial.icfootballfantasy.R;
import uk.ac.imperial.icfootballfantasy.controller.PlayerLab;
import uk.ac.imperial.icfootballfantasy.model.Team;
import uk.ac.imperial.icfootballfantasy.model.User;
import uk.ac.imperial.icfootballfantasy.model.UserData;

/**
 * Created by leszek on 8/12/17.
 */

public class LoginActivity extends AppCompatActivity {
    User user;
    Team team;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        final Button registerButton = (Button) findViewById(R.id.login_register);
        final ScrollView activityRootView = (ScrollView) findViewById(R.id.login_scroll_view);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int heightDiff = activityRootView.getRootView().getHeight() - activityRootView.getHeight();
                if (heightDiff > dpToPx(getApplicationContext(), 200)) { // if more than 200 dp, it's probably a keyboard...
                    activityRootView.smoothScrollTo(0, registerButton.getTop());
                }
            }
        });

        final EditText usernameEditText = (EditText) findViewById(R.id.login_username);
        final EditText passwordEditText = (EditText) findViewById(R.id.login_password);

        final Button signInButton = (Button) findViewById(R.id.login_sign_in);
        signInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final String username = usernameEditText.getText().toString();
                final String password = passwordEditText.getText().toString();

                String message = "You need to enter";
                if (username.equals("")) {
                    message += " your username";
                }
                if (password.equals("")) {
                    if (message.equals("You need to enter your username")) {
                        message += " and";
                    }
                    message += " your password";
                }
                if (message.equals("You need to enter")) { //then all fields filled
                    signInButton.setText("");
                    signInButton.setEnabled(false);
                    ProgressBar progressBar = (ProgressBar) findViewById(R.id.login_progress);
                    progressBar.setVisibility(View.VISIBLE);
                    getUsersFromDB(username, password);
                } else {
                    Toast.makeText(getBaseContext(), message,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getUsersFromDB(String... data) {

        AsyncTask<String, Void, String> asyncTask = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... data) {
                String message;

                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://union.ic.ac.uk/acc/football/android_connect/check_user.php?username=\"" + data[0]
                                + "\"&password=\""+ data[1] + "\"")
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String jsonArray = response.body().string();
                    if (!jsonArray.equals("\"Invalid username or password\"")) {
                        JSONArray array = new JSONArray(jsonArray);

                        JSONObject object = array.getJSONObject(0);

                        user = new User(object.getInt("user_id"), object.getInt("team_id"), data[0],
                                object.getInt("admined_team"), (object.getInt("is_super_admin") == 1));
                        message = "correct";

                    } else {
                        message = "Invalid username or password";
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                    message = "Could not connect to server";
                } catch (JSONException e) {
                    e.printStackTrace();
                    message = "Could not connect to server";
                }
                return message;
            }

            @Override
            protected void onPostExecute(String message) {
                PlayerLab.get(); //gets players from database
                if (message.equals("correct")) {
                    getTeamFromDB(user.getTeam_id());
                } else {
                    Toast.makeText(getBaseContext(), message,
                            Toast.LENGTH_SHORT).show();
                    Button signInButton = (Button) findViewById(R.id.login_sign_in);
                    signInButton.setText("Sign In");
                    signInButton.setEnabled(true);
                    ProgressBar progressBar = (ProgressBar) findViewById(R.id.login_progress);
                    progressBar.setVisibility(View.GONE);
                }
            }
        };

        asyncTask.execute(data);
    }

    private void getTeamFromDB(Integer... team_ids) {

        AsyncTask<Integer, Void, String> asyncTask = new AsyncTask<Integer, Void, String>() {
            @Override
            protected String doInBackground(Integer... team_ids) {
                String message;

                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://union.ic.ac.uk/acc/football/android_connect/team.php/?team_id=" + team_ids[0])
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String jsonArray = response.body().string();
                    if (!jsonArray.equals("null")) {
                        JSONArray array = new JSONArray(jsonArray);

                        JSONObject object = array.getJSONObject(0);

                        team = new Team(object.getInt("team_id"), object.getString("name"),
                                object.getString("owner"), object.getDouble("price"),
                                object.getInt("points"), object.getInt("points_week"),
                                object.getInt("def_num"), object.getInt("mid_num"),
                                object.getInt("fwd_num"), object.getInt("goal"),
                                object.getInt("player1"), object.getInt("player2"),
                                object.getInt("player3"), object.getInt("player4"),
                                object.getInt("player5"), object.getInt("player6"),
                                object.getInt("player7"), object.getInt("player8"),
                                object.getInt("player9"), object.getInt("player10"),
                                object.getInt("sub_goal"), object.getInt("sub1"),
                                object.getInt("sub2"), object.getInt("sub3"),
                                object.getInt("sub4"));
                        message = "correct";
                    } else {
                        message = "Team does not exist";
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                    message = "Could not connect to server";
                } catch (JSONException e) {
                    e.printStackTrace();
                    message = "Could not connect to server";
                }
                return message;
            }

            @Override
            protected void onPostExecute(String message) {
                if (message.equals("Team does not exist")) {
                    UserData.get(user.getUser_id(), user.getUsername(), user.adminedTeam(), user.is_super_admin());
                    launchCreateTeamActivity();
                } else if (message.equals("correct")) {
                    UserData.get(user.getUser_id(), user.getUsername(), user.getTeam_id(), team, user.adminedTeam(), user.is_super_admin());
                    nextActivity();
                } else {
                    Toast.makeText(getBaseContext(), message,
                            Toast.LENGTH_SHORT).show();
                }
            }
        };

        asyncTask.execute(team_ids);
    }


    private void nextActivity()
    { //sets the user_id and team_id in userData class
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void launchCreateTeamActivity()
    { //sets the user_id and team_id in userData class
        Intent intent = new Intent(getApplicationContext(), TeamCreateActivity.class);
        intent.putExtra("user_id", user.getUser_id());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public static float dpToPx(Context context, float valueInDp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }



}
