package com.team.beanie.recyclerview.currency;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.team.beanie.Currency_Exchange;
import com.team.beanie.R;

import java.util.List;

public class CustomCurrencyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


    private View view;

    private CustomCurrencyAdapter adapter;

    private List<String> list;


    private TextView textViewType;
    private CardView cardView;

    public CustomCurrencyViewHolder(@NonNull View itemView, CustomCurrencyAdapter adapter, List<String> list) {
        super(itemView);
        view = itemView;

        this.list = list;
        this.adapter = adapter;

        cardView = (CardView) itemView.findViewById(R.id.card_view_currency);
        cardView.setOnClickListener(this);
        textViewType = (TextView) itemView.findViewById(R.id.txt_currency_type);
    }


    @Override
    public void onClick(View v) {
        ISwitchType activity = (ISwitchType) adapter.getContext();
        activity.updateType(textViewType.getText().toString().split(":")[0]);
    }

    public void setEntry(String type){
        Log.i("CurrencyType",type);
        textViewType.setText(type);
    }
}
