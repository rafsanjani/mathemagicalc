package com.foreverrafs.numericals.model;

import com.foreverrafs.numericals.utils.Constants;

/**
 * Created by Rafsanjani on 6/5/2019
 */
public class OperationMenu {
    private String title;
    private int menuCategory;
    private int imageResource;

    public OperationMenu(String title, int imageResource, int menuCategory) {
        this.title = title;
        this.imageResource = imageResource;
        this.menuCategory = menuCategory;
    }

    public OperationMenu(String title, int imageResource) {
        this.title = title;
        this.imageResource = imageResource;
        this.menuCategory = Constants.MENU_CATEGORY_NONE;
    }

    public String getTitle() {
        return title;
    }

    public int getImageResource() {
        return imageResource;
    }

    public int getMenuCategory() {
        return menuCategory;
    }
}