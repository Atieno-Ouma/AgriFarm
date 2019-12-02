package com.fyp.agrifarm;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.fyp.agrifarm.News.DownloadNews;
import com.fyp.agrifarm.News.NewsEntity;
import com.fyp.agrifarm.News.NewsViewModel;
import com.fyp.agrifarm.utils.FirebaseUtils;
import com.fyp.agrifarm.utils.PicassoUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        HomeFragment.OnFragmentInteractionListener,
        WeatherFragment.OnFragmentInteractionListener,
        VideoRecyclerAdapter.OnItemClickListener {

    public static final String TAG = "MainActivity";
    private static HomeFragment homeFragment = null;
    private static WeatherFragment weatherFragment = null;
    private NewsViewModel newsViewModel;
    private static MainActivity mainActivityobj;
    private static Context appContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivityobj=this;
        appContext = getApplicationContext();
        if (new NewsViewModel((Application) MainActivity.getAppContext()).getAllNotes()!=null){
            new NewsViewModel((Application)MainActivity.getAppContext()).deleteAllNotes();
        }
        new DownloadNews().execute();




//        FrameLayout progressLayout = findViewById(R.id.progress_overlay);
//        progressLayout.setVisibility(View.VISIBLE);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentByTag(HomeFragment.TAG);
        if (fragment == null) {
            if (homeFragment == null)
                homeFragment = HomeFragment.newInstance("a", "b");
            fragment = homeFragment;
            fm.beginTransaction()
                    .disallowAddToBackStack()
                    .replace(R.id.fragmentHolder, fragment, HomeFragment.TAG)
                    .commit();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        if (headerView == null) {
            headerView = navigationView.inflateHeaderView(R.layout.drawer_header);
        }
        TextView tvUserFullName = headerView.findViewById(R.id.tvDrawerName);
        TextView tvUserOccupation = headerView.findViewById(R.id.tvDrawerOccupation);
        ImageView uProfilePhoto = headerView.findViewById(R.id.ivDrawerProfile);

        // Turned off for debugging purpose
//        FirebaseUtils.fetchCurrentUserFromFirebase(user -> {
//            tvUserFullName.setText(user.getFullname());
//            tvUserOccupation.setText(user.getOccupation());
////            uAge.setText(userAge);
////            uLocation.setText(userLocation);
//            // TODO: The image is returned with a bit margin in left, TO FIX, ScaleType: CenterCrop clips the image instead
//            PicassoUtils.loadCropAndSetImage(user.getPhotoUri(), uProfilePhoto, getResources());
//            progressLayout.setVisibility(View.GONE);
//        });
    }
    public static MainActivity getactivity(){
        return mainActivityobj;
    }
    public static Context getAppContext() { return appContext; }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            // Change orientation back to Portrait, when back from fullscreen video
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            FirebaseUtils.deleteAccount( o -> {
                        Log.i(TAG, "onOptionsItemSelected: Deleted");
                        Toast.makeText(this, "Account Deleted!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, UserRegistrationActivity.class));
                        finish();
                    },
                    e -> Toast.makeText(this, "Account could not be deleted!", Toast.LENGTH_SHORT).show()
            );

            return true;
        } else if (id == R.id.action_sign_out) {
            FirebaseUtils.signOut(this, () -> {
                startActivity(new Intent(MainActivity.this, UserRegistrationActivity.class));
                finish();
            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onForecastClick(View v) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentByTag(WeatherFragment.TAG);
        if (fragment == null) {
            if (weatherFragment == null)
                weatherFragment = WeatherFragment.newInstance("a", "b");
            fragment = weatherFragment;
        }

        fm.beginTransaction()
                .replace(R.id.fragmentHolder, fragment, WeatherFragment.TAG)
                .addToBackStack(HomeFragment.TAG)
                .commit();
    }

    @Override
    public void onVideoClicked(View v, final String videoUrl) {
        // TODO: Refactor YOUTUBE FRAGMENT: AndroidX
//        YoutubeFragment youtubeFragment = YoutubeFragment.newInstance(videoUrl, "b");
//
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.fragmentHolder, youtubeFragment, YoutubeFragment.TAG)
//                .addToBackStack(HomeFragment.TAG)
//                .commit();
    }

    @Override
    public void onItemClick(View v) {

    }
}
