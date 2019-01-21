package com.u1626889.warwickdrama;

public class PostComparator implements java.util.Comparator<Post>{
    @Override
    public int compare(Post post1, Post post2) {
// ---------------------- THIS IS WHERE THE RECOMMENDATION COMPARISON HAPPENS ----------------------
        return 1;
    }

    public int compareAlpha(Post post1, Post post2) {
        if(post1.getTitle().toCharArray()[0] == 'a') {
            return 1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object post1) {
        return true;
    }

}
