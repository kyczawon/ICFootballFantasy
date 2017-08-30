package uk.ac.imperial.icfootballfantasy.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import uk.ac.imperial.icfootballfantasy.R;

/**
 * Created by leszek on 7/27/17.
 */

public class TeamInfoFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.team_display_fragment, container, false);
        int playerId = getArguments().getInt("index");

        TextView goalName = (TextView) v.findViewById(R.id.goal_name);
        goalName.setText("Monk");
        TextView goalScore = (TextView) v.findViewById(R.id.goal_score);
        goalScore.setText("106");

        TextView sub1Name = (TextView) v.findViewById(R.id.sub1_name);
        sub1Name.setText("Alvarez");
        TextView sub1Score = (TextView) v.findViewById(R.id.sub1_score);
        sub1Score.setText("72");
        ImageView sub1 = (ImageView) v.findViewById(R.id.sub1);
        sub1.setImageResource(R.drawable.shirt4);

        TextView sub2Name = (TextView) v.findViewById(R.id.sub2_name);
        sub2Name.setText("Ho");
        TextView sub2Score = (TextView) v.findViewById(R.id.sub2_score);
        sub2Score.setText("51");
        ImageView sub2 = (ImageView) v.findViewById(R.id.sub2);
        sub2.setImageResource(R.drawable.shirt3);

        TextView sub3Name = (TextView) v.findViewById(R.id.sub3_name);
        sub3Name.setText("Hale");
        TextView sub3Score = (TextView) v.findViewById(R.id.sub3_score);
        sub3Score.setText("19");
        ImageView sub3 = (ImageView) v.findViewById(R.id.sub3);
        sub3.setImageResource(R.drawable.shirt6);

        TextView sub4Name = (TextView) v.findViewById(R.id.sub4_name);
        sub4Name.setText("Patel");
        TextView sub4Score = (TextView) v.findViewById(R.id.sub4_score);
        sub4Score.setText("696969");
        ImageView sub4 = (ImageView) v.findViewById(R.id.sub4);
        sub4.setImageResource(R.drawable.shirt7);

        TextView sub5Name = (TextView) v.findViewById(R.id.sub5_name);
        sub5Name.setText("Austin");
        TextView sub5Score = (TextView) v.findViewById(R.id.sub5_score);
        sub5Score.setText("53");
        ImageView sub5 = (ImageView) v.findViewById(R.id.sub5);
        sub5.setImageResource(R.drawable.shirt5);

        return v;
    }
}
