package com.team.beanie;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.beanielibrary.model.NewsModel;
import com.team.beanie.apicontrollers.NewsAPIController;
import com.team.beanie.apicontrollers.NewsJSONParser;
import com.team.beanie.recyclerview.currency.CustomCurrencyAdapter;
import com.team.beanie.recyclerview.news.MyNewsAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class News extends AppCompatActivity {

    private final String TAG = "NewsModel";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<NewsModel> articleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getResources().getText(R.string.news));
        setContentView(R.layout.activity_news);
        recyclerView = (RecyclerView) findViewById(R.id.newsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        runAsyncTask();
    }

    private void runAsyncTask() {
        String url = null;
//        Log.i("Locale", Locale.getDefault().getLanguage());
        if(Locale.getDefault().getLanguage().equals("en"))
        {
            url = "https://newsapi.org/v2/top-headlines?apiKey=a15e97d9ecdc4bcb9e364c704aea9718&sortBy=popularity&sources=bbc-news";
        }
        else if(Locale.getDefault().getLanguage().equals("fr"))
        {
            url = "https://newsapi.org/v2/top-headlines?apiKey=a15e97d9ecdc4bcb9e364c704aea9718&sortBy=popularity&language=fr";
        }
        APIAsyncTask task = new APIAsyncTask();
        task.execute(url);
    }

    private class APIAsyncTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... strings) {
            String response = "";
            NewsAPIController newsAPIController = new NewsAPIController();
            response = newsAPIController.makeApiCall(strings[0]);
            Log.i(TAG, response);
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            parseJSONResponse(result);
            populateRecyclerView();
        }
    }

    private void parseJSONResponse(String result)
    {
        NewsJSONParser jsonParser = new NewsJSONParser();
        List<NewsModel> articles = jsonParser.parse(result);
        articleList = articles;
    }

    private void populateRecyclerView(){
        //Recycler View here
        this.adapter = new MyNewsAdapter(articleList, this);

        this.recyclerView.setAdapter(adapter);
    }
}
