package uk.ac.imperial.icfootballfantasy.controller;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import uk.ac.imperial.icfootballfantasy.model.Player;

/**
 * Created by leszek on 7/20/17.
 */

public class PlayerLab {
    private static PlayerLab sPlayerLab;
    private PlayerLab mPlayerLab;
    private List<Player> mPlayers;
    public static PlayerLab get() {
        if (sPlayerLab == null) {
            sPlayerLab   = new PlayerLab();
        }
        return sPlayerLab;
    }

    private PlayerLab() {
        getPlayersFromDB();
    }

    public List<Player> getPlayers() {
        return mPlayers;
    }

    public List<Player> getPlayersCopy() {
        return new ArrayList<>(mPlayers);
    }

    public void addPlayer(Player player) {
        mPlayers.add(player);
    }

    public Player getPlayer(int id) {
        for (Player player : mPlayers) {
            if (player.getId() == id) {
                return player;
            }
        }
        return null;
    }

    private void getPlayersFromDB() {

        AsyncTask<Integer, Void, Void> asyncTask = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected void onPreExecute() {
                mPlayers = new ArrayList<>();
            }
            @Override
            protected Void doInBackground(Integer... team_ids) {

                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://union.ic.ac.uk/acc/football/android_connect/players.php")
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String jsonArray = response.body().string();
                    if (!jsonArray.equals("null")) {
                        JSONArray array = new JSONArray(jsonArray);

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);

                            Player player = new Player(object.getInt("player_id"), object.getString("first_name"),
                                    object.getString("last_name"), object.getString("position"),
                                    object.getInt("team"), object.getDouble("price"),
                                    object.getInt("points"), object.getInt("points_week"),
                                    object.getInt("appearances"), object.getInt("sub_appearances"),
                                    object.getInt("goals"), object.getInt("assists"),
                                    object.getInt("clean_sheets"), object.getInt("motms"),
                                    object.getInt("own_goals"), object.getInt("red_cards"),
                                    object.getInt("yellow_cards"), (object.getInt("is_fresher") == 1));
                            mPlayers.add(player);
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
}
