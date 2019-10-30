package com.team.beanie.recyclerview;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beanielibrary.model.Item;
import com.pedromassango.doubleclick.DoubleClick;
import com.pedromassango.doubleclick.DoubleClickListener;
import com.team.beanie.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for the view holder
 */
public class CustomItemViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

    //Instantiates an adapter
    CustomItemAdapter adapter;

    //Instantiate a list of entries
    private List<Item> itemsList;


    //Instantiates the components
    private TextView itemName;
    private TextView quantity;

    //Instantiates the item
    Item item;


    /**
     * Constructor for the view holder
     * @param itemView The view
     * @param adapter The adapter for the items and view
     * @param itemsList The list of items
     */
    public CustomItemViewHolder(@NonNull View itemView, CustomItemAdapter adapter, ArrayList<Item> itemsList) {
        super(itemView);
        this.adapter = adapter;
        this.itemsList = itemsList;

        //find the components of the layout
        ImageView delete = (ImageView) itemView.findViewById(R.id.image_delete);

        //Set on long click listener for the delete icon
        delete.setOnLongClickListener(this);
//        delete.setTag(7, getAdapterPosition());
        setDoubleClick();

        //Get the components
        itemName = (TextView) itemView.findViewById(R.id.txt_itemname);
        quantity = (TextView) itemView.findViewById(R.id.txt_quantity);
    }

    /**
     * Indicates what occurs on double click
     */
    private void setDoubleClick(){
        //Set on double click on the item's card
        ((CardView) itemView.findViewById(R.id.entity)).setOnClickListener(new DoubleClick(new DoubleClickListener() {
            @Override
            public void onSingleClick(View view) {
                Toast.makeText(adapter.context,getAdapterPosition() + "" , Toast.LENGTH_LONG).show();
            }

            //Indicats what occurs on double click
            @Override
            public void onDoubleClick(View view) {
                Log.i("DoubleClick","i clicked it" + getAdapterPosition());
                //Update the item with the new values
                updateEntry(getAdapterPosition());
            }
        }));
    }

    /**
     * Sets the entry with new values
     * @param item The item to be changed
     */
    public void setEntry(Item item){
        Log.wtf("recyclerBeanie","setEntry1");
        this.item = item;
        Log.wtf("recyclerBeanie","setEntry2");

        //Set the name and quantity of the item
        this.itemName.setText(item.getItemName());
        this.quantity.setText(item.getQuantity() + "");
        Log.wtf("recyclerBeanie","setEntry3");

    }

    /**
     * Update the item
     * @param position The position of the item
     */
    private void updateEntry(int position){
        //Notify the adapter of item update
        this.adapter.notifyWhatItemUpdate(position);
    }

    /**
     * Delete the item
     * @param position The position of the item
     */
    private void deleteEntry(int position){
        //this.itemsList.remove(position);

        //Tells the adapter to refresh the list
        this.adapter.customNotifyItemRemoved(position);
        this.adapter.notifyItemRemoved(position);
        this.adapter.notifyItemRangeChanged(position, this.itemsList.size());
    }

    /**
     * Indicates what occurs on long click
     * @param v The view to be affected
     * @return A boolean value to determine if long click occurred
     */
    @Override
    public boolean onLongClick(View v) {
        //If the view is the delete item icon
        if(v.getId() == R.id.image_delete)
        {
            final Snackbar confirmDeleteSnackbar = Snackbar.make(v.getRootView().findViewById(R.id.coordinatorLayout), R.string.snackbarText, Snackbar.LENGTH_LONG);
            confirmDeleteSnackbar.setAction(R.string.confirm, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Get the position of the item
                    int position = getAdapterPosition();

                    //Delete the entry
                    deleteEntry(position);

                    confirmDeleteSnackbar.dismiss();
                }
            });
            confirmDeleteSnackbar.show();

            //Return true, there was a long click
            return true;
        }
        //Return false, there was not a long click
        return false;
    }
}
