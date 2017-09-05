package uk.ac.imperial.icfootballfantasy.view;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import uk.ac.imperial.icfootballfantasy.model.Team;
import uk.ac.imperial.icfootballfantasy.model.UserData;

/**
 * Created by leszek on 7/25/17.
 */

public class TeamDisplayFragment extends Fragment {
    View view;
    Team team;
    PlayerLab playerLab;
    int points_week = 0;
    boolean teamEditable = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        if (arguments != null && arguments.getBoolean("teamList")) {
            team = ((MainActivity) getActivity()).getCurrentTeamShown();
            teamEditable = false;
            view = inflater.inflate(R.layout.team_display_no_button_fragment, container, false);
        } else {
            UserData userData = UserData.get();
            team = userData.getTeam();
            view = inflater.inflate(R.layout.team_display_fragment, container, false);
        }

        playerLab = PlayerLab.get();

        buildTeam();
        buildSubs();

        TextView pointsTextView = (TextView) view.findViewById(R.id.total_points);
        pointsTextView.setText(String.valueOf(team.getPoints() + points_week));
        TextView weekPointsTextView = (TextView) view.findViewById(R.id.points_week);
        weekPointsTextView.setText(String.valueOf(points_week));

        if (teamEditable) {
            final Button submitButton = (Button) view.findViewById(R.id.team_display_save_changes);
            submitButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    submitButton.setText("");
                    submitButton.setEnabled(false);
                    ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.team_display_progress);
                    progressBar.setVisibility(View.VISIBLE);

