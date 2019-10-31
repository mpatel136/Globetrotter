package com.team.beanie;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.team.beanie.fragments.AboutWorldWonderFragment;
import com.team.beanie.fragments.ChichenItzaFragment;
import com.team.beanie.fragments.ColosseumFragment;
import com.team.beanie.fragments.EiffelTowerFragment;
import com.team.beanie.fragments.GreatWallOfChinaFragment;
import com.team.beanie.fragments.HelpWorldWonderFragment;
import com.team.beanie.fragments.PyramidOfGiza;
import com.team.beanie.fragments.StonehengeFragment;
import com.team.beanie.fragments.TajMahalFragment;

/**
 * Class for the Pictures
 */
public class Pictures extends AppCompatActivity implements AdapterView.OnItemClickListener {

    //Instantiates the image view
    ImageView draw;

    /**
     * On create of the activity, set on item click listener on the grid view
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictures);

        setTitle(getString(R.string.wondersOfTheWorld));

        //Find the grid view
        GridView gridView = findViewById(R.id.gridview);

        //Set the adapter to the grid view
        gridView.setAdapter(new ImageAdapter(this));

        //Set onclick listener on the grid view
        gridView.setOnItemClickListener(this);
    }

    /**
     * On create of the options menu, inflate the view
     * @param menu The menu object
     * @return Boolean value to indicate change
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.world_wonder,menu);
        return true;
    }

    /**
     * Handles what happens depending on the item selected in menu
     * @param item The item in the menu
     * @return A boolean value to indicate change
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Instantiates a fragment trasaction
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch(item.getItemId())
        {
            //If the item is about
            case R.id.aboutWorldWonder:
                //Open the about fragment
                DialogFragment aboutWorldWonderFragment = new AboutWorldWonderFragment();
                aboutWorldWonderFragment.show(ft, "About World Wonder Fragment");
                return true;
            //If the item is help
            case R.id.helpWorldWonder:
                //Open the help fragment
                DialogFragment helpWorldWonderFragment = new HelpWorldWonderFragment();
                helpWorldWonderFragment.show(ft, "Help World Wonder Fragment");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * On create of the context menu, set menu headers
     * @param menu The context menu
     * @param v The view of the menu
     * @param menuInfo The info of the menu
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        //Set the imageview
        draw = (ImageView) v;
        super.onCreateContextMenu(menu, v, menuInfo);
        switch(v.getId())
        {
            //If the menu is for colosseum
            case R.drawable.colosseum:
                //Set the title
                menu.setHeaderTitle(R.string.colosseum);
                break;
            //If the menu is for eiffel tower
            case R.drawable.eiffel_tower:
                //Set the title
                menu.setHeaderTitle(R.string.eiffelTower);
                break;
            //If the menu is for pyramid
            case R.drawable.pyramid_of_giza:
                //Set the title
                menu.setHeaderTitle(R.string.pyramidOfGiza);
                break;
            //If the menu is for stonehenge
            case R.drawable.stonehenge:
                //Set the title
                menu.setHeaderTitle(R.string.stonehenge);
                break;
            //If the menu is for taj mahal
            case R.drawable.taj_mahal:
                //Set the title
                menu.setHeaderTitle(R.string.tajMahal);
                break;
            //If the menu is for great wall of china
            case R.drawable.great_wall_of_china:
                //Set the title
                menu.setHeaderTitle(R.string.greatWallOfChina);
                break;
            //If the menu is for chichen itza
            case R.drawable.chichen_itza:
                //Set the title
                menu.setHeaderTitle(R.string.chichenItza);
                break;
            default:
                //Set the title
                menu.setHeaderTitle(R.string.options);
                break;
        }
        //Add items in the menu
        menu.add(0, v.getId(), 0, R.string.viewInfo);
        menu.add(0, v.getId(), 0, R.string.visitWikiPage);
    }

    /**
     * Determines what happens depending on which item is selected in the menu
     * @param item The item in the menu
     * @return Boolean value to indicate change
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //If the item is visit wiki page
        if(item.getTitle() == getString(R.string.visitWikiPage))
        {
            //Go to wiki
            goToWiki();
        }
        //If the item is view info
        if(item.getTitle() == getString(R.string.viewInfo))
        {
            //Go to info
            goToInfo();
        }
        return true;
    }

    /**
     * Goes to world wonder's corresponding wiki page
     */
    private void goToWiki() {
        Intent intent;
        String location;
        switch (draw.getId()) {
            //If the image is colosseum
            case R.drawable.colosseum:
                //Set the location of the link
                location = "https://en.wikipedia.org/wiki/Colosseum";
                break;
            //If the image is eiffel tower
            case R.drawable.eiffel_tower:
                //Set the location of the link
                location = "https://en.wikipedia.org/wiki/Eiffel_Tower";
                break;
            //If the image is pyramid
            case R.drawable.pyramid_of_giza:
                //Set the location of the link
                location = "https://en.wikipedia.org/wiki/Great_Pyramid_of_Giza";
                break;
            //If the image is stonehenge
            case R.drawable.stonehenge:
                //Set the location of the link
                location = "https://en.wikipedia.org/wiki/Stonehenge";
                break;
            //If the image is taj mahal
            case R.drawable.taj_mahal:
                //Set the location of the link
                location = "https://en.wikipedia.org/wiki/Taj_Mahal";
                break;
            //If the image is great wall of china
            case R.drawable.great_wall_of_china:
                //Set the location of the link
                location = "https://en.wikipedia.org/wiki/Great_Wall_of_China";
                break;
            //If the image is chichen itza
            case R.drawable.chichen_itza:
                //Set the location of the link
                location = "https://en.wikipedia.org/wiki/Chichen_Itza";
                break;
            default:
                location = "en.wikipedia.org";
                break;
        }
        //Instantiates an intent with the location of the link
        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(location));
        //Open the link depending on the link selected
        startActivity(intent);
    }

    /**
     * Handles what occurs on click of the info
     */
    private void goToInfo()
    {
        //Instantiates fragmenttransaction
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (draw.getId()) {
            //If the image is colosseum
            case R.drawable.colosseum:
                //Open the colosseum fragment
                DialogFragment colosseumFragment = new ColosseumFragment();
                colosseumFragment.show(ft, "Colosseum Fragment");
                break;
            //If the image is eiffel tower
            case R.drawable.eiffel_tower:
                //Open the eiffel tower fragment
                DialogFragment eiffelTowerFragment = new EiffelTowerFragment();
                eiffelTowerFragment.show(ft, "Eiffel Tower Fragment");
                break;
            //If the image is pyramid
            case R.drawable.pyramid_of_giza:
                //Open the pyramid fragment
                DialogFragment pyramidOfGizaFragment = new PyramidOfGiza();
                pyramidOfGizaFragment.show(ft, "Pyramid of Giza Fragment");
                break;
            //If the image is stonehenge
            case R.drawable.stonehenge:
                //Open the stonehenge fragment
                DialogFragment stonehengeFragment = new StonehengeFragment();
                stonehengeFragment.show(ft, "Stonehenge Fragment");
                break;
            //If the image is taj mahal
            case R.drawable.taj_mahal:
                //Open the taj mahal fragment
                DialogFragment tajMahalFragment = new TajMahalFragment();
                tajMahalFragment.show(ft, "Taj Mahal Fragment");
                break;
            //If the image is great wall of china
            case R.drawable.great_wall_of_china:
                //Open the great wall of china fragment
                DialogFragment greatWallOfChinaFragment = new GreatWallOfChinaFragment();
                greatWallOfChinaFragment.show(ft, "Great Wall of China Fragment");
                break;
            //If the image is chichen itza
            case R.drawable.chichen_itza:
                //Open the chichen itza fragment
                DialogFragment chichenItzaFragment = new ChichenItzaFragment();
                chichenItzaFragment.show(ft, "Chichen Itza Fragment");
                break;
        }
    }

    /**
     * Makes context menu for the views
     * @param parent The adapter for the view
     * @param view The view of the image
     * @param position The position of the image
     * @param id The id of the image
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(Pictures.this, "" + position, Toast.LENGTH_LONG).show();
        //Make a context menu for the view
        registerForContextMenu(view);
    }


}