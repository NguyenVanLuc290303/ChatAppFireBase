package com.example.appchat_mob403;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.appchat_mob403.DTO.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    TextInputEditText ed_email,ed_name,ed_password;
    Button btn_register;
    private FirebaseAuth mAuth;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed_email = findViewById(R.id.ed_email);
        ed_name = findViewById(R.id.ed_name);
        ed_password = findViewById(R.id.ed_password);
        btn_register = findViewById(R.id.btn_register);
        mAuth = FirebaseAuth.getInstance();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = ed_email.getText().toString().trim();
                String name = ed_name.getText().toString().trim();
                String password = ed_password.getText().toString().trim();



                mAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Log.d("User", "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    String userID = mAuth.getUid();
                                    addToRealtimeDatabase(userID, name, email ,password);
                                }else {
                                    Log.w("User", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

//                mAuth.createUserWithEmailAndPassword(email, password)
//                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful()) {
//                                    // Sign in success, update UI with the signed-in user's information
//                                    Log.d("User", "createUserWithEmail:success");
//                                    FirebaseUser user = mAuth.getCurrentUser();
//                                    String userID = mAuth.getUid();
//                                    addToRealtimeDatabase(userID, name, email);
//
//                                } else {
//                                    // If sign in fails, display a message to the user.
//                                    Log.w("User", "createUserWithEmail:failure", task.getException());
//                                    Toast.makeText(MainActivity.this, "Authentication failed.",
//                                            Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });

//                signUP(email,name,password);

            }
        });
    }


    private void addToRealtimeDatabase(String userID, String name, String email, String password) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("users");
        User newUser = new User(userID ,email, name, password);
        usersRef.child(userID).setValue(newUser)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Thêm thành công
                            Toast.makeText(getApplicationContext(), "User added to Database",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this,Login.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // Thêm thất bại
                            Toast.makeText(getApplicationContext(), "Failed to add user to Database",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

}