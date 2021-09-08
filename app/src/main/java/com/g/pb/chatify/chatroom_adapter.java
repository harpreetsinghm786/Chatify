package com.g.pb.chatify;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class chatroom_adapter extends RecyclerView.Adapter {


    List<chat_class> uploads;
    String number;
    String recievernumber;
    Context context;
    String urlreciever;
    int maxwidth;

    public chatroom_adapter(Context context, List<chat_class> uploads, String number, String urlreciever, String recievernumber, int maxwidth) {
        this.uploads = uploads;
        this.number = number;
        this.urlreciever = urlreciever;
        this.context = context;
        this.recievernumber = recievernumber;
        this.maxwidth = maxwidth;

    }

    @Override
    public int getItemViewType(int position) {



        if (uploads.get(position).getNumber().equals(number)) {

            if (position > 0 && uploads.get(position - 1).getNumber().equals(number)) {
                return 1;
            }

            return 0;
        } else {
            if (position > 0 && uploads.get(position - 1).getNumber().equals(recievernumber)) {
                return 3;
            }

            return 2;
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;

        if (viewType == 0) {
            view = layoutInflater.inflate(R.layout.senderitem, parent, false);
            return new ViewHolderOne(view);
        } else if (viewType == 1) {

            view = layoutInflater.inflate(R.layout.senderitem, parent, false);
            return new ViewHolderTwo(view);
        } else if (viewType == 2) {

            view = layoutInflater.inflate(R.layout.recieveritem, parent, false);
            return new ViewHolderThree(view);
        } else {
            view = layoutInflater.inflate(R.layout.recieveritem, parent, false);
            return new ViewHolderfour(view);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {





        if (uploads.get(position).getNumber().equals(number)) {

            if (position > 0 && uploads.get(position - 1).getNumber().equals(number)) {
                ViewHolderTwo viewHolderTwo = (ViewHolderTwo) holder;





                viewHolderTwo.textView.setText(uploads.get(position).getMessage());
            } else {

                ViewHolderOne viewHolderOne = (ViewHolderOne) holder;




                viewHolderOne.textView.setText(uploads.get(position).getMessage());

            }

        } else {

            if (position > 0 && uploads.get(position - 1).getNumber().equals(recievernumber)) {
                ViewHolderfour viewHolderfour = (ViewHolderfour) holder;
                viewHolderfour.textView.setText(uploads.get(position).getMessage());

                viewHolderfour.imageView.setVisibility(View.GONE);

            } else {
                ViewHolderThree viewHolderThree = (ViewHolderThree) holder;

                viewHolderThree.textView.setText(uploads.get(position).getMessage());
                Picasso.with(context).load(urlreciever).into(viewHolderThree.imageView);

            }
        }


    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }

    class ViewHolderOne extends RecyclerView.ViewHolder {

        TextView textView;



        public ViewHolderOne(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.sendermessage);

        }
    }


    class ViewHolderThree extends RecyclerView.ViewHolder {

        TextView textView;
        RoundedImageView imageView;


        public ViewHolderThree(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.recievermessage);
            imageView = itemView.findViewById(R.id.profilepicreciever);

        }

    }

    class ViewHolderTwo extends RecyclerView.ViewHolder {

        TextView textView;


        public ViewHolderTwo(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.sendermessage);


        }


    }

    class ViewHolderfour extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;

        public ViewHolderfour(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.recievermessage);
            imageView = itemView.findViewById(R.id.profilepicreciever);

        }
    }





}






