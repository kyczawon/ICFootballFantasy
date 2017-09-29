package uk.ac.imperial.icfootballfantasy.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import uk.ac.imperial.icfootballfantasy.R;
import uk.ac.imperial.icfootballfantasy.controller.PlayerLab;
import uk.ac.imperial.icfootballfantasy.model.Player;
import uk.ac.imperial.icfootballfantasy.model.UserData;

/**
 * Created by leszek on 8/13/17.
 */

public class TeamStatsSummaryFragment extends Fragment {

    private static final int ICON_MAX_HEIGHT = 20;
    private static final int ICON_LEFT_MARGIN = 5;
    ArrayList<Player> playersApps;
    ArrayList<Player> playersSubs;
    ArrayList<Player> playersAppsAndSubs;
    ArrayList<Player> playersGoals;
    ArrayList<Player> playersAssists;
    ArrayList<Player> playersOwnGoals;
    ArrayList<Player> playersYellows;
    ArrayList<Player> playersReds;
    Player motm;
    View view;
    private int opponentScore;
    private boolean werePlayersUpdated = false;
    ProgressBar progressBar;
    JSONArray jsonArray = new JSONArray();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.team_stats_summary_fragment, container, false);
        TeamStatsActivity teamStatsActivity = (TeamStatsActivity) getActivity();

        TextView ICScoreTextView = (TextView) view.findViewById(R.id.team_stats_summary_ic_score);
        ICScoreTextView.setText(String.valueOf(teamStatsActivity.getICScore()));
        TextView opponentScoreTextView = (TextView) view.findViewById(R.id.team_stats_summary_opponent_score);
        opponentScore = teamStatsActivity.getOpponentScore();
        opponentScoreTextView.setText(String.valueOf(opponentScore));


        LinearLayout statsLinearLayout = (LinearLayout) view.findViewById(R.id.team_stats_summary_linear_layout);

        playersApps = teamStatsActivity.getPlayersAppearances();
        playersSubs = teamStatsActivity.getPlayersSubs();
        playersAppsAndSubs = teamStatsActivity.getPlayersAppsAndSubs();
        playersGoals = teamStatsActivity.getPlayersGoals();
        playersAssists = teamStatsActivity.getPlayersAssists();
        playersOwnGoals = teamStatsActivity.getPlayersOwnGoals();
        playersYellows = teamStatsActivity.getPlayersYellowCards();
        playersReds = teamStatsActivity.getPlayersRedCards();
        motm = teamStatsActivity.getMotm();

        int count = 0;
        for (int i = 0; i < playersApps.size(); i++) {
            Player player = playersApps.get(i);
            View vi = inflater.inflate(R.layout.team_stats_summary_player_row, null);
            TextView textView = (TextView) vi.findViewById(R.id.summary_player_row_name);
            textView.setText(player.getLastName());
            statsLinearLayout.addView(vi,3);

            populateRows(player, vi);
            count++;
        }

        for (int i = 0; i < playersSubs.size(); i++) {
            Player player = playersSubs.get(i);
            View vi = inflater.inflate(R.layout.team_stats_summary_player_row, null);
            TextView textView = (TextView) vi.findViewById(R.id.summary_player_row_name);
            textView.setText(player.getLastName());
            statsLinearLayout.addView(vi,4 + count);

            populateRows(player, vi);
        }

        final Button saveChangesButton = (Button) view.findViewById(R.id.team_stats_summary_save);
        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!werePlayersUpdated) {
                    updatePlayersInApp();
                    werePlayersUpdated = true;
                }

                saveChangesButton.setText("");
                saveChangesButton.setEnabled(false);
                progressBar = (ProgressBar) view.findViewById(R.id.team_stats_summary_progress);
                progressBar.setVisibility(View.VISIBLE);

                updatePlayersInDatabase();
            }
        });


        return view;
    }

    private void populateRows(Player player, View vi) {
        float pixels =  ICON_MAX_HEIGHT * getContext().getResources().getDisplayMetrics().density;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, (int) pixels);
        float margin =  ICON_LEFT_MARGIN * getContext().getResources().getDisplayMetrics().density;
        params.setMargins((int) margin, 0, 0, 0);

        if (player.getId() == motm.getId()) {
            TextView playerTextView = new TextView(getContext());
            playerTextView.setLayoutParams(params);
            playerTextView.setText("motm");

            LinearLayout playerLinearLayout = (LinearLayout) vi.findViewById(R.id.team_stats_summary_player_linear_layout);
            playerLinearLayout.addView(playerTextView);
        }

        for (int i = 0; i < playersGoals.size(); i++) {
            Player playerGoal = playersGoals.get(i);
            if (player.getId() == playerGoal.getId()) {
                ImageView playerImageView = new ImageView(getContext());
                playerImageView.setLayoutParams(params);
                playerImageView.setAdjustViewBounds(true);
                playerImageView.setImageResource(R.drawable.football);

                LinearLayout playerLinearLayout = (LinearLayout) vi.findViewById(R.id.team_stats_summary_player_linear_layout);
                playerLinearLayout.addView(playerImageView);
            }
        }
        for (int i = 0; i < playersAssists.size(); i++) {
            Player playerAssists = playersAssists.get(i);
            if (player.getId() == playerAssists.getId()) {
                ImageView playerImageView = new ImageView(getContext());
                playerImageView.setLayoutParams(params);
                playerImageView.setAdjustViewBounds(true);
                playerImageView.setImageResource(R.drawable.football_shoe);

                LinearLayout playerLinearLayout = (LinearLayout) vi.findViewById(R.id.team_stats_summary_player_linear_layout);
                playerLinearLayout.addView(playerImageView);
            }
        }

        for (int i = 0; i < playersOwnGoals.size(); i++) {
            Player playerAssists = playersOwnGoals.get(i);
            if (player.getId() == playerAssists.getId()) {
                ImageView playerImageView = new ImageView(getContext());
                playerImageView.setLayoutParams(params);
                playerImageView.setAdjustViewBounds(true);
                playerImageView.setImageResource(R.drawable.football_red);


                LinearLayout playerLinearLayout = (LinearLayout) vi.findViewById(R.id.team_stats_summary_player_linear_layout);
                playerLinearLayout.addView(playerImageView);
            }
        }

        for (int i = 0; i < playersYellows.size(); i++) {
            Player playerYellows = playersYellows.get(i);
            if (player.getId() == playerYellows.getId()) {
                ImageView playerImageView = new ImageView(getContext());
                playerImageView.setLayoutParams(params);
                playerImageView.setAdjustViewBounds(true);
                playerImageView.setImageResource(R.drawable.yellow_card);

                LinearLayout playerLinearLayout = (LinearLayout) vi.findViewById(R.id.team_stats_summary_player_linear_layout);
                playerLinearLayout.addView(playerImageView);
            }
        }

        for (int i = 0; i < playersReds.size(); i++) {
            Player playerReds = playersReds.get(i);
            if (player.getId() == playerReds.getId()) {
                ImageView playerImageView = new ImageView(getContext());
                playerImageView.setLayoutParams(params);
                playerImageView.setAdjustViewBounds(true);
                playerImageView.setImageResource(R.drawable.red_card);

                LinearLayout playerLinearLayout = (LinearLayout) vi.findViewById(R.id.team_stats_summary_player_linear_layout);
                playerLinearLayout.addView(playerImageView);
            }
        }

    }
    private void updatePlayersInApp() {

        PlayerLab playerLab = PlayerLab.get();

        int playerID = motm.getId();
        Player currentPlayer = playerLab.getPlayer(playerID);
        currentPlayer.updatePlayerByMOTM();


        for (Player player: playersApps) {
            playerID = player.getId();
            currentPlayer = playerLab.getPlayer(playerID);
            currentPlayer.updatePlayerByAppearance();
            if (opponentScore == 0) {
                currentPlayer.updatePlayerByCleanSheet();
            }
        }
        for (Player player: playersSubs) {
            playerID = player.getId();
            currentPlayer = playerLab.getPlayer(playerID);
            currentPlayer.updatePlayerBySubAppearance();
            if (opponentScore == 0) {
                currentPlayer.updatePlayerByCleanSheet();
            }
        }
        for (Player player: playersGoals) {
            playerID = player.getId();
            currentPlayer = playerLab.getPlayer(playerID);
            currentPlayer.updatePlayerByGoal();
        }
        for (Player player: playersAssists) {
            playerID = player.getId();
            currentPlayer = playerLab.getPlayer(playerID);
            currentPlayer.updatePlayerByAssist();
        }
        for (Player player: playersOwnGoals) {
            playerID = player.getId();
            currentPlayer = playerLab.getPlayer(playerID);
            currentPlayer.updatePlayerByOwnGoal();
        }
        for (Player player: playersYellows) {
            playerID = player.getId();
            currentPlayer = playerLab.getPlayer(playerID);
            currentPlayer.updatePlayerByYellowCard();
        }
        for (Player player: playersReds) {
            playerID = player.getId();
            currentPlayer = playerLab.getPlayer(playerID);
            currentPlayer.updatePlayerByRedCard();
        }
    }

    private void updatePlayersInDatabase() {
        editPlayersAtDBAsyncTask task = new editPlayersAtDBAsyncTask();
        task.setProgressBar(progressBar);
        task.execute();
    }


    public class editPlayersAtDBAsyncTask extends AsyncTask<Void, Integer, String> {
        ProgressBar bar;

        public void setProgressBar(ProgressBar bar) {
            this.bar = bar;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (this.bar != null) {
                bar.setProgress(values[0]);
            }
        }

        @Override
        protected String doInBackground(Void... params) {
            String message;

            try {
                int playerApssAndSubsSize = playersAppsAndSubs.size();
                for (int i = 0; i < playersAppsAndSubs.size(); i++) {
                    Player player = playersAppsAndSubs.get(i);
                    JSONObject json = new JSONObject();
                    json.put("player_id", player.getId());
                    json.put("apps", player.getAppearances());
                    json.put("sub_apps", player.getSubAppearances());
                    json.put("goals", player.getGoals());
                    json.put("assists", player.getAssists());
                    json.put("clean_sheets", player.getCleanSheets());
                    json.put("motms", player.getMotms());
                    json.put("own_goals", player.getOwnGoals());
                    json.put("yellow_cards", player.getYellowCards());
                    json.put("red_cards", player.getRedCards());
                    json.put("points_week", player.getPointsWeek());

                    publishProgress((int) (i * 100 / (playerApssAndSubsSize+1)));
                    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                    logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://union.ic.ac.uk/acc/football/android_connect/edit_player.php?player_id=" + player.getId()
                                    + "&apps=" + player.getAppearances() + "&sub_apps=" + player.getSubAppearances()
                                    + "&goals=" + player.getGoals() + "&assists=" + player.getAssists() + "&clean_sheets="
                                    + player.getCleanSheets() + "&motms=" + player.getMotms() + "&own_goals=" + player.getOwnGoals()
                                    + "&yellow_cards=" + player.getYellowCards() + "&red_cards=" + player.getRedCards()
                                    + "&points_week=" + player.getPointsWeek())
                            .build();
                    client.newCall(request).execute();

                    UserData userData = UserData.get();
                    client = new OkHttpClient();
                    request = new Request.Builder()
                            .url("https://union.ic.ac.uk/acc/football/android_connect/admin_log.php?user_id=" + userData.getUser_id()
                                    + "&username=" + userData.getUsername() + "&date=" + Calendar.getInstance().getTime() + "&log=" + json.toString())
                            .build();
                    client.newCall(request).execute();
                }

                message = "success";
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
            Toast.makeText(getContext(), message,
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}

