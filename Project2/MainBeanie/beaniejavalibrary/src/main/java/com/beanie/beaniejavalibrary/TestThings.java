package com.beanie.beaniejavalibrary;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;


import jdk.nashorn.internal.parser.JSONParser;


public class TestThings {
    public static void main(String[] args) throws IOException, JSONException {
        useAPI();
    }

    public static void useAPI() throws IOException, JSONException {
        String baseURL = "https://rest.coinapi.io/v1/exchangerate";

        String key = "2EFA0ADC-1669-4A5F-B566-B8BB8AEC5E24";

        String baseCurrency = "BTC";
        String quoteCurrency = "USD";

        String callingURL = baseURL + "/" + baseCurrency + "/" + quoteCurrency + "?apikey=" + key;
        String callingURL2 = "https://rest.coinapi.io/v1/assets";

        String response = "";


        URL url= new URL("https://api.exchangeratesapi.io/latest");
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
        System.out.println("Test API" + " " +  response);

        JSONObject object = new JSONObject(response);




        JSONObject object1 = object.getJSONObject("rates");
        Iterator something = object1.keys();
        //CurrencyTypeController

        while(something.hasNext()){
            System.out.println(something.next());
        }

        System.out.println(object1);

        //System.out.println("Test API" + " " +  response);


    }

}
