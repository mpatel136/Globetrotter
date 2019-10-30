package com.team.beanie;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

/**
 * Class for the loading screen
 */
public class LoadingScreen extends AppCompatActivity {

    //Instantiates the progress bar object
    private ProgressBar loadingBar;

    //Instantiates the status of the progress bar
    private int status;

    //Instantiates the handler
    private Handler handler = new Handler();

    //Instantiates the intent
    private Intent intent;

    /**
     * On create of the activity, perform actions on the progress bar
     * @param savedInstanceState The collection of values
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        setTitle(getString(R.string.loading));

        //Get the intent
        this.intent = getIntent().getParcelableExtra("intent");

        //If the intent has the request code
        if((getIntent().getIntExtra("requestCode", 1)) == 1) {
            //Set the result to intent
            setResult(2, intent);

            //Run the progress bar
            runBar();
        }
    }


    /**
     * Initiates the loading for the progress bar. Once it is done, it opens the result dialogue.
     */
    private void runBar(){
        //Drawable draw = getDrawable(R.drawable.customprogressbar);
        loadingBar = (ProgressBar) findViewById(R.id.progressBar);

        status = 0;
        //Set the progress of the progress bar to zero
        loadingBar.setProgress(status);
        new Thread(new Runnable() {
            @Override
            public void run() {
                //While the progress bar is not 100
                while(status < 100){
                    //Increment the status
                    status++;
                    //Set the time to 5 milliseconds
                    SystemClock.sleep(5);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Set the progress of the progress bar to the status
                            loadingBar.setProgress(status);
                        }
                    });
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //Closes the activity
                        finish();
                    }
                });
            }
        }).start();
    }
}
