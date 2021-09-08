package com.g.pb.chatify;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;
import com.hbb20.CountryCodePicker;

public class login extends AppCompatActivity {
    CountryCodePicker countryCodePicker;
    TextInputEditText number;
    Button btncontinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        countryCodePicker = findViewById(R.id.cpp);
        number = findViewById(R.id.phonenumber);
        btncontinue = findViewById(R.id.next);
        countryCodePicker.registerCarrierNumberEditText(number);

        btncontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(number.getText().toString())) {
                    number.setError("Number is required");
                } else if (number.getText().toString().replace(" ", "").length() != 10) {
                    number.setError("Enter correct number");
                } else{
                    Intent i = new Intent(login.this, verification.class);
                    i.putExtra("number", countryCodePicker.getFullNumberWithPlus().replace(" ", ""));
                    startActivity(i);
                    finish();
                }}
        });

        permissions();
    }

    private void permissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS, Manifest.permission.READ_CONTACTS}, 1);
        }
    }

}
