package com.g.pb.chatify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

public class receiver_profile extends AppCompatActivity {

    RoundedImageView reciever_profilepic;
    TextView reciever_name,rbio,rfollowers,rfollowing;

    Button follow,message;


    DatabaseReference databaseReference;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_profile);


        auth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference("Users");

        reciever_profilepic=findViewById(R.id.reciever_profilepic);
        Toolbar toolbar1=findViewById(R.id.reciever_profile_toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar1.getNavigationIcon().setTint(getResources().getColor(R.color.yellow));
        }

        reciever_name=findViewById(R.id.reciever_name);
        rbio=findViewById(R.id.reciever_bio);
        rfollowers=findViewById(R.id.followers);
        rfollowing=findViewById(R.id.following);
        message=findViewById(R.id.message);
        follow=findViewById(R.id.follow);


        toolbar1.setTitle(getIntent().getStringExtra("name"));
        reciever_name.setText(getIntent().getStringExtra("name"));
        rbio.setText(getIntent().getStringExtra("bio"));

        Picasso.with(this).load(getIntent().getStringExtra("url")).placeholder(R.mipmap.profile).into(reciever_profilepic);


        toolbar1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });


        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (follow.getTag().equals("follow")) {
                    databaseReference.child(auth.getCurrentUser().getPhoneNumber()).child("following").child(getIntent().getStringExtra("number")).setValue(true);
                    databaseReference.child(getIntent().getStringExtra("number")).child("followers").child(auth.getCurrentUser().getPhoneNumber()).setValue(true);

                } else {
                    databaseReference.child(auth.getCurrentUser().getPhoneNumber()).child("following").child(getIntent().getStringExtra("number")).removeValue();
                    databaseReference.child(getIntent().getStringExtra("number")).child("followers").child(auth.getCurrentUser().getPhoneNumber()).removeValue();

                }
            }
        });


        islikes();
        nrlikes();






        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(receiver_profile.this,chatroom.class);
                i.putExtra("url",getIntent().getStringExtra("url"));
                i.putExtra("name", getIntent().getStringExtra("name"));
                i.putExtra("number", getIntent().getStringExtra("number"));

                startActivity(i);

            }
        });


    }


    private void islikes() {

        databaseReference.child(auth.getCurrentUser().getPhoneNumber()).child("following").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(getIntent().getStringExtra("number"))) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        follow.getBackground().setTint(getResources().getColor(R.color.yellow));
                        follow.setText("Unfollow");
                        follow.setTextColor(getResources().getColor(R.color.main_hard));
                    }
                    follow.setTag("unfollow");
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        follow.getBackground().setTint(getResources().getColor(R.color.main_hard));
                        follow.setTextColor(getResources().getColor(R.color.yellow));
                        follow.setText("Follow");
                    }
                    follow.setTag("follow");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void nrlikes() {

        databaseReference.child(getIntent().getStringExtra("number")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("followers")){
                    rfollowers.setText(dataSnapshot.child("followers").getChildrenCount()+"");
                }else{
                    rfollowers.setText("0");
                }
                if(dataSnapshot.hasChild("following")) {
                    rfollowing.setText(dataSnapshot.child("following").getChildrenCount() + "");
                }else{
                    rfollowing.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
