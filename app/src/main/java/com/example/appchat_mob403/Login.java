package com.example.appchat_mob403;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appchat_mob403.DTO.User;
import com.example.appchat_mob403.DTO.UserCurrent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    TextInputEditText ed_email,ed_password;
    Button btn_login;
    TextView tv_register;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ed_email = findViewById(R.id.ed_email);
        ed_password = findViewById(R.id.ed_password);
        btn_login = findViewById(R.id.btn_login);
        tv_register = findViewById(R.id.tv_register);
        mAuth = FirebaseAuth.getInstance();

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = ed_email.getText().toString().trim();
                String password = ed_password.getText().toString().trim();
                signIN(email,password);

            }
        });
    }

    private void signIN(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Login", "signInWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
                              String userID = mAuth.getUid();


                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference usersRef = database.getReference("users").child(userID);

                            usersRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    // Xử lý dữ liệu khi nó thay đổi
                                    if (dataSnapshot.exists()) {
                                        // Dữ liệu tồn tại
                                        User user = dataSnapshot.getValue(User.class);
                                        UserCurrent.getInstance().setCurrentUser(user);
                                        Intent intent = new Intent(Login.this,ListUserChat.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    // Xử lý khi có lỗi xảy ra trong quá trình đọc dữ liệu
                                }
                            });



                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Login", "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}