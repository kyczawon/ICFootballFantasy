package uk.ac.imperial.icfootballfantasy.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import uk.ac.imperial.icfootballfantasy.R;

/**
 * Created by leszek on 8/26/17.
 */

public class TablesFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tables_fragment, container, false);

        ArrayList<TextView> textViews = new ArrayList<>();

        textViews.add((TextView) v.findViewById(R.id.tables_first_team_bucs));
        textViews.add((TextView) v.findViewById(R.id.tables_first_team_bucs_cup));
        textViews.add((TextView) v.findViewById(R.id.tables_first_team_lusl));
        textViews.add((TextView) v.findViewById(R.id.tables_first_team_lusl_cup));

        textViews.add((TextView) v.findViewById(R.id.tables_second_team_bucs));
        textViews.add((TextView) v.findViewById(R.id.tables_second_team_bucs_cup));
        textViews.add((TextView) v.findViewById(R.id.tables_second_team_lusl));
        textViews.add((TextView) v.findViewById(R.id.tables_second_team_lusl_cup));

        textViews.add((TextView) v.findViewById(R.id.tables_third_team_bucs));
        textViews.add((TextView) v.findViewById(R.id.tables_third_team_bucs_cup));
        textViews.add((TextView) v.findViewById(R.id.tables_third_team_lusl));
        textViews.add((TextView) v.findViewById(R.id.tables_third_team_lusl_cup));

        textViews.add((TextView) v.findViewById(R.id.tables_fourth_team_bucs));
        textViews.add((TextView) v.findViewById(R.id.tables_fourth_team_bucs_cup));
        textViews.add((TextView) v.findViewById(R.id.tables_fourth_team_lusl));
        textViews.add((TextView) v.findViewById(R.id.tables_fourth_team_lusl_cup));

        textViews.add((TextView) v.findViewById(R.id.tables_fifth_team_bucs));
        textViews.add((TextView) v.findViewById(R.id.tables_fifth_team_bucs_cup));
        textViews.add((TextView) v.findViewById(R.id.tables_fifth_team_lusl));
        textViews.add((TextView) v.findViewById(R.id.tables_fifth_team_lusl_cup));

        textViews.add((TextView) v.findViewById(R.id.tables_sixth_team_bucs));
        textViews.add((TextView) v.findViewById(R.id.tables_sixth_team_bucs_cup));
        textViews.add((TextView) v.findViewById(R.id.tables_sixth_team_lusl));
        textViews.add((TextView) v.findViewById(R.id.tables_sixth_team_lusl_cup));

        textViews.add((TextView) v.findViewById(R.id.tables_seventh_team_lusl));
        textViews.add((TextView) v.findViewById(R.id.tables_seventh_team_lusl_cup));

        for (TextView textView : textViews) {
            textView.setOnClickListener(mOnClickListener);
        }
        return v;
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(getContext(), WebViewActivity.class);
            String url = "";
            switch(v.getId())
            {
                case R.id.tables_first_team_bucs:
                    url = "https://www.bucs.org.uk//bucscore/TeamProfile.aspx?id=1468&sport=Football&institution=Imperial%20College%20London";
                    break;

                case R.id.tables_first_team_bucs_cup:
                    url = "https://www.bucs.org.uk//bucscore/Knockout.aspx?id=1696&sport=Football";
                    break;

                case R.id.tables_first_team_lusl:
                    url = "https://www.bucs.org.uk//bucscore/TeamProfile.aspx?id=5295&sport=Football&institution=Imperial%20College%20London";
                    break;

                case R.id.tables_first_team_lusl_cup:
                    url = "https://www.bucs.org.uk//bucscore/Knockout.aspx?id=1882&sport=Football";
                    break;

                case R.id.tables_second_team_bucs:
                    url = "https://www.bucs.org.uk/bucscore/TeamProfile.aspx?id=1469&sport=Football&institution=Imperial%20College%20London";
                    break;

                case R.id.tables_second_team_bucs_cup:
                    url = "https://www.bucs.org.uk/bucscore/Knockout.aspx?id=1700&sport=Football";
                    break;

                case R.id.tables_second_team_lusl:
                    url = "https://www.bucs.org.uk/bucscore/TeamProfile.aspx?id=5296&sport=Football&institution=Imperial%20College%20London";
                    break;

                case R.id.tables_second_team_lusl_cup:
                    url = "https://www.bucs.org.uk/bucscore/Knockout.aspx?id=1882&sport=Football";
                    break;

                case R.id.tables_third_team_bucs:
                    url = "https://www.bucs.org.uk/bucscore/TeamProfile.aspx?id=1470&sport=Football&institution=Imperial%20College%20London";
                    break;

                case R.id.tables_third_team_bucs_cup:
                    url = "https://www.bucs.org.uk/bucscore/Knockout.aspx?id=1704&sport=Football";
                    break;

                case R.id.tables_third_team_lusl:
                    url = "https://www.bucs.org.uk/bucscore/TeamProfile.aspx?id=5297&sport=Football&institution=Imperial%20College%20London";
                    break;

                case R.id.tables_third_team_lusl_cup:
                    url = "https://www.bucs.org.uk/bucscore/Knockout.aspx?id=1882&sport=Football";
                    break;

                case R.id.tables_fourth_team_bucs:
                    url = "https://www.bucs.org.uk/bucscore/TeamProfile.aspx?id=5809&sport=Football&institution=Imperial%20College%20London";
                    break;

                case R.id.tables_fourth_team_bucs_cup:
                    url = "https://www.bucs.org.uk/bucscore/Knockout.aspx?id=1704&sport=Football";
                    break;

                case R.id.tables_fourth_team_lusl:
                    url = "https://www.bucs.org.uk/bucscore/TeamProfile.aspx?id=5298&sport=Football&institution=Imperial%20College%20London";
                    break;

                case R.id.tables_fourth_team_lusl_cup:
                    url = "https://www.bucs.org.uk/bucscore/Knockout.aspx?id=1883&sport=Football";
                    break;

                case R.id.tables_fifth_team_bucs:
                    url = "https://www.bucs.org.uk/bucscore/TeamProfile.aspx?id=5299&sport=Football&institution=Imperial%20College%20London";
                    break;

                case R.id.tables_fifth_team_bucs_cup:
                    url = "https://www.bucs.org.uk/bucscore/Knockout.aspx?id=1883&sport=Football";
                    break;

                case R.id.tables_fifth_team_lusl:
                    url = "https://www.bucs.org.uk/bucscore/TeamProfile.aspx?id=5810&sport=Football&institution=Imperial%20College%20London";
                    break;

                case R.id.tables_fifth_team_lusl_cup:
                    url = "https://www.bucs.org.uk/bucscore/Knockout.aspx?id=1704&sport=Football";
                    break;

                case R.id.tables_sixth_team_bucs:
                    url = "https://www.bucs.org.uk/bucscore/TeamProfile.aspx?id=5811&sport=Football&institution=Imperial%20College%20London";
                    break;

                case R.id.tables_sixth_team_bucs_cup:
                    url = "https://www.bucs.org.uk/bucscore/Knockout.aspx?id=1704&sport=Football";
                    break;

                case R.id.tables_sixth_team_lusl:
                    url = "https://www.bucs.org.uk/bucscore/TeamProfile.aspx?id=5300&sport=Football&institution=Imperial%20College%20London";
                    break;

                case R.id.tables_sixth_team_lusl_cup:
                    url = "https://www.bucs.org.uk/bucscore/Knockout.aspx?id=1883&sport=Football";
                    break;

                case R.id.tables_seventh_team_lusl:
                    url = "https://www.bucs.org.uk/bucscore/TeamProfile.aspx?id=5301&sport=Football&institution=Imperial%20College%20London";
                    break;

                case R.id.tables_seventh_team_lusl_cup:
                    url = "https://www.bucs.org.uk/bucscore/Knockout.aspx?id=1883&sport=Football";
                    break;
            }
            i.putExtra("url", url);
            startActivity(i);
        }
    };
}
