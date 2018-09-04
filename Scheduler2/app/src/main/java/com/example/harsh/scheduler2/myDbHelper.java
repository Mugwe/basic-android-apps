package com.example.harsh.scheduler2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class myDbHelper extends SQLiteOpenHelper {

    SQLiteDatabase db;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "id3568543_fixitapp";

    public static final String TABLE_USERS = "users";
   /* public static final String FIRSTNAME = "firstname";
    public static final String LASTNAME = "lastname"; */
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    public myDbHelper (Context con) {
        super(con, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {

        String Createdb = "CREATE TABLE " + TABLE_USERS + "("
                + USERNAME + " TEXT,"
                + PASSWORD + " TEXT"
                + ")";
        db.execSQL(Createdb);
        Log.d("created", "table created");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
        Log.d("created", "table REcreated");
    }

    public void addUser(String username, String password) {
        String putData = "insert into "+TABLE_USERS+" ( "+USERNAME+","+PASSWORD+")values('" +username+"','"+password+"')";
        db = this.getWritableDatabase();
        db.execSQL(putData);
        /*ContentValues rec = new ContentValues();
        rec.put(USERNAME, username);
        rec.put(PASSWORD, password);
        db.insert(TABLE_USERS, null, rec);*/
        db.close();
        Log.d("created", "new Record created");
    }

    public void defaultCredentials() {
        db = this.getWritableDatabase();
        ContentValues rec = new ContentValues();
       /* rec.put(FIRSTNAME, "firstname");
        rec.put(LASTNAME, "lastname"); */
        rec.put(USERNAME, "username");
        rec.put(PASSWORD, "password");
        db.insert(TABLE_USERS, null, rec);
        db.close();
        Log.d("created", "Record created");
    }

    public boolean validation( String username, String password) {
        String uname = "";
        String pword = "";
        boolean flag = false;
        db = this.getReadableDatabase();

        String sql="Select * from users where username='"+username+"' and password='"+password+"'";
        Cursor cs = db.rawQuery(sql, null);

        if (cs.moveToFirst()){
            if (cs.getColumnIndex("username")!=-1){
                uname = cs.getString(cs.getColumnIndex("username"));
                pword = cs.getString(cs.getColumnIndex("password"));
                if (username.equals(uname) && password.equals(pword)) {
                    flag = true;
                }else flag = false;
            }
            else flag=false;
        }else flag=false;


        return flag;
    }}
