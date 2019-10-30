package com.team.beanie.currencyexchangecontroller;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class CustomCurrencyJSONParser {



    private final String RATES = "rates";


    private List<String> keyList;

    public HashMap<String, String> getRates(String result){
        HashMap<String, String> set;
        set = new HashMap<>();
        setKeys(result);
        List<String> keys = getKeys();

        JSONObject response;
        try{
            response = getAllRates(result);

            for(int i = 0; i < keys.size(); i++){
                set.put(keys.get(i), response.getString(keys.get(i)));
            }
        }
        catch (Exception e){
            Log.wtf("CurrencyExchange", "It's Broken @ getRates");
        }
        return set;
    }

    private JSONObject getAllRates(String result) {
        JSONObject object = null;
        try {
            object = new JSONObject(result).getJSONObject(RATES);
        }catch (JSONException e) {
            e.printStackTrace();
            Log.wtf("CurrencyExchange", "It's Broken @ getAllRates");

        }
        return object;
    }

    private void setKeys(String result){
        keyList = new ArrayList<>();

        Iterator something = getAllRates(result).keys();

        while(something.hasNext()){
            keyList.add(something.next().toString());
        }

    }

    private List<String> getKeys(){
        return keyList;
    }
}
