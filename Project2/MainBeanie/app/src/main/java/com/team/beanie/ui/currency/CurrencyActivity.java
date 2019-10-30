package com.team.beanie.ui.currency;

import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.team.beanie.R;
import com.team.beanie.recyclerview.currency.ISwitchType;

/**
 * Activity for the tabs.
 */
public class CurrencyActivity extends AppCompatActivity implements ISwitchType {
    SectionsPageAdapter sectionsPageAdapter;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);
        setTitle(getString(R.string.currenyExchange));

        sectionsPageAdapter = new SectionsPageAdapter(this, getSupportFragmentManager());

        viewPager = (ViewPager) findViewById(R.id.vp_currency);
        viewPager.setAdapter(sectionsPageAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void updateType(String type) {
        Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.vp_currency + ":" + viewPager.getCurrentItem());
        ICurrencyFrag frag = (ICurrencyFrag) page;

        assert frag != null;
        frag.updateType(type);
    }
}
