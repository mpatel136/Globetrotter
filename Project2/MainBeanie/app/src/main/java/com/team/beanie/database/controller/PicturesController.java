package com.team.beanie.database.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.team.beanie.database.Access;

public class PicturesController {
    private Access databaseAccessHelper;
    private SQLiteDatabase sqLiteDatabase;

    public PicturesController(Context context) {
        this.databaseAccessHelper = Access.getInstance(context);
    }

    public Bitmap getAllPictures(int id) {
        Bitmap bitmap = null;
        //-- 1) Open the database.
        sqLiteDatabase = this.databaseAccessHelper.openDatabase();
        try {
            // 2) Query the database.
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM  pictures WHERE id=?", new String[]{String.valueOf(id)});
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                byte[] image = cursor.getBlob(cursor.getColumnIndex("image"));
                bitmap = BitmapFactory.decodeByteArray(image,0,image.length);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //-- 3) Close the DB connection database and release any locked resources.
            this.databaseAccessHelper.closeDatabase();
        }
        return bitmap;
    }
}
