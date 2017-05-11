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
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    SharedPreferences.Editor editor;
    private static boolean splashLoaded = false;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        ImageView logo = (ImageView) findViewById(R.id.imageView);
        ImageView spl = (ImageView) findViewById(R.id.splashscreenimage);
        Picasso.with(this).load(R.drawable.logoidev).fit().centerCrop().into(logo);
        Picasso.with(this).load(R.drawable.splashbg).fit().into(spl);
        getToken();
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

    void getToken() {
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://94.177.203.179/api/auth/login",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject a = new JSONObject(response);
                            token = a.getString("token");
                            editor.putString("token", token);
                            editor.commit();
                            //Toast.makeText(getApplicationContext(), token, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            //Toast.makeText(Produk.this.getContext(), "error : " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
//tempat response di dapatkan
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                        error.printStackTrace();
                        //Toast.makeText(Produk.this.getContext(), "erroring: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                try {
                    params.put("email", "sdwn.parungkuda@gmail.com");
                    params.put("password", "sdwn123");
                    return params;
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    return params;
                }
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
