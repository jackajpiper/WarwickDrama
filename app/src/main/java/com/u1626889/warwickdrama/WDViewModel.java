package com.u1626889.warwickdrama;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.util.List;

public class WDViewModel extends AndroidViewModel {

    private WDRepository mRepository;

    private LiveData<List<Post>> mAllPosts;

    public WDViewModel(Application application) {
        super(application);
        Log.d("thing", "HEY         HEY     This is the constructor for WDViewModel");
        mRepository = new WDRepository(application);
        mAllPosts = mRepository.getmAllPosts();
    }

    LiveData<List<Post>> getAllPosts() {
        Log.d("thing", "HEY         HEY     This is the getall method for WDViewModel");
        return mAllPosts;
    }

    public void insert(Post post) {
        Log.d("thing", "HEY         HEY     This is the insert method for WDViewModel");
        mRepository.insert(post);
    }

}
