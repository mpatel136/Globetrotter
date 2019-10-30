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

/**
 * Controller for controlling the views in the layout and the ItemsList model.
 */

public class ItemsListController {

    //Instantiates the access to the database
    private Access databaseAccessHelper;

    //Instantiates the SQLite database
    private SQLiteDatabase sqLiteDatabase;

    //Instantiates the name of the dtabase table
    private static final String TABLE_NAME = "items";

    //Instantiates the field for the item's name
    private static final String ITEM_NAME = "name";

    //Instantiates the field for the item's quantity
    private static final String QUANTITY = "quantity";

    //Instantiates a tag for logging
    private static final String TAG = ItemsListController.class.getSimpleName();

    /**
     * Constructor for the Items List Controller
     * @param ctx The current context of the application
     */
    public ItemsListController(Context ctx){
        //Gets the instance of the context and assigns it the database access helper
        this.databaseAccessHelper = Access.getInstance(ctx);
    }

    /**
     * Gets all the the items in list
     * @return The list of items
     */
    public List<Item> getAllItems(){
        //Instantiates a list of the items
        List<Item> list = new ArrayList<>();

        //Instantiates the cursor and sets it to null
        Cursor cursor = null;

        //opening the database
        sqLiteDatabase = this.databaseAccessHelper.openDatabase();
        try {
            Log.wtf("getAllItems","I'm there1");

            //Set the cursor to select everything for the table
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
            Log.wtf("getAllItems","I'm there2");

            Log.i("getAllItems", cursor.getCount() + "");

            //Set the cursor to move to the next entry in the table
            cursor.moveToFirst();
            Log.wtf("getAllItems","I'm there3");

            //While the cursor is not out of bounds and we have not reached the end of the database entries
            while (!cursor.isAfterLast()){
                Log.i("getAllItems",""+ cursor.getString(0));

                //Add the item to the list
                list.add(new Item(cursor.getInt(cursor.getColumnIndex("position")),cursor.getString(cursor.getColumnIndex("name")), cursor.getInt(cursor.getColumnIndex("quantity"))));

                //Move the cursor to the next entry
                cursor.moveToNext();
            }
            Log.wtf("getAllItems","I tried and I did it");
        }
        catch (Exception e){
            e.printStackTrace();
            Log.wtf("getAllItems","exception");
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
        return list;
    }

    /**
     * Creates the item with the position, the name of the item, and the quantity of that item
     * @param item The item to add to the database
     */
    public void createItem(Item item){
        //Opens the database
        sqLiteDatabase = this.databaseAccessHelper.openDatabase();

        //Begin transaction/edits on the database
        sqLiteDatabase.beginTransaction();
        try{
            //Instantiates the values variable to add the fields to the item
            ContentValues cv = new ContentValues();

            //Put the position to the values
            cv.put("position", item.getPosition());

            //Put the name of the item to the values
            cv.put(ITEM_NAME, item.getItemName());

            //Put the quantity of the item to the values
            cv.put(QUANTITY, item.getQuantity());

            //Insert the values to the table
            sqLiteDatabase.insert(TABLE_NAME,null,cv);

            //Marks the transaction as successful
            sqLiteDatabase.setTransactionSuccessful();
        }
        catch (Exception e){
            Log.wtf(TAG, e.fillInStackTrace());
        } finally {
            //Ends the transaction for the database
            sqLiteDatabase.endTransaction();

            //Close the database
            this.databaseAccessHelper.closeDatabase();
        }
    }

    /**
     * Deletes the item in the database
     * @param item The item to be deleted
     */
    public void deleteItem(Item item)
    {
        //Opens the database
        sqLiteDatabase = this.databaseAccessHelper.openDatabase();
        try
        {
            //Delete the item at the given position
            sqLiteDatabase.delete(TABLE_NAME, "position = ?", new String[]{item.getPosition() + ""});
        }
        catch(Exception e)
        {
            Log.wtf("Delete", "Could not delete");
        }
        finally
        {
            //Releases reference to the database
            sqLiteDatabase.close();

            //Closes the database
            this.databaseAccessHelper.closeDatabase();
        }
    }

    /**
     * Updates the item to new values
     * @param oldItem The unmodified item
     * @param newItem The modified item
     */
    public void updateItem(Item oldItem, Item newItem){
        //Instantiates the values variable to add the fields to the item
        ContentValues contentValues = new ContentValues();

        //Put the position to the values
        contentValues.put("position", newItem.getPosition());

        //Put the name to the values
        contentValues.put("name", newItem.getItemName());

        //Put the quantity to the values
        contentValues.put("quantity", newItem.getQuantity());

        //Opens the database
        sqLiteDatabase = this.databaseAccessHelper.openDatabase();
        try
        {
            //Updates the database with the values in the content values
            sqLiteDatabase.update(TABLE_NAME, contentValues, "position=" + oldItem.getPosition(),null);
        }
        catch(Exception e)
        {
            Log.wtf("updateItem", "Could not update");
        }
        finally
        {
            //Releases the reference to the database
            sqLiteDatabase.close();

            //Closes the database
            this.databaseAccessHelper.closeDatabase();
        }
    }


}
