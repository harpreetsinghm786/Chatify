package com.g.pb.chatify;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;


public class calls extends Fragment {


    ImageButton add_call;
    LinearLayout linearLayout;
    RecyclerView recent_calls;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_calls, container, false);

        add_call=v.findViewById(R.id.add_call);
        linearLayout=v.findViewById(R.id.empty_msg);

        add_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),new_call.class);
                startActivity(i);
            }
        });

        return v;
    }

}
