package uk.ac.imperial.icfootballfantasy.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import uk.ac.imperial.icfootballfantasy.R;
import uk.ac.imperial.icfootballfantasy.controller.PlayerLab;
import uk.ac.imperial.icfootballfantasy.model.Team;
import uk.ac.imperial.icfootballfantasy.model.Player;

/**
 * Created by leszek on 6/27/17.
 */

public class PlayerListFragment extends Fragment {
    private RecyclerView mPlayerRecycleView;
    private PlayerAdapter mAdapter;
    Fragment fragment = new PlayerInfoFragment(); //next default fragment

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.player_list_fragment, container, false);

        if (getArguments().getString("next") != null) { //set next fragment
            fragment = new PlayerEditFragment();
        }

        if (getArguments().getBoolean("teamCreate")) { //set next fragment
            fragment = new TeamCreateFragment();
        }

        mPlayerRecycleView = (RecyclerView) view.findViewById(R.id.player_recycler_view);
        mPlayerRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {
        PlayerLab playerLab = PlayerLab.get();
        List<Player> players = playerLab.getPlayersCopy();

        if (getArguments().getString("positionFilter") != null) { //shows only list with filter && avoids duplicates

            Team team = ((TeamCreateActivity) getActivity()).getNewTeam();

            for (int i = 0; i < players.size(); i++) {
                if (!players.get(i).getPosition().equals(getArguments().getString("positionFilter"))) {
                    players.remove(i);
                    i--;
                }
            }

            for (int i = 0; i <= 16; i++) { //avoid duplicates
                if (team.getFullSquadPlayerId(i) != 0) {
                    for (int j = 0; j < players.size(); j++) {
                        if (players.get(j).getId() == team.getFullSquadPlayerId(i)) {
                            players.remove(j);
                            j--;
                        }
                    }
                }
            }
        }

        mAdapter = new PlayerAdapter(players);
        mPlayerRecycleView.setAdapter(mAdapter);
    }

    private class PlayerHolder extends RecyclerView.ViewHolder {
        public TextView mName;
        public TextView mPosition;
        public TextView mTeam;
        public TextView mPrice;
        public TextView mPoints;
        public TextView mApps;
        public TextView mSubs;
        public TextView mGoals;
        public TextView mAssists;
        public TextView mCleanSheets;
        public TextView mMotms;
        public TextView mYellowCards;
        public TextView mRedCards;
        public TextView mOwnGoals;
        public Player mPlayer;


        public PlayerHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Bundle args = new Bundle();
                    args.putInt("playerID",  mPlayer.getId());
                    args.putInt("playerNum",  getArguments().getInt("playerNum"));
                    fragment.setArguments(args);
                    getFragmentManager().beginTransaction().
                            replace(R.id.flContent, fragment).addToBackStack(null).commit();
                }
            });

            mName = (TextView) itemView.findViewById(R.id.list_player_name);
            mPosition = (TextView) itemView.findViewById(R.id.list_player_position);
            mTeam = (TextView) itemView.findViewById(R.id.list_player_team);
            mPrice = (TextView) itemView.findViewById(R.id.list_player_price);
            mPoints = (TextView) itemView.findViewById(R.id.list_player_points);
            mApps = (TextView) itemView.findViewById(R.id.list_player_apps);
            mSubs = (TextView) itemView.findViewById(R.id.list_player_subs);
            mGoals = (TextView) itemView.findViewById(R.id.list_player_goals);
            mAssists = (TextView) itemView.findViewById(R.id.list_player_assists);
            mCleanSheets = (TextView) itemView.findViewById(R.id.list_player_clean_sheets);
            mMotms = (TextView) itemView.findViewById(R.id.list_player_motms);
            mYellowCards = (TextView) itemView.findViewById(R.id.list_player_yellow_cards);
            mRedCards = (TextView) itemView.findViewById(R.id.list_player_red_cards);
            mOwnGoals = (TextView) itemView.findViewById(R.id.list_player_own_goals);
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
            View view = layoutInflater.inflate(R.layout.player_row, parent, false);
            return new PlayerHolder(view);
        }
        @Override
        public void onBindViewHolder(PlayerHolder holder, int position) {
            Player player = mPlayers.get(position);
            holder.mPlayer = player;
            holder.mName.setText(player.getLastName());
            holder.mPosition.setText(player.getPosition());
            holder.mTeam.setText("" + player.getTeam());
            holder.mPrice.setText("£" + player.getPrice() + " Mil");
            holder.mPoints.setText("" + player.getPoints());
            holder.mApps.setText("" + player.getAppearances());
            holder.mSubs.setText("" + player.getSubAppearances());
            holder.mGoals.setText("" + player.getGoals());
            holder.mAssists.setText("" + player.getAssists());
            holder.mCleanSheets.setText("" + player.getCleanSheets());
            holder.mMotms.setText("" + player.getMotms());
            holder.mYellowCards.setText("" + player.getYellowCards());
            holder.mRedCards.setText("" + player.getRedCards());
            holder.mOwnGoals.setText("" + player.getOwnGoals());
        }

        @Override
        public int getItemCount() {
            return mPlayers.size();
        }

    }

}
