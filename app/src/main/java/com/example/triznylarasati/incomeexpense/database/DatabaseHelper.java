package com.example.triznylarasati.incomeexpense.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Trizny Larasati on 11/3/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    //table di tambah 1 kolom createdOn untuk menyimpan waktu penginsertan
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Transaction.db";
    private static final String TABLE_NAME = "transactions";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "NAME";
    private static final String COL_3 = "AMOUNT";
    private static final String COL_4 = "CREATEDON";
    private static final String COL_5 = "FLAG";
    private static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " ( " +
            COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_2 + " TEXT, " +
            COL_3 + " INTEGER, " +
            COL_4 + " TEXT, " +
            COL_5 + " TEXT );";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS transaction" + TABLE_NAME);
        onCreate(db);
    }

    //method untuk menyimpan data dengan flag expense
    public boolean save_expense(String expense_name, String expense_amount,String createdOn) {
        //table di tambah 1 kolom createdOn untuk menyimpan waktu penginsertan
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content_values = new ContentValues();
        content_values.put(COL_2, expense_name);
        content_values.put(COL_3, Integer.parseInt(expense_amount));
        content_values.put(COL_4, createdOn);
        content_values.put(COL_5, "expense");
        long result = db.insert(TABLE_NAME, null, content_values);
        return result != -1;
    }

    //method untuk menyimpan data dengan flag income
    public boolean save_income(String income_name, String income_amount,String createdOn) {
        //table di tambah 1 kolom createdOn untuk menyimpan waktu penginsertan
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content_values = new ContentValues();
        content_values.put(COL_2, income_name);
        content_values.put(COL_3, Integer.parseInt(income_amount));
        content_values.put(COL_4, createdOn);
        content_values.put(COL_5, "income");
        long result = db.insert(TABLE_NAME, null, content_values);
        return result != -1;
    }

    //method untuk mengambil data row name dan amount dengan flag expense
    public Cursor list_expense() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor transaction = db.rawQuery("SELECT * FROM "+ TABLE_NAME+" WHERE FLAG = 'expense'", null);
        return transaction;
    }

    //method untuk mengambil data row name dan amount dengan flag income
    public Cursor list_income() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor transaction = db.rawQuery("SELECT * FROM "+ TABLE_NAME+" WHERE FLAG = 'income'", null);
        return transaction;
    }

    /*public boolean update_transaction(String id, String expense_name, String expense_amount, String income_name, String income_amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content_values = new ContentValues();
        content_values.put(COL_1, id);
        content_values.put(COL_2, expense_name);
        content_values.put(COL_3, expense_amount);
        content_values.put(COL_4, income_name);
        db.update(TABLE_NAME, content_values, "ID = ? ", new String[]{id});
        return true;
    }

    public Integer delete_transaction(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[] {id});
    }*/
}
