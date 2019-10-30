package com.team.beanie.recyclerview;

import com.example.beanielibrary.model.Item;

/**
 * Interface for handling the refreshing of views with new values
 */
public interface IRefresh {
    void refresh();
    void update(Item item);
    void onFinishUpdate(Item item);
}
