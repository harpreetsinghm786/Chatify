package com.g.pb.chatify;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class search extends Fragment {


    RecyclerView recyclerView;
    List<user_data> user_data;
    search_adapter search_adapter;
    FirebaseAuth auth;
    LinearLayout progressbar;
    DatabaseReference databaseReference;
    EditText search_bar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView=v.findViewById(R.id.search_recyclerview);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        auth=FirebaseAuth.getInstance();
        progressbar=v.findViewById(R.id.progressbar);
        search_bar=v.findViewById(R.id.search_bar);
        progressbar.setVisibility(View.VISIBLE);
        databaseReference=FirebaseDatabase.getInstance().getReference("Users");
        user_data=new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user_data.clear();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    if(dataSnapshot1.exists()) {

                        user_data user = dataSnapshot1.getValue(com.g.pb.chatify.user_data.class);
                        if(user.getPhonenumber().equals(auth.getCurrentUser().getPhoneNumber()+"")){

                        }else{
                            user_data.add(user);
                        }
                    }
                }

                search_adapter=new search_adapter(getContext(),user_data);
                recyclerView.setAdapter(search_adapter);
                progressbar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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





        return v;


    }


    private  void filter(String text){
        List<user_data> filterlist=new ArrayList<>();
        for(user_data item:user_data){
            if(item.getName().toLowerCase().contains(text.toLowerCase())){
                filterlist.add(item);
            }
        }
        search_adapter.filterlist(filterlist);

    }


}
