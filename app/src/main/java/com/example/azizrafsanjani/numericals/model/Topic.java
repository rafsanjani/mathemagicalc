package com.example.azizrafsanjani.numericals.model;

/**
 * Created by forev on 3/16/2018.
 */

public class Topic {
    private String title, description;

    public Topic(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Topic(){};

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
