package com.team.beanie.fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team.beanie.R;

/**
 * A fragment class for the help section of the app
 */
public class HelpFragment extends DialogFragment {

    public HelpFragment() {
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_help, container, false);
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
        builder.setMessage(R.string.helpText)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        return builder.create();
    }
}
