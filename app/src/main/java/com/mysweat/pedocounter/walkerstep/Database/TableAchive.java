package com.mysweat.pedocounter.walkerstep.Database;


public class TableAchive {
    public static final String CREATE_TABLE = "CREATE TABLE achievedata(columnid INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,distance TEXT,calorie TEXT,steps TEXT);";
    public static final String Calorie = "calorie";
    public static final String TABLE_NAME = "achievedata";
    private String calorie;
    private String distance;
    private int id;
    private String name;
    private String steps;

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getDistance() {
        return this.distance;
    }

    public void setDistance(String str) {
        this.distance = str;
    }

    public String getCalorie() {
        return this.calorie;
    }

    public void setCalorie(String str) {
        this.calorie = str;
    }

    public String getSteps() {
        return this.steps;
    }

    public void setSteps(String str) {
        this.steps = str;
    }
}
