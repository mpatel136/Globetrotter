package com.team.beanie.database.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.beanielibrary.model.Item;
import com.team.beanie.database.Access;

import java.util.ArrayList;
import java.util.List;

//The purpose of this controller is to return a list of the types of currency available in the API.
//Another purpose is to allow me to parse all the types from my java application into the database.
public class CurrencyTypeController{

    //The access to the database reference
    private Access databaseAccessHelper;

    //The SQLite database reference
    private SQLiteDatabase sqLiteDatabase;

    //Instantiates the name of the dtabase table
    private static final String TABLE_NAME = "currency";

    private static final String TYPE = "type";


    public CurrencyTypeController(Context ctx){
        this.databaseAccessHelper = Access.getInstance(ctx);
    }

    /**
     *
     */
    public List<String> getAll(){
        List<String> types = new ArrayList<>();

        Cursor cursor = null;

        //opening the database
        sqLiteDatabase = this.databaseAccessHelper.openDatabase();

        try {
            //Set the cursor to select everything for the table
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);

            Log.i("CurrencyExchange", cursor.getCount() + "");

            //Set the cursor to move to the next entry in the table
            cursor.moveToFirst();

            //While the cursor is not out of bounds and we have not reached the end of the database entries
            while (!cursor.isAfterLast()){

                //Add the item to the list
                types.add(cursor.getString(cursor.getColumnIndex("type")));

                //Move the cursor to the next entry
                cursor.moveToNext();
            }
        }
        catch (Exception e){
            e.printStackTrace();

        }
        finally {
            //Closes the database
            this.databaseAccessHelper.closeDatabase();

            //If the cursor is not out of bounds of the database
            if(cursor != null){
                //Closes the cursor
                cursor.close();
            }
        }
        //Return the list of items
        return types;
    }

    //Deletes everything in the database to enable it to be reparsed again with new types.

    //Adds a type
    public void addCurrencyType(String type){
        sqLiteDatabase = this.databaseAccessHelper.openDatabase();

        ContentValues cv = new ContentValues();
        cv.put("type",type);
            sqLiteDatabase.beginTransaction();
        try {


            sqLiteDatabase.insert(TABLE_NAME,null,cv);

            sqLiteDatabase.setTransactionSuccessful();


        }catch (Exception e){
            e.getStackTrace();
            Log.wtf("currencyDatabase","addCurrencyFail");

        }
        finally {
            sqLiteDatabase.endTransaction();
            sqLiteDatabase.close();
        }
    }

}
