package com.team.beanie.recyclerview.news;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.beanielibrary.model.NewsModel;
import com.team.beanie.R;

import java.util.List;

public class MyNewsAdapter extends RecyclerView.Adapter<MyNewsViewHolder> {

    private List<NewsModel>  articleList;
    private Context ctx;

    public MyNewsAdapter(List<NewsModel> articleList, Context ctx) {
        this.articleList = articleList;
        this.ctx = ctx;
    }

    @Override
    public MyNewsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_news_structure, viewGroup, false);

        return new MyNewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyNewsViewHolder myNewsViewHolder, int i) {
        final NewsModel newsModel = articleList.get(i);

        myNewsViewHolder.title.setText(newsModel.getTitle());
        myNewsViewHolder.description.setText(newsModel.getDescription());
        myNewsViewHolder.url.setText(newsModel.getUrl());

        myNewsViewHolder.newsLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToArticle = new Intent(Intent.ACTION_VIEW, Uri.parse(newsModel.getUrl()));
                ctx.startActivity(intentToArticle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }
}
