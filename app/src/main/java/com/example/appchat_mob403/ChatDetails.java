package com.example.appchat_mob403;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.appchat_mob403.DTO.Chat;
import com.example.appchat_mob403.DTO.User;
import com.example.appchat_mob403.DTO.UserCurrent;
import com.example.appchat_mob403.adapter.ChatAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatDetails extends AppCompatActivity {
    TextInputEditText ed_content;
    Button btn_send;
    RecyclerView rcv_contentChat;

    List<Chat> chatList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_details);
        ed_content  = findViewById(R.id.ed_content);
        btn_send = findViewById(R.id.btn_send);
        rcv_contentChat = findViewById(R.id.rcv_chat);
        Intent intent = getIntent();


        User receivedUser = (User) intent.getSerializableExtra("userObject");


        String convention = UserCurrent.getInstance().getCurrentUser().getId() + receivedUser.getId();
        String convention2 = receivedUser.getId() + UserCurrent.getInstance().getCurrentUser().getId();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference chatRef = database.getReference("chat").child(convention);
        DatabaseReference chatref = database.getReference("chat").child(convention2);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = ed_content.getText().toString().trim();
                Chat chat = new Chat(UserCurrent.getInstance().getCurrentUser().getId(),content);
                chatRef.push().setValue(chat)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });

                chatref.push().setValue(chat).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        ed_content.setText("");
                    }
                });

            }
        });

        chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatList = new ArrayList<>();
                for (DataSnapshot chatSnapshot : snapshot.getChildren()) {
                    Chat chat1 = chatSnapshot.getValue(Chat.class);
                    chatList.add(chat1);
                }
                ChatAdapter chatAdapter = new ChatAdapter(ChatDetails.this,chatList);
                rcv_contentChat.setAdapter(chatAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}