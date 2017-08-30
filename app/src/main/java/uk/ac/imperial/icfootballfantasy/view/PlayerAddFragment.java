package uk.ac.imperial.icfootballfantasy.view;

/**
 * Created by leszek on 7/26/17.
 */

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import uk.ac.imperial.icfootballfantasy.R;
import uk.ac.imperial.icfootballfantasy.controller.PlayerLab;
import uk.ac.imperial.icfootballfantasy.model.Player;

public class PlayerAddFragment extends Fragment {
    int team;
    String position;
    View v;

    private Spinner spinner, spinner2;
    private static final String[] teamPaths = {"1","2","3","4","5","6","7"};
    private static final String[] positionPaths = {"GK","DEF", "MID", "FOR"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.player_add, container, false);

        spinner = (Spinner) v.findViewById(R.id.add_player_team);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(v.getContext(),
                android.R.layout.simple_spinner_item, teamPaths);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                team = pos + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinner2 = (Spinner) v.findViewById(R.id.add_player_position);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(v.getContext(),
                android.R.layout.simple_spinner_item, positionPaths);
        spinner2.setAdapter(adapter2);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                switch (pos) {
                    case 0:
                        position = "GK";
                        break;
                    case 1:
                        position = "DEF";
                        break;
                    case 2:
                        position = "MID";
                        break;
                    case 3:
                        position = "FWD";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        final EditText editTextFirstName = (EditText) v.findViewById(R.id.add_player_first_name);
        final EditText editTextLastName = (EditText) v.findViewById(R.id.add_player_last_name);
        final EditText editTextPrice = (EditText) v.findViewById(R.id.add_player_price);
        final CheckBox isFresherCheckbox = (CheckBox) v.findViewById(R.id.add_player_is_fresher);

        final Button button = (Button) v.findViewById(R.id.new_player_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ;
                final String firstName = editTextFirstName.getText().toString();
                final String lastName = editTextLastName.getText().toString();
                final int price = Integer.parseInt(editTextPrice.getText().toString());
                final int isFresher = (isFresherCheckbox.isChecked()) ? 1 : 0;

                PlayerLab playerLab = PlayerLab.get();
                playerLab.addPlayer(new Player(firstName, lastName, position, team, price, (isFresher == 1)));

                button.setText("");
                button.setEnabled(false);
                ProgressBar progressBar = (ProgressBar) v.findViewById(R.id.new_player_progress);
                progressBar.setVisibility(View.VISIBLE);
                addPlayerToDB(firstName, lastName, String.valueOf(position), String.valueOf(team), String.valueOf(price), String.valueOf(isFresher));
            }
        });
        return v;
    }

    private void addPlayerToDB(String... input) {

        AsyncTask<String, Void, String> asyncTask = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... input) {
                String message;

                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://union.ic.ac.uk/acc/football/android_connect/add_player.php?first_name=\"" + input[0]
                                + "\"&last_name=\"" + input[1]+ "\"&position=\"" + input[2] + "\"&team="+ Integer.parseInt(input[3])
                                + "&price=" + Double.parseDouble(input[4]) + "&is_fresher=" + Integer.parseInt(input[5]))
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
                Toast.makeText(getContext(), message,
                        Toast.LENGTH_SHORT).show();
                Button registerButton = (Button) v.findViewById(R.id.new_player_button);
                registerButton.setText("Add Player");
                registerButton.setEnabled(true);
                ProgressBar progressBar = (ProgressBar) v.findViewById(R.id.new_player_progress);
                progressBar.setVisibility(View.GONE);
            }
        };

        asyncTask.execute(input);
    }
}