package uk.ac.imperial.icfootballfantasy.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import uk.ac.imperial.icfootballfantasy.R;
import uk.ac.imperial.icfootballfantasy.model.Constants;
import uk.ac.imperial.icfootballfantasy.model.Team;
import uk.ac.imperial.icfootballfantasy.model.UserData;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Team currentTeamShown;
    UserData userData = UserData.get();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Team team = userData.getTeam();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Fragment fragment = null;
        Class fragmentClass = null;
        fragmentClass = TeamDisplayFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView =  navigationView.getHeaderView(0);

        TextView teamNameSidebar = (TextView) hView.findViewById(R.id.nav_header_teamName);
        teamNameSidebar.setText(team.getName());

        TextView teamOwnerSidebar = (TextView) hView.findViewById(R.id.nav_header_owner);
        teamOwnerSidebar.setText(team.getOwner());

        Menu nav_Menu = navigationView.getMenu();
        if (userData.getAdminedTeam() == 0 && !userData.isSuperAdmin()) {
            nav_Menu.findItem(R.id.nav_admin).setVisible(false);
        } else if (!userData.isSuperAdmin()) {
            nav_Menu.findItem(R.id.nav_set_stats).setVisible(false);
            nav_Menu.findItem(R.id.nav_add_player).setVisible(false);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass = TeamDisplayFragment.class;
        Bundle args = new Bundle();
        if (id == R.id.nav_my_team) {
            fragmentClass = TeamDisplayFragment.class;
        } else if (id == R.id.nav_players) {
            fragmentClass = PlayerListFragment.class;
        } else if (id == R.id.nav_teams) {
            fragmentClass = TeamListFragment.class;
        } else if (id == R.id.nav_table) {
            fragmentClass = TablesFragment.class;
        } else if (id == R.id.nav_log_out) {
            SharedPreferences sharedPref = getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.remove("username");
            editor.apply();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_add_player) {
            fragmentClass = PlayerAddFragment.class;
        } else if (id == R.id.nav_set_stats) {
            fragmentClass = PlayerListFragment.class;
        } else if (id == R.id.nav_set_team_stats) {
            Intent intent = new Intent(getApplicationContext(), TeamStatsActivity.class);
            startActivity(intent);
        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (id == R.id.nav_set_stats) {
            args.putString("next", "info");
        }
        fragment.setArguments(args);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setCurrentTeamShown(Team currentTeamShown) {
        this.currentTeamShown = currentTeamShown;
    }

    public Team getCurrentTeamShown() {
        return currentTeamShown;
    }
}
