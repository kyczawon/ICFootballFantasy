package uk.ac.imperial.icfootballfantasy.controller;

/*
public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = DatabaseHelper.class.getName();

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "acc_football";

    // Table Names
    private static final String TABLE_ADMINS = "icff_admins";
    private static final String TABLE_ADMIN_LOG = "icff_admin_log";
    private static final String TABLE_PLAYERS = "icff_players";
    private static final String TABLE_TEAMS = "icff_teams";
    private static final String TABLE_USERS = "icff_users";

    // Common column names
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_TEAM_ID= "team_id";

    // ADMIN Table - column names
    private static final String KEY_ADMIN_USERNAME = "username";
    private static final String KEY_ADMIN_PASSWORD = "password";

    // ADMIN_LOG Table - column names
    private static final String KEY_ADMIN_LOG_USER = "user";
    private static final String KEY_ADMIN_LOG_DATE = "date";
    private static final String KEY_ADMIN_LOG_TIME = "time";

    // PLAYERS Table - column names
    private static final String KEY_PLAYER_ID= "player_id";
    private static final String KEY_PLAYER_FIRST_NAME = "first_name";
    private static final String KEY_PLAYER_LAST_NAME = "last_name";
    private static final String KEY_PLAYER_POSITION = "position";
    private static final String KEY_PLAYER_TEAM = "team";
    private static final String KEY_PLAYER_PRICE = "price";
    private static final String KEY_PLAYER_POINTS = "points";
    private static final String KEY_PLAYER_APPEARANCES = "appearances";
    private static final String KEY_PLAYER_SUB_APPEARANCES = "sub_appearances";
    private static final String KEY_PLAYER_GOALS = "goals";
    private static final String KEY_PLAYER_ASSISTS = "assists";
    private static final String KEY_PLAYER_CLEAN_SHEETS = "clean_sheets";
    private static final String KEY_PLAYER_MOTMS = "motms";
    private static final String KEY_PLAYER_OWN_GOALS = "own_goals";
    private static final String KEY_PLAYER_RED_CARDS = "red_cards";
    private static final String KEY_PLAYER_YELLOW_CARDS = "yellow_cards";
    private static final String KEY_PLAYER_IMAGE = "image";
    private static final String KEY_PLAYER_IS_FRESHER = "is_fresher";

    // TEAMS Table - column names
    private static final String KEY_TEAM_NAME = "name";
    private static final String KEY_TEAM_OWNER = "owner";
    private static final String KEY_TEAM_PRICE = "price";
    private static final String KEY_TEAM_POINTS = "points";
    private static final String KEY_TEAM_GOAL1 = "goal1";
    private static final String KEY_TEAM_GOAL2 = "goal2";
    private static final String KEY_TEAM_DEF1 = "def1";
    private static final String KEY_TEAM_DEF2 = "def2";
    private static final String KEY_TEAM_DEF3 = "def3";
    private static final String KEY_TEAM_DEF4 = "def4";
    private static final String KEY_TEAM_DEF5 = "def5";
    private static final String KEY_TEAM_MID1 = "mid1";
    private static final String KEY_TEAM_MID2 = "mid2";
    private static final String KEY_TEAM_MID3 = "mid3";
    private static final String KEY_TEAM_MID4 = "mid4";
    private static final String KEY_TEAM_MID5 = "mid5";
    private static final String KEY_TEAM_FWD1 = "fwd1";
    private static final String KEY_TEAM_FWD2 = "fwd2";
    private static final String KEY_TEAM_FWD3 = "fwd3";
    private static final String KEY_TEAM_FWD4 = "fwd4";

    // USERS Table - column names
    private static final String KEY_USERS_USERNAME = "username";
    private static final String KEY_ADMIN_PASSWORD = "password";

}
*/