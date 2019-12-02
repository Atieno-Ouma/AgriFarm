package com.fyp.agrifarm.News;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NewsDoa {

    @Insert
    void insert(NewsEntity note);

    @Query("DELETE FROM note_table")
    void deleteAllNotes();

    @Query("SELECT * FROM note_table ")
    LiveData<List<NewsEntity>> getAllNotes();
}
