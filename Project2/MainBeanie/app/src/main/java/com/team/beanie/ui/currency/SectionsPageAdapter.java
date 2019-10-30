package com.team.beanie.ui.currency;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SectionsPageAdapter extends FragmentPagerAdapter {

    private static final String[] TAB_TITLES = new String[]{ "All Currencies" , "Calculate" };
    private final Context mContext;

    public SectionsPageAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;

    }

    @Override
    public Fragment getItem(int i) {
        if(i == 0){
            return AllCurrencyExchangeFrag.newInstance(i);
        }

        return OneToOneCurrencyExchangeFrag.newInstance(i);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES[position];
    }
}
