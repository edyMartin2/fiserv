package com.gonzmor.fiserv.databaseAll;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;


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

        db.insert("cards", null, cv);

    }

    public ArrayList<String> getCards(){
        ArrayList <String> cards = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM cards", null);

        if (cursor.moveToFirst()){
            do {
                cards.add(cursor.getString(1));
            } while (cursor.moveToNext());
        } else{
            cards = null;
        }

        return  cards;
    }
}
