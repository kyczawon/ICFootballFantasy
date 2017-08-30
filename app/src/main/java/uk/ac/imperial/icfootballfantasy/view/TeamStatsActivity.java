package uk.ac.imperial.icfootballfantasy.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.ArrayList;

import uk.ac.imperial.icfootballfantasy.model.Player;

/**
 * Created by leszek on 8/12/17.
 */

public class TeamStatsActivity extends SingleFragmentActivity{

    private ArrayList<Player> playersAppearances;
    private ArrayList<Player> playersSubs;
    private ArrayList<Player> playersAppsAndSubs;
    private ArrayList<Player> playersGoals;
    private ArrayList<Player> playersAssists;
    private ArrayList<Player> playersOwnGoals;
    private ArrayList<Player> playersYellowCards;
    private ArrayList<Player> playersRedCards;
    private Player motm;
    private int ICScore;
    private int OpponentScore;

    @Override
    protected Fragment createFragment() {
        return new TeamStatsStartFragment();
    }

    @Override
    protected Bundle getBundle() {
        return new Bundle();
    }

    public ArrayList<Player> getPlayersAppearances() {
        return playersAppearances;
    }

    public void setPlayersAppearances(ArrayList<Player> playersAppearances) {
        this.playersAppearances = playersAppearances;
    }

    public ArrayList<Player> getPlayersSubs() {
        return playersSubs;
    }

    public void setPlayersSubs(ArrayList<Player> playersSubs) {
        this.playersSubs = playersSubs;
    }

    public ArrayList<Player> getPlayersGoals() {
        return playersGoals;
    }

    public void setPlayersGoals(ArrayList<Player> playersGoals) {
        this.playersGoals = playersGoals;
    }

    public ArrayList<Player> getPlayersAssists() {
        return playersAssists;
    }

    public void setPlayersAssists(ArrayList<Player> playersAssists) {
        this.playersAssists = playersAssists;
    }

    public ArrayList<Player> getPlayersOwnGoals() {
        return playersOwnGoals;
    }

    public void setPlayersOwnGoals(ArrayList<Player> playersOwnGoals) {
        this.playersOwnGoals = playersOwnGoals;
    }

    public ArrayList<Player> getPlayersYellowCards() {
        return playersYellowCards;
    }

    public void setPlayersYellowCards(ArrayList<Player> playersYellowCards) {
        this.playersYellowCards = playersYellowCards;
    }

    public ArrayList<Player> getPlayersRedCards() {
        return playersRedCards;
    }

    public void setPlayersRedCards(ArrayList<Player> playersRedCards) {
        this.playersRedCards = playersRedCards;
    }

    public ArrayList<Player> getPlayersAppsAndSubs() {
        return playersAppsAndSubs;
    }

    public void setPlayersAppsAndSubs(ArrayList<Player> playersAppsAndSubs) {
        this.playersAppsAndSubs = playersAppsAndSubs;
    }

    public int getICScore() {
        return ICScore;
    }

    public void setICScore(int ICScore) {
        this.ICScore = ICScore;
    }

    public int getOpponentScore() {
        return OpponentScore;
    }

    public void setOpponentScore(int opponentScore) {
        OpponentScore = opponentScore;
    }

    public Player getMotm() {
        return motm;
    }

    public void setMotm(Player motm) {
        this.motm = motm;
    }
}
