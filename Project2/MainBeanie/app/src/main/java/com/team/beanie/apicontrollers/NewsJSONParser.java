package com.team.beanie.apicontrollers;

import android.util.Log;

import com.example.beanielibrary.model.NewsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsJSONParser
{
    private final String TAG = "NewsJSONParser";

    public List<NewsModel> parse(String result)
    {
        final String ARTICLES = "articles";
        final String ARTICLE_TITLE = "title";
        final String ARTICLE_DESCRIPTION = "description";
        final String ARTICLE_URL = "url";

        JSONObject jsonResponse =null;
        List<NewsModel> newsList = new ArrayList<>();
        try
        {
            jsonResponse = new JSONObject(result);
            JSONArray articles =jsonResponse.getJSONArray(ARTICLES);
            for (int i = 0; i < articles.length(); i++)
            {
                JSONObject jsonArticleObj = articles.getJSONObject(i);

                NewsModel article = new NewsModel();
                article.setTitle(jsonArticleObj.getString(ARTICLE_TITLE));
                article.setDescription(jsonArticleObj.getString(ARTICLE_DESCRIPTION));
                article.setUrl(jsonArticleObj.getString(ARTICLE_URL));
                newsList.add(article);
            }
            for (int i = 0; i < newsList.size(); i++)
            {
                Log.i(TAG, "" + newsList.get(i));
            }
        }
        catch(JSONException exception)
        {
            Log.e(TAG, exception.getMessage(), exception);
        }
        return newsList;
    }
}
