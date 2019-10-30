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
 * A fragment class for the colosseum section of the world wonder part of the app
 */
public class ColosseumFragment extends DialogFragment implements TextToSpeech.OnInitListener{

    TextView txt;
    TextToSpeech ttsColosseum;
    Boolean isReady = false;
    View view;

    public ColosseumFragment() {
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
        view = inflater.inflate(R.layout.fragment_colosseum, container, false);

        ttsColosseum = new TextToSpeech(getContext(), this);
        if(Locale.getDefault().getLanguage().equals("en"))
        {
            ttsColosseum.setLanguage(Locale.ENGLISH);
        }
        else if (Locale.getDefault().getLanguage().equals("fr"))
        {
            ttsColosseum.setLanguage(Locale.FRENCH);
        }
        txt = (TextView) view.findViewById(R.id.colosseumTextView);

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
        builder.setTitle(R.string.colosseum);
        builder.setMessage(R.string.colosseumText)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        if(ttsColosseum.isSpeaking())
                        {
                            ttsColosseum.stop();
                        }
                        ttsColosseum.shutdown();
                    }
                })
                .setNegativeButton(R.string.read, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id){
                        if(isReady)
                        {
                            ttsColosseum.speak(txt.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, "Colosseum TTS");
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
