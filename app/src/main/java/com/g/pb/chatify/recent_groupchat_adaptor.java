package com.g.pb.chatify;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;


public class recent_groupchat_adaptor extends RecyclerView.Adapter<recent_groupchat_adaptor.ViewHolder> {

    private Context context;
    List<recents_groupchat_list> list;

    public recent_groupchat_adaptor(Context context, List<recents_groupchat_list> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public recent_groupchat_adaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recent_chat_item, parent, false);
        recent_groupchat_adaptor.ViewHolder viewHolder = new recent_groupchat_adaptor.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final recent_groupchat_adaptor.ViewHolder holder, final int position) {
        final recents_groupchat_list user_data = list.get(position);



        holder.name.setText(user_data.getName());
        Picasso.with(context).load(user_data.getUrl()).placeholder(R.mipmap.profile).into(holder.imageView);
        if(user_data.getLastmsg().equals("null")){
            holder.lastmsg.setText("new group created");
        }else{
        holder.lastmsg.setText(user_data.getLastmsg());}


        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, group_chat_room.class);
                i.putExtra("url", user_data.getUrl());
                i.putExtra("groupname", user_data.getGroupname());
                i.putExtra("key", user_data.getKey());


                context.startActivity(i);


            }
        });


    }

    public void filterlist(List<recents_groupchat_list> filterlist) {
        list = filterlist;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        View item;
        TextView name;
        RoundedImageView imageView;
        TextView lastmsg;



        public ViewHolder(final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.username);
            imageView = itemView.findViewById(R.id.profilepic);
            lastmsg = itemView.findViewById(R.id.lastmsg);
             this.item = itemView;

        }


    }
}

