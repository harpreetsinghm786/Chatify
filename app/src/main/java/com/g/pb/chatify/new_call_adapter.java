package com.g.pb.chatify;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;


public class new_call_adapter extends RecyclerView.Adapter<new_call_adapter.ViewHolder> {

    private Context context;

    List<user_data> user_data;



    public new_call_adapter(Context context, List<user_data> user_data) {
        this.user_data = user_data;
        this.context = context;


    }

    @Override
    public new_call_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.new_call_item, parent, false);
        new_call_adapter.ViewHolder viewHolder = new new_call_adapter.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final new_call_adapter.ViewHolder holder, final int position) {
        final user_data uploads = user_data.get(position);

        holder.name.setText(uploads.getName());
        holder.bio.setText(uploads.getUserbio());
        Picasso.with(context).load(uploads.getUrl()).placeholder(R.mipmap.profile).into(holder.imageView);


        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" +uploads.getPhonenumber()));
                    if (intent.resolveActivity(context.getPackageManager()) != null) {
                        context.startActivity(intent);
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
        TextView bio;


        public ViewHolder(final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.username);
            imageView = itemView.findViewById(R.id.profilepic);
            bio = itemView.findViewById(R.id.bio);
            this.item = itemView;

        }


    }


    }




