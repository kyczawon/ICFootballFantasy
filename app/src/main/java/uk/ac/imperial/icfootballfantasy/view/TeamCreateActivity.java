package uk.ac.imperial.icfootballfantasy.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import uk.ac.imperial.icfootballfantasy.model.Team;

/**
 * Created by leszek on 8/19/17.
 */

public class TeamCreateActivity extends SingleFragmentActivity {
    private Team newTeam = new Team();

    @Override
    protected Fragment createFragment() {
        return new TeamCreateFragment();
    }

    @Override
    protected Bundle getBundle() {
        Bundle args = new Bundle();
        return args;
    }

    public void setNewTeam(Team newTeam) {
        this.newTeam = newTeam;
    }

    public Team getNewTeam() {
        return newTeam;
    }
}
