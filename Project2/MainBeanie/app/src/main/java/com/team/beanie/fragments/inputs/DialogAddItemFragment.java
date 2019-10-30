package com.team.beanie.fragments.inputs;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.team.beanie.R;

/**
 * A dialog to add the items to the items list
 */
public class DialogAddItemFragment extends DialogFragment implements View.OnClickListener {

    //Instantiates an edit text for the name of the item
    EditText itemName;

    //Instantiates an edit text for the quantity of the item
    EditText quantity;

    //Instantiates a button to submit the item and add it to the list
    Button btnSubmit;

    //Instantiates a button to cancel the adding of an item
    Button btnCancel;


    public DialogAddItemFragment() {
        // Required empty public constructor
    }


    /**
     * When the view is created, set the style and call the the super's bundle
     * @param savedInstanceState Values bundled together
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.GlobeTrotter);

    }

    /**
     * Calls the super's onStart method
     */
    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if(dialog != null) {
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            int width = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    /**
     * When the view is created, match the layout with it
     * @param inflater The layout XML file to be inflated
     * @param container Collection of view
     * @param savedInstanceState Values bundled together
     * @return The view of the fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dialog_input_item, container, false);

        //Find the item name in the layout file and set it to the edit text
        this.itemName = (EditText) view.findViewById(R.id.edit_item);

        //Find the quantity in the layout file and set it to the edit text
        this.quantity = (EditText) view.findViewById(R.id.edit_quantity_picker);

        //Find the cancel in the layout file and set it to the button
        this.btnCancel = (Button) view.findViewById(R.id.btn_cancel);

        //Find the submit in the layout file and set it to the button
        this.btnSubmit = (Button) view.findViewById(R.id.btn_submit);

        //Set onClick listeners on the buttons
        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        //Return the view of the fragment
        return view;
    }

    /**
     * Handles the onClick events for the buttons
     * @param v The view which the onClick is associated with
     */
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            //If the button is cancel
            case R.id.btn_cancel:
                //Closes the the fragment
                dismiss();
                break;
            //If the button is submit
            case R.id.btn_submit:
                try
                {

                    if(containsDigit(itemName.getText().toString())){
                        Toast.makeText(getContext(), R.string.itemListAddItemFragmentCheckNum, Toast.LENGTH_LONG).show();
                    }
                    else {
                        //Instantiates a pass item interface to pass the values
                        IPassItem activity = (IPassItem) getActivity();

                        //On retrieval of the items, set the name and quantity
                        activity.onRetrieveItem(itemName.getText().toString(), Integer.parseInt(quantity.getText().toString()));

                    //Closes the fragment
                    dismiss();
                    }
                }
                catch (Exception e){
                    //Error handling
                    Toast.makeText(getContext(), R.string.itemListAddItemFragmentCheckEmpty, Toast.LENGTH_LONG).show();

                }
                break;
            default:
                break;
        }
    }


    private boolean containsDigit(String text){
        String numbers = "0123456789";
        for(char s : numbers.toCharArray()){
            if((text).contains(s+""))return true;
        }
        return false;
    }
}
