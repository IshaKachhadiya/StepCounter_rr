package com.mysweat.pedocounter.walkerstep.Database;


public class UserTable {
    public static final String COLUMN_ID = "columnid";
    public static final String CREATE_TABLE = "CREATE TABLE usersdata(columnid INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,gender TEXT,goal TEXT,unit TEXT,weight TEXT);";
    public static final String CREATE_TABLE2 = "CREATE TABLE IF NOT EXISTS usersdata(columnid INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,gender TEXT,goal TEXT,unit TEXT,weight TEXT);";
    public static final String Gender = "gender";
    public static final String Goal = "goal";
    public static final String Name = "name";
    public static final String TABLE_NAME = "usersdata";
    public static final String Unit = "unit";
    public static final String Weight = "weight";
    private String gender;
    private String goal;
    private int id;
    private String name;
    private String unit;
    private String weight;

    public UserTable() {
    }

    public UserTable(int i, String str, String str2, String str3, String str4, String str5) {
        this.id = i;
        this.name = str;
        this.gender = str2;
        this.weight = str3;
        this.goal = str4;
        this.unit = str5;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public String getGoal() {
        return this.goal;
    }

    public void setGoal(String str) {
        this.goal = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String str) {
        this.unit = this.unit;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String str) {
        this.gender = str;
    }

    public String getWeight() {
        return this.weight;
    }

    public void setWeight(String str) {
        this.weight = str;
    }
}
