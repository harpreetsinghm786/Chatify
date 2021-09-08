package com.g.pb.chatify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;

public class splash extends AppCompatActivity {

    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        auth=FirebaseAuth.getInstance();
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(auth.getCurrentUser()!=null){
                    Intent i=new Intent(splash.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }else {
                    Intent i=new Intent(splash.this,login.class);
                    startActivity(i);
                    finish();}
            }
        },3000);
    }
}
