package com.g.pb.chatify;


import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jsibbold.zoomage.ZoomageView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;


public class profile_ extends Fragment {


    RoundedImageView profilepic;
    TextView username,userbio,userdob,phone,followers,following;

    ImageButton chooser;
    Uri filePath;

    StorageReference storageReference;

    DatabaseReference databaseReference;
    FirebaseAuth auth;
    String profileimage;
    LinearLayout progressbar;
    private static final int PICK_IMAGE_REQUEST = 124;


    LinearLayout editname,editbio,editdate;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View v= inflater.inflate(R.layout.fragment_profile_, container, false);
        final Toolbar toolbar = v.findViewById(R.id.toolbar_userprofile);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);

        auth=FirebaseAuth.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference("Users/"+auth.getCurrentUser().getPhoneNumber());
        databaseReference= FirebaseDatabase.getInstance().getReference("Users").child(auth.getCurrentUser().getPhoneNumber());
        profilepic=v.findViewById(R.id.user_profile_pic);
        username=v.findViewById(R.id.username);
        userbio=v.findViewById(R.id.userbio);
        phone=v.findViewById(R.id.phonenumber);
        userdob=v.findViewById(R.id.userdob);
        progressbar=v.findViewById(R.id.progressbar);
        followers=v.findViewById(R.id.followers);
        following=v.findViewById(R.id.following);

        chooser=v.findViewById(R.id.chooser);

        editname=v.findViewById(R.id.editname);
        editbio=v.findViewById(R.id.editbio);
        editdate=v.findViewById(R.id.editdob);



        progressbar.setVisibility(View.VISIBLE);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if(dataSnapshot.exists()){
                   username.setText(dataSnapshot.child("name").getValue(String.class));
                   String date=dataSnapshot.child("userdob").child("day").getValue(String.class)+"/"+dataSnapshot.child("userdob").child("month").getValue(String.class)+"/"+dataSnapshot.child("userdob").child("year").getValue(String.class);
                   userdob.setText(date);
                   phone.setText(dataSnapshot.child("phonenumber").getValue(String.class));
                   userbio.setText(dataSnapshot.child("userbio").getValue(String.class));
                   if(dataSnapshot.hasChild("followers")){
                   followers.setText(dataSnapshot.child("followers").getChildrenCount()+"");
                   }else{
                       followers.setText("0");
                   }
                   following.setText(dataSnapshot.child("following").getChildrenCount()+"");
               }else{
                   followers.setText("0");
               }

                   profileimage=dataSnapshot.child("url").getValue(String.class);
                   Picasso.with(getContext()).load(profileimage).placeholder(R.mipmap.profile).into(profilepic);

                   progressbar.setVisibility(View.GONE);

               }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        editname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(getContext());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.edit_profile_popup);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                LinearLayout drawer = dialog.findViewById(R.id.drawer);
                final TextView heading;
                final EditText editor;
                editor = dialog.findViewById(R.id.edited_text);
                heading=dialog.findViewById(R.id.heading);
                heading.setText("Enter your name");
                editor.setText(username.getText().toString());
                Animation transintion = AnimationUtils.loadAnimation(getContext(), R.anim.transition_upward);
                drawer.setAnimation(transintion);
                Button save=dialog.findViewById(R.id.save);
                Button cancel=dialog.findViewById(R.id.cancel);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseAuth auth = FirebaseAuth.getInstance();
                        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(auth.getCurrentUser().getPhoneNumber()).child("name");
                        databaseReference.setValue(editor.getText().toString());
                        dialog.dismiss();
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });

        editbio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.edit_profile_popup);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                LinearLayout drawer = dialog.findViewById(R.id.drawer);
                final TextView heading;
                final EditText editor;
                editor = dialog.findViewById(R.id.edited_text);
                heading=dialog.findViewById(R.id.heading);
                heading.setText("Enter your Bio");
                editor.setText(userbio.getText().toString());
                Animation transintion = AnimationUtils.loadAnimation(getContext(), R.anim.transition_upward);
                drawer.setAnimation(transintion);
                Button save=dialog.findViewById(R.id.save);
                Button cancel=dialog.findViewById(R.id.cancel);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseAuth auth = FirebaseAuth.getInstance();
                        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(auth.getCurrentUser().getPhoneNumber()).child("userbio");
                        databaseReference.setValue(editor.getText().toString());
                        dialog.dismiss();
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();


            }
        });


        editdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(getContext());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.edit_date_pop_up);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                LinearLayout drawer = dialog.findViewById(R.id.drawer);
                final DatePicker datePicker;
                datePicker=dialog.findViewById(R.id.bday_date_picker);
                Animation transintion = AnimationUtils.loadAnimation(getContext(), R.anim.transition_upward);
                drawer.setAnimation(transintion);

                Button save=dialog.findViewById(R.id.save);
                Button cancel=dialog.findViewById(R.id.cancel);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        FirebaseAuth auth = FirebaseAuth.getInstance();
                        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(auth.getCurrentUser().getPhoneNumber()).child("userdob");
                        databaseReference.child("day").setValue(datePicker.getDayOfMonth()+"");
                        int month=datePicker.getMonth()+1;
                        databaseReference.child("month").setValue(month+"");
                        databaseReference.child("year").setValue(datePicker.getYear()+"");
                        dialog.dismiss();
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();


            }
        });

        chooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

            }
        });


        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(getContext());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.profile_pic_viewer);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                ImageView share=dialog.findViewById(R.id.share_image);
                ImageView edit=dialog.findViewById(R.id.edit_image);


                Toolbar toolbar1=dialog.findViewById(R.id.pic_viewer_toolbar);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    toolbar1.getNavigationIcon().setTint(getResources().getColor(R.color.white));
                }


                toolbar1.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

                    }
                });

                share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, profileimage);
                        sendIntent.setType("text/plain");
                        sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Intent shareIntent = Intent.createChooser(sendIntent, null);
                        v.getContext().startActivity(shareIntent);
                    }
                });


                ZoomageView image=dialog.findViewById(R.id.zoomimage);

                Picasso.with(getContext()).load(profileimage).into(image);


                dialog.show();
            }
        });




        return v;
    }



    private String getFileExtension(Uri uri) {
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    private void uploadimage(){
        if (filePath != null) {
            progressbar.setVisibility(View.VISIBLE);
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(filePath));


            fileReference.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                }
                            }, 500);

                            fileReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    Uri dowload= task.getResult();
                                    String downloadurl=dowload.toString();


                                     databaseReference.child("url").setValue(downloadurl);

                                }
                            });



                            progressbar.setVisibility(View.GONE);



                        }
                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressbar.setVisibility(View.GONE);
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            progressbar.setVisibility(View.VISIBLE);
                        }
                    });


        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == PICK_IMAGE_REQUEST
                    && data != null && data.getData() != null) {
                filePath = data.getData();
                Picasso.with(getContext()).load(filePath).into(profilepic);
                uploadimage();

            }

        }

    }



}
