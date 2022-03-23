package com.example.wecaremain;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.PorterDuff;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.parse.ParseObject;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ChennlAdapter extends RecyclerView.Adapter<ChennlAdapter.ViewHolder> {


    Context context;
    List<Pair<String, Pair<Integer, Integer>>> channels;

    public ChennlAdapter(Context context, List<Pair<String, Pair<Integer, Integer>>> stories) {
        this.context = context;
        this.channels = stories;
    }

    @NonNull
    @NotNull
    @Override
    public ChennlAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View posts = LayoutInflater.from(context).inflate(R.layout.item_channel,parent,false);
        return new ViewHolder(posts);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ChennlAdapter.ViewHolder holder, int position) {
        Pair<String, Pair<Integer, Integer>> match = channels.get(position);
        holder.bind(match);
    }

    @Override
    public int getItemCount() {
        return channels.size();
    }

    public void clear() {
        channels.clear();
    }

    public void addAll(List<Pair<String, Pair<Integer, Integer>>> p) {
        channels.addAll(p);
    }

    public void add(Pair<String, Pair<Integer, Integer>> s) {
        channels.add((s));
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvStory;
        ImageView ivPicture;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvStory = itemView.findViewById(R.id.tvChannel);
            ivPicture = itemView.findViewById(R.id.ivProfilePicture);
        }

        public void bind(Pair<String, Pair<Integer, Integer>> story) {

            tvStory.setText(story.first);
            tvStory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent((Activity)context, Comments.class);
                    i.putExtra("cname", story.first.toLowerCase());
                    context.startActivity(i);
                }
            });

            ivPicture.setImageDrawable(AppCompatResources.getDrawable(context, story.second.first));
            ivPicture.getBackground().setColorFilter(context.getResources().getColor(story.second.second), PorterDuff.Mode.SRC_ATOP);
            //ivPicture.setBackgroundColor(context.getColor(story.second.second));

        }
    }
}
