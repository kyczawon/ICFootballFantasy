package uk.ac.imperial.icfootballfantasy.view;

/**
 * Created by leszek on 7/27/17.
 */

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import uk.ac.imperial.icfootballfantasy.R;
import uk.ac.imperial.icfootballfantasy.model.Team;

/**
 * Created by leszek on 6/27/17.
 */

public class TeamListFragment extends Fragment {
    private RecyclerView mTeamRecycleView;
    private TeamAdapter mAdapter;
    private LinearLayoutManager linearLayout;
    private List<Team> teams;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.team_list_fragment, container, false);


        teams = new ArrayList<>();
        mTeamRecycleView = (RecyclerView) view.findViewById(R.id.team_recycler_view);
        linearLayout = new LinearLayoutManager(getContext());
        mTeamRecycleView.setLayoutManager(linearLayout);
        getTeamsFromDB(0);

        updateUI();

        return view;
    }

    private void updateUI() {

        mAdapter = new TeamAdapter(teams);
        mTeamRecycleView.setAdapter(mAdapter);

        mTeamRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (linearLayout.findLastCompletelyVisibleItemPosition() == teams.size() - 1) {
                    getTeamsFromDB(teams.get(teams.size() - 1).getTeam_id());
                }

            }
        });
    }

    private class TeamHolder extends RecyclerView.ViewHolder {
        public TextView mName;
        public TextView mOwner;
        public TextView mPrice;
        public TextView mPoints;
        public Team mTeam;


        public TeamHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Fragment fragment = new TeamDisplayFragment();
                    Bundle args = new Bundle();
                    args.putBoolean("teamList", true);
                    fragment.setArguments(args);
                    ((MainActivity) getActivity()).setCurrentTeamShown(mTeam);
                    fragment.setArguments(args);
                    getFragmentManager().beginTransaction().
                            replace(R.id.flContent, fragment).addToBackStack(null).commit();
                }
            });

            mName = (TextView) itemView.findViewById(R.id.list_team_name);
            mOwner = (TextView) itemView.findViewById(R.id.list_team_owner);
            mPrice = (TextView) itemView.findViewById(R.id.list_team_price);
            mPoints = (TextView) itemView.findViewById(R.id.list_team_points);
        }
    }

    private class TeamAdapter extends RecyclerView.Adapter<TeamHolder> {
        private List<Team> mTeams;
        public TeamAdapter(List<Team> teams) {
            mTeams = teams;
        }

        @Override
        public TeamHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.team_row, parent, false);
            return new TeamHolder(view);
        }
        @Override
        public void onBindViewHolder(TeamHolder holder, int position) {
            Team team = mTeams.get(position);
            holder.mTeam = team;
            holder.mName.setText(team.getName());
            holder.mOwner.setText(team.getOwner());
            holder.mPrice.setText("£" + team.getPrice() + " Mil");
            holder.mPoints.setText("" + team.getPoints());
        }

        @Override
        public int getItemCount() {
            return mTeams.size();
        }

    }

    private void getTeamsFromDB(int id) {

        AsyncTask<Integer, Void, Void> asyncTask = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... teamIds) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://union.ic.ac.uk/acc/football/android_connect/teams.php?id=" + teamIds[0])
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);
                        Team team = new Team(object.getInt("team_id"), object.getString("name"),
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

                        teams.add(team);
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                mAdapter.notifyDataSetChanged();
            }
        };

        asyncTask.execute(id);
    }

}
