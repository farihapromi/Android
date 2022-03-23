package com.example.wecaremain;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;

import java.util.Random;

public class MotivationActivity extends AppCompatActivity {
    TextView tvQuote;
    ImageView ivPic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motivation);
        tvQuote = findViewById(R.id.tvQuote);
        setButtons();
    }

    public void setButtons()
    {
        Button btnDream = findViewById(R.id.btnDream);
        ivPic = findViewById(R.id.ivIcon);
        btnDream.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
            setQuote("dream");
            tvQuote.getBackground().setColorFilter(getResources().getColor(R.color.mind), PorterDuff.Mode.SRC_ATOP);
                Glide.with(MotivationActivity.this).load(R.drawable.meditation).into(ivPic);
            }
        });
        Button btnInsp = findViewById(R.id.btnInspire);
        btnInsp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                setQuote("inspiration");
                tvQuote.getBackground().setColorFilter(getResources().getColor(R.color.light_blue), PorterDuff.Mode.SRC_ATOP);
                Glide.with(MotivationActivity.this).load(R.drawable.inspire).into(ivPic);
            }
        });
        Button btngrow = findViewById(R.id.btnGrow);
        btngrow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                tvQuote.getBackground().setColorFilter(getResources().getColor(R.color.yellow), PorterDuff.Mode.SRC_ATOP);
                Glide.with(MotivationActivity.this).load(R.drawable.grow).into(ivPic);
                setQuote("grow");
            }
        });
        Button btnLove = findViewById(R.id.btLove);
        btnLove.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Glide.with(MotivationActivity.this).load(R.drawable.love).into(ivPic);
                tvQuote.getBackground().setColorFilter(getResources().getColor(R.color.purple), PorterDuff.Mode.SRC_ATOP);
                setQuote("love");
            }
        });
        Button btnElse = findViewById(R.id.btnElse);
        btnElse.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Glide.with(MotivationActivity.this).load(R.drawable.selse).into(ivPic);
                tvQuote.getBackground().setColorFilter(getResources().getColor(R.color.pink), PorterDuff.Mode.SRC_ATOP);
                setQuote("else");
            }
        });


    }

    public void setQuote(String key)
    {
        Random rand = new Random(); //instance of random class
        int upperbound = Quotes.dict.get(key).size();
        tvQuote.setText(Quotes.dict.get(key).get(rand.nextInt(upperbound)));
    }
}