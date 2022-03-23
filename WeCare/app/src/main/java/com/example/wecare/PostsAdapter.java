package com.example.wecaremain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.ParseObject;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    Context context;
    List<ParseObject> stories;

    public PostsAdapter(Context context, List<ParseObject> stories) {
        this.context = context;
        this.stories = stories;
    }

    @NonNull
    @NotNull
    @Override
    public PostsAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View posts = LayoutInflater.from(context).inflate(R.layout.item_vent,parent,false);
        return new ViewHolder(posts);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PostsAdapter.ViewHolder holder, int position) {
        ParseObject match = stories.get(position);
        holder.bind(match);
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    public void clear() {
        stories.clear();
    }

    public void addAll(List<VentStory> p) {
        stories.addAll(p);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvStory;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvStory = itemView.findViewById(R.id.tvVentStory);
        }

        public void bind(ParseObject story) {
            tvStory.setText(story.getString("Story"));
        }
    }
}
