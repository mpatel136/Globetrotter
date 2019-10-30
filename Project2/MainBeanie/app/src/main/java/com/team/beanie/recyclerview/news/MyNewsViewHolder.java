package com.team.beanie.recyclerview.news;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.team.beanie.R;

public class MyNewsViewHolder extends RecyclerView.ViewHolder{

        public TextView title;
        public TextView description;
        public TextView url;
        public CardView newsCardView;
        public LinearLayout newsLinearLayout;

    public MyNewsViewHolder(View itemView) {
        super(itemView);

        title = (TextView) itemView.findViewById(R.id.txtArticleTitle);
        description = (TextView) itemView.findViewById(R.id.txtArticleDescription);
        url = (TextView) itemView.findViewById(R.id.txtURL);
        newsCardView = (CardView) itemView.findViewById(R.id.card_view);
        newsLinearLayout = (LinearLayout) itemView.findViewById(R.id.newsLinearLayout);
    }
}
