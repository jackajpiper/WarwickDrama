package com.u1626889.warwickdrama;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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

        tags = stemSentence(tags);

        String post1desc = post1.getDesc().toLowerCase();
        post1desc = stemSentence(post1desc);
        String post2desc = post2.getDesc().toLowerCase();
        post2desc = stemSentence(post2desc);

        String post1tags = post1.getTags().toLowerCase();
        String post2tags = post2.getTags().toLowerCase();

        int score1 = tagsCompare(post1desc, tags);
        int score2 = tagsCompare(post2desc, tags);

//        String leet = stemSentence("Hello, I'm jack! absolutely lovely to meet you, today we're going to replacing you without yeet");




        if(score1 > score2) return -1;
        else if(score1 < score2) return 1;
        else {
            //                  TIE-BREAKER?
            return 0;
        }
    }

    public int tagsCompare(String t1, String t2) {
        ArrayList<String> first = new ArrayList<>(Arrays.asList(t1.split(",")));
        ArrayList<String> second = new ArrayList<>(Arrays.asList(t2.split(",")));

        Log.d("thing","first is "+first.toString());
        Log.d("thing","second is '"+second.get(0)+"'");

        int score = 0;

        for(int i = 0; i < first.size(); i++) {
            if(second.contains(first.get(i))) {
                score++;
            }
        }
        Log.d("thing",""+score);
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

    public String stemSentence(String text) {

//        Log.d("thing","Initial value: " +text);


        // make it lower case
        text = text.toLowerCase();
        // hack to keep dashed words (e.g. "non-specific" rather than "non" and "specific")
        text = text.replaceAll("-+", "-0");
        // replace any punctuation char but apostrophes and dashes by a space
        text = text.replaceAll("[\\p{Punct}&&[^'-]]+", " ");
        // replace most common english contractions
        text = text.replaceAll("(?:'(?:[tdsm]|[vr]e|ll))+\\b", "");


        ArrayList<String> wordsList = new ArrayList<>(Arrays.asList(text.split(" ")));
        wordsList.removeAll(Collections.singleton(""));

//        Log.d("thing","After prep: " +wordsList);


        String stemmedSentence = "";

        Stemmer stemmer;
        for(int i = 0; i < wordsList.size(); i++) {
            stemmer = new Stemmer();
            stemmer.add(wordsList.get(i).toCharArray(), wordsList.get(i).length());
            stemmer.stem();
            stemmedSentence += " "+stemmer.toString();

        }

//        Log.d("thing","Finally: "+stemmedSentence);


        return stemmedSentence;
    }

}
