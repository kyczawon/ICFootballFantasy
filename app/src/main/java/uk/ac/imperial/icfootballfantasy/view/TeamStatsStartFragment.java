package uk.ac.imperial.icfootballfantasy.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import uk.ac.imperial.icfootballfantasy.R;

/**
 * Created by leszek on 8/13/17.
 */

public class TeamStatsStartFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.team_stats_start_fragment, container, false);

        final EditText icScoreEditText = (EditText) view.findViewById(R.id.team_stats_start_ic_score);
        final EditText opponentScoreEditText = (EditText) view.findViewById(R.id.team_stats_start_opponent_score);

        Button nextButton = (Button) view.findViewById(R.id.team_stats_button_next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final String icScoreString= icScoreEditText.getText().toString();
                final String opponentScoreString = opponentScoreEditText.getText().toString();

                String message = "You need to enter";
                if (icScoreString.equals("")) {
                    message += " ic score";
                }
                if (opponentScoreString.equals("")) {
                    if (message.equals("You need to enter ic score")) {
                        message += " and";
                    }
                    message += " opponent score";
                }
                if (message.equals("You need to enter")) { //then all fields filled
                    Fragment fragment = new TeamStatsFragment();
                    Bundle args = new Bundle();
                    args.putString("stat", "Appearances"); //set next stat in next fragment
                    int ICScore = Integer.parseInt(icScoreEditText.getText().toString());
                    ((TeamStatsActivity)getActivity()).setICScore(ICScore);
                    int opponentScore = Integer.parseInt(opponentScoreEditText.getText().toString());
                    ((TeamStatsActivity)getActivity()).setOpponentScore(opponentScore);
                    fragment.setArguments(args);
                    getFragmentManager().beginTransaction().
                            replace(R.id.flContent, fragment).addToBackStack(null).commit();
                } else {
                    Toast.makeText(getContext(), message,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}
