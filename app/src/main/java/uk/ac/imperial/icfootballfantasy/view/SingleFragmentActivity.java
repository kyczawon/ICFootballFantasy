package uk.ac.imperial.icfootballfantasy.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import uk.ac.imperial.icfootballfantasy.R;

/**
 * Created by leszek on 8/12/17.
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();

    protected abstract Bundle getBundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.flContent);

        if (fragment == null) {
            fragment = createFragment();
            Bundle args = getBundle();
            fragment.setArguments(args);
            fm.beginTransaction().add(R.id.flContent,fragment).commit();
        }
    }
}