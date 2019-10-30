package com.team.beanie.fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.team.beanie.R;

import java.util.Locale;

/**
 * A fragment class for the great wall of china section of the world wonder part of the app
 */
public class GreatWallOfChinaFragment extends DialogFragment implements TextToSpeech.OnInitListener{

    TextView txt;
    TextToSpeech ttsGreatWallOfChina;
    Boolean isReady = false;
    View view;

    public GreatWallOfChinaFragment() {
        // Required empty public constructor
    }

    /**
     * When the view is created, match the layout with it
     * @param inflater The layout XML file to be inflated
     * @param container Collection of view
     * @param savedInstanceState Values bundled together
     * @return The view of the fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_great_wall_of_china, container, false);

        ttsGreatWallOfChina = new TextToSpeech(getContext(), this);
        if(Locale.getDefault().getLanguage().equals("en"))
        {
            ttsGreatWallOfChina.setLanguage(Locale.ENGLISH);
        }
        else if (Locale.getDefault().getLanguage().equals("fr"))
        {
            ttsGreatWallOfChina.setLanguage(Locale.FRENCH);
        }
        txt = (TextView) view.findViewById(R.id.greatWallOfChinaText);

        // Inflate the layout for this fragment
        return view;
    }

    /**
     * When the dialog is created, display it
     * @param savedInstanceState Values bundled together
     * @return The dialog of the fragment
     */
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.greatWallOfChina);
        builder.setMessage(R.string.greatWallOfChinaText)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        if(ttsGreatWallOfChina.isSpeaking())
                        {
                            ttsGreatWallOfChina.stop();
                        }
                        ttsGreatWallOfChina.shutdown();
                    }
                })
                .setNegativeButton(R.string.read, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id){
                        if(isReady)
                        {
                            ttsGreatWallOfChina.speak(txt.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, "Eiffel Tower TTS");
                            builder.show();
                        }
                    }
                });
        return builder.create();
    }

    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS)
        {
            isReady = true;
        }
        else
        {
            Toast.makeText(getContext(), "Cannot use Text to Speech on this device", Toast.LENGTH_LONG);
        }
    }
}
