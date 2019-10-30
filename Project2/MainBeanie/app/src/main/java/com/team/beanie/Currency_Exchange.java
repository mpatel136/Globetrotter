package com.team.beanie;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.preference.Preference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.team.beanie.currencyexchangecontroller.APIController;
import com.team.beanie.currencyexchangecontroller.CustomCurrencyJSONParser;
import com.team.beanie.database.controller.CurrencyTypeController;
import com.team.beanie.recyclerview.currency.CustomCurrencyAdapter;
import com.team.beanie.recyclerview.currency.CustomCurrencyViewHolder;
import com.team.beanie.recyclerview.currency.ISwitchType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class Currency_Exchange extends AppCompatActivity implements ISwitchType {

    CurrencyTypeController currencyTypeController;
    List<String> key;
    TextView base;
    String baseType;
    SharedPreferences.Editor edit;

    SharedPreferences preference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_exchange);



//          https://api.exchangeratesapi.io/latest?base=USD
        createRecyclerView();
        startTimer();

    }

    public void afficher() {
        runAPI();
        handler.postDelayed(runnable,30000);

    }

    public void startTimer() {
        runnable.run();
    }

    public void stopTimer() {
        handler.removeCallbacks(runnable);
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        public void run() {
            afficher();
        }
    };

    public void runAPI(){
        preference = getPreferences(MODE_PRIVATE);
        baseType = preference.getString("default", "CAD");
        base = (TextView) findViewById(R.id.txt_currency_base);
        base.setText(baseType);
        String url = "https://api.exchangeratesapi.io/latest?base=" + baseType;
        APISyncTask task = new APISyncTask();
        task.execute(url);

        currencyTypeController = new CurrencyTypeController(getApplicationContext());
        key = currencyTypeController.getAll();
        for(String k : key){
            Log.i("CurrencyExchange",k);}

    }

    @Override
    public void updateType(String type) {
        edit = preference.edit();
        edit.putString("default", type);
        edit.apply();

        runAPI();
    }

    private class APISyncTask extends AsyncTask<String, Void, String>{

        private List<String> quotes;

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            refreshRecyclerView();

        }

        @Override
        protected String doInBackground(String... strings) {
            String response = "";
            APIController apiController = new APIController();

            response = apiController.getFromAPICall(strings[0]);

            Log.i("CurrencyExchange", "hello" + response);
            parseJSONResponse(response);



            return response;
        }

        private void parseJSONResponse(String result){
            CustomCurrencyJSONParser parser = new CustomCurrencyJSONParser();
            HashMap<String, String> rates = parser.getRates(result);

            quotes = new ArrayList<>();
            quotes.addAll(rates.keySet());

            quotes.remove(baseType);
            for(int i = 0; i < quotes.size(); i ++){

                quotes.set(i, quotes.get(i) + ": " + String.format("%.2f",Double.parseDouble(rates.get(quotes.get(i)))));
            }

            setQuotes(quotes);
            Log.i("CurrencyExchange", rates + "\nparseCompleted");

            Log.i("CurrencyExchange", result + "\nparseCompleted");


        }


    }
    private List<String> quotes;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter<CustomCurrencyViewHolder> adapter;
    private LinearLayoutManager lm;

    private  void createRecyclerView(){
        this.recyclerView = (RecyclerView) findViewById(R.id.recycler_currency);

        this.lm = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(lm);

    }

    private void refreshRecyclerView(){
        for(String k : quotes){
            Log.i("CurrencyType", k);
        }

        List<String> temp = new ArrayList<>();
        temp.add("dab");
        //Recycler View here
        this.adapter = new CustomCurrencyAdapter(this, quotes);

        this.recyclerView.setAdapter(adapter);


    }

    private void setQuotes(List<String> quotes){
        this.quotes = quotes;
    }

}
