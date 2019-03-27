package com.example.shahz.cricinfo.model_class;

public class Match_OverView_Details {

    String batting;
    String bowling;
    String first;
    String second;
    String modselect;
    String overs;
    String team1;
    String team2;
    String tosswinby;

   public Match_OverView_Details()
    {}

    public Match_OverView_Details(String batting, String bowling, String first, String second, String modselect, String overs, String team1, String team2, String tosswinby) {
        this.batting = batting;
        this.bowling = bowling;
        this.first = first;
        this.second = second;
        this.modselect = modselect;
        this.overs = overs;
        this.team1 = team1;
        this.team2 = team2;
        this.tosswinby = tosswinby;
    }


    public String getBatting() {
        return batting;
    }

    public void setBatting(String batting) {
        this.batting = batting;
    }

    public String getBowling() {
        return bowling;
    }

    public void setBowling(String bowling) {
        this.bowling = bowling;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public String getModselect() {
        return modselect;
    }

    public void setModselect(String modselect) {
        this.modselect = modselect;
    }

    public String getOvers() {
        return overs;
    }

    public void setOvers(String overs) {
        this.overs = overs;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public String getTosswinby() {
        return tosswinby;
    }

    public void setTosswinby(String tosswinby) {
        this.tosswinby = tosswinby;
    }
}
