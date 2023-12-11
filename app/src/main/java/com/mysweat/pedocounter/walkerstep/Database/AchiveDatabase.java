package com.mysweat.pedocounter.walkerstep.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;


public class AchiveDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "achieve_db";
    Context context;
    ArrayList<String> islamics;

    public AchiveDatabase(Context context) {
        super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 2);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(TableAchive.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS achievedata");
        onCreate(sQLiteDatabase);
    }

    public String insertAchieveSteps(String str, String str2) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", str);
            contentValues.put("steps", str2);
            int i = (writableDatabase.insert(TableAchive.TABLE_NAME, null, contentValues) > 0L ? 1 : (writableDatabase.insert(TableAchive.TABLE_NAME, null, contentValues) == 0L ? 0 : -1));
            writableDatabase.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String insertAchieveDistance(String str, String str2) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", str);
            contentValues.put("distance", str2);
            int i = (writableDatabase.insert(TableAchive.TABLE_NAME, null, contentValues) > 0L ? 1 : (writableDatabase.insert(TableAchive.TABLE_NAME, null, contentValues) == 0L ? 0 : -1));
            writableDatabase.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String insertAchieveCalories(String str, String str2) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", str);
            contentValues.put(TableAchive.Calorie, str2);
            int i = (writableDatabase.insert(TableAchive.TABLE_NAME, null, contentValues) > 0L ? 1 : (writableDatabase.insert(TableAchive.TABLE_NAME, null, contentValues) == 0L ? 0 : -1));
            writableDatabase.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<String> checkAchieveEntrySteps(String str) {
        try {
            this.islamics = new ArrayList<>();
            SQLiteDatabase writableDatabase = getWritableDatabase();
            Cursor rawQuery = writableDatabase.rawQuery("select * from achievedata", null);
            if (rawQuery.moveToFirst()) {
                this.islamics.add(rawQuery.getString(rawQuery.getColumnIndex("steps")));
            }
            writableDatabase.close();
            return this.islamics;
        } catch (Exception unused) {
            return this.islamics;
        }
    }

    public String getUserAchievement(String str) {
        try {
            SQLiteDatabase readableDatabase = getReadableDatabase();
            Cursor rawQuery = readableDatabase.rawQuery("select Achievement from achievedata where Name ='" + str + "'", null);
            if (rawQuery.moveToFirst()) {
                do {
                } while (rawQuery.moveToNext());
                rawQuery.close();
                return null;
            }
            rawQuery.close();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getUserAchievementSteps(String str) {
        try {
            SQLiteDatabase readableDatabase = getReadableDatabase();
            Cursor rawQuery = readableDatabase.rawQuery("select Steps from achievedata where Name ='" + str + "'", null);
            String string = rawQuery.moveToFirst() ? rawQuery.getString(rawQuery.getColumnIndex("steps")) : "";
            rawQuery.close();
            return string == null ? "0" : string;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getUserAchievementDistance(String str) {
        try {
            SQLiteDatabase readableDatabase = getReadableDatabase();
            Cursor rawQuery = readableDatabase.rawQuery("SELECT Distance FROM achievedata where Name ='" + str + "'", null);
            String string = rawQuery.moveToFirst() ? rawQuery.getString(rawQuery.getColumnIndex("distance")) : "";
            rawQuery.close();
            if (string != null) {
                if (!string.equals("")) {
                    return string;
                }
            }
            return "0";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getUserAchievementCalories(String str) {
        try {
            SQLiteDatabase readableDatabase = getReadableDatabase();
            Cursor rawQuery = readableDatabase.rawQuery("select Calorie from achievedata where Name ='" + str + "'", null);
            String string = rawQuery.moveToFirst() ? rawQuery.getString(rawQuery.getColumnIndex(TableAchive.Calorie)) : null;
            rawQuery.close();
            return string == null ? "0" : string;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<String> checkAchieveEntry(String str) {
        try {
            this.islamics = new ArrayList<>();
            SQLiteDatabase writableDatabase = getWritableDatabase();
            Cursor rawQuery = writableDatabase.rawQuery("select * from achievedata where Name ='" + str + "'", null);
            if (rawQuery.moveToFirst()) {
                do {
                    this.islamics.add(rawQuery.getString(rawQuery.getColumnIndex("name")));
                } while (rawQuery.moveToNext());
                writableDatabase.close();
                return this.islamics;
            }
            writableDatabase.close();
            return this.islamics;
        } catch (Exception unused) {
            return this.islamics;
        }
    }

    public int updateAchieveSteps(String str, String str2) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("steps", str2);
            return writableDatabase.update(TableAchive.TABLE_NAME, contentValues, "name = ?", new String[]{str});
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int updateAchieveDistance(String str, String str2) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("distance", str2);
            return writableDatabase.update(TableAchive.TABLE_NAME, contentValues, "name = ?", new String[]{str});
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int updateAchieveCalories(String str, String str2) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(TableAchive.Calorie, str2);
            return writableDatabase.update(TableAchive.TABLE_NAME, contentValues, "name = ?", new String[]{str});
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
