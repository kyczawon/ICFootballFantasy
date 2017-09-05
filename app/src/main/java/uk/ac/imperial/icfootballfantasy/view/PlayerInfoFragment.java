package uk.ac.imperial.icfootballfantasy.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import uk.ac.imperial.icfootballfantasy.R;
import uk.ac.imperial.icfootballfantasy.controller.PlayerLab;
import uk.ac.imperial.icfootballfantasy.model.Player;

/**
 * Created by leszek on 7/27/17.
 */

public class PlayerInfoFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.player_info_fragment, container, false);
        int playerId = getArguments().getInt("playerID");

        PlayerLab playerLab = PlayerLab.get();
        final Player player = playerLab.getPlayer(playerId);

        ImageView mShirt = (ImageView) v.findViewById(R.id.player_info_image);
        mShirt.setImageResource(player.getImage());

        TextView mName = (TextView) v.findViewById(R.id.player_info_name);
        mName.setText(player.getFirstName() + " " + player.getLastName());

        TextView mPositionTeam = (TextView) v.findViewById(R.id.player_info_position_team);
        mPositionTeam.setText(player.getPosition() + " in the "+ player.getTeamString() + " team");

        TextView mPoints = (TextView) v.findViewById(R.id.player_info_points);
        mPoints.setText(String.valueOf(player.getPoints()));

        TextView mPointsWeek = (TextView) v.findViewById(R.id.player_info_points_week);
        mPointsWeek.setText(String.valueOf(player.getPointsWeek()));

        TextView mApps = (TextView) v.findViewById(R.id.player_info_apps);
        mApps.setText(String.valueOf(player.getAppearances()));

        TextView mSubs = (TextView) v.findViewById(R.id.player_info_subs);
        mSubs.setText(String.valueOf(player.getSubAppearances()));

        TextView mGoals = (TextView) v.findViewById(R.id.player_info_goals);
        mGoals.setText(String.valueOf(player.getGoals()));

        TextView mAssits = (TextView) v.findViewById(R.id.player_info_assists);
        mAssits.setText(String.valueOf(player.getAssists()));

        TextView mCleanSheets= (TextView) v.findViewById(R.id.player_info_clean_sheets);
        mCleanSheets.setText(String.valueOf(player.getCleanSheets()));

        TextView mMotms = (TextView) v.findViewById(R.id.player_info_motms);
        mMotms.setText(String.valueOf(player.getMotms()));

        TextView mYellows = (TextView) v.findViewById(R.id.player_info_yellows);
        mYellows.setText(String.valueOf(player.getYellowCards()));

        TextView mReds = (TextView) v.findViewById(R.id.player_info_reds);
        mReds.setText(String.valueOf(player.getRedCards()));

        TextView mOwnGoals = (TextView) v.findViewById(R.id.player_info_own_goals);
        mOwnGoals.setText(String.valueOf(player.getOwnGoals()));

        return v;
    }
}
