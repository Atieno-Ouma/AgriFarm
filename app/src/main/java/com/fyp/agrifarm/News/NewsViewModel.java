package com.fyp.agrifarm.News;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NewsViewModel extends AndroidViewModel {
    private NewsRepository repository;
    private LiveData<List<NewsEntity>> allNotes;

    public NewsViewModel(@NonNull Application application) {
        super(application);
        repository = new NewsRepository(application);
        allNotes = repository.getAllNotes();
    }

    public  void insert(NewsEntity note) {
        repository.insert(note);
    }

    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }

    public LiveData<List<NewsEntity>> getAllNotes() {
        return allNotes;
    }
}