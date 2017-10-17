package uk.ac.imperial.icfootballfantasy.model;

import uk.ac.imperial.icfootballfantasy.R;

/**
 * Created by leszek on 7/20/17.
 */

public class Player {
    private static final int POINTS_APPEARANCE = 4;
    private static final int POINTS_SUB_APPEARANCE = 2;
    private static final int POINTS_GOAL = 5;
    private static final int POINTS_ASSIST = 3;
    private static final int POINTS_CLEAN_SHEET = 5;
    private static final int POINTS_MOTMS = 5;
    private static final int POINTS_OWN_GOAL = -5;
    private static final int POINTS_YELLOW_CARD = -3;
    private static final int POINTS_RED_CARD = -5;

    private int playerID;
    private String firstName;
    private String lastName;
    private String position;
    private int team;
    private double price;
    private int points = 0;
    private int pointsWeek = 0;
    private int appearances = 0;
    private int subAppearances = 0;
    private int goals = 0;
    private int assists = 0;
    private int cleanSheets = 0;
    private int motms = 0;
    private int ownGoals = 0;
    private int redCards = 0;
    private int yellowCards = 0;
    private boolean isFresher = false;
    private int image;


    public Player(int playerID, String firstName, String lastName, String position, int team, double price, int points, int pointsWeek, int appearances, int subAppearances, int goals, int assists, int cleanSheets, int motms, int ownGoals, int redCards, int yellowCards, boolean isFresher) {
        this.playerID = playerID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.team = team;
        this.price = price;
        this.points = points;
        this.pointsWeek = pointsWeek;
        this.appearances = appearances;
        this.subAppearances = subAppearances;
        this.goals = goals;
        this.assists = assists;
        this.cleanSheets = cleanSheets;
        this.motms = motms;
        this.ownGoals = ownGoals;
        this.redCards = redCards;
        this.yellowCards = yellowCards;
        this.isFresher = isFresher;

        switch (team) {
            case 1:  this.image = R.drawable.shirt1;
                break;
            case 2:  this.image = R.drawable.shirt2;
                break;
            case 3:  this.image = R.drawable.shirt3;
                break;
            case 4:  this.image = R.drawable.shirt4;
                break;
            case 5:  this.image = R.drawable.shirt5;
                break;
            case 6:  this.image = R.drawable.shirt6;
                break;
            case 7:  this.image = R.drawable.shirt7;
                break;
            default: this.image = R.drawable.shirt;
                break;
        }
        if (position.equals("GK")) {
            this.image = R.drawable.goalkeeper;
        }

    }

    public Player(String firstName, String lastName, String position, int team, double price) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.team = team;
        this.price = price;
        this.position = position;

