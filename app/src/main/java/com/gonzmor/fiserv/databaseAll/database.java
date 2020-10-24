package com.gonzmor.fiserv.databaseAll;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class database extends SQLiteOpenHelper {

    private static final String createDatabase = "CREATE TABLE cards(ID INTEGER PRIMARY KEY AUTOINCREMENT, Number VARCHAR(18), CVV VARCHAR(3), Date VARCHAR(5) )";

    public database(@Nullable Context context ) {
        super(context, "wallet.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS cards");
    }
}
