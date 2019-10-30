package com.team.beanie.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Allows connection between database and device with permissions to open and close it
 */
public class Access {
    //Instantiates the helper to open the database
    private SQLiteOpenHelper openHelper;
    //Instantiates the SQLite database
    private SQLiteDatabase database;
    //Instantiates the access to the instance of the database
    private static Access instance;

    /**
     * Calls the open helper to access the database by using the context
     * @param ctx The current context of the application
     */
    private Access(Context ctx){
        this.openHelper = new Open(ctx);
    }

    /**
     * Returns the access for the specific instance
     * @param context The current context of the application
     * @return an instance of the Access class
     */
    public static synchronized Access getInstance(Context context){
        if(instance == null)
            instance = new Access(context);
        return instance;
    }

    /**
     * Opens the database
     * @return The database instance
     */
    public SQLiteDatabase openDatabase(){
        this.database = openHelper.getWritableDatabase();
        return this.database;
    }

    /**
     * Closes the database
     */
    public void closeDatabase(){
        if(database != null){
            this.database.close();
        }
    }
}
