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
import java.util.List;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

public class group_chat_room extends AppCompatActivity {

    Toolbar toolbar;
    RoundedImageView groupicon;
    TextView name,groupmembers;
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
    String myurl,myname;
    LinearLayout progressbar;
    String recievernumber;
    TextView activity;
    List<chat_class> chat;
    DatabaseReference databaseReference,databaseReference2,databaseReference3;
    FirebaseAuth auth;
    String a="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat_room);

        recievernumber=getIntent().getStringExtra("number");
        toolbar = findViewById(R.id.chatroom_toolbar);
        name = findViewById(R.id.groupname);
         auth = FirebaseAuth.getInstance();
        LinearLayoutManager layoutManager = new LinearLayoutManager(group_chat_room.this, RecyclerView.VERTICAL, false);
        layoutManager.setStackFromEnd(true);
        recyclerView = findViewById(R.id.chatroom_recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        send = findViewById(R.id.send);
        rootView = findViewById(R.id.root_view);
        progressbar=findViewById(R.id.progressbar);
        online = findViewById(R.id.activity);
        typing = findViewById(R.id.typing);
        groupicon = findViewById(R.id.groupicon);
        groupmembers=findViewById(R.id.group_members);
        setSupportActionBar(toolbar);



        databaseReference= FirebaseDatabase.getInstance().getReference("groups");
        databaseReference2=FirebaseDatabase.getInstance().getReference("recent_chats");
        databaseReference3=FirebaseDatabase.getInstance().getReference("Users").child(auth.getCurrentUser().getPhoneNumber());


        activity = findViewById(R.id.activity);
        emojiButton = (ImageView) findViewById(R.id.emoji_btn);
        //submitButton = (ImageView) findViewById(R.id.submit_btn);

       // progressbar.setVisibility(View.VISIBLE);
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
        name.setText(" " + getIntent().getStringExtra("groupname"));

        databaseReference.child(getIntent().getStringExtra("key")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                a="";
                for(int i=0;i<dataSnapshot.child("members_list").getChildrenCount();i++){
                    a=a+dataSnapshot.child("members_list").child(i+"").child("name").getValue(String.class)+", ";

                }

                groupmembers.setText(a);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Picasso.with(group_chat_room.this).load(getIntent().getStringExtra("url")).placeholder(R.mipmap.profile).into(groupicon);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.getNavigationIcon().setTint(getResources().getColor(R.color.yellow));

        }



        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        databaseReference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myurl = dataSnapshot.child("url").getValue(String.class);
                myname=dataSnapshot.child("name").getValue(String.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });










        databaseReference.child(getIntent().getStringExtra("key")).child("chat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    chat.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                        chat_class chat_class = dataSnapshot1.getValue(chat_class.class);
                        chat.add(chat_class);
                    }

                    Log.d("sizeoflist", "onDataChange: " + chat.size());
                    adapter = new chatroom_adapter(group_chat_room.this, chat, auth.getCurrentUser().getPhoneNumber(), getIntent().getStringExtra("url"), getIntent().getStringExtra("number"), emojiconEditText.getWidth());
                    recyclerView.setAdapter(adapter);
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
            group_chat_class chat_class1 = new group_chat_class(messasagekey, emojiconEditText.getText().toString(), auth.getCurrentUser().getPhoneNumber(),myname,myurl);

            databaseReference.child(getIntent().getStringExtra("key")).child("chat").child(messasagekey).setValue(chat_class1);
            recents_list recents_list = new recents_list(getIntent().getStringExtra("groupname"),getIntent().getStringExtra("key"),getIntent().getStringExtra("url"),emojiconEditText.getText().toString());
            databaseReference2.child("group_chat").child(getIntent().getStringExtra("key")).setValue(recents_list);



            emojiconEditText.setText("");


        }
    }





}
