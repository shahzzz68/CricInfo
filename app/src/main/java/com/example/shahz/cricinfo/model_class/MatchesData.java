package com.example.shahz.cricinfo.model_class;

import java.util.Date;

public class MatchesData {
    private String team1,team2,time,date,description;
    private Date timestamp;

    public MatchesData(){};

    public MatchesData(String team1, String team2, String time, String date, String description, Date timeStamp) {
        this.team1 = team1;
        this.team2 = team2;
        this.time = time;
        this.date = date;
        this.description = description;

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
