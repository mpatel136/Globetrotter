package com.team.beanie.recyclerview.currency;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team.beanie.R;

import java.util.ArrayList;
import java.util.List;

public class CustomCurrencyAdapter extends RecyclerView.Adapter<CustomCurrencyViewHolder> {

    private Context context;
    private List<String> typeList;


    public CustomCurrencyAdapter(Context context, List<String> type){
        this.context = context;
        typeList = type;

    }

    @NonNull
    @Override
    public CustomCurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.currency_layout_entry, viewGroup, false);

        CustomCurrencyViewHolder viewHolder = new CustomCurrencyViewHolder(view, this, typeList);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomCurrencyViewHolder customCurrencyViewHolder, int i) {

        Log.i("CurrencyType",typeList.get(i));
        customCurrencyViewHolder.setEntry(typeList.get(i));
    }

    @Override
    public int getItemCount() {
        return typeList.size();
    }

    public Context getContext(){
        return context;
    }
}
