package com.example.beanielibrary.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class SeperatorView extends View {

    public SeperatorView(Context context) {
        super(context);
        setBackgroundColor(getResources().getColor(android.R.color.black));
    }

    public SeperatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(getResources().getColor(android.R.color.black));
    }

    public SeperatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundColor(getResources().getColor(android.R.color.black));
    }

    public SeperatorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setBackgroundColor(getResources().getColor(android.R.color.black));
    }
}
