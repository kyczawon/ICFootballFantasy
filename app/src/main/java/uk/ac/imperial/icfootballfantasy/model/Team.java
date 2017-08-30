package uk.ac.imperial.icfootballfantasy.model;

/**
 * Created by leszek on 8/17/17.
 */

public class Team {
    private int team_id;
    private String name;
    private String owner;
    private double price;
    private int points;
    private int points_week;
    private int defNum;
    private int midNum;
    private int fwdNum;
    private int goalId;
    private int player1Id;
    private int player2Id;
    private int player3Id;
    private int player4Id;
    private int player5Id;
    private int player6Id;
    private int player7Id;
    private int player8Id;
    private int player9Id;
    private int player10Id;
    private int subGoalId;
    private int sub1Id;
    private int sub2Id;
    private int sub3Id;
    private int sub4Id;

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getDefNum() {
        return defNum;
    }

    public void setDefNum(int defNum) {
        this.defNum = defNum;
    }

    public int getMidNum() {
        return midNum;
    }

    public void setMidNum(int midNum) {
        this.midNum = midNum;
    }

    public int getFwdNum() {
        return fwdNum;
    }

    public void setFwdNum(int fwdNum) {
        this.fwdNum = fwdNum;
    }

    public int getGoalId() {
        return goalId;
    }

    public void setGoalId(int goalId) {
        this.goalId = goalId;
    }

    public int getPlayer1Id() {
        return player1Id;
    }

    public void setPlayer1Id(int player1Id) {
        this.player1Id = player1Id;
    }

    public int getPlayer2Id() {
        return player2Id;
    }

    public void setPlayer2Id(int player2Id) {
        this.player2Id = player2Id;
    }

    public int getPlayer3Id() {
        return player3Id;
    }

    public void setPlayer3Id(int player3Id) {
        this.player3Id = player3Id;
    }

    public int getPlayer4Id() {
        return player4Id;
    }

    public void setPlayer4Id(int player4Id) {
        this.player4Id = player4Id;
    }

    public int getPlayer5Id() {
        return player5Id;
    }

    public void setPlayer5Id(int player5Id) {
        this.player5Id = player5Id;
    }

    public int getPlayer6Id() {
        return player6Id;
    }

    public void setPlayer6Id(int player6Id) {
        this.player6Id = player6Id;
    }

    public int getPlayer7Id() {
        return player7Id;
    }

    public void setPlayer7Id(int player7Id) {
        this.player7Id = player7Id;
    }

    public int getPlayer8Id() {
        return player8Id;
    }

    public void setPlayer8Id(int player8Id) {
        this.player8Id = player8Id;
    }

    public int getPlayer9Id() {
        return player9Id;
    }

    public void setPlayer9Id(int player9Id) {
        this.player9Id = player9Id;
    }

    public int getPlayer10Id() {
        return player10Id;
    }

    public void setPlayer10Id(int player10Id) {
        this.player10Id = player10Id;
    }

    public int getSubGoalId() {
        return subGoalId;
    }

    public void setSubGoalId(int subGoalId) {
        this.subGoalId = subGoalId;
    }

    public int getSub1Id() {
        return sub1Id;
    }

    public void setSub1Id(int sub1Id) {
        this.sub1Id = sub1Id;
    }

    public int getSub2Id() {
        return sub2Id;
    }

    public void setSub2Id(int sub2Id) {
        this.sub2Id = sub2Id;
    }

    public int getSub3Id() {
        return sub3Id;
    }

    public void setSub3Id(int sub3Id) {
        this.sub3Id = sub3Id;
    }

    public int getSub4Id() {
        return sub4Id;
    }

    public void setSub4Id(int sub4Id) {
        this.sub4Id = sub4Id;
    }

    public void setFormation(int defNum, int midNum, int fwdNum) {
        this.defNum = defNum;
        this.midNum = midNum;
        this.fwdNum = fwdNum;
    }

    public void shiftPlayers(int insert, int insertId, int overwrite) {

        int i = overwrite + 1;

        if (i >= insert) {
            while (i > insert) {
                switch (i) {
                    case 2:
                        player2Id = player1Id;
                        break;
                    case 3:
                        player3Id = player2Id;
                        break;
                    case 4:
                        player4Id = player3Id;
                        break;
                    case 5:
                        player5Id = player4Id;
                        break;
                    case 6:
                        player6Id = player5Id;
                        break;
                    case 7:
                        player7Id = player6Id;
                        break;
                    case 8:
                        player8Id = player7Id;
                        break;
                    case 9:
                        player9Id = player8Id;
                        break;
                }
                i--;
            }
        } else {
            insert--;
            while (i < insert) {
                switch (i) {
                    case 1:
                        player1Id = player2Id;
                        break;
                    case 2:
                        player2Id = player3Id;
                        break;
                    case 3:
                        player3Id = player4Id;
                        break;
                    case 4:
                        player4Id = player5Id;
                        break;
                    case 5:
                        player5Id = player6Id;
                        break;
                    case 6:
                        player6Id = player7Id;
                        break;
                    case 7:
                        player7Id = player8Id;
                        break;
                    case 8:
                        player8Id = player9Id;
                        break;
                    case 9:
                        player9Id = player10Id;
                        break;
                }
                i++;
            }
        }
        setStartPlayerId(insert, insertId);
    }


