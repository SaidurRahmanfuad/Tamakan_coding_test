package com.saidur.own.tamakan.DB;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.saidur.own.tamakan.Model.ModelSession;
public class DBCrudHelper {
    DBMain dbMain;
    Context context;

    public DBCrudHelper(Context context) {
        this.context = context;
        dbMain = new DBMain(context);
    }
    public boolean CheckDataInTable(String tableName) {
        SQLiteDatabase database = dbMain.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM '" + tableName + "'", null);
        int count = cursor.getCount();
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }
    @SuppressLint("Range")
    public boolean checkSessonExist() {
        SQLiteDatabase database = dbMain.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM  tblSession", null);
        int count = cursor.getCount();
        if (count > 0) {
            return true;
        } else {
            return false;
        }

    }
    public void _deleteAllFromTable(String tableName) {
        SQLiteDatabase database = dbMain.getWritableDatabase();
        try {
            String query = "delete from '" + tableName + "';";
            database.execSQL(query);

        } catch (Exception ex) {
            Log.e("recordDelError", ex.toString());
        }
    }
    public boolean _deleteAllFromTableBolean(String tableName) {
        SQLiteDatabase database = dbMain.getWritableDatabase();
        try {
            String query = "delete from '" + tableName + "';";
            database.execSQL(query);

        } catch (Exception ex) {
            Log.e("recordDelError", ex.toString());
        }
        return true;
    }
    public boolean InsertSessionInfo(ModelSession session) {
        try {
            String tableName = "tblSession";
            _deleteAllFromTable(tableName);
            SQLiteDatabase database = dbMain.getWritableDatabase();
            String insertQuery = "Insert into tblSession(UserId,name,department,type,created_at," +
                    "updated_at,access_token,token_type,expires_at) " +
                    "values('" + session.getUserId() + "','" + session.getName() + "','" + session.getDepartment() + "','" + session.getType() + "'," +
                    "'" + session.getCreated_at() + "','" + session.getUpdated_at() + "','" + session.getAccess_token() + "','" + session.getToken_type() + "','" + session.getExpires_at() + "')";

            database.execSQL(insertQuery);

        } catch (Exception exception) {
            Log.e("db", exception.toString());
        }
        return true;
    }
    @SuppressLint("Range")
    public ModelSession getSession() {
        SQLiteDatabase database = dbMain.getWritableDatabase();
        String oderMasterQuery = "Select * from tblSession";
        ModelSession user=new ModelSession();
        try {
            Cursor cursor;
            cursor = database.rawQuery(oderMasterQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    user.setUserId(cursor.getInt(cursor.getColumnIndex("UserId")));
                    user.setName(cursor.getString(cursor.getColumnIndex("name")));
                    user.setDepartment(cursor.getString(cursor.getColumnIndex("department")));
                    user.setType(cursor.getString(cursor.getColumnIndex("type")));
                    user.setCreated_at(cursor.getString(cursor.getColumnIndex("created_at")));
                    user.setUpdated_at(cursor.getString(cursor.getColumnIndex("updated_at")));
                    user.setAccess_token(cursor.getString(cursor.getColumnIndex("access_token")));
                    user.setToken_type(cursor.getString(cursor.getColumnIndex("token_type")));
                    user.setExpires_at(cursor.getString(cursor.getColumnIndex("expires_at")));
                }
            }
            cursor.close();
        } catch (Exception exception) {
            Log.e("tblSession", exception.toString());
            exception.printStackTrace();
        }
        return user;
    }

}
