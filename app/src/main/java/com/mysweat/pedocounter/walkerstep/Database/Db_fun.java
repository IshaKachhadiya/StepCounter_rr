package com.mysweat.pedocounter.walkerstep.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Db_fun extends SQLiteOpenHelper {
    boolean check;
    Context context;
    int totalCalories;
    int totalSteps;
    private static final String DATABASE_ALTER_TEAM_TO_V3 = "ALTER TABLE recordsUsers ADD COLUMN fulldate TEXT;";
    private static final String DATABASE_NAME = "recordUsers_db";
    public static ArrayList<String> totalArr = new ArrayList<>();


    public Db_fun(Context context) {
        super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 2);
        this.totalCalories = 0;
        this.check = false;
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(DbTable.CREATE_TABLE);
        sQLiteDatabase.execSQL(UserTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        if (i < 2) {
            sQLiteDatabase.execSQL(DATABASE_ALTER_TEAM_TO_V3);
        }
    }

    public long insert(String str, String str2, String str3, String str4, String str5, String str6) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", str);
            contentValues.put("distance", str2);
            contentValues.put(DbTable.Calories, str3);
            contentValues.put(DbTable.Date, str4);
            contentValues.put(DbTable.FULLDate, str5);
            contentValues.put("steps", str6);
            writableDatabase.insert(DbTable.TABLE_NAME, null, contentValues);
            writableDatabase.close();
            return 0L;
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    public long insertUsers(String str, String str3, String str4) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", str);
//            contentValues.put(TABLE_USERS.Gender, str2);
            contentValues.put(UserTable.Goal, str3);
            contentValues.put(UserTable.Unit, str4);
//            contentValues.put(TABLE_USERS.Weight, str5);
            writableDatabase.insert(UserTable.TABLE_NAME, null, contentValues);
            writableDatabase.close();
            return 0L;
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    public String getFromCurrDate(String str) {
        String str2;
        totalArr = new ArrayList<>();
        this.totalSteps = 0;
        this.totalCalories = 0;
        this.context.getSharedPreferences("CurrentProfile", 0).getString("name", "0");
        new ArrayList();
        SQLiteDatabase writableDatabase = getWritableDatabase();
        Cursor rawQuery = writableDatabase.rawQuery("SELECT * FROM recordsUsers where Date ='" + str + "'", null);
        if (rawQuery.moveToFirst()) {
            do {
                String string = rawQuery.getString(rawQuery.getColumnIndex("steps"));
                String string2 = rawQuery.getString(rawQuery.getColumnIndex("distance"));
                String string3 = rawQuery.getString(rawQuery.getColumnIndex(DbTable.Calories));
                str2 = string + "," + Double.parseDouble(string2.split(" ")[0]) + "," + string3;
            } while (rawQuery.moveToNext());
        } else {
            str2 = "";
        }
        writableDatabase.close();
        return str2;
    }

    public int updateDateWise(String str, String str2, String str3, String str4) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("distance", str);
            contentValues.put(DbTable.Calories, str2);
            contentValues.put("distance", str);
            contentValues.put("steps", str4);
            return writableDatabase.update(DbTable.TABLE_NAME, contentValues, "date = ?", new String[]{str3});
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<String> getAllDB() {
        ArrayList arrayList = new ArrayList();
        SQLiteDatabase writableDatabase = getWritableDatabase();
        Cursor rawQuery = writableDatabase.rawQuery("SELECT  Name FROM usersdata", null);
        if (rawQuery.moveToFirst()) {
            do {
                arrayList.add(rawQuery.getString(rawQuery.getColumnIndex("name")));
            } while (rawQuery.moveToNext());
            writableDatabase.close();
            return arrayList;
        }
        writableDatabase.close();
        return arrayList;
    }

    public String getGoal(String str) {
        try {
            SQLiteDatabase readableDatabase = getReadableDatabase();
            Cursor rawQuery = readableDatabase.rawQuery("select Goal from usersdata where Name ='" + str + "'", null);
            if (rawQuery != null) {
                rawQuery.moveToFirst();
            }
            String string = rawQuery.getString(rawQuery.getColumnIndex(UserTable.Goal));
            rawQuery.close();
            return (string == null || string.equals("")) ? this.context.getSharedPreferences("Goals", 0).getString(UserTable.Goal, "0") : string;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getUnit(String str) {
        try {
            SQLiteDatabase readableDatabase = getReadableDatabase();
            Cursor rawQuery = readableDatabase.rawQuery("select Unit from usersdata where Name ='" + str + "'", null);
            if (rawQuery != null) {
                rawQuery.moveToFirst();
            }
            String string = rawQuery.getString(rawQuery.getColumnIndex(UserTable.Unit));
            rawQuery.close();
            return string;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int updateGoal(String str, String str2) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(UserTable.Goal, str2);
            return writableDatabase.update(UserTable.TABLE_NAME, contentValues, "name = ?", new String[]{str});
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int updateUnit(String str, String str2) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(UserTable.Unit, str2);
            return writableDatabase.update(UserTable.TABLE_NAME, contentValues, "name = ?", new String[]{str});
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public ArrayList<String> getAllDB2() {
        ArrayList<String> arrayList = new ArrayList<>();
        SQLiteDatabase writableDatabase = getWritableDatabase();
        Cursor rawQuery = writableDatabase.rawQuery("SELECT  * FROM usersdata", null);
        if (rawQuery.moveToFirst()) {
            do {
                arrayList.add(rawQuery.getString(rawQuery.getColumnIndex("name")));
            } while (rawQuery.moveToNext());
            writableDatabase.close();
            return arrayList;
        }
        writableDatabase.close();
        return arrayList;
    }

    public ArrayList<String> getAllRecords() {
        totalArr = new ArrayList<>();
        this.totalSteps = 0;
        this.totalCalories = 0;
        String string = this.context.getSharedPreferences("CurrentProfile", 0).getString("name", "0");
        ArrayList<String> arrayList = new ArrayList<>();
        SQLiteDatabase writableDatabase = getWritableDatabase();
        Cursor rawQuery = writableDatabase.rawQuery("SELECT * FROM recordsUsers ORDER BY date(Date) DESC", null);
        if (rawQuery.moveToFirst()) {
            double d = 0.0d;
            do {
                if (rawQuery.getString(rawQuery.getColumnIndex("name")).equals(string)) {
                    String string2 = rawQuery.getString(rawQuery.getColumnIndex("distance"));
                    String string3 = rawQuery.getString(rawQuery.getColumnIndex(DbTable.Calories));
                    String string4 = rawQuery.getString(rawQuery.getColumnIndex(DbTable.Date));
                    String string5 = rawQuery.getString(rawQuery.getColumnIndex(DbTable.FULLDate));
                    String string6 = rawQuery.getString(rawQuery.getColumnIndex("steps"));
                    d += Double.parseDouble(string2.split(" ")[0]);
                    this.totalSteps += Integer.parseInt(string6);
                    this.totalCalories += Integer.parseInt(string3);
                    if (string5 == null) {
                        string5 = "";
                    }
                    arrayList.add(string2 + "," + string3 + "," + string4 + "," + string5 + "," + string6);
                    this.check = true;
                }
            } while (rawQuery.moveToNext());
            String valueOf = String.valueOf(this.totalSteps);
            String valueOf2 = String.valueOf(this.totalCalories);
            String valueOf3 = String.valueOf(d);
            totalArr.add(valueOf + "," + valueOf2 + "," + valueOf3);
        }
        writableDatabase.close();
        return arrayList;
    }

    public String getTotalDistance() {
        double d = 0.0d;
        try {
            String string = this.context.getSharedPreferences("CurrentProfile", 0).getString("name", "0");
            SQLiteDatabase readableDatabase = getReadableDatabase();
            Cursor rawQuery = readableDatabase.rawQuery("select Distance from recordsUsers where Name ='" + string + "'", null);
            if (rawQuery.moveToFirst()) {
                do {
                    d += Double.parseDouble(rawQuery.getString(rawQuery.getColumnIndex("distance")).split(" ")[0]);
                } while (rawQuery.moveToNext());
                String valueOf = String.valueOf(d);
                rawQuery.close();
                return valueOf;
            }
            String valueOf2 = String.valueOf(d);
            rawQuery.close();
            return valueOf2;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getTotalDaySteps() {
        try {
            int i = 0;
            String string = this.context.getSharedPreferences("CurrentProfile", 0).getString("name", "0");
            SQLiteDatabase readableDatabase = getReadableDatabase();
            Cursor rawQuery = readableDatabase.rawQuery("select Steps from recordsUsers where Name ='" + string + "'", null);
            if (rawQuery.moveToFirst()) {
                do {
                    i += Integer.parseInt(rawQuery.getString(rawQuery.getColumnIndex("steps")));
                } while (rawQuery.moveToNext());
                String valueOf = String.valueOf(i);
                rawQuery.close();
                return valueOf;
            }
            String valueOf2 = String.valueOf(i);
            rawQuery.close();
            return valueOf2;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getTotalCalories() {
        try {
            int i = 0;
            String string = this.context.getSharedPreferences("CurrentProfile", 0).getString("name", "0");
            SQLiteDatabase readableDatabase = getReadableDatabase();
            Cursor rawQuery = readableDatabase.rawQuery("select Calories from recordsUsers where Name ='" + string + "'", null);
            if (rawQuery.moveToFirst()) {
                do {
                    i += Integer.parseInt(rawQuery.getString(rawQuery.getColumnIndex(DbTable.Calories)));
                } while (rawQuery.moveToNext());
                String valueOf = String.valueOf(i);
                rawQuery.close();
                return valueOf;
            }
            String valueOf2 = String.valueOf(i);
            rawQuery.close();
            return valueOf2;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getUserDetail(String str) {
        try {
            Cursor rawQuery = getReadableDatabase().rawQuery("select * from usersdata where Name ='" + str + "'", null);
            if (rawQuery != null) {
                rawQuery.moveToFirst();
            }
            String str2 = rawQuery.getString(rawQuery.getColumnIndex("name")) + "," + rawQuery.getString(rawQuery.getColumnIndex(UserTable.Weight)) + "," + rawQuery.getString(rawQuery.getColumnIndex(UserTable.Gender));
            rawQuery.close();
            return str2;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<String> getSteps() {
        int i = 0;
        String string = this.context.getSharedPreferences("CurrentProfile", 0).getString("name", "0");
        ArrayList<String> arrayList = new ArrayList<>();
        SQLiteDatabase writableDatabase = getWritableDatabase();
        Cursor rawQuery = writableDatabase.rawQuery("SELECT * FROM recordsUsers ORDER BY date(Date) DESC", null);
        if (rawQuery.moveToFirst()) {
            do {
                if (rawQuery.getString(rawQuery.getColumnIndex("name")).equals(string)) {
                    if (i >= 10) {
                        break;
                    }
                    arrayList.add(rawQuery.getString(rawQuery.getColumnIndex("steps")));
                    i++;
                }
            } while (rawQuery.moveToNext());
        }
        writableDatabase.close();
        return arrayList;
    }

    public ArrayList<Double> getDistance() {
        int i = 0;
        String string = this.context.getSharedPreferences("CurrentProfile", 0).getString("name", "0");
        ArrayList<Double> arrayList = new ArrayList<>();
        SQLiteDatabase writableDatabase = getWritableDatabase();
        Cursor rawQuery = writableDatabase.rawQuery("SELECT * FROM recordsUsers ORDER BY date(Date) DESC", null);
        if (rawQuery.moveToFirst()) {
            do {
                if (rawQuery.getString(rawQuery.getColumnIndex("name")).equals(string)) {
                    if (i >= 10) {
                        break;
                    }
                    double d = 0.0d;
                    try {
                        d = NumberFormat.getInstance(Locale.US).parse(rawQuery.getString(rawQuery.getColumnIndex("distance"))).doubleValue();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    arrayList.add(Double.valueOf(d));
                    i++;
                }
            } while (rawQuery.moveToNext());
        }
        writableDatabase.close();
        return arrayList;
    }

    public String checkIfDateEntered(String str) {
        try {
            SQLiteDatabase readableDatabase = getReadableDatabase();
            Cursor rawQuery = readableDatabase.rawQuery("select Date from recordsUsers where Date ='" + str + "'", null);
            if (rawQuery != null) {
                rawQuery.moveToFirst();
            }
            String string = rawQuery.getString(rawQuery.getColumnIndex(DbTable.Date));
            rawQuery.close();
            return string;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<String> getCalories() {
        int i = 0;
        String string = this.context.getSharedPreferences("CurrentProfile", 0).getString("name", "0");
        ArrayList<String> arrayList = new ArrayList<>();
        SQLiteDatabase writableDatabase = getWritableDatabase();
        Cursor rawQuery = writableDatabase.rawQuery("SELECT * FROM recordsUsers ORDER BY date(Date) DESC", null);
        if (rawQuery.moveToFirst()) {
            do {
                if (rawQuery.getString(rawQuery.getColumnIndex("name")).equals(string)) {
                    if (i >= 10) {
                        break;
                    }
                    arrayList.add(rawQuery.getString(rawQuery.getColumnIndex(DbTable.Calories)));
                    i++;
                }
            } while (rawQuery.moveToNext());
        }
        writableDatabase.close();
        return arrayList;
    }

    public boolean deleteRow(String str) {
        return getReadableDatabase().delete(DbTable.TABLE_NAME, "columnid=?", new String[]{str}) > 0;
    }

    public void deleteAll(String str) {
        getWritableDatabase().delete(DbTable.TABLE_NAME, "name=?", new String[]{str});
    }
}
