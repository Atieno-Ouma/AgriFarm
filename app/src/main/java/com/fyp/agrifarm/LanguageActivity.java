package com.fyp.agrifarm;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class LanguageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);


        Button btnEnglish = findViewById(R.id.btnLangEng);
        btnEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForLocale(new Locale("en", "US"));
            }
        });
        Button btnUrdu = findViewById(R.id.btnLangUrdu);
        btnUrdu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForLocale(new Locale("ur", "PK"));
            }
        });
    }

    private void startActivityForLocale(Locale locale){
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        startActivity(new Intent(LanguageActivity.this,MainActivity.class));
    }

}
