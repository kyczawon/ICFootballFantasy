package uk.ac.imperial.icfootballfantasy.controller;

import java.util.List;

import uk.ac.imperial.icfootballfantasy.model.Team;

/**
 * Created by leszek on 7/20/17.
 */

public class TeamLab {
    private static TeamLab sTeamLab;
    private List<Team> mTeams;

    public static TeamLab get() {
        if (sTeamLab == null) {
            sTeamLab = new TeamLab();
        }
        return sTeamLab;
    }

    private TeamLab() {
        //getTeamsFromDB();
    }

    public void addTeam(Team team) {
        mTeams.add(team);
    }

    public List<Team> getTeams() {
        return mTeams;
    }

    public Team getTeam(int id) {
        for (Team team : mTeams) {
            if (team.getTeam_id() == id) {
                return team;
            }
        }
        return null;
    }

    public Team getTeam(String teamName) {
        for (Team team : mTeams) {
            if (team.getName().equals(teamName)) {
                return team;
            }
        }
        return null;
    }
/*
    private void getTeamsFromDB() {

        AsyncTask<Integer, Void, Void> asyncTask = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected void onPreExecute() {
                mTeams = new ArrayList<>();
            }
            @Override
            protected Void doInBackground(Integer... team_ids) {

                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://union.ic.ac.uk/acc/football/android_connect/teams.php")
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String jsonArray = response.body().string();
                    if (!jsonArray.equals("null")) {
                        JSONArray array = new JSONArray(jsonArray);

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);

                            Team team = new Team(object.getInt("team_id"), object.getString("name"),
                                    object.getString("owner"), object.getDouble("price"),
                                    object.getInt("points"), object.getInt("points_week"),
                                    object.getInt("def_num"), object.getInt("mid_num"),
                                    object.getInt("fwd_num"), object.getInt("goal1"),
                                    object.getInt("player1"), object.getInt("player2"),
                                    object.getInt("player3"), object.getInt("player4"),
                                    object.getInt("player5"), object.getInt("player6"),
                                    object.getInt("player7"), object.getInt("player8"),
                                    object.getInt("player9"), object.getInt("player10"),
                                    object.getInt("sub_goal"), object.getInt("sub1"),
                                    object.getInt("sub2"), object.getInt("sub3"),
                                    object.getInt("sub4"));
                            mTeams.add(team);
                        }
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
            }
        };

        asyncTask.execute();
    }
*/

}