package uk.ac.imperial.icfootballfantasy.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import uk.ac.imperial.icfootballfantasy.R;
import uk.ac.imperial.icfootballfantasy.controller.PlayerLab;
import uk.ac.imperial.icfootballfantasy.model.Player;
import uk.ac.imperial.icfootballfantasy.model.Team;

import static uk.ac.imperial.icfootballfantasy.R.id.players_list_fresher_layout;
import static uk.ac.imperial.icfootballfantasy.R.id.players_list_name_checkbox;
import static uk.ac.imperial.icfootballfantasy.R.id.players_list_name_layout;

/**
 * Created by leszek on 6/27/17.
 */

public class PlayerListFragment extends Fragment {
    private RecyclerView mPlayerRecycleView;
    private RecyclerView mPlayerRecycleViewName;
    private PlayerAdapter mAdapter;
    private PlayerAdapterName mAdapterName;
    static boolean rvName = false;
    Fragment fragment = new PlayerInfoFragment(); //next default fragment
    View view;
    List<Player> players;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.player_list_fragment, container, false);

        if (getArguments().getString("next") != null) { //set next fragment
            fragment = new PlayerEditFragment();
        }

        if (getArguments().getBoolean("teamCreate")) { //set next fragment
            fragment = new TeamCreateFragment();
        }

        mPlayerRecycleView = (RecyclerView) view.findViewById(R.id.player_recycler_view);
        mPlayerRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mPlayerRecycleViewName = (RecyclerView) view.findViewById(R.id.player_name_recycler_view);
        mPlayerRecycleViewName.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        view.findViewById(players_list_name_layout).setOnClickListener(mOnClickListener);

        LinearLayout headers = (LinearLayout) view.findViewById(R.id.players_list_headers);
        for (int i = 0; i < headers.getChildCount(); i++) {
            RelativeLayout header = (RelativeLayout) headers.getChildAt(i);
            header.setOnClickListener(mOnClickListener);
        }

        return view;
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RelativeLayout relV = (RelativeLayout) v;
            hideAllCheckboxes();
            CheckBox checkBox = (CheckBox) relV.getChildAt(1);
            checkBox.toggle();
            checkBox.setVisibility(View.VISIBLE);
            switch(v.getId()) {
                case players_list_name_layout:
                    if (checkBox.isChecked()) {
                        Collections.sort(players, new Comparator<Player>() {
                            @Override
                            public int compare(Player o1, Player o2) {
                                return o1.getLastName().compareTo(o2.getLastName());
                            }
                        });
                    } else {
                        Collections.sort(players, new Comparator<Player>() {
                            @Override
                            public int compare(Player o1, Player o2) {
                                return o2.getLastName().compareTo(o1.getLastName());
                            }
                        });
                    }
                    break;
                case R.id.players_list_position_layout:
                    if (checkBox.isChecked()) {
                        Collections.sort(players, new Comparator<Player>() {
                            @Override
                            public int compare(Player o1, Player o2) {
                                return o1.getPosition().compareTo(o2.getPosition());
                            }
                        });
                    } else {
                        Collections.sort(players, new Comparator<Player>() {
                            @Override
                            public int compare(Player o1, Player o2) {
                                return o2.getPosition().compareTo(o1.getPosition());
                            }
                        });
                    }
                    break;
                case R.id.players_list_price_layout:
                    if (checkBox.isChecked()) {
                        Collections.sort(players, new Comparator<Player>() {
                            @Override
                            public int compare(Player o1, Player o2) {
                                return Double.compare(o1.getPrice(), o2.getPrice());
                            }
                        });
                    } else {
                        Collections.sort(players, new Comparator<Player>() {
                            @Override
                            public int compare(Player o1, Player o2) {
                                return Double.compare(o2.getPrice(), o1.getPrice());
                            }
                        });
                    }
                    break;
                case R.id.players_list_team_layout:
                    if (checkBox.isChecked()) {
                        Collections.sort(players, new Comparator<Player>() {
                            @Override
                            public int compare(Player o1, Player o2) {
                                return Double.compare(o1.getTeam(), o2.getTeam());
                            }
                        });
                    } else {
                        Collections.sort(players, new Comparator<Player>() {
                            @Override
                            public int compare(Player o1, Player o2) {
                                return Double.compare(o2.getTeam(), o1.getTeam());
                            }
                        });
                    }
                    break;
                case players_list_fresher_layout:
                    if (checkBox.isChecked()) {
                        Collections.sort(players, new Comparator<Player>() {
                            @Override
                            public int compare(Player o1, Player o2) {
                                int b1 = o1.isFresher() ? 1 : 0;
                                int b2 = o2.isFresher() ? 1 : 0;

                                return b2 - b1;
                            }
                        });
                    } else {
                        Collections.sort(players, new Comparator<Player>() {
                            @Override
                            public int compare(Player o1, Player o2) {
                                int b1 = o2.isFresher() ? 1 : 0;
                                int b2 = o1.isFresher() ? 1 : 0;

                                return b2 - b1;
                            }
                        });
                    }
                    break;
                case R.id.players_list_points_layout:
                    if (checkBox.isChecked()) {
                        Collections.sort(players, new Comparator<Player>() {
                            @Override
                            public int compare(Player o1, Player o2) {
                                return Double.compare(o1.getPoints(), o2.getPoints());
                            }
                        });
                    } else {
                        Collections.sort(players, new Comparator<Player>() {
                            @Override
                            public int compare(Player o1, Player o2) {
                                return Double.compare(o2.getPoints(), o1.getPoints());
                            }
                        });
                    }
                    break;
                case R.id.players_list_apps_layout:
                    if (checkBox.isChecked()) {
                        Collections.sort(players, new Comparator<Player>() {
                            @Override
                            public int compare(Player o1, Player o2) {
                                return Double.compare(o1.getAppearances(), o2.getAppearances());
                            }
                        });
                    } else {
                        Collections.sort(players, new Comparator<Player>() {
                            @Override
                            public int compare(Player o1, Player o2) {
                                return Double.compare(o2.getAppearances(), o1.getAppearances());
                            }
                        });
                    }
                    break;
                case R.id.players_list_subs_layout:
                    if (checkBox.isChecked()) {
                        Collections.sort(players, new Comparator<Player>() {
                            @Override
                            public int compare(Player o1, Player o2) {
                                return Double.compare(o1.getSubAppearances(), o2.getSubAppearances());
                            }
                        });
                    } else {
                        Collections.sort(players, new Comparator<Player>() {
                            @Override
                            public int compare(Player o1, Player o2) {
                                return Double.compare(o2.getSubAppearances(), o1.getSubAppearances());
                            }
                        });
                    }
                    break;
                case R.id.players_list_goals_layout:
                    if (checkBox.isChecked()) {
                        Collections.sort(players, new Comparator<Player>() {
                            @Override
                            public int compare(Player o1, Player o2) {
                                return Double.compare(o1.getGoals(), o2.getGoals());
                            }
                        });
                    } else {
                        Collections.sort(players, new Comparator<Player>() {
                            @Override
                            public int compare(Player o1, Player o2) {
                                return Double.compare(o2.getGoals(), o1.getGoals());
                            }
                        });
                    }
                    break;
                case R.id.players_list_assists_layout:
                    if (checkBox.isChecked()) {
                        Collections.sort(players, new Comparator<Player>() {
                            @Override
                            public int compare(Player o1, Player o2) {
                                return Double.compare(o1.getAssists(), o2.getAssists());
                            }
                        });
                    } else {
                        Collections.sort(players, new Comparator<Player>() {
                            @Override
                            public int compare(Player o1, Player o2) {
                                return Double.compare(o2.getAssists(), o1.getAssists());
                            }
                        });
                    }
                    break;
                case R.id.players_list_clean_sheets_layout:
                    if (checkBox.isChecked()) {
                        Collections.sort(players, new Comparator<Player>() {
                            @Override
                            public int compare(Player o1, Player o2) {
                                return Double.compare(o1.getCleanSheets(), o2.getCleanSheets());
                            }
                        });
                    } else {
                        Collections.sort(players, new Comparator<Player>() {
                            @Override
                            public int compare(Player o1, Player o2) {
                                return Double.compare(o2.getCleanSheets(), o1.getCleanSheets());
                            }
                        });
                    }
                    break;
                case R.id.players_list_motms_layout:
                    if (checkBox.isChecked()) {
                        Collections.sort(players, new Comparator<Player>() {
                            @Override
                            public int compare(Player o1, Player o2) {
                                return Double.compare(o1.getMotms(), o2.getMotms());
                            }
                        });
                    } else {
                        Collections.sort(players, new Comparator<Player>() {
                            @Override
                            public int compare(Player o1, Player o2) {
                                return Double.compare(o2.getMotms(), o1.getMotms());
                            }
                        });
                    }
                    break;
                case R.id.players_list_yellows_layout:
                    if (checkBox.isChecked()) {
                        Collections.sort(players, new Comparator<Player>() {
                            @Override
                            public int compare(Player o1, Player o2) {
                                return Double.compare(o1.getYellowCards(), o2.getYellowCards());
                            }
                        });
                    } else {
                        Collections.sort(players, new Comparator<Player>() {
                            @Override
                            public int compare(Player o1, Player o2) {
                                return Double.compare(o2.getYellowCards(), o1.getYellowCards());
                            }
                        });
                    }
                    break;
                case R.id.players_list_reds_layout:
                    if (checkBox.isChecked()) {
                        Collections.sort(players, new Comparator<Player>() {
                            @Override
                            public int compare(Player o1, Player o2) {
                                return Double.compare(o1.getRedCards(), o2.getRedCards());
                            }
                        });
                    } else {
                        Collections.sort(players, new Comparator<Player>() {
                            @Override
                            public int compare(Player o1, Player o2) {
                                return Double.compare(o2.getRedCards(), o1.getRedCards());
                            }
                        });
                    }
                    break;
                case R.id.players_list_own_goals_layout:
                    if (checkBox.isChecked()) {
                        Collections.sort(players, new Comparator<Player>() {
                            @Override
                            public int compare(Player o1, Player o2) {
                                return Double.compare(o1.getOwnGoals(), o2.getOwnGoals());
                            }
                        });
                    } else {
                        Collections.sort(players, new Comparator<Player>() {
                            @Override
                            public int compare(Player o1, Player o2) {
                                return Double.compare(o2.getOwnGoals(), o1.getOwnGoals());
                            }
                        });
                    }
                    break;
            }
            if (checkBox.isChecked()) {
                checkBox.setButtonDrawable(R.drawable.ic_keyboard_arrow_up);
            } else {
                checkBox.setButtonDrawable(R.drawable.ic_keyboard_arrow_down);
            }
            mAdapter.notifyDataSetChanged();
            mAdapterName.notifyDataSetChanged();
        }
    };
    private void updateUI() {
        PlayerLab playerLab = PlayerLab.get();
        players = playerLab.getPlayersCopy();

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

        mAdapterName = new PlayerAdapterName(players);
        mPlayerRecycleViewName.setAdapter(mAdapterName);

        mAdapter = new PlayerAdapter(players);
        mPlayerRecycleView.setAdapter(mAdapter);

        mPlayerRecycleViewName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                PlayerListFragment.rvName = true;
                return false;
            }
        });

        mPlayerRecycleView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                PlayerListFragment.rvName = false;
                return false;
            }
        });

        mPlayerRecycleViewName.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (rvName) {
                    mPlayerRecycleView.scrollBy(dx,dy);
                }
            }
        });

        mPlayerRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!rvName) {
                    mPlayerRecycleViewName.scrollBy(dx,dy);
                }
            }
        });
    }

    private class PlayerHolder extends RecyclerView.ViewHolder {
        public TextView mPosition;
        public TextView mTeam;
        public TextView mPrice;
        public TextView mFresher;
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
                    args.putString("teamName",  getArguments().getString("teamName"));
                    fragment.setArguments(args);
                    getFragmentManager().beginTransaction().
                            replace(R.id.flContent, fragment).addToBackStack(null).commit();
                }
            });

            mPosition = (TextView) itemView.findViewById(R.id.list_player_position);
            mTeam = (TextView) itemView.findViewById(R.id.list_player_team);
            mPrice = (TextView) itemView.findViewById(R.id.list_player_price);
            mFresher = (TextView) itemView.findViewById(R.id.list_player_fresher);
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
            holder.mPosition.setText(player.getPosition());
            holder.mTeam.setText(String.valueOf(player.getTeam()));
            holder.mFresher.setText(String.valueOf(player.isFresher()));
            holder.mPrice.setText("£" + player.getPrice() + " Mil");
            holder.mPoints.setText(String.valueOf(player.getPoints()));
            holder.mApps.setText(String.valueOf(player.getAppearances()));
            holder.mSubs.setText(String.valueOf(player.getSubAppearances()));
            holder.mGoals.setText(String.valueOf(player.getGoals()));
            holder.mAssists.setText(String.valueOf(player.getAssists()));
            holder.mCleanSheets.setText(String.valueOf(player.getCleanSheets()));
            holder.mMotms.setText(String.valueOf(player.getMotms()));
            holder.mYellowCards.setText(String.valueOf(player.getYellowCards()));
            holder.mRedCards.setText(String.valueOf(player.getRedCards()));
            holder.mOwnGoals.setText(String.valueOf(player.getOwnGoals()));
        }

        @Override
        public int getItemCount() {
            return mPlayers.size();
        }

    }

    private class PlayerHolderName extends RecyclerView.ViewHolder {
        public TextView mName;
        public Player mPlayer;

        public PlayerHolderName (View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Bundle args = new Bundle();
                    args.putInt("playerID",  mPlayer.getId());
                    args.putInt("playerNum",  getArguments().getInt("playerNum"));
                    args.putString("teamName",  getArguments().getString("teamName"));
                    fragment.setArguments(args);
                    getFragmentManager().beginTransaction().
                            replace(R.id.flContent, fragment).addToBackStack(null).commit();
                }
            });

            mName = (TextView) itemView.findViewById(R.id.list_player_name);
        }
    }

    private class PlayerAdapterName extends RecyclerView.Adapter<PlayerHolderName> {
        private List<Player> mPlayers;
        public PlayerAdapterName(List<Player> players) {
            mPlayers = players;
        }

        @Override
        public PlayerHolderName onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.player_name, parent, false);
            return new PlayerHolderName(view);
        }
        @Override
        public void onBindViewHolder(PlayerHolderName holder, int position) {
            Player player = mPlayers.get(position);
            holder.mPlayer = player;
            holder.mName.setText(player.getLastName());
        }

        @Override
        public int getItemCount() {
            return mPlayers.size();
        }

    }

    private void hideAllCheckboxes() {
        view.findViewById(players_list_name_checkbox).setVisibility(View.GONE);
        LinearLayout headers = (LinearLayout) view.findViewById(R.id.players_list_headers);
        for (int i = 0; i < headers.getChildCount(); i++) {
            RelativeLayout header = (RelativeLayout) headers.getChildAt(i);
            for (int j = 0; j < header.getChildCount(); j++) {
                View v = header.getChildAt(j);
                if (v instanceof CheckBox) {
                    v.setVisibility(View.GONE);
                }
            }
        }

    }

}
