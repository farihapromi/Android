package com.example.wecaremain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class antiburnout extends AppCompatActivity {

    RecyclerView recyclerView;

    String[] s1, s2;
    int[] images = {R.drawable.writing, R.drawable.meditating, R.drawable.painting, R.drawable.walking};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_antiburnout);

        recyclerView = findViewById(R.id.recyclerView);

        s1 = getResources().getStringArray(R.array.relaxationActivities);
        s2 = getResources().getStringArray(R.array.rActivityDescriptions);

        MyAdapter myAdapter = new MyAdapter(this, s1, s2, images);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}