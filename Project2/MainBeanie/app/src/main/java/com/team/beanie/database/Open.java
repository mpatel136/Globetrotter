package com.team.beanie.database;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Allows access to open the database
 */
public class Open extends SQLiteAssetHelper {

    //Instantiates the name of the database
    private static final String DB_NAME = "globetrotter.db";

    //Instantiates the version of the database
    private static final int DB_VERSION = 1;

    /**
     * Opens the database
     * @param ctx The current context of the application
     */
    public Open(Context ctx){
        super(ctx, DB_NAME, null, DB_VERSION);

    }

}
