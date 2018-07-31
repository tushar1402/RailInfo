package com.myproject.railinfo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference users;

    EditText password,usernaam,email;
    Button create,toLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseDatabase = FirebaseDatabase.getInstance();
        users = firebaseDatabase.getReference("Users");

        password = findViewById(R.id.et_password);
        usernaam = findViewById(R.id.et_username);
        toLogin = findViewById(R.id.logging);
        email = findViewById(R.id.et_email);

        create = findViewById(R.id.createaccount);

        toLogin.setOnClickListener(new View.OnClickListener() {{}
            @Override
            public void onClick(View v) {
                signIn(usernaam.getText().toString(),
                        password.getText().toString());
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    private void signIn(final String username, final String password) {

        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(username).exists()){
                    if(!username.isEmpty()){
                        User login = dataSnapshot.child(username).getValue(User.class);
                        if(login.getPassword().equals(password)){
                            Toast.makeText(Login.this,"Success Login",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(Login.this,"Username or Password Wrong",Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Toast.makeText(Login.this,"Username not registered",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
