package com.team.beanie.fragments.inputs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.beanielibrary.model.Item;
import com.team.beanie.recyclerview.IRefresh;
import com.team.beanie.R;

/**
 * A dialog to edit the items in the items lis
 */
public class DialogUpdateItemFragment extends DialogFragment implements View.OnClickListener {

    //Instantiates an edit text for the name of the item
    EditText itemName;

    //Instantiates an edit text for the quantity of the item
    EditText quantity;

    //Instantiates a button to cancel the adding of an item
    Button btnCancel;

    //Instantiates a button to submit the item and add it to the list
    Button btnSubmit;

    //Instantiates an old item to be updated with new values
    Item oldItem;

    public DialogUpdateItemFragment() {
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

        //Set the values of the old item
        oldItem = getArguments().getParcelable("oldItem");

        //Set the text of the fragment to the old item's name
        itemName.setText(oldItem.getItemName());

        //Set the text of the fragment to the old item's quantity
        quantity.setText(oldItem.getQuantity() + "");

        //Find the cancel in the layout file and set it to the button
        this.btnCancel = (Button) view.findViewById(R.id.btn_cancel);

        //Find the submit in the layout file and set it to the button
        this.btnSubmit = (Button) view.findViewById(R.id.btn_submit);

        //Toast.makeText(getContext(), itemName.getText().toString() + quantity.getText().toString(),Toast.LENGTH_LONG).show();

        //Sets onClickListeneres to both buttons
        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        //Returns the view of the fragment
        return view;
    }

    /**
     * Handles the click event of the buttons
     * @param v The button views
     */
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            //If the view is the cancel button
            case R.id.btn_cancel:
                //Closes the fragment
                dismiss();
                break;
            //If the view is the submit button
            case R.id.btn_submit:
                try
                {
                    //Instantiates a refresh item interface to pass the new values
                    IRefresh activity = (IRefresh) getActivity();
                    if(containsDigit(itemName.getText().toString())){
                        Toast.makeText(getContext(), R.string.itemListAddItemFragmentCheckNum, Toast.LENGTH_LONG).show();
                    }
                    else {
                        //Once updates are done, change the text from the old one to the new one
                        activity.onFinishUpdate((new Item(oldItem.getPosition(), itemName.getText().toString(), Integer.parseInt(quantity.getText().toString()))));

                        //Closes the fragment
                        dismiss();
                    }
                }
                catch (Exception e){
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
