package com.example.wecaremain;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ventCompose extends AppCompatActivity {
    Button btn;
    EditText text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vent_compose);
        text = findViewById(R.id.edVent);
        btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VentStory story = new VentStory();
                story.put("Story", text.getText().toString());
                story.saveInBackground();
                finish();
            }
        });
    }
}