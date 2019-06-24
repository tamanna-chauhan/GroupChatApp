package com.acadview.groupchat;

import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.acadview.groupchat.Adapters.MessageAdapter;
import com.acadview.groupchat.Models.AllMethods;
import com.acadview.groupchat.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GroupChatActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference messagedb;
    MessageAdapter messageAdapter;
    User u;
    List<Message> messages;

    RecyclerView rvMessage;
    EditText etMessage;
    ImageButton imgButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        init();
    }

    private void init() {

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        u = new User();
        rvMessage = findViewById(R.id.rvMessage);
        etMessage = findViewById(R.id.et_message);
        imgButton = findViewById(R.id.sendBtn);
        imgButton.setOnClickListener(this);
        messages = new ArrayList<>();
    }

    @Override
    public void onClick(View v) {

        if(!TextUtils.isEmpty(etMessage.getText().toString())){
            Message message = new Message(etMessage.getText().toString(),u.getName());
            etMessage.setText("");
            messagedb.push().setValue(message);
        }else{
            Toast.makeText(getApplicationContext(),"You cannot send blank message",Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    protected void onStart() {
        super.onStart();

        final FirebaseUser currentUser = auth.getCurrentUser();

        u.setUid(currentUser.getUid());
        u.setEmail(currentUser.getEmail());


        database.getReference("User").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                u = dataSnapshot.getValue(User.class);
                u.setUid(currentUser.getUid());
                AllMethods.name = u.getName();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        messagedb = database.getReference("messages");
        messagedb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


                Message message = dataSnapshot.getValue(Message.class);
                message.setKey(dataSnapshot.getKey());
                messages.add(message);
                displayMessages(messages);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Message message = dataSnapshot.getValue(Message.class);
                message.setKey(dataSnapshot.getKey());

                List<Message> newMessages = new ArrayList<>();

                for(Message m: messages){
                    if(m.getKey().equals(message.getKey())){
                        newMessages.add(message);
                    }else
                    {
                        newMessages.add(m);
                    }
                }
                messages= newMessages;
                displayMessages(messages);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                Message message = dataSnapshot.getValue(Message.class);
                message.setkey(dataSnapshot.getKey());

                List<com.acadview.groupchat.Models.Message> newMessages = new ArrayList<>();

                for(Message m:messages){
                    if(!m.getKey().equals(message.getKey())){
                        displayMessages(messages);
                    }
                }

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
    @Override
    protected void onPostResume() {
        super.onPostResume();
        messages = new ArrayList<>();
    }


    private void displayMessages(List<Message> messages) {

        rvMessage. setLayoutManager(new LinearLayoutManager(GroupChatActivity.this));
        messageAdapter = new MessageAdapter(GroupChatActivity.this,messages,messagedb);
        rvMessage.setAdapter(messageAdapter);
    }
}
