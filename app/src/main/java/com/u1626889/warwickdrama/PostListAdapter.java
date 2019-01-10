package com.u1626889.warwickdrama;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostViewHolder> {

    class PostViewHolder extends RecyclerView.ViewHolder {
        private final TextView postItemView;
        private final TextView postDescView;
        private final ImageView postImageView;
        private final ConstraintLayout postView;

        private PostViewHolder(View itemView) {
            super(itemView);
            postView = itemView.findViewById(R.id.cardView);
            postItemView = itemView.findViewById(R.id.titleText);
            postDescView = itemView.findViewById(R.id.descText);
            postImageView = itemView.findViewById(R.id.imageView2);
//            Log.d("thing","I'm in the PostViewHolder class constructor. thing is "+ postItemView.getText());
        }
    }

    private final LayoutInflater mInflator;
    private ArrayList<Post> mPosts; // Cached copy of the posts

    PostListAdapter(Context context) { mInflator = LayoutInflater.from(context); }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflator.inflate(R.layout.recyclerview_post,parent,false);
        return new PostViewHolder(itemView);
    }

    //  HERE IS WHERE WE FILL OUT THE CARD WITH THE APPROPRIATE DATA
    @Override
    public void onBindViewHolder(final PostViewHolder holder, final int position) {
        if (mPosts != null) {
            final Post current = mPosts.get(position);
            holder.postItemView.setText(current.getTitle()); // THIS IS WHAT'S DISPLAYED IN THE CARD
            Log.d("thing","Setting the onclick listener");
            holder.postImageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("thing","BIG. YEET. text is '"+holder.postDescView.getText()+"'");
                    if(holder.postDescView.getText().equals("")) {
                        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) holder.postDescView.getLayoutParams();
                        params.height = 200;
                        holder.postDescView.setLayoutParams(params);
                        holder.postDescView.setText(current.getDesc());
                        holder.postImageView.setBackgroundResource(R.drawable.ic_collapse_card);
                    } else {ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) holder.postDescView.getLayoutParams();
                        params.height = 0;
                        holder.postDescView.setLayoutParams(params);
                        holder.postDescView.setText("");
                        holder.postImageView.setBackgroundResource(R.drawable.ic_expand_card);
                    }
                }
            });
            holder.postView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ViewPostActivity.class);
                    intent.putExtra("postNumber", holder.getAdapterPosition());

                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("posts", getPosts());

                    boolean thing = getPosts()dickchaneymademoneyofftheiraqwar==null;

                    Log.d("thing","Touchdown! "+thing);
                    intent.putExtras(bundle);
                    v.getContext().startActivity(intent);
                }
            });

//            Log.d("thing", "onBindViewHolder, setting the text in a PostView holder to be "+current.getTitle()+", holder item text is now "+holder.postItemView.getText());
        } else {
            // Covers the case of data not being ready yet.
            holder.postItemView.setText("No data");
        }
    }

    void setPosts(ArrayList<Post> posts) {
        mPosts = posts;

//        Log.d("thing", "PostListAdapter.   title of second is "+posts.get(1).getDesc());
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

    public Post getPostAt(int index) {
        return mPosts.get(index);
    }

    public ArrayList<Post> getPosts() {
        return mPosts;
    }

}
