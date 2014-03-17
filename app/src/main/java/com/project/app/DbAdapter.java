package com.project.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Mat on 12.03.14.
 */
public  class DbAdapter extends SQLiteOpenHelper {

    public static final int DB_VERSION = 2;
    public static final String DB_BOOKSIGNATURE = "booksignature";
    public static final String DB_SHELF = "shelf";

    public static final String DATABASE_NAME = "databasename";
    public static final String DATABASE_TABLE = "table";

    public static final String DATABASE_CREATE = "create table "
            + DATABASE_TABLE
            + "("
            + DB_BOOKSIGNATURE + " integer primary key autoincrement not null, "
            + DB_SHELF + " text not null"
            + ");";

    DbAdapter(Context context){
        super(context, DATABASE_NAME, null,DB_VERSION );
        Log.i("asd", "asd");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }

}
