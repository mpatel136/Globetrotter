package com.team.beanie;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.beanielibrary.model.Item;
import com.team.beanie.fragments.inputs.DialogAddItemFragment;
import com.team.beanie.fragments.inputs.DialogUpdateItemFragment;
import com.team.beanie.fragments.inputs.IPassItem;
import com.team.beanie.recyclerview.CustomItemAdapter;
import com.team.beanie.recyclerview.CustomItemViewHolder;
import com.team.beanie.recyclerview.IRefresh;

import java.util.ArrayList;

/**
 * Class for the item list activity
 */
public class CustomItemsListActivity extends AppCompatActivity implements View.OnClickListener, IPassItem, IRefresh {

    //RecyclerView user interface component
    private RecyclerView recyclerView;

    //Adapter for the RecyclerView
    private RecyclerView.Adapter<CustomItemViewHolder> adapter;

    //LayoutManager for the RecyclerView
    private RecyclerView.LayoutManager lm;

    //Array list of items
    ArrayList<Item> itemsList;

    //DatabaseHelper myDb;

    /**
     * Create the options menu and inflate the corresponding layout file
     * @param menu Menu handler
     * @return True
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.change_view,menu);
        return true;
    }

    /**
     * Handles what happens on click of items in the options menu
     * @param item The item in the options menu
     * @return True if item exists and is selected
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.linearLayout:
                this.lm = new LinearLayoutManager(this);
                refresh();
                return true;
            case R.id.staggeredLayout:
                lm = new StaggeredGridLayoutManager(2, LinearLayoutManager.HORIZONTAL);
                refresh();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * On create of the activity
     * @param savedInstanceState A group of values
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_items_list);
        setTitle(getString(R.string.customItemsList));

        //Find the add button and set a onclick listener to it
        FloatingActionButton add = (FloatingActionButton) findViewById(R.id.btn_add);
        add.setOnClickListener(this);

        //Get the list
//        itemsList = new ArrayList<>();
//        itemsList.add(new Item("Sun Screen",3));
//        itemsList.add(new Item("Glasses",3));

        this.recyclerView = (RecyclerView) findViewById(R.id.recycler_custom_items_list);

        this.lm = new LinearLayoutManager(this);



        //Refresh the view
        refreshRecyclerView();

        //Create the recycler
        createRecycler();

// Toast.makeText(this, "" + adapter.getItemCount(), Toast.LENGTH_LONG).show();

    }

    /**
     * Method to create recycler
     */
    private void createRecycler(){
        //Creates and sets the components for RecyclerView
        Log.wtf("recyclerBeanie","onCreate1");

        //Set the layoutManager
        this.recyclerView.setLayoutManager(lm);
        Log.wtf("recyclerBeanie","onCreate2");
    }

    /**
     * Determines what occurs onClick
     * @param v The view which is affected
     */
    @Override
    public void onClick(View v) {
        //Instantiate the fragment manager
        FragmentManager fm = getSupportFragmentManager();

        //Instantiate the add item fragment
        DialogAddItemFragment fragment = new DialogAddItemFragment();

        //Set cancelable to true
        fragment.setCancelable(true);

        //Show the add item fragment
        fragment.show(fm, "input");
    }

    /**
     * On retrieve of the item, update the view and set values of item
     * @param itemName The name of the item
     * @param quantity The quantity of the item
     */
    @Override
    public void onRetrieveItem(String itemName, int quantity) {
        //Instantiates an item with the position, name and quantity
        Item item = new Item(adapter.getItemCount() + 1, itemName, quantity);
        Log.wtf("recycler","onRetrieveItem1");

        //Create the item with the values
        ((CustomItemAdapter) adapter).createItem(item);

        //Update the view
        refreshRecyclerView();
        Log.wtf("recycler","onRetrieveItem2");

//        boolean isInserted = itemsListController.insertData(itemName, quantity);
//        if(isInserted == true)
//        {
//            Log.i("IsInserted","Data Inserted");
//        }
//        else
//        {
//            Log.i("IsInserted","Data Not Inserted");
//        }
    }

    /**
     * Refresh the view
     */
    private void refreshRecyclerView(){

        //Set the adapter to the custom item adapter with the current context
        this.adapter = new CustomItemAdapter(this);

        //Set the recycler view to the adapter
        this.recyclerView.setAdapter(adapter);

        //If the layout manager is an instance of the staggered grid layout
        if(lm instanceof StaggeredGridLayoutManager) {
            //Set the layout manager to a new staggered grid layout
            lm = new StaggeredGridLayoutManager(2, LinearLayoutManager.HORIZONTAL);
        }
        //Set the layout manager to the recycler view
        recyclerView.setLayoutManager(lm);
        Log.i("refreshRecycler", "It has been refreshed");

    }

    /**
     * Refreshes the view
     */
    @Override
    public void refresh() {

        //Refresh the view
        refreshRecyclerView();
    }

    /**
     * Updates the item
     * @param item The item to be updated
     */
    @Override
    public void update(Item item) {
        //Instantiates the bundle
        Bundle bundle = new Bundle();

        //Put the parcelable of the olditem with the new one
        bundle.putParcelable("oldItem",item);

        //Instantiates the fragment manager
        FragmentManager fm = getSupportFragmentManager();

        //Instantiates the update fragment
        DialogUpdateItemFragment fragment = new DialogUpdateItemFragment();

        //Set the arguments of the fragment to the bundle
        fragment.setArguments(bundle);

        //Set cancelable to true
        fragment.setCancelable(true);

        //Show the update fragment
        fragment.show(fm, "update");
    }

    /**
     * Once the new values are inputted, update the item
     * @param item The item to be updated
     */
    @Override
    public void onFinishUpdate(Item item) {
        //Update the item
        ((CustomItemAdapter) adapter).update(item);
    }

//    //Takes all the entries in the table
//    public void viewAll(){
//        Cursor res = myDb.getAllData();
//        if(res.getCount() == 0){
//            //Nothing
//            return;
//        }
//
//        StringBuffer buffer = new StringBuffer();
//        while (res.moveToNext()){
//            buffer.append("Item :" + res.getString(0) + "\n");
//            buffer.append("Quantity:" + res.getString(1) + "\n\n");
//        }
//    }

//    /**
//     * Adds the list of items into the UI
//     */
//    private void refresh(){
//        for(Object i : list.getList()){
//            Item item = (Item) i;
//            String text = item.getItemName() + "\t" + item.getQuantity();
//        }
//    }
}
