package com.team.beanie.ui.currency;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.beanielibrary.model.PageViewModel;
import com.team.beanie.R;
import com.team.beanie.currencyexchangecontroller.APIController;
import com.team.beanie.currencyexchangecontroller.CustomCurrencyJSONParser;
import com.team.beanie.database.controller.CurrencyTypeController;
import com.example.beanielibrary.views.ValidationAutoText;

import android.arch.lifecycle.ViewModelProviders;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Calculates the exchange rate between two types of currencies.
 * A simple {@link Fragment} subclass.
 */
public class OneToOneCurrencyExchangeFrag extends Fragment {

    private static final String PAGE_NUM = "section_number";
    private CurrencyTypeController controller;

    private PageViewModel pageViewModel;
    private View view;
    private List<String> type;
    private TextView label;
    private ValidationAutoText base;
    private ValidationAutoText quote;
    private EditText amount;

    /**
     * Constructor used for the tabbed activity.
     * @param index Indicates the index of the tab.
     * @return Returns the fragment
     */
    public static OneToOneCurrencyExchangeFrag newInstance(int index){
        OneToOneCurrencyExchangeFrag fragment = new OneToOneCurrencyExchangeFrag();
        Bundle bundle = new Bundle();
        bundle.putInt(PAGE_NUM, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * Needed constructor
     */
    public OneToOneCurrencyExchangeFrag() {
        // Required empty public constructor
    }

    /**
     * Sets the frag to the pageview.
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(PAGE_NUM);
        }
        pageViewModel.setIndex(index);

    }

    /**
     * Sets the UI and the implementation of functions for the fragment.
     * Sets the layout for the UI.
     * @param inflater Inflates the UI
     * @param container Container
     * @param savedInstanceState savedInstanceState
     * @return A View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_one_to_one_currency_exchange, container, false);
        base = (ValidationAutoText) view.findViewById(R.id.edit_text_base);
        quote = (ValidationAutoText) view.findViewById(R.id.edit_text_quote);
        label = (TextView)  view.findViewById(R.id.txt_display_result);
        amount = (EditText) view.findViewById(R.id.editText_amount);

        base.setThreshold(1);
        quote.setThreshold(1);

        controller = new CurrencyTypeController(getActivity());
        type = controller.getAll();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.select_dialog_item, type);

        base.setAdapter(adapter);
        quote.setAdapter(adapter);
        base.setHint(R.string.type);
        quote.setHint(R.string.type);

        startTimer();
        return view;
    }


    /**
     * Stops the API running onPause.
     */
    @Override
    public void onPause() {
        super.onPause();
        stopTimer();
    }

    /**
     * Starts the API on resume.
     */
    @Override
    public void onResume() {
        super.onResume();
        startTimer();
    }
    //display base*rate of quote

    /**
     * Calls the API
     */
    public void runAPI(){
        try {
            base.validate();
            quote.validate();
            Log.i("runAPI","Run");
            if(type.contains(base.getText().toString().toUpperCase())) {
                Log.i("runAPI","Run2");
                String url = "https://api.exchangeratesapi.io/latest?base=" + base.getText().toString().toUpperCase();
                OneToOneCurrencyExchangeFrag.APISyncTask task = new OneToOneCurrencyExchangeFrag.APISyncTask();
                task.execute(url);
            }
        }catch (Exception e){
            Log.wtf("runAPI","faulty inputs");
        }

    }

    /**
     * Calls the API every second
     */
    public void afficher() {
        runAPI();
        handler.postDelayed(runnable,1000);

    }

    /**
     * Starts the runnable to call the api.
     */
    public void startTimer() {
        runnable.run();
    }

    /**
     * Stops the runnable.
     */
    public void stopTimer() {
        handler.removeCallbacks(runnable);
    }


    private Handler handler = new Handler();

    /**
     * Runs the timer for the API call.
     */
    Runnable runnable = new Runnable() {
        public void run() {
            afficher();
        }
    };

    /**
     * Creates a separate thread from the main one.
     */
    private class APISyncTask extends AsyncTask<String, Void, String> {
        HashMap<String, String> rates;
        private List<String> quotes;

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            parseJSONResponse(s);
            displayResult();
        }

        private void displayResult(){
            try{
                String result = (Double.parseDouble(amount.getText().toString()) * Double.parseDouble(rates.get(quote.getText().toString().toUpperCase())))+ "";
                Log.i("runAPI", result);
                label.setText(result);
            }
            catch (Exception e){
                e.getStackTrace();
            }
        }
        @Override
        protected String doInBackground(String... strings) {
            String response = "";
            APIController apiController = new APIController();

            response = apiController.getFromAPICall(strings[0]);




            return response;
        }

        private void parseJSONResponse(String result){
            CustomCurrencyJSONParser parser = new CustomCurrencyJSONParser();
            HashMap<String, String> rates = parser.getRates(result);

            this.rates = rates;
            quotes = new ArrayList<>();
            quotes.addAll(rates.keySet());

        }


    }


}
