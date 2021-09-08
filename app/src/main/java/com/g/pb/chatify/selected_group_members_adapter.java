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


public class selected_group_members_adapter extends RecyclerView.Adapter<selected_group_members_adapter.ViewHolder> {

    private Context context;

    List<user_data> user_data;
    RecyclerView pool;
    List<user_data> selected_members;
    ImageView imageView;
    TextView names;


    public selected_group_members_adapter(Context context, List<user_data> user_data, RecyclerView pool) {
        this.user_data=user_data;
        this.context = context;
        this.pool =pool;
        selected_members=new ArrayList<>();


    }

    @Override
    public selected_group_members_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.new_group_member_item, parent, false);
        selected_group_members_adapter.ViewHolder viewHolder = new selected_group_members_adapter.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final selected_group_members_adapter.ViewHolder holder, final int position) {
        final user_data uploads = user_data.get(position);

        holder.name.setText(uploads.getName());
        Picasso.with(context).load(uploads.getUrl()).placeholder(R.mipmap.profile).into(holder.imageView);


        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i< pool.getChildCount(); i++){
                    imageView= pool.getChildAt(i).findViewById(R.id.checked);
                    names= pool.getChildAt(i).findViewById(R.id.username);
                       if(names.getText().toString().equals(uploads.getName())){
                           Log.d("names", "onClick: "+names.getText().toString());
                           imageView.setVisibility(View.GONE);
                       }


                }
                user_data.remove(position);
                selected_group_members_adapter.this.notifyDataSetChanged();

            }
        });


        }

    @Override
    public int getItemCount() {
        return user_data.size();
    }





    class ViewHolder extends RecyclerView.ViewHolder {

        View item;
        TextView name;
        RoundedImageView imageView;



        public ViewHolder(final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.username);
            imageView = itemView.findViewById(R.id.profilepic);
            this.item = itemView;

        }


    }





}



