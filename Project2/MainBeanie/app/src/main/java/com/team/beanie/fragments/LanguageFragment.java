package com.team.beanie.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.os.ConfigurationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.team.beanie.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class LanguageFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    Spinner languageSpinner;
    Button btnOk;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    int selectedPosition;
    String selectedLanguage;

    public LanguageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_language, container, false);

        languageSpinner = (Spinner) view.findViewById(R.id.spinnerLanguage);
        languageSpinner.setOnItemSelectedListener(this);

        btnOk = (Button) view.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(this);

        List<String> languages = new ArrayList<String>();
        languages.add(getResources().getString(R.string.english));
        languages.add(getResources().getString(R.string.french));

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,languages);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(dataAdapter);

        prefs = getContext().getSharedPreferences("Language Settings", Context.MODE_PRIVATE);
        selectedPosition = languageSpinner.getSelectedItemPosition();
        languageSpinner.setSelection(prefs.getInt("Current Language",0));

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();

        editor = prefs.edit();
        editor.putInt("Current Language", position);
        editor.apply();

        if(item.equals(getResources().getString(R.string.english)))
        {
            String languageEnglish  = "en";
            Locale localeEnglish = new Locale(languageEnglish);
            Locale.setDefault(localeEnglish);
            Configuration configEnglish = new Configuration();
            configEnglish.setLocale(localeEnglish);
            updateLanguage(configEnglish);

        }
        else if(item.equals(getResources().getString(R.string.french))) {
            String languageFrench = "fr";
            Locale localeFrench = new Locale(languageFrench);
            Locale.setDefault(localeFrench);
            Configuration configFrench = new Configuration();
            configFrench.setLocale(localeFrench);
            updateLanguage(configFrench);
        }
    }

    private void updateLanguage(Configuration config) {
        getActivity().getResources().updateConfiguration(config, getActivity().getResources().getDisplayMetrics());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnOk)
        {
            closeFragment();
            getActivity().finish();
            startActivity(getActivity().getIntent());
        }
    }

    private void closeFragment() {
        getFragmentManager().beginTransaction().remove(LanguageFragment.this).commit();
    }
}
