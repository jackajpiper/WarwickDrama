package com.u1626889.warwickdrama;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostViewHolder> {

    class PostViewHolder extends RecyclerView.ViewHolder {
        private final TextView postItemView;
        private final ImageView postImageView;

        private PostViewHolder(View itemView) {
            super(itemView);
            postItemView = itemView.findViewById(R.id.textView);
            postImageView = itemView.findViewById(R.id.imageView2);
            Log.d("thing","I'm in the PostViewHolder class constructor. thing is "+ postItemView.getText());
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
            holder.postItemView.setText(current.getTitle()+"yeet"); // THIS IS WHAT'S DISPLAYED IN THE CARD
            holder.postImageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    int arrayNum = idArr.indexOf(cardid);
                    tv.setText(" "+arrayNum);
                    if(expandArr.get(arrayNum) == false) {
                        tv.setText(society + "\n"+ type + "\n\n" + title + "\n\n" + desc);
                        button.setBackgroundResource(R.drawable.ic_collapse_card);
                        expandArr.set(arrayNum,true);
                    } else {
                        tv.setText(society + "\n"+ type + "\n\n" + title);
                        button.setBackgroundResource(R.drawable.ic_expand_card);
                        expandArr.set(arrayNum,false);
                    }
                }
            });

            Log.d("thing", "onBindViewHolder, setting the text in a PostView holder to be "+current.getTitle()+", holder item text is now "+holder.postItemView.getText());
        } else {
            // Covers the case of data not being ready yet.
            holder.postItemView.setText("No data");
        }
    }

    void setPosts(List<Post> posts) {
        mPosts = posts;

        Log.d("thing", "PostListAdapter.   title of second is "+posts.get(1).getDesc());
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

}
