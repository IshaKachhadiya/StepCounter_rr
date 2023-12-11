package com.mysweat.pedocounter.walkerstep.Database;


public class DbTable {
    public static final String CREATE_TABLE = "CREATE TABLE recordsUsers(columnid INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,distance TEXT,date TEXT,fulldate TEXT,calories TEXT,steps TEXT);";
    public static final String Calories = "calories";
    public static final String Date = "date";
    public static final String FULLDate = "fulldate";
    public static final String TABLE_NAME = "recordsUsers";
    private String calories;
    private String date;
    private String datetime;
    private String distance;
    private String fulldate;
    private int id;
    private String name;
    private String steps;
    private String time;
    private String timer;

    public DbTable() {
    }

    public DbTable(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) {
        this.id = i;
        this.name = str;
        this.datetime = str2;
        this.distance = str3;
        this.timer = str4;
        this.calories = str5;
        this.date = str6;
        this.fulldate = str7;
        this.time = str8;
        this.steps = str9;
    }

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

    public String getDateTime() {
        return this.datetime;
    }

    public void setDateTime(String str) {
        this.datetime = str;
    }

    public void setDistance(String str) {
        this.distance = str;
    }

    public String getDistance() {
        return this.distance;
    }

    public void setTimer(String str) {
        this.timer = str;
    }

    public String getTimer() {
        return this.timer;
    }

    public void setCalories(String str) {
        this.calories = str;
    }

    public String getCalories() {
        return this.calories;
    }

    public void setTime(String str) {
        this.time = str;
    }

    public String getTime() {
        return this.time;
    }

    public void setDate(String str) {
        this.date = str;
    }

    public String getDate() {
        return this.date;
    }

    public void setFULLDate(String str) {
        this.fulldate = str;
    }

    public String getFULLDate() {
        return this.fulldate;
    }

    public void setSteps(String str) {
        this.steps = str;
    }

    public String getSteps() {
        return this.steps;
    }
}
