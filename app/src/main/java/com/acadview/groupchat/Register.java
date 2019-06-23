package com.acadview.groupchat;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class Register extends AppCompatActivity {

    TextInputEditText inputEmail,inputPassword;
    ProgressBar progressBar_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputEmail = findViewById(R.id.email_register);
        inputPassword = findViewById(R.id.password_register);
        progressBar_login = findViewById(R.id.progressbar_register);

    }
}
