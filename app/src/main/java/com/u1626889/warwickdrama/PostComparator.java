package com.u1626889.warwickdrama;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

public class PostComparator implements java.util.Comparator<Post>{
    @Override
    public int compare(Post post1, Post post2) {
// ---------------------- THIS IS WHERE THE RECOMMENDATION COMPARISON HAPPENS ----------------------

        String placeholderTags = "perform,accent";

        int score1 = tagsCompare(post1.getTags(), placeholderTags);
        int score2 = tagsCompare(post2.getTags(), placeholderTags);

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
