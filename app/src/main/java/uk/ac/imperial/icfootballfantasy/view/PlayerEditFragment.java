package uk.ac.imperial.icfootballfantasy.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import uk.ac.imperial.icfootballfantasy.R;
import uk.ac.imperial.icfootballfantasy.controller.PlayerLab;
import uk.ac.imperial.icfootballfantasy.model.Player;

import static java.lang.Integer.parseInt;

/**
 * Created by leszek on 7/26/17.
 */

public class PlayerEditFragment extends Fragment {
    View v;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.player_edit_fragment, container, false);
        int playerId = getArguments().getInt("playerID");

        PlayerLab playerLab = PlayerLab.get();
        final Player player = playerLab.getPlayer(playerId);

        TextView mFirstName = (TextView) v.findViewById(R.id.edit_player_first_name);
        mFirstName.setText(player.getFirstName());
        TextView mLastName = (TextView) v.findViewById(R.id.edit_player_last_name);
        mLastName.setText(player.getLastName());
        TextView mApps = (TextView) v.findViewById(R.id.edit_player_current_apps);
        mApps.setText("" + player.getAppearances());
        TextView mSubs = (TextView) v.findViewById(R.id.edit_player_current_subs);
        mSubs.setText("" + player.getSubAppearances());
        TextView mGoals = (TextView) v.findViewById(R.id.edit_player_current_goals);
        mGoals.setText("" + player.getGoals());
        TextView mAssits = (TextView) v.findViewById(R.id.edit_player_current_assists);
        mAssits.setText("" + player.getAssists());
        TextView mCleanSheets= (TextView) v.findViewById(R.id.edit_player_current_clean_sheets);
        mCleanSheets.setText("" + player.getCleanSheets());
        TextView mMotms = (TextView) v.findViewById(R.id.edit_player_current_motms);
        mMotms.setText("" + player.getMotms());
        TextView mYellows = (TextView) v.findViewById(R.id.edit_player_current_yellows);
        mYellows.setText("" + player.getYellowCards());
        TextView mReds = (TextView) v.findViewById(R.id.edit_player_current_reds);
        mReds.setText("" + player.getRedCards());
        TextView mOwnGoals = (TextView) v.findViewById(R.id.edit_player_current_own_goals);
        mOwnGoals.setText("" + player.getOwnGoals());

        final EditText mNext0 = (EditText) v.findViewById(R.id.edit_player_next_0);
        mNext0.setText("" + player.getAppearances());
        final EditText mNext1 = (EditText) v.findViewById(R.id.edit_player_next_1);
        mNext1.setText("" + player.getSubAppearances());
        final EditText mNext2 = (EditText) v.findViewById(R.id.edit_player_next_2);
        mNext2.setText("" + player.getGoals());
        final EditText mNext3 = (EditText) v.findViewById(R.id.edit_player_next_3);
        mNext3.setText("" + player.getAssists());
        final EditText mNext4= (EditText) v.findViewById(R.id.edit_player_next_4);
        mNext4.setText("" + player.getCleanSheets());
        final EditText mNext5 = (EditText) v.findViewById(R.id.edit_player_next_5);
        mNext5.setText("" + player.getMotms());
        final EditText mNext6 = (EditText) v.findViewById(R.id.edit_player_next_6);
        mNext6.setText("" + player.getYellowCards());
        final EditText mNext7 = (EditText) v.findViewById(R.id.edit_player_next_7);
        mNext7.setText("" + player.getRedCards());
        final EditText mNext8 = (EditText) v.findViewById(R.id.edit_player_next_8);
        mNext8.setText("" + player.getOwnGoals());

        for (int i = 0; i < 9; i++) {
            String buttonDecID = "edit_player_next_button_" + i + "_decrement";
            int resDecID = getResources().getIdentifier(buttonDecID, "id", getActivity().getPackageName());
            Button buttonDec = (Button) v.findViewById(resDecID);
            final String editTextID = "edit_player_next_" + i;
            buttonDec.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    int resID = getResources().getIdentifier(editTextID, "id", getActivity().getPackageName());
                    EditText editText = (EditText) ((ViewGroup) v.getParent()).findViewById(resID);
                    int value = Integer.parseInt(editText.getText().toString());
                    value--;
                    editText.setText("" + value);
                }
            });

            String buttonIncID = "edit_player_next_button_" + i + "_increment";
            int resIncID = getResources().getIdentifier(buttonIncID, "id", getActivity().getPackageName());
            Button buttonInc = (Button) v.findViewById(resIncID);
            buttonInc.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    int resID = getResources().getIdentifier(editTextID, "id", getActivity().getPackageName());
                    EditText editText = (EditText) ((ViewGroup) v.getParent()).findViewById(resID);
                    int value = Integer.parseInt(editText.getText().toString());
                    value++;
                    editText.setText("" + value);
                }
            });
        }


        final Button button = (Button) v.findViewById(R.id.edit_player_save_changes);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int apps = parseInt(mNext0.getText().toString());
                player.updatePlayerByAppearances(apps);
                int subApps = parseInt(mNext1.getText().toString());
                player.updatePlayerBySubAppearances(subApps);
                int goals = parseInt(mNext2.getText().toString());
                player.updatePlayerByGoals(goals);
                int assists = parseInt(mNext3.getText().toString());
                player.updatePlayerByAssists(assists);
                int cleanSheets = parseInt(mNext4.getText().toString());
                player.updatePlayerByCleanSheets(cleanSheets);
                int motms = parseInt(mNext5.getText().toString());
                player.updatePlayerByMotms(motms);
                int yellowCards = parseInt(mNext6.getText().toString());
                player.updatePlayerByYellowCards(yellowCards);
                int redCards = parseInt(mNext7.getText().toString());
                player.updatePlayerByRedCards(redCards);
                int ownGoals = parseInt(mNext8.getText().toString());
                player.updatePlayerByOwnGoals(ownGoals);

                button.setText("");
                button.setEnabled(false);
                ProgressBar progressBar = (ProgressBar) v.findViewById(R.id.edit_player_progress);
                progressBar.setVisibility(View.VISIBLE);

                editPlayerAtDB(player.getId(), apps, subApps, goals, assists, cleanSheets, motms, ownGoals, yellowCards, redCards, player.getPointsWeek());
            }
        });

        return v;
    }

    private void editPlayerAtDB(Integer... input) {

        AsyncTask<Integer, Void, String> asyncTask = new AsyncTask<Integer, Void, String>() {
            @Override
            protected String doInBackground(Integer... input) {
                String message;

                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://union.ic.ac.uk/acc/football/android_connect/edit_player.php?player_id=" + input[0]
                                + "&apps=" + input[1] + "&sub_apps=" + input[2] + "&goals="+ input[3]
                                + "&assists=" + input[4] + "&clean_sheets=" + input[5] + "&motms="+ input[6]
                                + "&own_goals=" + input[7] + "&yellow_cards=" + input[8] + "&red_cards="+ input[9]
                                + "&points_week=" + input[10])
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
                Button registerButton = (Button) v.findViewById(R.id.edit_player_save_changes);
                registerButton.setText("Save Changes");
                registerButton.setEnabled(true);
                ProgressBar progressBar = (ProgressBar) v.findViewById(R.id.edit_player_progress);
                progressBar.setVisibility(View.GONE);
            }
        };

        asyncTask.execute(input);
    }

}
