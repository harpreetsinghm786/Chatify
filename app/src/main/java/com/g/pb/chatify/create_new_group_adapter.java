package com.g.pb.chatify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

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


public class create_new_group_adapter extends RecyclerView.Adapter<create_new_group_adapter.ViewHolder> {

    private Context context;

    String key;
    List<user_data> user_data;
    user_data currentuserdata;
    RecyclerView selectedmember,pool;
    selected_group_members_adapter selected_group_members_adapter;
    List<user_data> selected_members;
    ImageButton imageButton;
    DatabaseReference databaseReference,databaseReference2;
    FirebaseAuth auth;




    public create_new_group_adapter(Context context, List<user_data> user_data, RecyclerView selectedmembers, RecyclerView pool, ImageButton imageButton) {
        this.user_data=user_data;
        this.context = context;
        this.selectedmember=selectedmembers;
        selected_members=new ArrayList<>();
        this.imageButton=imageButton;

        this.pool=pool;

    }

    @Override
    public create_new_group_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.new_group_selection_item, parent, false);
        create_new_group_adapter.ViewHolder viewHolder = new create_new_group_adapter.ViewHolder(v);

        auth=FirebaseAuth.getInstance();
        databaseReference2=FirebaseDatabase.getInstance().getReference().child("Users").child(auth.getCurrentUser().getPhoneNumber());


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final create_new_group_adapter.ViewHolder holder, final int position) {
        final user_data uploads = user_data.get(position);

        holder.name.setText(uploads.getName());
        holder.userbio.setText(uploads.getUserbio());
        Picasso.with(context).load(uploads.getUrl()).placeholder(R.mipmap.profile).into(holder.imageView);
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               currentuserdata =  new user_data(dataSnapshot.child("name").getValue(String.class),auth.getCurrentUser().getPhoneNumber(),dataSnapshot.child("url").getValue(String.class),dataSnapshot.child("userbio").getValue(String.class),dataSnapshot.child("userdob").getValue(userdob.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (holder.checked.getVisibility() == View.VISIBLE) {

                     holder.checked.setVisibility(View.GONE);
                    if(selected_members.size()>1){
                        for(int i=0;i<selected_members.size();i++){
                            if(selected_members.get(i).getPhonenumber().equals(uploads.getPhonenumber())){
                                selected_members.remove(i);}
                        }
                        selectedmember.getAdapter().notifyDataSetChanged();
                        holder.checked.setVisibility(View.GONE);}


                } else {

                    selected_members.add(uploads);
                    selectedmember.setVisibility(View.VISIBLE);
                   selected_group_members_adapter=new selected_group_members_adapter(context,selected_members,pool);
                   selectedmember.setAdapter(selected_group_members_adapter);
                    holder.checked.setVisibility(View.VISIBLE);
                }
            }
        });

        databaseReference=FirebaseDatabase.getInstance().getReference().child("groups");

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selected_members.size()>0){
                    selected_members.add(0,currentuserdata);
                key=databaseReference.push().getKey();
                new_group_list new_group=new new_group_list(selected_members,"null",null,"null","null",key);
                databaseReference.child(key).setValue(new_group);

                Bundle bundle=new Bundle();
                bundle.putString("key",key);

                new_group_naming new_group_naming=new new_group_naming();
                new_group_naming.setArguments(bundle);


                ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().add(R.id.new_group_container, new_group_naming).commit();

            }else{
                    Toast.makeText(context, "Select atleast one member", Toast.LENGTH_SHORT).show();
                }
            }
        });


        }

    @Override
    public int getItemCount() {
        return user_data.size();
    }


    public void filterlist(List<user_data> filterlist) {
        user_data = filterlist;
        notifyDataSetChanged();
    }




    class ViewHolder extends RecyclerView.ViewHolder {

        View item;
        TextView name;
        RoundedImageView imageView;
        TextView userbio;
        ImageView checked;


        public ViewHolder(final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.username);
            imageView = itemView.findViewById(R.id.profilepic);
            userbio = itemView.findViewById(R.id.bio);
            checked=itemView.findViewById(R.id.checked);
            this.item = itemView;

        }


    }





}