                    editTeamAtDB();
                }
            });
        }

        return view;
    }

    private void buildSubs() {
        for (int i = 1; i < 6; i++) {
            final Player subPlayer = playerLab.getPlayer(team.getSubId(i));
            points_week += subPlayer.getPointsWeek();

            String playerNameID = "sub" + i + "_name";
            int resID = getResources().getIdentifier(playerNameID, "id", getActivity().getPackageName());
            TextView subName = (TextView) view.findViewById(resID);
            subName.setText(subPlayer.getLastName());

            String playerScoreID = "sub" + i + "_score";
            int resScoreID = getResources().getIdentifier(playerScoreID, "id", getActivity().getPackageName());
            TextView subScore = (TextView) view.findViewById(resScoreID);
            subScore.setText(String.valueOf(subPlayer.getPointsWeek()));

            String playerImageID = "sub" + i + "_image";
            int resPlayerImageID = getResources().getIdentifier(playerImageID, "id", getActivity().getPackageName());
            final ImageView subImage = (ImageView) view.findViewById(resPlayerImageID);
            subImage.setImageResource(subPlayer.getImage());

            String playerID = "sub" + i;
            int resPlayerID = getResources().getIdentifier(playerID, "id", getActivity().getPackageName());
            final LinearLayout sub = (LinearLayout) view.findViewById(resPlayerID);
            if (teamEditable) {
                sub.setTag(i);
                sub.setOnLongClickListener(myOnLongClickListener);
            }

        }
    }

    private void buildTeam() {
        TextView goalName = (TextView) view.findViewById(R.id.goal_name);
        Player goal = playerLab.getPlayer(team.getGoalId());
        goalName.setText(goal.getLastName());
        TextView goalScore = (TextView) view.findViewById(R.id.goal_score);
        goalScore.setText(String.valueOf(goal.getPointsWeek()));
        LinearLayout goalLinearLayout = (LinearLayout) view.findViewById(R.id.goal);
        points_week += goal.getPointsWeek();

        if (teamEditable) {
            goalLinearLayout.setTag(0);
            goalLinearLayout.setOnDragListener(new MyDragListener());
        }

        int defNum = team.getDefNum();
        int midNum = team.getMidNum();

        for (int i = 1; i < 11; i++) {
            Player player;
            player = playerLab.getPlayer(team.getStartPlayerId(i));

            points_week += player.getPointsWeek();

            LinearLayout linearLayoutLine;
            if (i <= defNum) {
                linearLayoutLine = (LinearLayout) view.findViewById(R.id.main_def_linear_layout);
            } else if (i <= (midNum+defNum)) {
                linearLayoutLine = (LinearLayout) view.findViewById(R.id.main_mid_linear_layout);
            } else {
                linearLayoutLine = (LinearLayout) view.findViewById(R.id.main_fwd_linear_layout);
            }

            LinearLayout playerLinearLayout = new LinearLayout(getContext());
            playerLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
            playerLinearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayoutLine.addView(playerLinearLayout);

            ImageView playerImageView = new ImageView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1.0f);
            params.gravity = Gravity.CENTER;
            playerImageView.setLayoutParams(params);
            playerImageView.setImageResource(player.getImage());
            playerLinearLayout.addView(playerImageView);

            //set up the layout params for two text views. Don't know why needed as text_view_template contains exactly the same thing...
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params2.gravity = Gravity.CENTER;

            TextView playerNameTextView = (TextView) getActivity().getLayoutInflater().inflate(R.layout.text_view_template, null);
            playerNameTextView.setText(String.valueOf(player.getLastName()));
            playerNameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            playerNameTextView.setLayoutParams(params2);
            playerLinearLayout.addView(playerNameTextView);

            TextView playerPointsTextView = (TextView) getActivity().getLayoutInflater().inflate(R.layout.text_view_template, null);
            playerPointsTextView.setText(String.valueOf(player.getPointsWeek()));
            playerPointsTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            playerPointsTextView.setLayoutParams(params2);
            playerLinearLayout.addView(playerPointsTextView);

            if (teamEditable) {
                playerLinearLayout.setTag(i);
                playerLinearLayout.setOnDragListener(new MyDragListener());
            }
        }
    }

    private class MyDragListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();

            LinearLayout target = (LinearLayout) v;
            LinearLayout dragged = (LinearLayout) event.getLocalState();

            int playerDraggedNum = (Integer) dragged.getTag();
            int playerDraggedID = team.getSubId(playerDraggedNum);
            Player playerDragged = playerLab.getPlayer(playerDraggedID);
            int playerTargetID;

            int playerTargetNum = (Integer) target.getTag();
            if (playerTargetNum == 0) {
                playerTargetID = team.getGoalId();
            } else {
                playerTargetID = team.getStartPlayerId(playerTargetNum);
            }
            Player playerTarget = playerLab.getPlayer(playerTargetID);

            String playerDraggedPos = playerDragged.getPosition();
            String playerTargetPos = playerTarget.getPosition();

            LinearLayout defLinearLayout = ((LinearLayout) view.findViewById(R.id.main_def_linear_layout));
            LinearLayout midLinearLayout  = ((LinearLayout) view.findViewById(R.id.main_mid_linear_layout));
            LinearLayout fwdLinearLayout  = ((LinearLayout) view.findViewById(R.id.main_fwd_linear_layout));

            int defNum = defLinearLayout.getChildCount();
            int midNum = midLinearLayout.getChildCount();
            int fwdNum = fwdLinearLayout.getChildCount();

            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    if ((playerTargetPos.equals("GK") && !playerDraggedPos.equals("GK")) ||
                            (playerDraggedPos.equals("GK") && !playerTargetPos.equals("GK")))
                    {
                        return false;
                    }
                    if (playerDraggedPos.equals("GK") && playerTargetPos.equals("GK"))
                    {
                        target.setBackgroundColor(Color.parseColor("#4FFF0000"));
                    } else {

                        if (!playerDraggedPos.equals(playerTargetPos) && ((//if players are different and row overfilled disable other rows
                                (defNum > 4 &&  playerDraggedPos.equals("DEF")) ||
                                        (midNum > 4 &&  playerDraggedPos.equals("MID"))
                                        || (fwdNum > 2 &&  playerDraggedPos.equals("FWD")))
                                || ((defNum < 4 && playerTargetPos.equals("DEF")) || //or players are different and rows can't be smaller
                                (midNum < 4 && playerTargetPos.equals("MID")) ||
                                (fwdNum < 3 && playerTargetPos.equals("FWD")))
                                ))
                        {
                            return false;
                        } else {
                            target.setBackgroundColor(Color.parseColor("#4FFF0000"));
                        }
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    target.setBackgroundColor(Color.parseColor("#4F0000FF"));
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    target.setBackgroundColor(Color.parseColor("#4FFF0000"));
                    break;
                case DragEvent.ACTION_DROP:
                    if (!playerDraggedPos.equals(playerTargetPos)) {
                        int insert = 0;
                        switch (playerDraggedPos) {
                            case "DEF":
                                defNum++;
                                insert = defNum;
                                break;
                            case "MID":
                                midNum++;
                                insert = defNum + midNum;
                                break;
                            case "FWD":
                                fwdNum++;
                                insert = 11;
                                break;
                        }
                        switch (playerTargetPos) {
                            case "DEF":
                                defNum--;
                                break;
                            case "MID":
                                midNum--;
                                break;
                            case "FWD":
                                fwdNum--;
                                break;
                        }
                        team.setFormation(defNum, midNum, fwdNum);
                        team.shiftPlayers(insert, playerDraggedID, playerTargetNum-1);//playerTargetNum-1 because
                        defLinearLayout.removeAllViews();
                        midLinearLayout.removeAllViews();
                        fwdLinearLayout.removeAllViews();
                        buildTeam();
                        team.setSubPlayerId(playerDraggedNum, playerTargetID);

                        ImageView draggedImage = (ImageView) dragged.getChildAt(0);
                        TextView draggedName = (TextView) dragged.getChildAt(1);
                        TextView draggedPoints = (TextView) dragged.getChildAt(2);
                        draggedImage.setImageResource(playerTarget.getImage());
                        draggedName.setText(playerTarget.getLastName());
                        draggedPoints.setText(String.valueOf(playerTarget.getPointsWeek()));

                } else {


                    ImageView targetImage = (ImageView) target.getChildAt(0);
                    ImageView draggedImage = (ImageView) dragged.getChildAt(0);
                    TextView targetName = (TextView) target.getChildAt(1);
                    TextView draggedName = (TextView) dragged.getChildAt(1);
                    TextView targetPoints = (TextView) target.getChildAt(2);
                    TextView draggedPoints = (TextView) dragged.getChildAt(2);

                    targetImage.setImageResource(playerDragged.getImage());
                    draggedImage.setImageResource(playerTarget.getImage());
                    targetName.setText(playerDragged.getLastName());
                    draggedName.setText(playerTarget.getLastName());
                    targetPoints.setText(String.valueOf(playerDragged.getPointsWeek()));
                    draggedPoints.setText(String.valueOf(playerTarget.getPointsWeek()));

                    team.setSubPlayerId(playerDraggedNum, playerTargetID);
                    team.setStartPlayerId(playerTargetNum, playerDraggedID);
                }

                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    target.setBackgroundColor(Color.parseColor("#00000000"));
                    dragged.setVisibility(View.VISIBLE);
                    break;
            }
            return true;
        }
    }

    View.OnLongClickListener myOnLongClickListener = new View.OnLongClickListener(){
        @Override
        public boolean onLongClick(View v) {
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                    v);

            // Starts the drag

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                v.startDragAndDrop(null,  // the data to be dragged
                        shadowBuilder,  // the drag shadow builder
                        v,      // the view that is dragged
                        0);
            } else {
                v.startDrag(null,  // the data to be dragged
                        shadowBuilder,  // the drag shadow builder
                        v,      // the view that is dragged
                        0);
            }
            v.setVisibility(View.INVISIBLE);
            return false;
        }
    };

    private void editTeamAtDB(Integer... input) {

        AsyncTask<Integer, Void, String> asyncTask = new AsyncTask<Integer, Void, String>() {
            @Override
            protected String doInBackground(Integer... input) {
                String message;

                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://union.ic.ac.uk/acc/football/android_connect/edit_team.php?team_id=" + team.getTeam_id()
                                + "&def_num=" + team.getDefNum() + "&mid_num=" + team.getMidNum() + "&fwd_num="+ team.getFwdNum()
                                + "&goal=" + team.getGoalId() + "&player1=" + team.getPlayer1Id() + "&player2=" + team.getPlayer2Id()
                                + "&player3=" + team.getPlayer3Id() + "&player4=" + team.getPlayer4Id() + "&player5=" + team.getPlayer5Id()
                                + "&player6=" + team.getPlayer6Id() + "&player7=" + team.getPlayer7Id() + "&player8=" + team.getPlayer8Id()
                                + "&player9=" + team.getPlayer9Id() + "&player10=" + team.getPlayer10Id() + "&sub_goal=" + team.getSubGoalId()
                                + "&sub1=" + team.getSub1Id() + "&sub2=" + team.getSub2Id() + "&sub3=" + team.getSub3Id()
                                + "&sub4=" + team.getSub4Id())
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
                Button registerButton = (Button) view.findViewById(R.id.team_display_save_changes);
                registerButton.setText("Save Changes by 01/10/17 23:59");
                registerButton.setEnabled(true);
                ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.team_display_progress);
                progressBar.setVisibility(View.GONE);
            }
        };

        asyncTask.execute(input);
    }
}
