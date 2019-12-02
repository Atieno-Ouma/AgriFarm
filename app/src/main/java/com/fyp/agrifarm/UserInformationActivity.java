package com.fyp.agrifarm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.fyp.agrifarm.utils.PicassoUtils;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.net.URI;

public class UserInformationActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    UserIntrestsViewPagerAdapter userIntrestsViewPagerAdapter;
    ImageView userimage;
    TextView textView;
    Button userfollowbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        tabLayout = findViewById(R.id.usertabs);
        viewPager = findViewById(R.id.vpuseritrest);
        userIntrestsViewPagerAdapter = new UserIntrestsViewPagerAdapter (getSupportFragmentManager());
        viewPager.setAdapter(userIntrestsViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        userimage = findViewById(R.id.userimage);
        textView = findViewById(R.id.username);
        userfollowbutton = findViewById(R.id.userfollowbutton);

        //Users Image
        Intent intent = getIntent();
        String image = intent.getStringExtra("userphoto");
        textView.setText(intent.getStringExtra("username"));
        Picasso.get().load(image).into(userimage, new Callback() {
            @Override
            public void onSuccess() {
                PicassoUtils.changeToCircularImage(userimage,getResources());
            }

            @Override
            public void onError(Exception e) {

            }
        });

        //Follow Button

        userfollowbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
