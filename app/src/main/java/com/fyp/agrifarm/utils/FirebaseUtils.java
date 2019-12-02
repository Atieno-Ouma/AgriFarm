package com.fyp.agrifarm.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.fyp.agrifarm.R;
import com.fyp.agrifarm.UserDataFetchListener;
import com.fyp.agrifarm.UserDataUploadListener;
import com.fyp.agrifarm.UserRegistrationActivity;
import com.fyp.agrifarm.beans.User;
import com.fyp.agrifarm.UserSignOutListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoProvider;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FirebaseUtils {

    private File image;

    private static final CollectionReference firestoreUsers = FirebaseFirestore.getInstance().collection("users");
    private static final StorageReference storageReference = FirebaseStorage.getInstance().getReference("userImages");
    static final String TAG = "FirebaseUtils";

    private static final FirebaseUtils ourInstance = new FirebaseUtils();

    private static final String USER_LOCATION = "location";
    private static final String USER_AGE = "age";
    private static final String USER_OCCUPATION = "occupation";
    private static final String USER_FULLNAME = "fullname";
    private static final String USER_PHOTO_URI = "photoUri";

    public static void signOut(Context context, @NonNull UserSignOutListener userSignOutListener){
        FirebaseAuth.getInstance().signOut();
//        AuthUI.getInstance().signOut(context).addOnCompleteListener(
//                task -> {
                    Toast.makeText(context, "Signed Out", Toast.LENGTH_SHORT).show();
                    userSignOutListener.onUserSignedOut();
//                }
//        );
    }

    public static void downloadUserProfileImage(Context ctx, FirebaseUser user, ImageView targetImageView, Resources resources){
        try {
            Uri url = user.getPhotoUrl();
            String finalUrl = "" + url;
            UserInfo u = null;
            for(UserInfo userInfo: user.getProviderData()){
                u = userInfo; // Loop to end item
            }

            switch (Objects.requireNonNull(u).getProviderId()){
                case "facebook.com":
                    // Facebook profile picture comes in square size only prefix height at the end of url
                    finalUrl += "?height=200";
                    break;
                //  https://lh3.googleusercontent.com/-HP0cCZ3OMK0/AAAAAAAAAAI/AAAAAAAAFVQ/ghaGRQkw_Lg/s96-c/photo.jpg
                case "google.com":
                    // s96 means small size with 96 the resolution of the picture
                    // we need l96: large size with 96 resolution
                    finalUrl = finalUrl.replace("s96-c", "l96-c");
                    break;
            }
            // TODO: Cache the image for next time usage
            loadUserProfileImage(ctx, finalUrl, targetImageView, resources);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    private static void loadUserProfileImage(Context ctx, String url, ImageView targetImageView, Resources resources){

        PicassoUtils.saveBitmapToFile(ctx, url, "img", savedFile -> {
            Log.i(TAG, "loadUserProfileImage: Loading now...");
            PicassoUtils.loadCropAndSetImage(savedFile, targetImageView, resources);
            ourInstance.image = savedFile;
        });
//        Picasso.get().load(url).into(targetImageView, new Callback() {
//            @Override
//            public void onSuccess() {
//                Bitmap bitmap = ((BitmapDrawable) targetImageView.getDrawable()).getBitmap();
//                RoundedBitmapDrawable roundedBitmap = RoundedBitmapDrawableFactory
//                        .create(resources, bitmap);
//                roundedBitmap.setCircular(true);
//                roundedBitmap.setCornerRadius(bitmap.getWidth() / 2.0f);
//                targetImageView.setImageDrawable(roundedBitmap);
//            }
//
//            @Override
//            public void onError(Exception e) {
//                targetImageView.setImageResource(R.drawable.profile_default_user);
//            }
//        });
    }

    public static void fetchCurrentUserFromFirebase(@NonNull UserDataFetchListener userDataFetchListener){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser == null){
            try {
                throw new Exception("Firebase user is null", new Throwable("FetchCurrentUserFromFirebase"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        String userUid =firebaseUser.getUid();
        firestoreUsers.document(userUid).get().addOnSuccessListener(documentSnapshot -> {
            if(documentSnapshot.exists()){
                User user = documentSnapshot.toObject(User.class);
                userDataFetchListener.onUserDataFetched(user);
            }else {
                Log.d(TAG, "Data could not be fetched!");
            }
        }).addOnFailureListener(e -> {
            Log.d(TAG, "Data fetching, failed!");
        });

    }

    public static void uploadUserData(Activity activity, User user, ImageView targetImageView, UserDataUploadListener userDataUploadListener){
        Target mTarget = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                compressAndUpload(bitmap, activity, user, userDataUploadListener);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                Log.d(TAG, "onBitmapFailed: Image Compression Failed");
                Toast.makeText(activity.getApplicationContext(),
                        "onBitmapFailed: Image Compression Failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        // TODO: [Efficiency Toolkit] :: How can the this method be refactored to avoid defining compressAndUpload method below
        if(user.getPhotoUri() == null){
            // User picture is picked from Social Network & not local file
//            try {
//                // TODO: TIME CONSTRAINT :: Remove Class cast error, I'm moving on due to Time Constraint
//                Bitmap bitmap = ((BitmapDrawable) targetImageView.getDrawable()).getBitmap();
//                compressAndUpload(bitmap, activity, user, userDataUploadListener);
//            } catch (NullPointerException e) {
//                Log.e(TAG, "uploadUserData: Bitmap was Null, no image selected");
//                e.printStackTrace();
//            }
            Picasso.get().load(ourInstance.image)
                    .resize(300,300)
                    .centerCrop().into(mTarget);
        }else{
            // Good practice: Don't use Anonymous Interface for Target, doing so will make it a victim of garbage collection
            Picasso.get().load(user.getPhotoUri())
                    .resize(300,300)
                    .centerCrop().into(mTarget);
        }
        Log.i(TAG, "uploadUserData: After wards " + user.getPhotoUri());
    }

    private static void compressAndUpload(Bitmap bitmap, Activity activity, User user, UserDataUploadListener userDataUploadListener){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        byte[] thumbnailData = byteArrayOutputStream.toByteArray();

        String userUid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        UploadTask uploadTask = storageReference.child(userUid + ".jpg").putBytes(thumbnailData);
        StorageReference reference = storageReference.child(userUid + ".jpg");

        uploadTask.continueWithTask(task -> {
            Log.i(TAG, "onBitmapLoaded: continuing task");
            if(!task.isSuccessful())
                throw Objects.requireNonNull(task.getException());

            return reference.getDownloadUrl();
        }).addOnCompleteListener(activity, task -> {
            if(task.isSuccessful()){
                Log.i(TAG, "onBitmapLoaded:  Download complete");
                FirebaseUtils.storeUserDetails(activity.getApplicationContext(), task, user, userUid,
                        userDataUploadListener);
            }else {
                Toast.makeText(activity.getApplicationContext(),
                        "URL Fetch Failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static void storeUserDetails(Context context, Task<Uri> task, User user, String userUid, UserDataUploadListener userDataUploadListener){
        Uri downloadUri = task.getResult();
//        if(task != null)
//            downloadUri = task.getResult();
//        else
//            downloadUri = userProfileUri;
        Log.i(TAG, "storeUserDetails: online" );
        Map<String, Object> userData = new HashMap<>();
        userData.put(USER_FULLNAME, user.getFullname());
        userData.put(USER_OCCUPATION, user.getOccupation());
        userData.put(USER_AGE, user.getAge());
        userData.put(USER_LOCATION, user.getLocation());
        userData.put(USER_PHOTO_URI, Objects.requireNonNull(downloadUri).toString());

        firestoreUsers.document(userUid).set(userData)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(context,"User Registered!", Toast.LENGTH_SHORT).show();
                    userDataUploadListener.onUserDataUploaded();
                })
                .addOnFailureListener(e -> Toast.makeText(context,
                        "Registration failed!", Toast.LENGTH_SHORT).show());
    }

    public static void deleteAccount(OnSuccessListener successListener, OnFailureListener failureListener){
        Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).delete()
                .addOnSuccessListener(successListener)
                .addOnFailureListener(failureListener);
    }

    // Used it at the start of the project to get SHA for Firebase credentials
    // Used it in LauncherActivity (UserRegistrationActivity)

    // HASH KEY: q5n5Cpqua3ahjtxSk8FT+SU1mKQ=

//    public static void printSHA(){
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(
//                    "com.cloud.firebaselogin",
//                    PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("mKeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//    }


}
