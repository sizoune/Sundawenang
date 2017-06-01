package com.example.pattimura.sundawenang;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SplashScreen extends AppCompatActivity {
    private static boolean splashLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ImageView logo = (ImageView) findViewById(R.id.imageView);
        ImageView spl = (ImageView) findViewById(R.id.splashscreenimage);
        Picasso.with(this).load(R.drawable.logoidev).fit().centerCrop().into(logo);
        Picasso.with(this).load(R.drawable.splashbg).fit().into(spl);
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
