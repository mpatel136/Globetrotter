package com.team.beanie;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.beanielibrary.model.Item;
import com.team.beanie.database.Access;
import com.team.beanie.recyclerview.CustomItemAdapter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {

    @Mock
    ProgressBar loadingBar;

    @Mock
    Context mockContext;

    private int status;

    @Mock
    Handler handler = new Handler();

    @Test
    public void testProgressBar(){
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

                    }
                });
            }
        }).start();
    }

    @Test
    public void testNotification()
    {
        String title = mockContext.getString(R.string.notificationTitle);
        String message = mockContext.getString(R.string.notificationText);

        Intent intentToMainActivity = new Intent(mockContext, MainActivity.class);
        //Ensures that the activity that is being called will be replaced if needed
        intentToMainActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(mockContext, 1, intentToMainActivity, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mockContext,"notificationChannel");
        notificationBuilder.setContentIntent(pendingIntent);
        notificationBuilder.setSmallIcon(R.drawable.globe);
        notificationBuilder.setContentTitle(title);
        notificationBuilder.setContentText(message);
        //Makes notification dismissible on swipe
        notificationBuilder.setAutoCancel(true);
    }

    @Test
    public void testAPI() throws IOException {
        String baseURL = "https://rest.coinapi.io/v1/exchangerate";

        String key = "2EFA0ADC-1669-4A5F-B566-B8BB8AEC5E24";

        String baseCurrency = "BTC";
        String quoteCurrency = "USD";

        String callingURL = baseURL + "/" + baseCurrency + "/" + quoteCurrency + "?apikey=" + key;
        String callingURL2 = "https://rest.coinapi.io/v1/assets";

        String response = "";


            URL url= new URL(callingURL2);
            HttpURLConnection connection =(HttpURLConnection)url.openConnection();

            connection.connect();

            //OPEN THE STREAM
            InputStream in = connection.getInputStream();


            //
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;

            while ((line = reader.readLine()) != null){
                stringBuilder.append(line);
            }
            response = stringBuilder.toString();

            Log.i("Test API", response);


    }
}