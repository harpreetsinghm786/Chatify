package com.g.pb.chatify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    com.g.pb.chatify.chats chats;
    calls calls;
    profile_ profile_;
    search search;
    groups groups;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth=FirebaseAuth.getInstance();





        bottomNavigationView=findViewById(R.id.botton_nav_bar);
        chats=new chats();
        calls=new calls();
        profile_=new profile_();
        search=new search();
        groups=new groups();



        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.home);
            getSupportFragmentManager().beginTransaction().replace(R.id.mycontainer,new chats()).commit();
        }








            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.chats:
                            setFragment(chats);
                            return true;
                        case R.id.groups:
                            setFragment(groups);
                            return true;
//                        case R.id.calls:
//                            setFragment(calls);
//                            return true;
                        case R.id.profile:
                            setFragment(profile_);
                            return true;
                        case R.id.search:
                            setFragment(search);
                            return true;


                        default:
                            return false;
                    }
                }
            });


        }


    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.mycontainer,fragment);
        fragmentTransaction.commit();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.new_group:
                Intent i=new Intent(MainActivity.this,new_group.class);
                startActivity(i);
                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent j=new Intent(MainActivity.this,login.class);
                startActivity(j);
                finish();
                break;





        }

        return true;
    }




    }
