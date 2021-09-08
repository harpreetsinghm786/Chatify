package com.g.pb.chatify;


import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class groups extends Fragment {

    recent_groupchat_adaptor adaptor;
    List<recents_groupchat_list> list;
    DatabaseReference databaseReference,databaseReference2;
    FirebaseAuth auth;
    LinearLayout progressbar;
    RecyclerView recyclerView;
    List<String> groupkeys;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_groups, container, false);
        Toolbar toolbar = v.findViewById(R.id.groups_toolbar);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.getOverflowIcon().setTint(getResources().getColor(R.color.yellow));
        }
        toolbar.inflateMenu(R.menu.header_menu_main);
        setHasOptionsMenu(true);

        progressbar=v.findViewById(R.id.progressbar);
        progressbar.setVisibility(View.VISIBLE);


        auth = FirebaseAuth.getInstance();

        recyclerView = v.findViewById(R.id.groups_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        databaseReference = FirebaseDatabase.getInstance().getReference("recent_chats");
        databaseReference2 = FirebaseDatabase.getInstance().getReference("Users").child(auth.getCurrentUser().getPhoneNumber());

        list = new ArrayList<>();
        groupkeys=new ArrayList<>();




        databaseReference2.child("groups").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                groupkeys.clear();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    String key=dataSnapshot1.getValue(String.class);
                    groupkeys.add(0,key);
                }

                Log.d("karodimal", "onCreateView: "+groupkeys.size());

                databaseReference.child("group_chat").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        list.clear();
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            recents_groupchat_list user_data = dataSnapshot1.getValue(recents_groupchat_list.class);
                            for(int i=0;i<groupkeys.size();i++){
                                if(user_data.key.equals(groupkeys.get(i))){
                                    list.add(0,user_data);
                                }

                            }

                        }

                        Log.d("listsize", "onDataChange: "+list.size());

                        adaptor = new recent_groupchat_adaptor(getContext(), list);
                        recyclerView.setAdapter(adaptor);

                        progressbar.setVisibility(View.GONE);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






        return v;
    }



    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.header_menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.new_group:

                break;

        }
        return true;

    }


}
