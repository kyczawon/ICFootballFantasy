package uk.ac.imperial.icfootballfantasy.model;

/**
 * Created by leszek on 8/11/17.
 */

public class User {

    private int user_id;
    private int team_id;
    private String username;
    private int adminedTeam;
    private boolean is_super_admin;

    public User(int user_id, int team_id, String username, int adminedTeam, boolean is_super_admin) {
        this.user_id = user_id;
        this.team_id = team_id;
        this.username = username;
        this.adminedTeam = adminedTeam;
        this.is_super_admin = is_super_admin;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int adminedTeam() {
        return adminedTeam;
    }

    public boolean is_super_admin() {
        return is_super_admin;
    }
}
