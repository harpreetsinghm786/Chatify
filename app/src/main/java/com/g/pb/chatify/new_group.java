package com.g.pb.chatify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class new_group extends AppCompatActivity {


    Toolbar toolbar;
    RecyclerView recyclerView, selectedmembers;
    LinearLayout progressbar;
    EditText search_bar;
    create_new_group_adapter create_new_group_adapter;
    List<user_data> user_data;
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    ImageButton next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);

        progressbar = findViewById(R.id.progressbar);
        toolbar = findViewById(R.id.grouplist_toolbar);
        search_bar = findViewById(R.id.search_bar);
        auth = FirebaseAuth.getInstance();
        next = findViewById(R.id.next);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(new_group.this, RecyclerView.HORIZONTAL, false);

        selectedmembers = findViewById(R.id.selected_members);
        selectedmembers.setLayoutManager(linearLayoutManager2);
        setSupportActionBar(toolbar);
        progressbar.setVisibility(View.VISIBLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle("New Group");
        toolbar.setSubtitle("Add participants ");
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        user_data = new ArrayList<>();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.getNavigationIcon().setTint(getResources().getColor(R.color.yellow));
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(new_group.this, RecyclerView.VERTICAL, false);
        recyclerView = findViewById(R.id.mycontacts);
        recyclerView.setLayoutManager(linearLayoutManager);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user_data.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if (dataSnapshot1.exists()) {

                        user_data user = dataSnapshot1.getValue(com.g.pb.chatify.user_data.class);
                        if (user.getPhonenumber().equals(auth.getCurrentUser().getPhoneNumber() + "")) {

                        } else {
                            user_data.add(user);
                        }
                    }
                }

                create_new_group_adapter = new create_new_group_adapter(new_group.this, user_data,selectedmembers,recyclerView,next);
                recyclerView.setAdapter(create_new_group_adapter);
                progressbar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void filter(String text) {
        List<user_data> filterlist = new ArrayList<>();
        for (user_data item : user_data) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filterlist.add(item);
            }
        }
        create_new_group_adapter.filterlist(filterlist);

    }

}
