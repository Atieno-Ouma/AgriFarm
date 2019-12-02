package com.fyp.agrifarm.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.fyp.agrifarm.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.annotation.Nullable;

public class PicassoUtils {

    private class SaveTask extends AsyncTask<Void, Void, Void> {

        File imageFile;
        Bitmap bitmap;
        PhotoDownloadAndSaveListener listener;

        public SaveTask(File imageFile, Bitmap bitmap, PhotoDownloadAndSaveListener listener){
            this.imageFile = imageFile;
            this.bitmap = bitmap;
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try (FileOutputStream fos = new FileOutputStream(imageFile)) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.i(TAG, "Image saved to >>> " + imageFile.getAbsolutePath());
            listener.onDownloadAndSaveComplete(imageFile);
        }
    }

    public static final String TAG = "PicassoUtils";

    public static void saveBitmapToFile(Context context, String url, String imageName, PhotoDownloadAndSaveListener listener) {
        ContextWrapper contextWrapper = new ContextWrapper(context);

        File directory = contextWrapper.getDir("cached_images", Context.MODE_PRIVATE);
        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                File imageFile = new File(directory, imageName);
                new PicassoUtils().new SaveTask(imageFile, bitmap, listener).execute();
//                new Thread(() -> {
//                    File imageFile = new File(directory, imageName);
//                    try (FileOutputStream fos = new FileOutputStream(imageFile)) {
//                        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    listener.onDownloadAndSaveComplete(imageFile);
//                    Log.i(TAG, "Image saved to >>> " + imageFile.getAbsolutePath());
//                }).start();
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                Log.e(TAG, "onBitmapFailed: " + e.getMessage());
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        Picasso.get().load(url).into(target);
    }

    public static void loadCropAndSetImage(File file, ImageView targetImageView, Resources resources) {
        Picasso.get().load(file).into(targetImageView, new Callback() {
            @Override
            public void onSuccess() {
                changeToCircularImage(targetImageView, resources);
            }

            @Override
            public void onError(Exception e) {
                targetImageView.setImageResource(R.drawable.profile_default_user);
            }
        });
    }

    public static void loadCropAndSetImage(String uri, ImageView targetImageView, Resources resources) {
        Picasso.get().load(uri).into(targetImageView, new Callback() {
            @Override
            public void onSuccess() {
                changeToCircularImage(targetImageView, resources);
            }

            @Override
            public void onError(Exception e) {
                targetImageView.setImageResource(R.drawable.profile_default_user);
            }
        });
    }

    public static void changeToCircularImage(ImageView targetImageView, Resources resources){
        Bitmap bitmap = ((BitmapDrawable) targetImageView.getDrawable()).getBitmap();
        RoundedBitmapDrawable roundedBitmap = RoundedBitmapDrawableFactory
                .create(resources, bitmap);
        roundedBitmap.setCircular(true);
        roundedBitmap.setCornerRadius(bitmap.getWidth());
        targetImageView.setImageDrawable(roundedBitmap);
    }

}