        switch (team) {
            case 1:  this.image = R.drawable.shirt1;
                break;
            case 2:  this.image = R.drawable.shirt2;
                break;
            case 3:  this.image = R.drawable.shirt3;
                break;
            case 4:  this.image = R.drawable.shirt4;
                break;
            case 5:  this.image = R.drawable.shirt5;
                break;
            case 6:  this.image = R.drawable.shirt6;
                break;
            case 7:  this.image = R.drawable.shirt7;
                break;
            default: this.image = R.drawable.shirt;
                break;
        }
        if (position.equals("GK")) {
            this.image = R.drawable.goalkeeper;
        }

    }

    public Player(String firstName, String lastName, String position, int team, double price, boolean isFresher) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.team = team;
        this.price = price;
        this.position = position;
        this.isFresher = isFresher;

        switch (team) {
            case 1:  this.image = R.drawable.shirt1;
                break;
            case 2:  this.image = R.drawable.shirt2;
                break;
            case 3:  this.image = R.drawable.shirt3;
                break;
            case 4:  this.image = R.drawable.shirt4;
                break;
            case 5:  this.image = R.drawable.shirt5;
                break;
            case 6:  this.image = R.drawable.shirt6;
                break;
            case 7:  this.image = R.drawable.shirt7;
                break;
            default: this.image = R.drawable.shirt;
                break;
        }
        if (position.equals("GK")) {
            this.image = R.drawable.goalkeeper;
        }

    }

    public boolean isFresher() {
        return isFresher;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLastNameOneWord() {
        String arr[] = lastName.split(" ", 2);
        return arr[0];
    }

    public String getPosition() {
        return position;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getTeam() {
        return team;
    }

    public String getTeamString() {

        if (team == 1) {
            return "1st";
        } else if (team == 2) {
            return "2nd";
        } else if (team == 3) {
            return "3rd";
        } else {
            return team + "th";
        }
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getPoints() {
        points = appearances * 2 + subAppearances + goals * 4 + assists * 3 + motms * 5 +  ownGoals * -3 + redCards *-5 +yellowCards * -3;
        if (position.equals("GK") || position.equals("DEF")) {
            points += 3*cleanSheets;
            points += goals;
        }
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getAppearances() {
        return appearances;
    }

    public int getImage() {
        return image;
    }

    public void setAppearances(int appearances) {
        this.appearances = appearances;
    }

    public int getSubAppearances() {
        return subAppearances;
    }

    public void setSubAppearances(int subAppearances) {
        this.subAppearances = subAppearances;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getCleanSheets() {
        return cleanSheets;
    }

    public void setCleanSheets(int cleanSheets) {
        this.cleanSheets = cleanSheets;
    }

    public int getMotms() {
        return motms;
    }

    public void setMotms(int motms) {
        this.motms = motms;
    }

    public int getOwnGoals() {
        return ownGoals;
    }

    public void setOwnGoals(int ownGoals) {
        this.ownGoals = ownGoals;
    }

    public int getRedCards() {
        return redCards;
    }

    public void setRedCards(int redCards) {
        this.redCards = redCards;
    }

    public int getYellowCards() {
        return yellowCards;
    }

    public void setYellowCards(int yellowCards) {
        this.yellowCards = yellowCards;
    }

    public int getId() {
        return playerID;
    }

    public int getPointsWeek() {
        return pointsWeek;
    }

    public void setPointsWeek(int pointsWeek) {
        this.pointsWeek = pointsWeek;
    }

    public void updatePlayerByAppearance() {
        this.pointsWeek += POINTS_APPEARANCE;
        this.appearances++;
    }

    public void updatePlayerByAppearances(int apps) {
        for (int i = 0; i < apps; i++) {
            this.pointsWeek += POINTS_APPEARANCE;
            this.appearances++;
        }
    }

    public void updatePlayerBySubAppearance() {
        this.pointsWeek += POINTS_SUB_APPEARANCE;
        this.subAppearances++;
    }

    public void updatePlayerBySubAppearances(int subApps) {
        for (int i = 0; i < subApps; i++) {
            this.pointsWeek += POINTS_SUB_APPEARANCE;
            this.subAppearances++;
        }
    }


    public void updatePlayerByGoal() {
        this.pointsWeek += POINTS_GOAL;
        this.goals++;
    }

    public void updatePlayerByGoals(int goals) {
        for (int i = 0; i < goals; i++) {
            this.pointsWeek += POINTS_GOAL;
            this.goals++;
        }
    }

    public void updatePlayerByAssist() {
        this.pointsWeek += POINTS_ASSIST;
        this.assists++;
    }

    public void updatePlayerByAssists(int assists) {
        for (int i = 0; i < assists; i++) {
            this.pointsWeek += POINTS_ASSIST;
            this.assists++;
        }
    }

    public void updatePlayerByOwnGoal() {
        this.pointsWeek += POINTS_OWN_GOAL;
        this.ownGoals++;
    }

    public void updatePlayerByOwnGoals(int ownGoals) {
        for (int i = 0; i < ownGoals; i++) {
            this.pointsWeek += POINTS_OWN_GOAL;
            this.ownGoals++;
        }
    }

    public void updatePlayerByCleanSheet() {
        this.pointsWeek += POINTS_CLEAN_SHEET;
        this.cleanSheets++;
    }

    public void updatePlayerByCleanSheets(int cleanSheets) {
        for (int i = 0; i < cleanSheets; i++) {
            this.pointsWeek += POINTS_CLEAN_SHEET;
            this.cleanSheets++;
        }
    }

    public void updatePlayerByMOTM() {
        this.pointsWeek += POINTS_MOTMS;
        this.motms++;
    }

    public void updatePlayerByMotms(int motms) {
        for (int i = 0; i < motms; i++) {
            this.pointsWeek += POINTS_MOTMS;
            this.motms++;
        }
    }

    public void updatePlayerByYellowCard() {
        this.pointsWeek += POINTS_YELLOW_CARD;
        this.yellowCards++;
    }

    public void updatePlayerByYellowCards(int yellowCards) {
        for (int i = 0; i < yellowCards; i++) {
            this.pointsWeek += POINTS_YELLOW_CARD;
            this.yellowCards++;
        }
    }

    public void updatePlayerByRedCard() {
        this.pointsWeek += POINTS_RED_CARD;
        this.redCards++;
    }

    public void updatePlayerByRedCards(int redCards) {
        for (int i = 0; i < redCards; i++) {
            this.pointsWeek += POINTS_RED_CARD;
            this.redCards++;
        }
    }
}
