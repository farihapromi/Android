package com.example.wecaremain;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("VentStory")
public class VentStory extends ParseObject {

    public String getStory() {
        return getString("Story");
    }
}
