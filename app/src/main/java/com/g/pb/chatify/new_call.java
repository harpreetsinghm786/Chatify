package com.g.pb.chatify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class new_call extends AppCompatActivity {

    RecyclerView new_call_view;
    new_call_adapter new_call_adapter;
    List<user_data> user_data;
    DatabaseReference databaseReference;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_call);
        auth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference("Users");

        new_call_view=findViewById(R.id.new_call_recycler_view);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(new_call.this,RecyclerView.VERTICAL,false);
        new_call_view.setLayoutManager(linearLayoutManager);


        user_data=new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    user_data user=dataSnapshot1.getValue(user_data.class);
                    if(user.getPhonenumber().equals(auth.getCurrentUser().getPhoneNumber())){

                    } else{
                        user_data.add(user);
                    }

                }
                new_call_adapter =new new_call_adapter(new_call.this,user_data);
                new_call_view.setAdapter(new_call_adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
