package com.u1626889.warwickdrama;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class PostComparator implements java.util.Comparator<Post>{

    private Context context;
    private FragmentActivity activity;
    // The interface through which we access the database
    private WDViewModel mViewModel;

    public PostComparator(Context context, Activity activity) {
        this.context = context;
        this.activity = (FragmentActivity) activity;
    }

    @Override
    public boolean equals(Object post1) {
        return true;
    }

    @Override
    public int compare(Post post1, Post post2) {
// ---------------------- THIS IS WHERE THE RECOMMENDATION COMPARISON HAPPENS ----------------------
        SharedPreferences prefs = context.getSharedPreferences("user_tags",0);
        String tags = prefs.getString("user_tags","").toLowerCase();
        tags = stemSentence(tags);

        mViewModel = ViewModelProviders.of(activity).get(WDViewModel.class);
        ArrayList<Post> posts = (ArrayList<Post>) mViewModel.getAllPosts().getValue();

        String corpus = "";
        for(int i = 0; i < posts.size(); i++) {
            corpus += posts.get(i).getDesc();
        }
        corpus = stemSentence(corpus);

        String post1desc = stemSentence(post1.getDesc().toLowerCase());
        String post2desc = stemSentence(post2.getDesc().toLowerCase());

        ArrayList<String> document = new ArrayList<>(Arrays.asList(post1desc.split(" ")));
        double post1TFIDF = 0.0;
        for(int i = 0; i < )






        String post1tags = post1.getTags().toLowerCase();
        String post2tags = post2.getTags().toLowerCase();
        int score1 = tagsCompare(post1tags, tags);
        int score2 = tagsCompare(post2tags, tags);

        if(score1 > score2) return -1;
        else if(score1 < score2) return 1;
        else {
            //                  TIE-BREAKER?
            return 0;
        }
    }

    public double calculateTfIdf(ArrayList<String> corpus, ArrayList<String> document, String term) {

        // Finds the number of times the term is in THIS post
        int tf = 0;
        for(int i = 0; i < document.size(); i++) {
            if(document.get(i).equalsIgnoreCase(term)) tf++;
        }
        tf /= document.size();

        // Finds the number of times the term is in the whole document corpus
        double idf = 0.0;
        for(int i = 0; i < corpus.size(); i++) {
            if(corpus.get(i).equalsIgnoreCase(term)) idf++;
        }
        idf = Math.log(corpus.size()/idf);

        double tfIdf = tf*idf;

        return tfIdf;
    }

    public int tagsCompare(String t1, String t2) {

        t1 = stemSentence(t1);
        t2 = stemSentence(t2);

        ArrayList<String> first = new ArrayList<>(Arrays.asList(t1.split(" ")));
        ArrayList<String> second = new ArrayList<>(Arrays.asList(t2.split(" ")));

        int score = 0;

        for(int i = 0; i < first.size(); i++) {
            if(second.contains(first.get(i))) {
                score++;
            }
        }
        Log.d("thing",""+score);
        return score;
    }

    public String stemSentence(String text) {

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

        String stemmedSentence = "";

        Stemmer stemmer;
        for(int i = 0; i < wordsList.size(); i++) {
            stemmer = new Stemmer();
            stemmer.add(wordsList.get(i).toCharArray(), wordsList.get(i).length());
            stemmer.stem();
            stemmedSentence += stemmer.toString()+" ";

        }

        if(stemmedSentence.length()==0) return stemmedSentence;
        else return stemmedSentence.substring(0,stemmedSentence.length()-1);
    }

    public int getCount(String term, ArrayList<String> array) {
        int x = 0;
        for(int i = 0; i < array.size(); i++) {
            if(array.get(i)==term) x++;
        }
        return x;
    }

}
