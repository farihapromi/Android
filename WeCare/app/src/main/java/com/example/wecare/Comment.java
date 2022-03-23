package com.example.wecaremain;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Comment")
public class Comment extends ParseObject {
    public String getText() {
        return getString("text");
    }

    public ParseUser getAuthor() {
        return getParseUser("Author");
    }
}
