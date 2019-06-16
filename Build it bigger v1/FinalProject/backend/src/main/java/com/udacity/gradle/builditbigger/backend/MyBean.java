package com.udacity.gradle.builditbigger.backend;

import com.example.jokeslibrary.JokesLibrary;

/** The object model for the data we are sending through endpoints */
public class MyBean {

    private String myData;

    public String getData() {
        return new JokesLibrary().getJoke();
    }

}