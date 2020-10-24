package com.gonzmor.fiserv.databaseAll;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class controllerWallet {
    SQLiteDatabase db = null;

    public controllerWallet(Context context) {
        database con = new database(context);
        this.db = con.getWritableDatabase();
    }

    public Boolean getData(){
        Cursor c = this.db.rawQuery("SELECT * FROM wallet ", null);

        if (c.moveToFirst()){
            return  true;
        } else{
            return false;
        }


    }

    public void  insertNewWallet(String number, String cvv, String date){
        ContentValues cv = new ContentValues();
        cv.put("Number", number);
        cv.put("CVV", cvv);
        cv.put("Date", date);
        db.insert("wallet", null, cv);

    }
}
