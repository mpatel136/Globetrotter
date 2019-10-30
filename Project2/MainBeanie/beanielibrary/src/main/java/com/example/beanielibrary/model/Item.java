package com.example.beanielibrary.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A Representation of an item.
 * It is a parcelable so that it can be sent through intents and bundles.
 */
public class Item implements Parcelable {

    /**
     * The quantity of the item.
     */
    int quantity;
    /**
     * The name of the item.
     */
    String itemName;
    /**
     * The position of the item in the recycler view and in the database.
     */
    int position;

    /**
     * Creates an empty Item.
     */
    public Item(){

    }

    /**
     * Sets the quantity of the item.
     * @param quantity The amount that the item is set to.
     */
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    /**
     * Sets the name of the item.
     * @param itemName The name that the item is set to,
     */
    public void setItemName(String itemName){
        this.itemName = itemName;
    }

    /**
     * Creates an item.
     * @param position The position of the item.
     * @param itemName The name of the item.
     * @param quantity The amount of the item.
     */
    public Item(int position, String itemName, int quantity){
        this.position = position;
        this.itemName = itemName;
        this.quantity = quantity;
    }

    /**
     *
     * @param in
     */
    protected Item(Parcel in) {
        position = in.readInt();
        quantity = in.readInt();
        itemName = in.readString();
    }

    /**
     * Creates an Item from a parcelable.
     */
    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        /**
         *
         * @param size
         * @return
         */
        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    /**
     * Returns the amount of the item.
     * @return quantity.
     */
    public int getQuantity(){
        return quantity;
    }

    /**
     * Returns the name of the item.
     * @return
     */
    public String getItemName(){
        return itemName;
    }
    public int getPosition(){return position;}

    /**
     * Describes the contents of the list
     * @return An integer representation
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Writes to the parcelable object
     * @param dest The destination of the parcelable object
     * @param flags A flag indicator with 2 options of true/false
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(position);
        dest.writeInt(quantity);
        dest.writeString(itemName);
    }
}
