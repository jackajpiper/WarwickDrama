package com.u1626889.warwickdrama;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import android.util.Log;

public class PostComparator implements java.util.Comparator<Post>{

    private Context context;
    private List<Post> posts;
    private ArrayList<Integer> savedArr;
    private String corpus;
    private StringBuilder savedCorpus;
    private String tags;

    public PostComparator(Context context, List<Post> newposts) {
        this.context = context;
        posts = newposts;
//        Log.d("thing","Forming corpus");
        StringBuilder corpusBuilder = new StringBuilder();
        for(int i = 0; i < posts.size(); i++) {
            corpusBuilder.append(posts.get(i).getDesc()).append(" ").append(posts.get(i).getTitle()).append(" ");
        }
        corpus = corpusBuilder.toString();
        corpus = stemSentence(corpus.substring(0,corpus.length()-1));

        SharedPreferences prefs = context.getSharedPreferences("user_tags",0);
        String tags = prefs.getString("user_tags","").toLowerCase();
        tags = stemSentence(tags);
        this.tags = tags;

//        SharedPreferences savedPrefs = context.getSharedPreferences("savedPosts",0);
//        String savedIds = savedPrefs.getString("savedPosts","");
//        ArrayList<String> savedArrStr = new ArrayList<>(Arrays.asList(savedIds.split(",")));
//        Log.d("thing","str is "+savedIds);
//        savedArr = new ArrayList<>();
//
//        if(!savedIds.equals("")) {
//            for(int i =0; i<savedArrStr.size(); i++) {
//                savedArr.add(Integer.parseInt(savedArrStr.get(i)));
//            }
//        }
//        ArrayList<String> savedStrings = new ArrayList<>();
////        for(int i=0; i<savedArr.size(); i++) {
////            if(s)
////        }
//
//        savedCorpus = new StringBuilder();
//        for(int i=0; i<savedArr.size(); i++) {
//
//            for(int n=0; n<posts.size(); n++) {
//                if(posts.get(n).getId() == savedArr.get(i)); {
//
//                    savedCorpus.append(posts.get(n).getDesc());
//
//                }
//            }
//
//        }


    }

    @Override
    public boolean equals(Object post1) {
        return true;
    }

    @Override
    public int compare(Post post1, Post post2) {
// ---------------------- THIS IS WHERE THE RECOMMENDATION COMPARISON HAPPENS ----------------------
//        Log.d("thing","Starting comparison");

//        Log.d("thing","Stemming post descs");
        String post1Text = stemSentence(post1.getDesc().toLowerCase());
        String post2Text = stemSentence(post2.getDesc().toLowerCase());

//        Log.d("thing","Calculating tfIdf");

//        Calculates the tf-idf score between a post and the corpus
//        That is, find the tf-idf score of a particular tag, and sum them
        double tfIdf1 = calculateTfIdf(corpus,post1Text,tags);
        double tfIdf2 = calculateTfIdf(corpus, post2Text, tags);

        String post1tags = post1.getTags().toLowerCase();
        String post2tags = post2.getTags().toLowerCase();
        int tagMatch1 = tagsCompare(post1tags, tags);
        int tagMatch2 = tagsCompare(post2tags, tags);

        int savedAddition1 = 0;
        int savedAddition2 = 0;
//        if(savedArr.contains(post1.getId())) savedAddition1+=500;
//        if(savedArr.contains(post2.getId())) savedAddition2+=500;

//        Total is weighted cos tfIdf yields like 5 times smaller numbers
//        TODO: I didn't have total2's tag match *5 before, does it change how the algorithm behaves?
        double total1 = tagMatch1+(tfIdf1*5)+savedAddition1;
        double total2 = tagMatch2+(tfIdf2*5)+savedAddition2;
//        Log.d("thing","post1 score "+savedAddition1+", post2 score "+savedAddition2);
//        Log.d("thing","second saved id is "+savedArr.toArray());

        if(total1 > total2) return -1;
        else if(total1 < total2) return 1;
        else {
            //                  TIE-BREAKER?
            return 0;
        }
    }

    public double calculateTfIdf(String corpusStr, String documentStr, String termsStr) {
        ArrayList<String> corpus = new ArrayList<>(Arrays.asList(corpusStr.split(" ")));
        ArrayList<String> document = new ArrayList<>(Arrays.asList(documentStr.split(" ")));
        ArrayList<String> terms = new ArrayList<>(Arrays.asList(termsStr.split(" ")));
        double total = 0.0;
        for(int n = 0; n < terms.size(); n++) {
            // Finds the number of times the term is in THIS post
            double tf = 0;
            for(int i = 0; i < document.size(); i++) {
                if(document.get(i).equalsIgnoreCase(terms.get(n))) tf++;
            }
            tf /= document.size();

            // Finds the number of times the term is in the whole document corpus
            double idf = 0.0;
            for(int i = 0; i < corpus.size(); i++) {
                if(corpus.get(i).equalsIgnoreCase(terms.get(n))) idf++;
            }

            idf = Math.log(corpus.size()/idf);
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
            if(second.contains(first.get(i))) score++;
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
