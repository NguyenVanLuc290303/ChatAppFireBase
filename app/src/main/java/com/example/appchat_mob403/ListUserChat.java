package com.example.appchat_mob403;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.appchat_mob403.DTO.User;
import com.example.appchat_mob403.adapter.UserAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListUserChat extends AppCompatActivity {
    RecyclerView rcv_listUser;
    UserAdapter userAdapter;

//    List<User> userList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user_chat);
        rcv_listUser = findViewById(R.id.rcv_userChat);
//        userAdapter = new UserAdapter(ListUserChat.this,userList);
//        rcv_listUser.setAdapter(userAdapter);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("users");

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<User> userList = new ArrayList<>();

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {

                    User user = userSnapshot.getValue(User.class);

                    if (user != null && !user.id.equals(currentUser.getUid())) {
                        userList.add(user);
                    }
                }

                // Hiển thị danh sách người dùng trong RecyclerView
                UserAdapter userAdapter = new UserAdapter(ListUserChat.this,userList);
                rcv_listUser.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
}