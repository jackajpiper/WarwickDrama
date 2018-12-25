package com.u1626889.warwickdrama;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface PostDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Post post);

    @Query("DELETE FROM post_table")
    void deleteAll();

    @Query("SELECT * from post_table ORDER BY id ASC")
    LiveData<List<Post>> getAllPosts();
}
