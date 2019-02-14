package com.u1626889.warwickdrama;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PostCalendarAdapter extends RecyclerView.Adapter<PostCalendarAdapter.PostViewHolder> {

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

        }
    }

    private final LayoutInflater mInflator;
    private ArrayList<Post> mPostsCache; // Cached copy of the posts
    private ArrayList<Post> mPosts;
    private ArrayList<Post> filteredPosts;
    private Context context;

    PostCalendarAdapter(Context context2) {
        mInflator = LayoutInflater.from(context2);
        mPosts = new ArrayList<Post>();
        filteredPosts = new ArrayList<Post>();
        mPostsCache = new ArrayList<Post>();
        this.context = context2;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflator.inflate(R.layout.recyclerview_calendar_post,parent,false);
        return new PostViewHolder(itemView);
    }

    //  HERE IS WHERE WE FILL OUT THE CARD WITH THE APPROPRIATE DATA
    @Override
    public void onBindViewHolder(final PostViewHolder holder, final int position) {
        if (mPosts != null) {
            final Post current = mPosts.get(position);
            Log.d("thing","This is the "+position+"th post in a list of "+mPosts.size()+" posts");
            holder.postItemView.setText(current.getTitle()); // THIS IS WHAT'S DISPLAYED IN THE CARD
            holder.postView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ViewPostActivity.class);
                    intent.putExtra("postNumber", holder.getAdapterPosition());
                    intent.putParcelableArrayListExtra("post", mPosts);
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
        mPostsCache = new ArrayList<>(mPosts);
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
        return mPostsCache;
    }

    public void filter(String date, boolean saves) {

        if(saves && !date.equals("")) {
            mPosts.clear();
            // Gets the list of currently saved posts
            SharedPreferences prefs = context.getSharedPreferences("savedPosts",0);
            String savedPosts = prefs.getString("savedPosts","");
            ArrayList<String> savedIds = new ArrayList<>(Arrays.asList(savedPosts.split(",")));

            for(Post post : mPostsCache) {
                ArrayList<String> givenArr = new ArrayList<>(Arrays.asList(date.split("-")));
                ArrayList<String> postArr = new ArrayList<>(Arrays.asList(post.getExp_date().split("/")));
                int givenYear = Integer.parseInt(givenArr.get(0));
                int postYear = Integer.parseInt(postArr.get(2));
                int givenMonth = Integer.parseInt(givenArr.get(1));
                int postMonth = Integer.parseInt(postArr.get(1));
                int givenDay = Integer.parseInt(givenArr.get(2));
                int postDay = Integer.parseInt(postArr.get(0));

                if(givenYear==postYear & givenMonth==postMonth & givenDay==postDay & savedIds.contains(Integer.toString(post.getId()))) {
                    mPosts.add(post);
                }
            }
        } else if(!date.equals("")) {
            mPosts.clear();
            for(Post post : mPostsCache) {
                ArrayList<String> givenArr = new ArrayList<>(Arrays.asList(date.split("-")));
                ArrayList<String> postArr = new ArrayList<>(Arrays.asList(post.getExp_date().split("/")));
                int givenYear = Integer.parseInt(givenArr.get(0));
                int postYear = Integer.parseInt(postArr.get(2));
                int givenMonth = Integer.parseInt(givenArr.get(1));
                int postMonth = Integer.parseInt(postArr.get(1));
                int givenDay = Integer.parseInt(givenArr.get(2));
                int postDay = Integer.parseInt(postArr.get(0));

                if(givenYear==postYear & givenMonth==postMonth & givenDay==postDay) {
                    mPosts.add(post);
                }
            }
        }

        notifyDataSetChanged();

    }

}
