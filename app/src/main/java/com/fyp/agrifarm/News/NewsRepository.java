package com.fyp.agrifarm.News;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NewsRepository {
    private NewsDoa newsDao;
    private LiveData<List<NewsEntity>> allNotes;
    NewsDatabase database;

    public NewsRepository(Application application) {
        database = NewsDatabase.getInstance(application);
        newsDao = database.newsDoa();
        allNotes = newsDao.getAllNotes();
    }

    public void insert(NewsEntity note) {
        new InsertNoteAsyncTask(newsDao).execute(note);
    }

    public void deleteAllNotes() {
        new DeleteAllNotesAsyncTask(newsDao).execute();
    }

    public LiveData<List<NewsEntity>> getAllNotes() {
        return allNotes;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<NewsEntity, Void, Void> {
        private NewsDoa newsDao;

        private InsertNoteAsyncTask(NewsDoa newsDao) {
            this.newsDao = newsDao;
        }

        @Override
        protected Void doInBackground(NewsEntity... notes) {
            newsDao.insert(notes[0]);
            return null;
        }
    }


    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private NewsDoa newsDao;

        private DeleteAllNotesAsyncTask(NewsDoa newsDao) {
            this.newsDao = newsDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            newsDao.deleteAllNotes();
            return null;
        }
    }
}