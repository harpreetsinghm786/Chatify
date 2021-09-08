package com.g.pb.chatify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

public class chatroom extends AppCompatActivity {
    Toolbar toolbar;
    RoundedImageView imageView;
    TextView name;
    EmojiconEditText emojiconEditText;
    ImageView emojiButton;
    View rootView;
    String messasagekey;
    EmojIconActions emojIcon;
    TextView online;
    TextView typing;
    chatroom_adapter adapter;
    ImageButton send;
    RecyclerView recyclerView;
    String myurl;
    LinearLayout progressbar;
    String recievernumber;
    TextView activity;
    List<chat_class> chat;
    DatabaseReference databaseReference,databaseReference2;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);
        recievernumber=getIntent().getStringExtra("number");
        toolbar = findViewById(R.id.chatroom_toolbar);
        name = findViewById(R.id.name);
        databaseReference=FirebaseDatabase.getInstance().getReference("Chats");
        databaseReference2=FirebaseDatabase.getInstance().getReference("recent_chats");
        auth = FirebaseAuth.getInstance();
        LinearLayoutManager layoutManager = new LinearLayoutManager(chatroom.this, RecyclerView.VERTICAL, false);
        layoutManager.setStackFromEnd(true);
        recyclerView = findViewById(R.id.chatroom_recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        send = findViewById(R.id.send);
        rootView = findViewById(R.id.root_view);
        progressbar=findViewById(R.id.progressbar);
        online = findViewById(R.id.activity);
        typing = findViewById(R.id.typing);
        imageView = findViewById(R.id.profilepic);
        setSupportActionBar(toolbar);

        activity = findViewById(R.id.activity);
        emojiButton = (ImageView) findViewById(R.id.emoji_btn);
        //submitButton = (ImageView) findViewById(R.id.submit_btn);

        progressbar.setVisibility(View.VISIBLE);
        emojiconEditText = (EmojiconEditText) findViewById(R.id.emojicon_edit_text);
        emojIcon = new EmojIconActions(this, rootView, emojiconEditText, emojiButton);
        emojIcon.setIconsIds(R.drawable.ic_action_keyboard, R.drawable.smiley);
        emojIcon.ShowEmojIcon();

        emojIcon.setKeyboardListener(new EmojIconActions.KeyboardListener() {
            @Override
            public void onKeyboardOpen() {
                Log.e("Keyboard", "open");
            }

            @Override
            public void onKeyboardClose() {
                Log.e("Keyboard", "close");
            }
        });


        chat=new ArrayList<>();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitleTextColor(getResources().getColor(R.color.yellow));
        name.setText(" " + getIntent().getStringExtra("name"));
        Picasso.with(chatroom.this).load(getIntent().getStringExtra("url")).placeholder(R.mipmap.profile).into(imageView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.getNavigationIcon().setTint(getResources().getColor(R.color.yellow));

        }

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myurl = dataSnapshot.child("url").getValue(String.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        Log.d("name", "onCreate: " + getIntent().getStringExtra("name"));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });




        databaseReference.child(auth.getCurrentUser().getPhoneNumber()).child(recievernumber).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    chat.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                        chat_class chat_class = dataSnapshot1.getValue(chat_class.class);
                        chat.add(chat_class);
                    }

                    Log.d("sizeoflist", "onDataChange: " + chat.size());
                    adapter = new chatroom_adapter(chatroom.this, chat, auth.getCurrentUser().getPhoneNumber(), getIntent().getStringExtra("url"), getIntent().getStringExtra("number"), emojiconEditText.getWidth());
                    recyclerView.setAdapter(adapter);
                    progressbar.setVisibility(View.GONE);

                }else {
                    progressbar.setVisibility(View.GONE);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendmessage();
            }

        });






    }

    private void sendmessage() {

        if (TextUtils.isEmpty(emojiconEditText.getText().toString())) {


        } else {


            messasagekey = databaseReference.push().getKey();
            chat_class chat_class1 = new chat_class(messasagekey, emojiconEditText.getText().toString(), auth.getCurrentUser().getPhoneNumber());
            databaseReference.child(auth.getCurrentUser().getPhoneNumber()).child(recievernumber).child(messasagekey).setValue(chat_class1);


            chat_class chat_class2 = new chat_class(messasagekey, emojiconEditText.getText().toString(), auth.getCurrentUser().getPhoneNumber());
            databaseReference.child(recievernumber).child(auth.getCurrentUser().getPhoneNumber()).child(messasagekey).setValue(chat_class2);

            recents_list user_data = new recents_list(getIntent().getStringExtra("name"), getIntent().getStringExtra("number"), getIntent().getStringExtra("url"), emojiconEditText.getText().toString());
            databaseReference2.child("single_chat").child(auth.getCurrentUser().getPhoneNumber()).child(recievernumber).setValue(user_data);
            recents_list user_data2 = new recents_list("null", auth.getCurrentUser().getPhoneNumber(), myurl, emojiconEditText.getText().toString());
            databaseReference2.child("single_chat").child(recievernumber).child(auth.getCurrentUser().getPhoneNumber()).setValue(user_data2);


            emojiconEditText.setText("");


        }
    }






}
