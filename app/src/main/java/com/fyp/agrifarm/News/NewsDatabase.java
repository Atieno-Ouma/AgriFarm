package com.fyp.agrifarm.News;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {NewsEntity.class}, version = 4,exportSchema = false)
public abstract class NewsDatabase extends RoomDatabase {

    private static NewsDatabase instance;

    public abstract NewsDoa newsDoa();

    public static synchronized NewsDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NewsDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }


//    public void downloadAndSetImages(ArrayList<Uri> uris, ImageDownloadCallback callback){
//        new DownloadImagesTask(callback).execute(uris);
//    }
//
//    private static class DownloadImagesTask extends AsyncTask<ArrayList<Uri>, Void, ArrayList<Bitmap>>
//    {
//        ImageDownloadCallback callback;
//        private DownloadImagesTask(ImageDownloadCallback callback){
//            this.callback = callback;
//        }
//        @Override
//        protected ArrayList<Bitmap> doInBackground(ArrayList<Uri>... uris) {
//            ArrayList<Bitmap> bitmapList = new ArrayList<>();
//            for(Uri u: uris[0]){
//                Picasso.get().load(u).into(new Target() {
//                    @Override
//                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                        bitmapList.add(bitmap);
//                    }
//
//                    @Override
//                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
//
//                    }
//
//
//                    @Override
//                    public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//                    }
//                });
//            }
//            return bitmapList;
//        }
//
//        @Override
//        protected void onPostExecute(ArrayList<Bitmap> bitmaps) {
//            super.onPostExecute(bitmaps);
//
//            if(callback != null)
//                callback.onTaskPerformed(bitmaps);
//        }
//    }
}