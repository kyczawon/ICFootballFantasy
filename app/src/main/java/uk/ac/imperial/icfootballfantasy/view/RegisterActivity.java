package uk.ac.imperial.icfootballfantasy.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import uk.ac.imperial.icfootballfantasy.R;

/**
 * Created by leszek on 8/12/17.
 */

public class RegisterActivity extends AppCompatActivity{
    String username;
    String email;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        final EditText usernameEditText = (EditText) findViewById(R.id.register_username);
        final EditText emailEditText = (EditText) findViewById(R.id.register_email);
        final EditText emailRepeatEditText = (EditText) findViewById(R.id.register_repeat_email);
        final EditText passwordEditText = (EditText) findViewById(R.id.register_password);
        final EditText passwordRepeatEditText = (EditText) findViewById(R.id.register_repeat_password);



        final Button registerButton = (Button) findViewById(R.id.register_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = usernameEditText.getText().toString();
                email = emailEditText.getText().toString();
                final String emailRepeat = emailRepeatEditText.getText().toString();
                password = passwordEditText.getText().toString();
                final String passwordRepeat = passwordRepeatEditText.getText().toString();
                String message = "";
                if (username.length() < 4) {
                    message += "Enter a username that is at least 4 characters long\n";
                } else if (username.length() > 29) {
                    message += "Unfortunately your username is too long\n";
                }
                if (email.equals("")) {
                    message += "Enter an email address\n";
                } else if (!isEmailValid(email)) {
                    message += "Enter a valid email\n";
                } else if (!email.equals(emailRepeat)) {
                    message += "emails don't match\n";
                }
                if (password.length() < 8) {
                    message += "Enter a password that is at least 8 characters long\n";
                } else if (password.length() > 29) {
                    message += "Unfortunately your password is too long\n";
                } else if (!password.equals(passwordRepeat)) {
                    message += "passwords don't match\n";
                }
                if (message.equals("")) {//then all field correct
                    //show progress bar instead of text
                    registerButton.setText("");
                    registerButton.setEnabled(false);
                    ProgressBar progressBar = (ProgressBar) findViewById(R.id.register_progress);
                    progressBar.setVisibility(View.VISIBLE);
                    checkUsernameAndPasswordDB(username, email);
                } else {
                    message = message.substring(0, message.length() - 1); //to get rid of last \n
                    Toast.makeText(getApplicationContext(), message,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void checkUsernameAndPasswordDB(String... input) {

        AsyncTask<String, Void, String> asyncTask = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... input) {
                String message;

                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://union.ic.ac.uk/acc/football/android_connect/check_username&email.php?username=" + input[0]
                                + "&email=" + input[1])
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    message = response.body().string();

                } catch (IOException e) {
                    e.printStackTrace();
                    message = "Could not connect to server";
                }
                return message;
            }

            @Override
            protected void onPostExecute(String message) {
                if (message.equals("success")) {
                    addUserToDB(username, email, password);
                } else {
                    Toast.makeText(getBaseContext(), message,
                            Toast.LENGTH_SHORT).show();
                    //show text instead of progress
                    Button registerButton = (Button) findViewById(R.id.register_register);
                    registerButton.setText("Register");
                    registerButton.setEnabled(true);
                    ProgressBar progressBar = (ProgressBar) findViewById(R.id.register_progress);
                    progressBar.setVisibility(View.GONE);
                }
            }
        };

        asyncTask.execute(input);
    }

    private void addUserToDB(String... input) {

        AsyncTask<String, Void, String> asyncTask = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... input) {
                String message;

                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://union.ic.ac.uk/acc/football/android_connect/add_user.php?username=" + input[0]
                                + "&email=" + input[1]+ "&password=" + input[2] + "")
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    message = response.body().string();

                } catch (IOException e) {
                    e.printStackTrace();
                    message = "Could not connect to server";
                }
                return message;
            }

            @Override
            protected void onPostExecute(String message) {
                if (message.equals("success")) {
                    Toast.makeText(getBaseContext(), "Please confirm email to log in",
                            Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(getBaseContext(), message,
                            Toast.LENGTH_SHORT).show();
                    //show text instead of progress
                    Button registerButton = (Button) findViewById(R.id.register_register);
                    registerButton.setText("Register");
                    registerButton.setEnabled(true);
                    ProgressBar progressBar = (ProgressBar) findViewById(R.id.login_progress);
                    progressBar.setVisibility(View.GONE);
                }
            }
        };

        asyncTask.execute(input);
    }
}
