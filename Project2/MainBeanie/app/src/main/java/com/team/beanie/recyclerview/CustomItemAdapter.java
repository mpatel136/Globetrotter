package com.team.beanie.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.beanielibrary.model.Item;
import com.team.beanie.R;
import com.team.beanie.database.controller.ItemsListController;

import java.util.ArrayList;

/**
 * Adapter class for the items in the item list
 */
public class CustomItemAdapter extends RecyclerView.Adapter<CustomItemViewHolder> {
    //Instantiates an interface variable
    IRefresh activity;

    //Instantiates an arraylist of items
    private ArrayList<Item> itemsList;

    //Controller for database
    private ItemsListController itemsListController;

    /**
     * Constructor for the adapter of the items
     * @param context The context of the application
     */
    public CustomItemAdapter(Context context){
        //Sets the controller to a new controller with the context
        itemsListController = new ItemsListController(context);

        //Get all the items and set it to the list
        itemsList = (ArrayList<Item>) itemsListController.getAllItems();

        //Sets the context to the activity interface so it can be used to pass data
        activity = (IRefresh) context;
    }

    /**
     * Notify if the item has been removed
     * @param position The position of the item in the list
     */
    public void customNotifyItemRemoved(int position){
        //Indicates if it was updated or not
        boolean update = false;

        //Toast.makeText(context, itemsList.get(position).getPosition() + "", Toast.LENGTH_LONG).show();

        //Deletes the item at the position
        itemsListController.deleteItem(itemsList.get(position));

        try{
            //If the position is not null and there is an item
            if(itemsList.get(position) != null){

            //Sets the update to true
            update = true;

            //For each iteration through the list
            for(int i = position; i < itemsList.size()-1;i++){

                //Create the item with the name and quantity
                Item item = new Item(i+1, itemsList.get(i+1).getItemName(), itemsList.get(i+1).getQuantity());
                //item = itemsList.get(i);
                Log.i("updateItem", "New Item " + item.getItemName() + " " + item.getPosition() + "");
                Log.i("updateItem", "Original " + itemsList.get(i+1).getItemName() + " " + itemsList.get(i+1).getPosition() + "");

                itemsListController.updateItem(itemsList.get(i+1), item);

            }
        }}
        catch(Exception e){
            Log.i("update","update not needed");
        }

        //Refresh the activity
        activity.refresh();
        //for each item after the deleted item, -1 on position
        //Use update to update the position in the database
    }

    /**
     * Notify which item is being updated
     * @param position The position of the item
     */
    public void notifyWhatItemUpdate(int position){

        //Update the activity with the item in the list at the position
        activity.update(itemsList.get(position));

        //Refresh the activity
        refresh();
    }

    /**
     * Update the item with new values
     * @param item The item with new updated values
     */
    public void update(Item item){
        Log.wtf("updatingItem","nice guy");
        //Updates the item at the position with the new item
        itemsListController.updateItem(itemsList.get(item.getPosition()-1), item);

        refresh();

    }

    /**
     * Refreshes the activity
     */
    private void refresh(){
        activity.refresh();
    }

    //Used as a representation of the activity
    Context context;


    /**
     * On create of the view holder, inflate the view with the XML
     * @param viewGroup A group of views
     * @param i Index
     * @return A view holder
     */
    @Override
    public CustomItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //Get the view group context
        context = viewGroup.getContext();

        //Inflate the view with the XML
        View view = LayoutInflater.from(context).inflate(R.layout.item_entry_layout, viewGroup, false);

        //Set the view holder to the view with the adapter and the item list
        CustomItemViewHolder viewHolder = new CustomItemViewHolder(view, this, itemsList);

        //Returns the view holder
        return viewHolder;
    }

    /**
     * On the binding of the view holder, set the entry
     * @param customItemViewHolder The view holder
     * @param i The position
     */
    @Override
    public void onBindViewHolder(CustomItemViewHolder customItemViewHolder, int i) {
        Log.wtf("recyclerBeanie", "onBindViewHolder1");
        //Get the item at the index
        Item item = (Item) itemsList.get(i);
        Log.wtf("recyclerBeanie", "onBindViewHolder2: " + item.getItemName());

        //Set the entry of the view holder
        customItemViewHolder.setEntry(item);
        Log.wtf("recyclerBeanie", "onBindViewHolder3");

    }


    /**
     * Creates the item
     * @param item The item to be created.
     */
    public void createItem(Item item){
        //Create the item in the controller
        itemsListController.createItem(item);
    }

    /**
     * Gets the number of items
     * @return The number of items
     */
    @Override
    public int getItemCount() {
        return itemsList.size();
    }


}
