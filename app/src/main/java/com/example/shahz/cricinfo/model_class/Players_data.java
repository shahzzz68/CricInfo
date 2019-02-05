package com.example.shahz.cricinfo.model_class;

import java.util.HashMap;
import java.util.Map;

public class Players_data {

private String name,style,team,type;

public  Players_data(){}

    public Players_data(String name, String style, String team, String type) {
        this.name = name;
        this.style = style;
        this.team = team;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
