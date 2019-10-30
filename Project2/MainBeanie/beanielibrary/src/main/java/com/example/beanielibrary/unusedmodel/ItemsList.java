package com.example.beanielibrary.unusedmodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.beanielibrary.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Unused
 * This is a representation of the ItemsList.
 * It is a parcelable so that it can passed between intents and bundles.
 */
public class ItemsList<E> implements Parcelable {

    //Instantiates an arraylist of items
    private ArrayList<Item> list;

    /**
     * Constructor for the items list
     */
    public ItemsList(){
        list = new ArrayList<>();
    }

    /**
     * Constructor taking in a list as parameter
     * @param list List of items
     */
    public ItemsList(List<E> list) {
        super();


    }

    /**
     * Deletes an item at the index
     * @param index The position of the item
     */
    public void deleteItem(int index){
        list.remove(index);
    }

    /**
     * Constructor taking in a parcelable as parameter
     * @param in A parcelable object
     */
    protected ItemsList(Parcel in)
    {
    }

    /**
     * Writes to the parcelable object
     * @param dest The destination of the parcelable object
     * @param flags A flag indicator with 2 options of true/false
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    /**
     * Describes the contents of the list
     * @return An integer representation
     */
    @Override
    public int describeContents() {
        return 0;
    }

    //Instantiates a object that generates instances of parcelable class from a parcel.
    public static final Creator<ItemsList> CREATOR = new Creator<ItemsList>() {
        /**
         * Creates an itemlist from the parcelable
         * @param in The parcel object
         * @return The itemlist
         */
        @Override
        public ItemsList createFromParcel(Parcel in) {
            return new ItemsList(in);
        }

        /**
         * Creates a new array of items
         * @param size The size of the array
         * @return An array of items
         */
        @Override
        public ItemsList[] newArray(int size) {
            return new ItemsList[size];
        }
    };

    /**
     * Add an item to the list
     * @param item The item to be added to the list
     */
    public void addItem(E item){
        if(item instanceof Item){
            list.add((Item) item);
        }
    }

    /**
     * Retrieves the list of items
     * @return The list of items
     */
    public List<Item> getList(){
        return list;
    }



}
