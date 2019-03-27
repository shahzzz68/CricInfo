package com.example.shahz.cricinfo.model_class;

public class First_inning_Details {

    String ballsPlayed;
    String fours;
    String name;
    String out;
    String runs;
    String sixs;

    First_inning_Details(){};

    public String getBallsPlayed() {
        return ballsPlayed;
    }

    public void setBallsPlayed(String ballsPlayed) {
        this.ballsPlayed = ballsPlayed;
    }

    public String getFours() {
        return fours;
    }

    public void setFours(String fours) {
        this.fours = fours;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }

    public String getRuns() {
        return runs;
    }

    public void setRuns(String runs) {
        this.runs = runs;
    }

    public String getSixs() {
        return sixs;
    }

    public void setSixs(String sixs) {
        this.sixs = sixs;
    }

    public First_inning_Details(String ballsPlayed, String fours, String name, String out, String runs, String sixs) {
        this.ballsPlayed = ballsPlayed;
        this.fours = fours;
        this.name = name;
        this.out = out;
        this.runs = runs;
        this.sixs = sixs;



    }
}
