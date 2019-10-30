package com.team.beanie;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.team.beanie.fragments.AboutFragment;
import com.team.beanie.fragments.HelpFragment;
import com.team.beanie.fragments.LanguageFragment;
import com.team.beanie.ui.currency.CurrencyActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Class for the main home screen
 */
public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private DrawerLayout dl;
    private ActionBarDrawerToggle toggle;
    private NavigationView nv;

    /**
     * On activity result, start the activity
     * @param requestCode The request code
     * @param resultCode The result code
     * @param data The data being affected
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Start the activity
        startActivity(data);
    }

    /**
     * On create on the activity
     * @param savedInstanceState Collection of values
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set the title of the page
        setTitle("Globetrotter");

        dl = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, dl, R.string.open, R.string.close);

        dl.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Intent loading = new Intent(this, LoadingScreen.class);

        nv = (NavigationView) findViewById(R.id.nav_view);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.itmWondersOfTheWorld:
                        loading.putExtra("intent", new Intent(MainActivity.this, Pictures.class));
                        startActivityForResult(loading, 1);
                        break;
                    case R.id.itmItemsList:
                        loading.putExtra("intent",(new Intent(MainActivity.this, CustomItemsListActivity.class)));
                        startActivityForResult(loading,1);
                        break;
                    case R.id.itmCurrencyExchange:
                        loading.putExtra("intent", new Intent(MainActivity.this, CurrencyActivity.class));
//                        loading.putExtra("intent", new Intent(MainActivity.this, Currency_Exchange.class));
                        startActivityForResult(loading, 1);
                        break;
                    case R.id.itmNews:
                        loading.putExtra("intent", new Intent(MainActivity.this, News.class));
                        startActivityForResult(loading, 1);
                        break;
                }
                return true;
            }
        });

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        calendar.set(Calendar.MILLISECOND, 00);

        if (calendar.getTime().compareTo(new Date()) < 0)
        {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        Intent intentToBroadcastReceiver = new Intent(getApplicationContext(), NotificationReceiver.class);

        //Flag indicates that the intent can be updated in the future in case of time change
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, intentToBroadcastReceiver, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManger = (AlarmManager) getSystemService(ALARM_SERVICE);

        //RTC types says that the alarm will be triggered even if device is in sleep mode
        //Trigger at is the time it will be triggered
        //Interval is how often the notification gets called
        alarmManger.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    /**
     * On create of the options menu
     * @param menu The menu object
     * @return A boolean value to indicate the result
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu with the layout file
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu,menu);
        return true;
    }

    /**
     * Handles events on click of items in the options menu
     * @param item The item in the options menu
     * @return Boolean value to indicate if item was pressed
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Instantiates fragment transaction
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if(toggle.onOptionsItemSelected(item))
        {
            return true;
        }

        switch(item.getItemId())
        {
            //If the item is about
            case R.id.about:
                //Instantiates the about fragment
                DialogFragment aboutFragment = new AboutFragment();
                //Show the about fragment
                aboutFragment.show(ft, "About Fragment");
                return true;
            //If the item is help
            case R.id.help:
                //Instantiates the help fragment
                DialogFragment helpFragment = new HelpFragment();
                //Show the help fragment
                helpFragment.show(ft, "Help Fragment");
                return true;
            case R.id.changeLanguage:
                Fragment changeLanguageFragment = new LanguageFragment();
                ft.add(R.id.frameLayout,changeLanguageFragment);
                ft.show(changeLanguageFragment);
                ft.commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Start the activity with an intent and wait for the results
     * @param intent The intent to be passed
     * @param requestCode The request code to be passed
     */
    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        intent.putExtra("requestCode", requestCode);
        super.startActivityForResult(intent, requestCode);
    }

    /**
     * Handles on touch of views
     * @param v The view affected
     * @param event The event of the motion
     * @return Boolean value to indicate a change
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
