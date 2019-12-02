package com.fyp.agrifarm;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fyp.agrifarm.beans.User;
import com.fyp.agrifarm.utils.FirebaseUtils;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.fyp.agrifarm.utils.PicassoUtils;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserRegistrationActivity extends AppCompatActivity {

    public static final String TAG = "mirTag";
    public static final int RC_SIGN_IN = 10123;

    Uri userProfileUri = null;

    @BindView(R.id.imageView)
    ImageView imgViewProfile;
    @BindView(R.id.uFullName)
    TextInputEditText uFullName;
    @BindView(R.id.uOccupation)
    TextInputEditText uOccupation;
    @BindView(R.id.uAge)
    TextInputEditText uAge;
    @BindView(R.id.uLocation)
    TextInputEditText uLocation;
    @BindView(R.id.progress_overlay)
    FrameLayout progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // if no user has logged in
        if (user == null) {
            launchLoginActivity();
        }
        else {
            launchMainActivity();
            finish();
        }
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(
//                    "com.fyp.agrifarm",
//                    PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Toast.makeText(this, "SHA " + Base64.encodeToString(md.digest(), Base64.DEFAULT), Toast.LENGTH_SHORT).show();
//                Log.i("mKeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }

        setContentView(R.layout.activity_registration_container);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.imageView)
    public void changeProfileImage() {
        CropImage.ActivityBuilder cropImage;
        if (userProfileUri == null) {
            cropImage = CropImage.activity();
        } else {
            // If picture already selected
            cropImage = CropImage.activity(userProfileUri);
        }

        cropImage.setGuidelines(CropImageView.Guidelines.ON)
                .setFixAspectRatio(true)
                .setCropShape(CropImageView.CropShape.OVAL)
                .setScaleType(CropImageView.ScaleType.CENTER_CROP)
                .setMinCropResultSize(300, 300)
                .setRequestedSize(400, 400, CropImageView.RequestSizeOptions.RESIZE_INSIDE)
                .setActivityTitle("Profile Photo")
//                .setMinCropResultSize(300,700)
                .start(this);
    }

    @OnClick(R.id.btnSignOut)
    public void signOut() {
        AuthUI.getInstance().signOut(this).addOnCompleteListener(
                task -> {
                    Toast.makeText(UserRegistrationActivity.this, "Signed Out", Toast.LENGTH_SHORT).show();
                    launchLoginActivity();
                }
        );
    }

    @OnClick(R.id.btnGetStarted)
    public void getUserDetails() {
        String userFullName = uFullName.getText().toString();
        String userOccupation = uOccupation.getText().toString();
        String userAge = uAge.getText().toString();
        String userLocation = uLocation.getText().toString();

        if (userFullName.isEmpty() || userOccupation.isEmpty() || userAge.isEmpty() || userLocation.isEmpty()) {
            View v = findViewById(R.id.container);
            Snackbar.make(v, "Please fill all the fields...", Snackbar.LENGTH_LONG).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        String photoUri = userProfileUri == null ? null : userProfileUri.toString();
        User user = new User(userLocation, userAge, userOccupation, userFullName, photoUri);
        FirebaseUtils.uploadUserData(this, user, imgViewProfile, () -> {
            progressBar.setVisibility(View.GONE);
            launchMainActivity();
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: called");
        switch (requestCode) {
            case RC_SIGN_IN:
                // TODO: @Ehtisham TASK:1 you have to insert code here for time-out
                IdpResponse response = IdpResponse.fromResultIntent(data);
                if (resultCode == RESULT_OK) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    Log.i(TAG, "onActivityResult: New User status " + response.isNewUser());
                    if (!response.isNewUser()) {
                        // No need for registration
                        launchMainActivity();
                        finish();
                    }
                    if(user.getDisplayName() != null) {
                        uFullName.setText(user.getDisplayName());
                    }
                    // Downloads and Sets up the profile picture in the ImageView
                    FirebaseUtils.downloadUserProfileImage(this, user, imgViewProfile, getResources());
                } else {
                    View v = findViewById(R.id.container);
                    Snackbar.make(v, "Sign in failed!", Snackbar.LENGTH_LONG).show();
                }

                break;
            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    Uri resultUri = result.getUri();
                    if (resultUri != null) {
                        userProfileUri = resultUri;
                        PicassoUtils.loadCropAndSetImage(userProfileUri.toString(), imgViewProfile, getResources());
                    }
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                    Toast.makeText(this, "Cropping Error: " + error.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                Log.d(TAG, "onActivityResult: Something is wrong in onActivity Result");
        }
    }

    private void launchLoginActivity() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build()
        );
        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false)
                        .setAvailableProviders(providers)
                        .setLogo(R.drawable.ic_logo)
                        .setTheme(R.style.LoginTheme)
                        .build(),
                RC_SIGN_IN);
    }

    private void launchMainActivity() {
        Log.d(TAG, "launchMainActivity: Main");
        startActivity(new Intent(this, MainActivity.class));
    }
}
