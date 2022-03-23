package com.example.wecaremain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class Comments extends AppCompatActivity {

    ArrayList<Comment> comments;
    RecyclerView rvStories;
    CommentsAdapter adapter ;
    EditText edComment;
    Button btnAddComment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments3);
    rvStories = findViewById(R.id.rvPosts);
        edComment = findViewById(R.id.edComment);
        btnAddComment = findViewById(R.id.btnCOmpose);
        comments = new ArrayList<>();
        adapter = new CommentsAdapter(Comments.this, comments);
        rvStories.setLayoutManager(new LinearLayoutManager(Comments.this));
        rvStories.setAdapter(adapter);
        populateQueryPosts();

        btnAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edComment.getText().toString().length() == 0)
                    return;
                Comment cmt = new Comment();
                cmt.put("text", edComment.getText().toString());
                cmt.put("Author", ParseUser.getCurrentUser());
                cmt.put("channel", getIntent().getExtras().getString("cname"));
                cmt.saveInBackground();
                edComment.setText("");
                comments.add(cmt);
                adapter.notifyDataSetChanged();
            }
        });
    }




    protected void populateQueryPosts()
    {
        ParseQuery<Comment> query = ParseQuery.getQuery(Comment.class);
        query.include("Author");
        query.setLimit(5);
        query.whereEqualTo("channel", getIntent().getExtras().getString("cname"));
        query.addAscendingOrder(Comment.KEY_CREATED_AT);

        query.findInBackground(new FindCallback<Comment>() {
            @Override
            public void done(List<Comment> p, ParseException e) {
                if(e != null)
                {
                    return;
                }


                adapter.clear();
                adapter.addAll(p);
                adapter.notifyDataSetChanged();
            }
        });
    }
}