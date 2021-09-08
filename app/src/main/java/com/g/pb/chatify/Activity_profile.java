package com.g.pb.chatify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

public class Activity_profile extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 124;
    TextInputEditText username, emailid, password, confirmpassword, bio;
    DatePicker datePicker;
    CheckBox confirmed, robot, correct;
    LinearLayout reset,progressBar;
    RoundedImageView profilepic;
    Button register;
    ImageButton chooser;
    Uri filePath;
    userdob userdob;
    user_data upload;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        username = findViewById(R.id.username);
        databaseReference= FirebaseDatabase.getInstance().getReference("userdata");
        auth=FirebaseAuth.getInstance();
        chooser=findViewById(R.id.chooser);
        databaseReference= FirebaseDatabase.getInstance().getReference("Users").child(auth.getCurrentUser().getPhoneNumber());
        storageReference= FirebaseStorage.getInstance().getReference("Users/"+auth.getCurrentUser().getPhoneNumber());
        emailid = findViewById(R.id.emailid);
        bio = findViewById(R.id.bio);
        datePicker = findViewById(R.id.bday_date_picker);
        confirmed = findViewById(R.id.confirmed);
        robot = findViewById(R.id.robot);
        correct =findViewById(R.id.correct);
        progressBar =findViewById(R.id.progressbar);
        reset = findViewById(R.id.reset);
        profilepic = findViewById(R.id.profile_pic);
        register = findViewById(R.id.save);



        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profilepic.setImageResource(R.mipmap.profile);
                username.setText("");
                emailid.setText("");
                bio.setText("");
                confirmed.setChecked(false);
                robot.setChecked(false);
                correct.setChecked(false);

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






        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                if (TextUtils.isEmpty(username.getText().toString())) {
                    username.requestFocus();
                    username.setError("User name is Required.",getResources().getDrawable(R.drawable.ic_error_outline_black_24dp));
                    progressBar.setVisibility(View.GONE);
                } else if (TextUtils.isEmpty(emailid.getText().toString())) {
                    emailid.requestFocus();
                    emailid.setError("Email id is Required.",getResources().getDrawable(R.drawable.ic_error_outline_black_24dp));
                    progressBar.setVisibility(View.GONE);
                }   else if (!confirmed.isChecked()) {
                    Toast.makeText(Activity_profile.this, "Check and confirm all the data fields.", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                } else  if(!robot.isChecked()){
                    Toast.makeText(Activity_profile.this, "I think you are robot.", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }else  if(!correct.isChecked()){
                    Toast.makeText(Activity_profile.this, "Information should be verified by you.", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }else{

                  uploadimage();


                }

            }
        });


    }








    private String getFileExtension(Uri uri) {
        ContentResolver cR = this.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    private void uploadimage(){
        if (filePath != null) {
            progressBar.setVisibility(View.VISIBLE);
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

                                    int month=datePicker.getMonth()+1;

                                    userdob = new userdob(datePicker.getDayOfMonth()+"",month+"",datePicker.getYear()+"");

                                    if(TextUtils.isEmpty(bio.getText())){
                                        upload = new user_data(username.getText().toString().trim(),auth.getCurrentUser().getPhoneNumber(),downloadurl,"Hey! I am using Post Card",userdob);

                                    }else{
                                        upload = new user_data(username.getText().toString().trim(),auth.getCurrentUser().getPhoneNumber(),downloadurl,bio.getText().toString(),userdob);
                                    }
                                    databaseReference.setValue(upload);

                                }
                            });


                            Toast.makeText(Activity_profile.this, "Profile Updated", Toast.LENGTH_LONG).show();

                            progressBar.setVisibility(View.GONE);
                            Intent i=new Intent(Activity_profile.this,MainActivity.class);
                            startActivity(i);
                            finish();

                        }
                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(Activity_profile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            progressBar.setVisibility(View.VISIBLE);
                        }
                    });


        } else {
            Toast.makeText(this, "Select Activity_profile pic", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGE_REQUEST
                    && data != null && data.getData() != null) {
                filePath = data.getData();
                Picasso.with(this).load(filePath).into(profilepic);

            }

        }

    }


}



