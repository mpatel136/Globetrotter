package com.team.beanie.ui.currency;


import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.beanielibrary.model.PageViewModel;
import com.team.beanie.R;
import com.team.beanie.currencyexchangecontroller.APIController;
import com.team.beanie.currencyexchangecontroller.CustomCurrencyJSONParser;
import com.team.beanie.database.controller.CurrencyTypeController;
import com.team.beanie.recyclerview.currency.CustomCurrencyAdapter;
import com.team.beanie.recyclerview.currency.CustomCurrencyViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


/**
 * Displays all the types of currency available from the API
 * A simple {@link Fragment} subclass.
 */
public class AllCurrencyExchangeFrag extends Fragment implements ICurrencyFrag {
    private static final String PAGE_NUM = "section_number";

    private PageViewModel pageViewModel;


    private CurrencyTypeController currencyTypeController;

    private List<String> key;
    private TextView base;
    private String baseType;
    private SharedPreferences.Editor edit;
    private SharedPreferences preference;

    private View view;

    /**
     * Constructor used for the tabbed activity.
     * @param index Indicates the index of the tab.
     * @return Returns the fragment
     */
    public static AllCurrencyExchangeFrag newInstance(int index){
        AllCurrencyExchangeFrag fragment = new AllCurrencyExchangeFrag();
        Bundle bundle = new Bundle();
        bundle.putInt(PAGE_NUM, index);
        Log.i("AllCurrencyExchangeFrag","Should be 1:" + index);
        fragment.setArguments(bundle);
        return fragment;
    }
    public AllCurrencyExchangeFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(PAGE_NUM);
        }
        pageViewModel.setIndex(index);

        preference = getActivity().getPreferences(MODE_PRIVATE);

    }

    @Override
    public void onPause() {
        super.onPause();
        stopTimer();
    }
    @Override
    public void onResume() {
        super.onResume();
        startTimer();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_all_currency_exchange, container, false);
        createRecyclerView();
        startTimer();
        return view;
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

        baseType = preference.getString("default", "CAD");
        base = (TextView) view.findViewById(R.id.txt_currency_base);
        base.setText(baseType);
        String url = "https://api.exchangeratesapi.io/latest?base=" + baseType;
        AllCurrencyExchangeFrag.APISyncTask task = new AllCurrencyExchangeFrag.APISyncTask();
        task.execute(url);

        currencyTypeController = new CurrencyTypeController(getActivity());
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

    private class APISyncTask extends AsyncTask<String, Void, String> {

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
        this.recyclerView = (RecyclerView) view.findViewById(R.id.recycler_currency);



    }

    private void refreshRecyclerView(){
        for(String k : quotes){
            Log.i("CurrencyType", k);
        }

        List<String> temp = new ArrayList<>();
        temp.add("dab");
        //Recycler View here
        this.adapter = new CustomCurrencyAdapter(this.getContext(), quotes);

        this.recyclerView.setAdapter(adapter);
        this.lm = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(lm);

    }

    private void setQuotes(List<String> quotes){
        this.quotes = quotes;
    }

}
