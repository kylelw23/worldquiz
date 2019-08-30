package com.example.worldquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SessionDatabase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "sessiondb.db";
    public static final String TABLE_NAME = "session";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NICKNAME";
    public static final String COL_3 = "DATE";
    public static final String COL_4 = "POINTS";
    public SessionDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    //creating the table
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NICKNAME TEXT, DATE TEXT, POINTS TEXT)");
    }
    // called when the database needs to be upgraded to the new schema version
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
        onCreate(db);
    }
    //adding a record
    public boolean addSession(String itemname, String remarks, String qty) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, itemname);
        contentValues.put(COL_3, remarks);
        contentValues.put(COL_4, qty);
        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }
    //retrieving all records
    public Cursor viewAllSession() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return res;
    }
    //retrieving single record matching the id
    public Cursor getSession(int id) {
        //String[] coloumns = new String[] { "ID", "ITEMNAME", "REMARKS", "QTY" };
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME +
                " WHERE ID=" +id, null);
        //Cursor res = sqLiteDatabase.query(TABLE_NAME, coloumns, "ID=" +id,null, null, null, null);
        return res;
    }

    //updating the record matching the id
    public boolean updateQuestion(String id, String points){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_4, points);
        sqLiteDatabase.update(TABLE_NAME, contentValues,"ID = ?",
                new String[] {id});
        return true;
    }
    //deleting the record matching the id
    public Integer deleteQuestion(String id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }
}
