package com.u1626889.warwickdrama;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PostComparator implements java.util.Comparator<Post>{

    private Context context;
    private List<Post> posts;

    public PostComparator(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
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

        String corpus = "";
        for(int i = 0; i < posts.size(); i++) {
            corpus += posts.get(i).getDesc() + " ";
        }
        corpus = stemSentence(corpus.substring(0,corpus.length()-1));

        String post1desc = stemSentence(post1.getDesc().toLowerCase());
        String post2desc = stemSentence(post2.getDesc().toLowerCase());

        double tfIdf1 = calculateTfIdf(corpus,post1desc,post1.getTags());
        double tfIdf2 = calculateTfIdf(corpus, post2desc, post2.getTags());

        Log.d("thing", "1 is "+tfIdf1+" 2 is "+tfIdf2);

        String post1tags = post1.getTags().toLowerCase();
        String post2tags = post2.getTags().toLowerCase();
        int tagMatch1 = tagsCompare(post1tags, tags);
        int tagMatch2 = tagsCompare(post2tags, tags);

        if(tagMatch1 > tagMatch2) return -1;
        else if(tagMatch1 < tagMatch2) return 1;
        else {
            //                  TIE-BREAKER?
            return 0;
        }
    }

    public double calculateTfIdf(String corpusStr, String documentStr, String termsStr) {
        ArrayList<String> corpus = new ArrayList<>(Arrays.asList(corpusStr.split(" ")));
        ArrayList<String> document = new ArrayList<>(Arrays.asList(documentStr.split(" ")));
        ArrayList<String> terms = new ArrayList<>(Arrays.asList(termsStr.split(",")));

//        Log.d("thing","term length is "+terms.size()+" doc len is "+document.size()+" corpus len is "+corpus.size());

        double total = 0.0;
        for(int n = 0; n < terms.size(); n++) {
            // Finds the number of times the term is in THIS post
            int tf = 0;
            for(int i = 0; i < document.size(); i++) {
                if(document.get(i).equalsIgnoreCase(terms.get(n))) tf++;
            }
            tf /= document.size();

            // Finds the number of times the term is in the whole document corpus
            double idf = 0.0;
            for(int i = 0; i < corpus.size(); i++) {
                if(corpus.get(i).equalsIgnoreCase(terms.get(n))) idf++;

//                Log.d("thing","'"+corpus.get(i)+"'");

                if(corpus.get(i).equalsIgnoreCase("post")) Log.d("thing","Found post. It's definitely here in post "+i);

            }
//            Log.d("thing"," shiiiit "+idf);
            idf = Math.log(corpus.size()/idf);

            if(tf!=0) {
                Log.d("thing", "YAAAAAAYYYYY tf "+tf+" idf "+idf);
            }
            total += tf*idf;
        }

        return total;
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
