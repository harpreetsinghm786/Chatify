package com.g.pb.chatify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class search_adapter extends RecyclerView.Adapter<search_adapter.ViewHolder> {

    private Context context;

    List<user_data> user_data;


    public search_adapter(Context context, List<user_data> user_data) {
        this.user_data=user_data;
        this.context = context;


    }

    @Override
    public search_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_item, parent, false);
        search_adapter.ViewHolder viewHolder = new search_adapter.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final search_adapter.ViewHolder holder, final int position) {
        final user_data uploads = user_data.get(position);

        holder.name.setText(uploads.getName());
        holder.userbio.setText(uploads.getUserbio());
        Picasso.with(context).load(uploads.getUrl()).placeholder(R.mipmap.profile).into(holder.imageView);



        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(context,receiver_profile.class);
                i.putExtra("name",uploads.getName());
                i.putExtra("url",uploads.getUrl());
                i.putExtra("bio",uploads.getUserbio());
                i.putExtra("number",uploads.getPhonenumber());
                context.startActivity(i);



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


        public ViewHolder(final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.username);
            imageView = itemView.findViewById(R.id.profilepic);
            userbio = itemView.findViewById(R.id.userbio);
            this.item = itemView;

        }


    }





}



