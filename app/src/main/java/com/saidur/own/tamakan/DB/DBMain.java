package com.saidur.own.tamakan.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBMain extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "SaidurTamakanDB.db";
    private static final int DATABASE_VERSION = 1;
    private Context context;

    public DBMain(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Session Table
        String tblSession = "Create Table if not exists tblSession" +
                "(UserId Integer,name varchar(500),email varchar(500),department varchar(500),type varchar(500),created_at varchar(500),updated_at varchar(500),access_token varchar(500),token_type varchar(500),expires_at varchar(500))";
        //Initial Table
        String tblInitTable = "Create Table if not exists tblInitTable" +
                "(IsLoginDone Integer,Date varchar(500),Time varchar(500))";
        try {
            sqLiteDatabase.execSQL(tblSession);
            sqLiteDatabase.execSQL(tblInitTable);

        } catch (Exception e) {
            Log.e("SQLiteDBHelper", e.toString());
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //version 1
        try {
            db.execSQL("ALTER TABLE tblSession ADD COLUMN Remarks varchar(500)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}
