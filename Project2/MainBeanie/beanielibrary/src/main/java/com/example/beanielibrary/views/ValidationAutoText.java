package com.example.beanielibrary.views;

import android.content.Context;
import android.util.AttributeSet;


import com.example.beanielibrary.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ValidationAutoText extends android.support.v7.widget.AppCompatAutoCompleteTextView {


    private final String[] types= {"CHF", "HRK", "MXN", "ZAR", "INR", "THB", "CNY", "AUD", "ILS", "KRW", "JPY", "PLN", "GBP", "IDR", "HUF", "PHP", "TRY", "RUB", "HKD", "ISK", "DKK", "CAD", "USD", "MYR", "BGN", "NOK", "RON", "SGD", "CZK", "SEK", "NZD", "BRL", "EUR"};
    private final List<String> list = new ArrayList(Arrays.asList(types));

    public ValidationAutoText(Context context) {
        super(context);
    }

    public ValidationAutoText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ValidationAutoText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void validate(){
        if(this.getText().length() == 3){
            if(list.contains(getText().toString().toUpperCase()))
            {
                this.setBackgroundColor(getResources().getColor(R.color.green));

            }
            else{
                this.setBackgroundColor(getResources().getColor(R.color.red));
            }
        }
        else if (this.getText().length() > 3){
            this.setBackgroundColor(getResources().getColor(R.color.red));
        }
        else{
            this.setBackgroundColor(getResources().getColor(R.color.transparent));
        }
    }
}
