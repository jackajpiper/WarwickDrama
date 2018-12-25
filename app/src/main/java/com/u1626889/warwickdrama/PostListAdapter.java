package com.u1626889.warwickdrama;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostViewHolder> {

    class PostViewHolder extends RecyclerView.ViewHolder {
        private final TextView postItemView;

        private PostViewHolder(View itemView) {
            super(itemView);
            postItemView = itemView.findViewById(R.id.textView);
        }
    }


    private final LayoutInflater mInflator;
    private List<Post> mPosts; // Cached copy of the posts

    PostListAdapter(Context context) { mInflator = LayoutInflater.from(context); }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflator.inflate(R.layout.recyclerview_post,parent,false);
        return new PostViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        if (mPosts != null) {
            Post current = mPosts.get(position);
            holder.postItemView.setText(current.getTitle()); // THIS IS WHAT'S DISPLAYED IN THE CARD
        } else {
            // Covers the case of data not being ready yet.
            holder.postItemView.setText("No data");
        }
    }

    void setPosts(List<Post> posts) {
        mPosts = posts;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mPosts != null) {
            return mPosts.size();
        } else return 0;
    }
}
