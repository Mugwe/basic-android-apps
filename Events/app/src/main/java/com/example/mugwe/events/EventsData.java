package com.example.mugwe.events;

import static android.provider.BaseColumns._ID;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EventsData extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "events.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Events";
    private static final String TITLE = "eventTitle";
    private static final String TIME = "eventTime";


    /**
     * Create a helper object for the Events database
     */
    public EventsData(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + _ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TIME
                + " INTEGER," + TITLE + " TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}
