package com.example.wecaremain;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    Context context;
    List<Comment> channels;

    public CommentsAdapter(Context context, List<Comment> comments) {
        this.context = context;
        this.channels = comments;
    }

    @NonNull
    @NotNull
    @Override
    public CommentsAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View posts = LayoutInflater.from(context).inflate(R.layout.item_comment,parent,false);
        return new ViewHolder(posts);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CommentsAdapter.ViewHolder holder, int position) {
        Comment comment = channels.get(position);
        holder.bind(comment);
    }

    @Override
    public int getItemCount() {
        return channels.size();
    }

    public void clear() {
        channels.clear();
    }

    public void addAll(List<Comment> p) {
        channels.addAll(p);
    }

    public void add(Comment s) {
        channels.add((s));
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvText;
        ImageView ivPic;
        TextView tvAuthor;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvText = itemView.findViewById(R.id.tvCommentText);
            ivPic = itemView.findViewById(R.id.ivProfilePicture);
            tvAuthor = itemView.findViewById(R.id.tvAuthorName);
        }

        public void bind(Comment comment) {

            tvText.setText(comment.getText());
            tvAuthor.setText(comment.getAuthor().getUsername());
            tvText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent((Activity)context, Comments.class);
                    i.putExtra("cname", comment.getText());
                    context.startActivity(i);
                }
            });

            try {
                ParseFile p = (comment.getAuthor().getParseFile("picture"));
                if (p != null) {
                    Glide.with(context).load(p.getFile()).transform(new CircleCrop()).into(ivPic);
                } else {
                    Glide.with(context).load(R.drawable.ic_launcher_background).transform(new CircleCrop()).into(ivPic);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }
}