    public int getStartPlayerId(int playerNum) {
        switch (playerNum) {
            case 1:
                return player1Id;
            case 2:
                return player2Id;
            case 3:
                return player3Id;
            case 4:
                return player4Id;
            case 5:
                return player5Id;
            case 6:
                return player6Id;
            case 7:
                return player7Id;
            case 8:
                return player8Id;
            case 9:
                return player9Id;
            case 10:
                return player10Id;
            default:
                return 0;
        }
    }

    public int getFullSquadPlayerId(int playerNum) {
        switch (playerNum) {
            case 1:
                return goalId;
            case 2:
                return subGoalId;
            case 3:
                return player1Id;
            case 4:
                return player2Id;
            case 5:
                return player3Id;
            case 6:
                return sub3Id;
            case 7:
                return sub4Id;
            case 8:
                return player4Id;
            case 9:
                return player5Id;
            case 10:
                return player6Id;
            case 11:
                return player7Id;
            case 12:
                return sub2Id;
            case 13:
                return player8Id;
            case 14:
                return player9Id;
            case 15:
                return player10Id;
            case 16:
                return sub1Id;
            default:
                return 0;
        }
    }


    public int getSubId(int subNum) {
        switch (subNum) {
            case 1:
                return getSubGoalId();
            case 2:
                return sub1Id;
            case 3:
                return sub2Id;
            case 4:
                return sub3Id;
            case 5:
                return sub4Id;
            default:
                return 0;
        }
    }

    public Team() {

    }


    public Team(int team_id, String name, String owner, double price, int points, int points_week, int defNum, int midNum, int fwdNum, int goalId, int player1Id, int player2Id, int player3Id, int player4Id, int player5Id, int player6Id, int player7Id, int player8Id, int player9Id, int player10Id, int subGoalId, int sub1Id, int sub2Id, int sub3Id, int sub4Id) {
        this.team_id = team_id;
        this.name = name;
        this.owner = owner;
        this.price = price;
        this.points = points;
        this.points_week = points_week;
        this.defNum = defNum;
        this.midNum = midNum;
        this.fwdNum = fwdNum;
        this.goalId = goalId;
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        this.player3Id = player3Id;
        this.player4Id = player4Id;
        this.player5Id = player5Id;
        this.player6Id = player6Id;
        this.player7Id = player7Id;
        this.player8Id = player8Id;
        this.player9Id = player9Id;
        this.player10Id = player10Id;
        this.subGoalId = subGoalId;
        this.sub1Id = sub1Id;
        this.sub2Id = sub2Id;
        this.sub3Id = sub3Id;
        this.sub4Id = sub4Id;
    }

    public void setStartPlayerId(int playerNum, int playerid) {
        switch (playerNum) {
            case 1: player1Id = playerid;
                break;
            case 2: player2Id = playerid;
                break;
            case 3: player3Id = playerid;
                break;
            case 4: player4Id = playerid;
                break;
            case 5: player5Id = playerid;
                break;
            case 6: player6Id = playerid;
                break;
            case 7: player7Id = playerid;
                break;
            case 8: player8Id = playerid;
                break;
            case 9: player9Id = playerid;
                break;
            case 10: player10Id = playerid;
                break;
        }
    }

    public void setSubPlayerId(int playerNum, int playerid) {
        switch (playerNum) {
            case 1: subGoalId = playerid;
                break;
            case 2: sub1Id = playerid;
                break;
            case 3: sub2Id = playerid;
                break;
            case 4: sub3Id = playerid;
                break;
            case 5: sub4Id = playerid;
        }
    }


    public void setPlayerId(int playerNum, int playerid) {
        defNum = 3;
        midNum = 4;
        fwdNum = 3;
        switch (playerNum) {
            case 1: goalId = playerid;
                break;
            case 2: subGoalId = playerid;
                break;
            case 3: player1Id = playerid;
                break;
            case 4: player2Id = playerid;
                break;
            case 5: player3Id = playerid;
                break;
            case 6: sub3Id = playerid;
                break;
            case 7: sub4Id = playerid;
                break;
            case 8: player4Id = playerid;
                break;
            case 9: player5Id = playerid;
                break;
            case 10: player6Id = playerid;
                break;
            case 11: player7Id = playerid;
                break;
            case 12: sub2Id = playerid;
                break;
            case 13: player8Id = playerid;
                break;
            case 14: player9Id = playerid;
                break;
            case 15: player10Id = playerid;
                break;
            case 16: sub1Id = playerid;
                break;
        }
    }

    public String[] getAllIds() {
        return new String[]{String.valueOf(goalId), String.valueOf(player1Id), String.valueOf(player2Id),
                String.valueOf(player3Id), String.valueOf(player4Id),String.valueOf(player5Id), String.valueOf(player6Id),
                String.valueOf(player7Id), String.valueOf(player8Id), String.valueOf(player9Id), String.valueOf(player10Id),
                String.valueOf(subGoalId), String.valueOf(sub1Id), String.valueOf(sub2Id), String.valueOf(sub3Id),
                String.valueOf(sub4Id)};
    }

}
