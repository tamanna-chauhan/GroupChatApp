package com.acadview.groupchat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.acadview.groupchat.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    TextInputEditText inputEmail,inputPassword,inputName;
    ProgressBar progressBar_register;
    DatabaseReference reference;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputEmail = findViewById(R.id.email_register);
        inputPassword = findViewById(R.id.password_register);
        progressBar_register = findViewById(R.id.progressbar_register);
        inputName = findViewById(R.id.name);

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("Users");

    }

    /*when user want to get registrer */

   public void RegisterUsers (View v){
        progressBar_register.setVisibility(View.VISIBLE);
        final String email = inputEmail.getText().toString();
        final String password = inputPassword.getText().toString();
        final String name = inputName.getText().toString();

        if(!email.equals("") && !password.equals("") && password.length()>6){

            auth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
//                                /*insert values in database*/
                                FirebaseUser firebaseUser = auth.getCurrentUser();
                                User u = new User();
                                u.setName(name);
                                u.setEmail(email);

                                reference.child(firebaseUser.getUid()).setValue(u)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(getApplicationContext(),"User registered successfully",Toast.LENGTH_SHORT).show();
                                                    progressBar_register.setVisibility(View.GONE);
                                                    finish();
                                                    Intent i = new Intent(Register.this,GroupChatActivity.class);
                                                    startActivity(i);
                                                }
                                                else {
                                                    progressBar_register.setVisibility(View.GONE);
                                                    Toast.makeText(getApplicationContext(),"User could not be created",Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });

                            }

                        }
                    });
        }

   }

   public void gotoLogin(View v){
       Intent i =  new Intent(Register.this,MainActivity.class);
       startActivity(i);
   }
}
