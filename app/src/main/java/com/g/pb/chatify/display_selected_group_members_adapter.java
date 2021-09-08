package com.g.pb.chatify;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class display_selected_group_members_adapter extends RecyclerView.Adapter<display_selected_group_members_adapter.ViewHolder> {

    private Context context;

    List<user_data> user_data;
    RecyclerView pool;
    List<user_data> selected_members;
    ImageView imageView;
    TextView names;


    public display_selected_group_members_adapter(Context context, List<user_data> user_data, RecyclerView pool) {
        this.user_data=user_data;
        this.context = context;
        this.pool =pool;
        selected_members=new ArrayList<>();


    }

    @Override
    public display_selected_group_members_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.new_group_member_item, parent, false);
        display_selected_group_members_adapter.ViewHolder viewHolder = new display_selected_group_members_adapter.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final display_selected_group_members_adapter.ViewHolder holder, final int position) {
        final user_data uploads = user_data.get(position);

        holder.name.setText(uploads.getName());
        Picasso.with(context).load(uploads.getUrl()).placeholder(R.mipmap.profile).into(holder.imageView);
        holder.unchecked.setVisibility(View.GONE);




        }

    @Override
    public int getItemCount() {
        return user_data.size();
    }





    class ViewHolder extends RecyclerView.ViewHolder {

        View item;
        TextView name;
        RoundedImageView imageView;
            ImageView unchecked;



        public ViewHolder(final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.username);
            imageView = itemView.findViewById(R.id.profilepic);
            unchecked=itemView.findViewById(R.id.unchecked);
            this.item = itemView;

        }


    }





}



