package com.u1626889.warwickdrama;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

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
            // TODO - see if there's another way to generate ids for the posts, this way takes up view ids
            Post post = new Post(View.generateViewId(),"new post", "Jack", "Misc", "Codpeice", "It's a new post", "new,post,attempt,try","");
            mDao.insert(post);

            post = new Post(View.generateViewId(),"second post", "Ben" , "Show", "Freshblood", "another post here, just checking in", "second,another,stop,try,post", "");
            mDao.insert(post);

            post = new Post(View.generateViewId(),"WITS Presents: A Play auditions", "Jack Piper" , "Audition", "WITS", "Come audition for Warwick Improv's edinburgh show, \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g  \\n g tentatively entitled 'A Play'! The audition will consist of a series of short form games.", "improv,comedy,spontaneity,edinburgh,audition,drama", "");
            mDao.insert(post);

            post = new Post(View.generateViewId(),"Rent fundraiser", "Ben Chapman" , "Social", "MTW", "Rent is having a fundraiser, come support this terrible show. Try not to think about how Angel kills dogs.", "musical,singing,dogs,music,perform", "");
            mDao.insert(post);

            post = new Post(View.generateViewId(),"A View From The Bridge", "Amy Hodkin" , "Show", "WUDS", "A View From The Bridge is a crackin ply with accents and a chair. Come see.", "american,accent,show,wuds,miller,arthur", "");
            mDao.insert(post);

            post = new Post(View.generateViewId(),"Producer needed! Behind the Eyes", "Matt Groaning" , "Production Opportunity", "Codpeice", "behind the eyes needs an assistant producer, and that assistant producer that behind the eyes needs, it could be you.", "producer,role,opportunity,real", "");
            mDao.insert(post);

            post = new Post(View.generateViewId(),"WUDS and improv and you", "Jack Starkey" , "Workshop", "WUDS", "WUDS are running a worskhop on improv, and they've used WITS as a way of injecting a bit of that.", "practice,improv,comedy,beginner", "");
            mDao.insert(post);

            post = new Post(View.generateViewId(),"Freshblood Views The Bridge", "Amy Cairns" , "Social", "Freshblood", "Your lovely freshblood social secs are gonna be at the duck at 6pm practicing our accents before jetting off to see WUDS' latest production - A View From The Bridge!", "social,drink,accent,miller,arthur,love", "");
            mDao.insert(post);

            post = new Post(View.generateViewId(),"Death Of An Anarchist auditions", "Charlie Cooper" , "Audition", "WUDS", "Be an actor! Not an acorn. Act as the lead. Or as a support. Yay.", "audition,acting,perform,ensemble,farce", "");
            mDao.insert(post);

            post = new Post(View.generateViewId(),"Be on the DC panel", "Marianne Steggall" , "Announcement", "Other", "Would YOU like to be the sole arbiter of people's hopes and dreams? Well, this is the next best thing I promise.", "judge,dc,panel,announcement", "");
            mDao.insert(post);
            return null;
        }
    }


}
