package com.example.pattimura.sundawenang;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class SplashScreen extends AppCompatActivity {
    private static boolean splashLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ImageView logo = (ImageView) findViewById(R.id.imageView);
        Picasso.with(this).load(R.drawable.logoidev).fit().centerCrop().into(logo);
        if (!splashLoaded) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity
                    Intent i = new Intent(SplashScreen.this, LandingPage.class);
                    startActivity(i);

                    // close this activity
                    finish();
                }
            }, 3000);
            splashLoaded = true;
        } else {
            Intent goToMainActivity = new Intent(SplashScreen.this, LandingPage.class);
            goToMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(goToMainActivity);
            finish();
        }

    }
}
