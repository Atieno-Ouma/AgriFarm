package com.fyp.agrifarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.net.URL;
import java.time.LocalDateTime;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsDetailsActivity extends AppCompatActivity {
    ImageView ivnews;
    TextView title,date,desc;
    @BindView(R.id.newsImageContainer)
    RelativeLayout imageContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        ButterKnife.bind(this);

        Intent intent=getIntent();

        title=findViewById(R.id.tvNewsTitleDetail);
        date=findViewById(R.id.tvNewsDate);
        desc=findViewById(R.id.tvNewsDesp);

        LocalDateTime dateTime = null;
        Picasso.get().load(intent.getExtras().get("image").toString())
//                .centerCrop()
//                .resize(imageContainer.getMeasuredWidth(), imageContainer.getMeasuredHeight())
                .into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Log.i("NONE", "onBitmapLoaded: " + imageContainer.getMeasuredWidthAndState());
                imageContainer.setBackground(new BitmapDrawable(getResources(), bitmap));
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
        Log.i("newsdetailtitle", intent.getExtras().get("title").toString());
        title.setText(intent.getExtras().get("title").toString());
        date.setText(intent.getExtras().get("date").toString());
        desc.setText("Agriculture is a vital sector of Pakistan's economy and accounted for 25.9 percent of GDP in 1999-2000, according to government estimates. The sector directly supports three-quarters of the country's population, employs half the labor force , and contributes a large share of foreign exchange earnings. The main agricultural products are cotton, wheat, rice, sugarcane, fruits, and vegetables, in addition to milk, beef, mutton, and eggs. Pakistan depends on one of the world's largest irrigation systems to support production. There are 2 principal seasons. Cotton, rice, and sugarcane are produced during the kharif season, which lasts from May to November. Wheat is the major rabi crop, which extends from November to April. The key to a much-needed improvement of productivity lies in a more efficient use of resources, principally land and water. However, change is dependent on the large landowners who own 40 percent of the arable land and control most of the irrigation system, which makes widespread reform difficult. Assessments by independent agencies, including the World Bank, show these large landholdings to be very unproductive. Pakistan is a net importer of agricultural commodities. Annual imports total about US$2 billion and include wheat, edible oils, pulses, and consumer foods.\n" +
                "\n" +
                "Pakistan is one of the world's largest producers of raw cotton. The size of the annual cotton crop—the bulk of it grown in Punjab province—is a crucial barometer of the health of the overall economy, as it determines the availability and cost of the main raw material for the yarn-spinning industry, much of which is concentrated around the southern port city of Karachi. Official estimates put the 1999-2000 harvest at some 11.2 million 170-kilogram bales, compared with the 1998-99 outturn of 8.8 million bales and the record 12.8 million bales achieved in 1991-92. The government recently actively intervened in the market to boost prices and to encourage production. A major problem is that the cotton crop is highly susceptible to adverse weather and pest damage, which is reflected in crop figures. After peaking at 2.18 million tons in 1991-92, the lint harvest has since fluctuated considerably, ranging from a low of 1.37 million tons in 1993-94 to a high of 1.9 million tons in 1999-2000.\n" +
                "\n" +
                "\n" +
                "\n" +
                "Read more: https://www.nationsencyclopedia.com/economies/Asia-and-the-Pacific/Pakistan-AGRICULTURE.html#ixzz61PUrOelT");
    }
}
