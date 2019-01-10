package com.u1626889.warwickdrama;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

public class WDRepository {

    private  PostDao mPostDao;
    private LiveData<List<Post>> mAllPosts;

    WDRepository(Application application) {
        MyRoomDatabase db = MyRoomDatabase.getDatabase(application);
        mPostDao = db.postDao();
        mAllPosts = mPostDao.getAllPosts();
    }

    LiveData<List<Post>> getmAllPosts() {
        return mAllPosts;
    }

    public void insert (Post post) {
        new insertAsyncTask(mPostDao).execute(post);
    }

    private static class insertAsyncTask extends AsyncTask<Post, Void, Void> {

        private PostDao mAsyncTaskDao;

        insertAsyncTask(PostDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Post... params) {
            mAsyncTaskDao.insert(params[0]);
            Log.d("thing", "In the repository, item is added");
            return null;
        }

    }

}
