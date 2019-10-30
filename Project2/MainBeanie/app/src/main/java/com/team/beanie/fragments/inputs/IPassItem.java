package com.team.beanie.fragments.inputs;

/**
 * Interface to pass values from fragment to the activity for the item list
 */
public interface IPassItem {
    void onRetrieveItem(String item, int quantity);
}
