package com.team.beanie.apicontrollers;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NewsAPIController
{
    private final String TAG = "NewsAPIController";

    public NewsAPIController()
    {

    }

    public String makeApiCall(String endpoint)
    {
        String response="";

        try
        {
            URL url =new URL(endpoint);

            HttpURLConnection connection =(HttpURLConnection)url.openConnection();

            connection.connect();
            InputStream inputStream =connection.getInputStream();
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null)
            {
                stringBuilder.append(line);
            }
            response = stringBuilder.toString();
        }
        catch(IOException exception)
        {
            Log.e(TAG, exception.getMessage(), exception);
        }
        return response;
    }
}
