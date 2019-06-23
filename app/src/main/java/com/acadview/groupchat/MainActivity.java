package com.acadview.groupchat;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    TextInputEditText inputEmail,inputPassword;
    ProgressBar progressBar_login;

    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

                auth = FirebaseAuth.getInstance();

                if (auth.getCurrentUser() != null) {

                    Intent i = new Intent(MainActivity.this, GroupChatActivity.class);
                    startActivity(i);

                }
                else {

                        setContentView(R.layout.activity_main);

                        inputEmail = findViewById(R.id.email_login);
                        inputPassword = findViewById(R.id.password);
                        progressBar_login = findViewById(R.id.progressbar_login);
                        reference = FirebaseDatabase.getInstance().getReference().child("Users");
                }

    }


    public void LoginUser(View v){
        progressBar_login.setVisibility(View.VISIBLE);

        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        if(!email.equals("") && !password.equals("")){
            auth.
        }

    }


    public void gotoRegister(View v) {

        Intent i =new Intent(MainActivity.this,Register.class);
        startActivity(i);
    }

    public void forgetPassword(View v){

    }
}
