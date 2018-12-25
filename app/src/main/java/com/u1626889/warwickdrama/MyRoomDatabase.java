package com.u1626889.warwickdrama;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

@Database(entities = {Post.class}, version = 1)
public abstract class MyRoomDatabase extends RoomDatabase {
    public abstract PostDao postDao();

    private static volatile MyRoomDatabase INSTANCE;
    static MyRoomDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (MyRoomDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MyRoomDatabase.class, "WD_database").addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){

        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }

    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final PostDao mDao;

        PopulateDbAsync(MyRoomDatabase db) {
            mDao = db.postDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            Post post = new Post(1,"new post", "Jack", "Misc", "Codpeice", "It's a new post", "this,that,the","");
            mDao.insert(post);
            post = new Post(2,"second post", "Ben" , "Show", "Freshblood", "another post here, just checking in", "thing,think,that", "");
            mDao.insert(post);
            Log.d("thing","okay, added two posts to the database");
            return null;
        }
    }


}
