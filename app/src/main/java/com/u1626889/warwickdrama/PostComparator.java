package com.u1626889.warwickdrama;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

public class PostComparator implements java.util.Comparator<Post>{

    private Context context;

    public PostComparator(Context context) {
        this.context = context;
    }

    @Override
    public int compare(Post post1, Post post2) {
// ---------------------- THIS IS WHERE THE RECOMMENDATION COMPARISON HAPPENS ----------------------

        SharedPreferences prefs = context.getSharedPreferences("user_tags",0);
        String tags = prefs.getString("user_tags","").toLowerCase();

        String post1tags = post1.getTags().toLowerCase();
        String post2tags = post2.getTags().toLowerCase();

        int score1 = tagsCompare(post1tags, tags);
        int score2 = tagsCompare(post2tags, tags);

        if(score1 > score2) return -1;
        else if(score1 < score2) return 1;
        else {
            //                  TIE-BREAKER ONE
            return 0;
        }
    }

    public int tagsCompare(String t1, String t2) {
        ArrayList<String> first = new ArrayList<>(Arrays.asList(t1.split(",")));
        ArrayList<String> second = new ArrayList<>(Arrays.asList(t2.split(",")));

        int score = 0;

        for(int i = 0; i < first.size(); i++) {
            if(second.contains(first.get(i))) {
                score++;
            }
        }

        return score;
    }

    public int compareAlpha(Post post1, Post post2) {
        String title1 = post1.getTitle().toLowerCase();
        String title2 = post2.getTitle().toLowerCase();
        int result = title1.compareTo(title2);
        return result;
    }

    @Override
    public boolean equals(Object post1) {
        return true;
    }

}
