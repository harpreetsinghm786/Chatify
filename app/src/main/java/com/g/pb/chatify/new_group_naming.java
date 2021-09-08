package com.g.pb.chatify;


import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;


public class new_group_naming extends Fragment {


    private static final int PICK_IMAGE_REQUEST = 123;
    ImageView profile;
    EmojiconEditText emojiconEditText;
    ImageButton done;
    RoundedImageView groupprofilepic;
    StorageReference storageReference;
    ImageView emojicon;
    EmojIconActions emojIcon;
    View rootView;
    RecyclerView selectedmembers;
    Uri filePath;
    LinearLayout progressbar;
    FirebaseAuth auth;
     String downloadurl;
     String myurl,myname;
    DatabaseReference databaseReference,databaseReference2,databaseReference3;
    List<user_data> list;
    TextView membersnum;
    Bundle bundle;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_new_group_naming, container, false);


        profile = v.findViewById(R.id.profilepic);
        progressbar=v.findViewById(R.id.progressbar);
        selectedmembers = v.findViewById(R.id.selected_members);
        done = v.findViewById(R.id.create_group);
        auth=FirebaseAuth.getInstance();
        databaseReference3=FirebaseDatabase.getInstance().getReference("Users");
        databaseReference2=FirebaseDatabase.getInstance().getReference("recent_chats");
        bundle=getArguments();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        selectedmembers.setLayoutManager(linearLayoutManager);
        emojiconEditText = v.findViewById(R.id.emojicon_group);
        membersnum = v.findViewById(R.id.number_of_members);
        storageReference = FirebaseStorage.getInstance().getReference("groups/" +bundle.getString("key"));
        groupprofilepic = v.findViewById(R.id.groupprofilepic);
        emojicon = v.findViewById(R.id.emoji_imageview);
        list = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("groups").child(bundle.getString("key"));
        rootView = v.findViewById(R.id.group);
        emojIcon = new EmojIconActions(getContext(), rootView, emojiconEditText, emojicon);
        emojIcon.setIconsIds(R.drawable.ic_action_keyboard, R.drawable.smiley);
        emojIcon.ShowEmojIcon();

        emojIcon.setKeyboardListener(new EmojIconActions.KeyboardListener() {
            @Override
            public void onKeyboardOpen() {
                Log.e("Keyboard", "open");
            }

            @Override
            public void onKeyboardClose() {
                Log.e("Keyboard", "close");
            }
        });


        databaseReference.child("members_list").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    user_data selected_members_list = dataSnapshot1.getValue(user_data.class);
                    list.add(selected_members_list);
                }
                membersnum.setText(list.size() + " Participants");
                display_selected_group_members_adapter display_selected_group_members_adapter = new display_selected_group_members_adapter(getContext(), list,null);
                selectedmembers.setAdapter(display_selected_group_members_adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        databaseReference3.child(auth.getCurrentUser().getPhoneNumber()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myurl = dataSnapshot.child("url").getValue(String.class);
                myname=dataSnapshot.child("name").getValue(String.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        groupprofilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseimage();
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(emojiconEditText.getText().toString())){
                    Toast.makeText(getContext(), "Group subject is required", Toast.LENGTH_SHORT).show();
                }else {

                    uploadimage();
                }
            }
        });

        return v;
    }

    private void chooseimage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    private void uploadimage() {
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
                                    Uri dowload = task.getResult();
                                    downloadurl = dowload.toString();
                                    databaseReference.child("url").setValue(downloadurl);
                                    databaseReference.child("groupname").setValue(emojiconEditText.getText().toString());

                                    recents_list recents_list = new recents_list(emojiconEditText.getText().toString(),bundle.getString("key"),downloadurl,"null");
                                    databaseReference2.child("group_chat").child(bundle.getString("key")).setValue(recents_list);


                                    for(int i=0;i<list.size();i++){
                                        databaseReference3.child(list.get(i).getPhonenumber()).child("groups").child(bundle.getString("key")).setValue(bundle.getString("key"));
                                    }

                                    progressbar.setVisibility(View.GONE);
                                    Toast.makeText(getContext(), "Group Created", Toast.LENGTH_LONG).show();

                                    Intent i = new Intent(getContext(), group_chat_room.class);
                                    i.putExtra("groupname",emojiconEditText.getText().toString());
                                    i.putExtra("url",downloadurl);
                                    i.putExtra("key",bundle.getString("key"));
                                    startActivity(i);
                                    getActivity().finish();


                                }
                            });



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

                        }
                    });


        } else {
            Toast.makeText(getContext(), "Select profile pic", Toast.LENGTH_SHORT).show();
        }
    }





    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == PICK_IMAGE_REQUEST
                    && data != null && data.getData() != null) {
                filePath = data.getData();
                Picasso.with(getContext()).load(filePath).into(groupprofilepic);

            }

        }


    }
}
