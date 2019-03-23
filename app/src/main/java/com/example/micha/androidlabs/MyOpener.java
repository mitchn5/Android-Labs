package com.example.micha.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyOpener extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MyDatabaseFile";
    public static final int VERSION_NUM = 2;
    public static final String TABLE_NAME = "Messages";
    public static final String COL_ID = "_id";
    public static final String COL_TYPE = "TYPE";
    public static final String COL_TEXT = "TEXT";

    public MyOpener(Context c) {
        super(c, DATABASE_NAME, null, VERSION_NUM );
    }

    public void onCreate(SQLiteDatabase db) {
        //Make sure you put spaces between SQL statements and Java strings:
        db.execSQL("CREATE TABLE " + TABLE_NAME + "( "
                + COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_TYPE + " TEXT, "
                + COL_TEXT + " TEXT)");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.i("Database upgrade", "Old version:" + oldVersion + " newVersion:"+newVersion);
        //Delete the old table:
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        //Create a new table:
        onCreate(db);
    }
}
