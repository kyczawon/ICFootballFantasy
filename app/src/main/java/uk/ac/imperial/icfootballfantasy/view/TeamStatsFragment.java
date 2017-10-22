package uk.ac.imperial.icfootballfantasy.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import uk.ac.imperial.icfootballfantasy.R;
import uk.ac.imperial.icfootballfantasy.controller.OnSwipeTouchListener;
import uk.ac.imperial.icfootballfantasy.controller.PlayerLab;
import uk.ac.imperial.icfootballfantasy.model.Player;
import uk.ac.imperial.icfootballfantasy.model.UserData;

/**
 * Created by leszek on 8/12/17.
 */

public class TeamStatsFragment extends Fragment {
    private RecyclerView mPlayerRecycleView;
    private PlayerAdapter mAdapter;
    private ArrayList<Player> mPlayersSelected = new ArrayList<>();
    private ArrayList<Player> mPlayersNotSelected = new ArrayList<>();
    private List<Player> players;
    private List<Player> players2;
    private String currentStat;
    private int goalsEntered;
    private int numPlayers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.team_stats_fragment, container, false);
        TeamStatsActivity teamStatsActivity = ((TeamStatsActivity)getActivity());

        UserData userData = UserData.get();
        int adminedTeam = userData.getAdminedTeam();

        numPlayers = 0;
        goalsEntered = 0;

        Bundle args = new Bundle();

        currentStat = getArguments().getString("stat");
        TextView statTextView = (TextView) view.findViewById(R.id.team_stats_stat);
        statTextView.setText(currentStat);

        if (currentStat.equals("Appearances")) {
            PlayerLab playerLab = PlayerLab.get();
            players = playerLab.getPlayersCopy();
            players2 = playerLab.getPlayersCopy();
        } else if (currentStat.equals("SubAppearances")) {
            players = teamStatsActivity.getPlayersSubs();
        } else {
            players = teamStatsActivity.getPlayersAppsAndSubs();
        }

        switch (currentStat) {
            case "Appearances":
                for (int i = 0; i < players.size(); i++) {
                    if (players.get(i).getTeam() != adminedTeam) {
                        players.remove(i);
                        i--;
                    } else {
                        players2.remove(i);
                    }
                }
                Collections.sort(players, new Comparator<Player>() {
                    @Override
                    public int compare(Player o1, Player o2) {
                        return o1.getLastName().compareTo(o2.getLastName());
                    }
                });
                Collections.sort(players2, new Comparator<Player>() {
                    @Override
                    public int compare(Player o1, Player o2) {
                        return o1.getLastName().compareTo(o2.getLastName());
                    }
                });
                players.addAll(players2);
                mPlayersNotSelected = new ArrayList<>(players);
                args.putString("stat", "SubAppearances");
                break;
            case "SubAppearances":
                args.putString("stat", "Motm");
                break;
            case "Motm":
                args.putString("stat", "Goals");
                break;
            case "Goals":
                args.putString("stat", "Assists");
                break;
            case "Assists":
                args.putString("stat", "OwnGoals");
                break;
            case "OwnGoals":
                args.putString("stat", "Yellow Cards");
                break;
            case "Yellow Cards":
                args.putString("stat", "Red Cards");
                break;
        }

        final Bundle finalArgs = args;

        mPlayerRecycleView = (RecyclerView) view.findViewById(R.id.team_stats_recycler_view);
        mPlayerRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        final Button nextButton = (Button) view.findViewById(R.id.team_stats_button_next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                nextFragment(finalArgs);
            }
        });

        mPlayerRecycleView.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            public void onSwipeRight() {
                getFragmentManager().popBackStackImmediate();
            }
            public void onSwipeLeft() {
                nextFragment(finalArgs);
            }
        });

        return view;
    }

    private void updateUI() {

        mAdapter = new PlayerAdapter(players);
        mPlayerRecycleView.setAdapter(mAdapter);
    }

    private class PlayerHolder extends RecyclerView.ViewHolder {
        public TextView mName;
        public Player mPlayer;
        public CheckBox mCheckBox;

        public PlayerHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.team_stats_row_player);
            if (isRowView()) {
                mCheckBox = (CheckBox) itemView.findViewById(R.id.team_stats_row_player_checkbox);

                mName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCheckBox.toggle();
                    }
                });
                mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            if (!mPlayersSelected.contains(mPlayer)) {
                                numPlayers++;
                                mPlayersSelected.add(mPlayer);
                                mPlayersNotSelected.remove(mPlayer);
                            }
                        } else {
                            if (!mPlayersNotSelected.contains(mPlayer)) {
                                numPlayers--;
                                mPlayersSelected.remove(mPlayer);
                                mPlayersNotSelected.add(mPlayer);
                            }
                        }
                    }
                });
            } else {
                Button buttonDec = (Button) itemView.findViewById(R.id.team_stats_row_player_increment);
                final EditText editText = (EditText) itemView.findViewById(R.id.team_stats_row_player_number);
                buttonDec.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        int value = Integer.parseInt(editText.getText().toString());
                        goalsEntered++;
                        value++;
                        editText.setText(String.valueOf(value));
                        mPlayersSelected.add(mPlayer);
                    }
                });
                Button buttonInc = (Button) itemView.findViewById(R.id.team_stats_row_player_decrement);
                buttonInc.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        int value = Integer.parseInt(editText.getText().toString());
                        if (value != 0) {
                            mPlayersSelected.remove(mPlayer);
                            goalsEntered--;
                            value--;
                            editText.setText(String.valueOf(value));
                        }
                    }
                });
            }
        }
    }

    private class PlayerAdapter extends RecyclerView.Adapter<PlayerHolder> {
        private List<Player> mPlayers;
        public PlayerAdapter(List<Player> players) {
            mPlayers = players;
        }

        @Override
        public PlayerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view;
            if (isRowView()) {
                view = layoutInflater.inflate(R.layout.team_stats_player_row, parent, false);
            } else {
                view = layoutInflater.inflate(R.layout.team_stats_player_row_buttons, parent, false);
            }
            return new PlayerHolder(view);
        }
        @Override
        public void onBindViewHolder(PlayerHolder holder, int position) {
            holder.mPlayer =  mPlayers.get(position);
            holder.mName.setText(holder.mPlayer.getLastName() + " " + holder.mPlayer.getFirstName());
            if (mPlayersSelected.contains(holder.mPlayer)) {
                holder.mCheckBox.setChecked(true);
            } else {
                holder.mCheckBox.setChecked(false);
            }
        }

        @Override
        public int getItemCount() {
            return mPlayers.size();
        }

    }

    private void nextFragment(Bundle finalArgs){
        TeamStatsActivity teamStatsActivity = ((TeamStatsActivity)getActivity());
        String message = "";
        if (currentStat.equals("Red Cards")) {//launch summary activity
            teamStatsActivity.setPlayersRedCards(mPlayersSelected);
            Fragment fragment = new TeamStatsSummaryFragment();
            getFragmentManager().beginTransaction().
                    replace(R.id.flContent, fragment).addToBackStack(null).commit();
        } else { //save data and launch show next stat
            switch (currentStat) {
                case "Appearances":
                    if (numPlayers > 11) {
                        message = "More than 11 players can't have played more than 60 minutes";
                    } else {
                        teamStatsActivity.setPlayersAppearances(mPlayersSelected);
                        teamStatsActivity.setPlayersSubs(mPlayersNotSelected);
                    }
                    break;
                case "SubAppearances":
                    teamStatsActivity.setPlayersSubs(mPlayersSelected);
                    ArrayList<Player> playersApps = teamStatsActivity.getPlayersAppearances();
                    playersApps.removeAll(mPlayersSelected);
                    ArrayList<Player> playersAppsAndSubs = new ArrayList<>();
                    playersAppsAndSubs.addAll(mPlayersSelected);
                    playersAppsAndSubs.addAll(playersApps);
                    teamStatsActivity.setPlayersAppsAndSubs(playersAppsAndSubs);
                    break;
                case "Motm":
                    if (numPlayers > 1) {
                        message = "Only one player can be MOTM";
                    } else {
                        teamStatsActivity.setMotm(mPlayersSelected.get(0));
                    }
                    break;
                case "Goals":
                    if (goalsEntered > teamStatsActivity.getICScore()) {
                        message = "There can't have been more goals scored than scoreline";
                    } else {
                        teamStatsActivity.setPlayersGoals(mPlayersSelected);
                    }
                    break;
                case "Assists":
                    if (goalsEntered > teamStatsActivity.getICScore()) {
                        message = "There can't have been more assists scored than scoreline";
                    } else {
                        teamStatsActivity.setPlayersAssists(mPlayersSelected);
                    }
                    break;
                case "OwnGoals":
                    if (goalsEntered > teamStatsActivity.getOpponentScore()) {
                        message = "There can't have been more own goals scored than scoreline";
                    } else {
                        teamStatsActivity.setPlayersOwnGoals(mPlayersSelected);
                    }
                    break;
                case "Yellow Cards":
                    teamStatsActivity.setPlayersYellowCards(mPlayersSelected);
                    break;
            }
            if (message.equals("")) {
                Fragment fragment = new TeamStatsFragment();
                fragment.setArguments(finalArgs);
                getFragmentManager().beginTransaction().
                        replace(R.id.flContent, fragment).addToBackStack(null).commit();
            } else {
                Toast.makeText(getContext(), message,
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isRowView() {
        return (currentStat.equals("Appearances") || currentStat.equals("SubAppearances")
                || currentStat.equals("Yellow Cards") || currentStat.equals("Red Cards") || currentStat.equals("Motm"));
    }

}
