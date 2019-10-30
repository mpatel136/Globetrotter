package com.team.beanie.currencyexchangecontroller;

import android.util.Log;

import org.json.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * API controller to read
 */
public class APIController {

    /**
     * Class TAG
     */
    private final String TAG = "APICONTROLLER";

    /**
     * Default constructor to initialize the controller
     */
    public APIController(){

    }

    /**
     * Retrieves the url and reads the file and converts it into a string.
     * @param callingURL The URL
     * @return Returns the string from the URL
     */
    public String getFromAPICall(String callingURL){
        String response = "";

        try{


            URL url= new URL(callingURL);
            HttpURLConnection connection =(HttpURLConnection)url.openConnection();
            connection.connect();
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                //OPEN THE STREAM
                InputStream in = connection.getInputStream();

                Log.i("connection", connection.getResponseCode() + "");

                //
                StringBuilder stringBuilder = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;

                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                response = stringBuilder.toString();
            }
            else{
                connection.disconnect();
            }

        }
        catch (MalformedURLException me) {
            Log.e(TAG, me.getMessage(), me);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
        }

        return response;

    }
}
