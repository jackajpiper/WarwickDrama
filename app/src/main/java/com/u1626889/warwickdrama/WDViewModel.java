package com.u1626889.warwickdrama;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class WDViewModel extends AndroidViewModel {

    private WDRepository mRepository;

    private LiveData<List<Post>> mAllPosts;

    public WDViewModel(Application application) {
        super(application);
        mRepository = new WDRepository(application);
        mAllPosts = mRepository.getmAllPosts();
    }

    LiveData<List<Post>> getmAllPosts() {return mAllPosts;}

    public void insert(Post post) {mRepository.insert(post);}

}
