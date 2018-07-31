package com.myproject.railinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.Query;



public class MainActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference users;

    EditText email,password,usernaam;
    Button signup,toSignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseDatabase = FirebaseDatabase.getInstance();
        users = firebaseDatabase.getReference("Users");

        email =  findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        usernaam = findViewById(R.id.et_username);
        signup = findViewById(R.id.login);
        toSignin = findViewById(R.id.signin);

        toSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Login.class);
                startActivity(i);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final User user = new User(usernaam.getText().toString(),
                        password.getText().toString(),
                        email.getText().toString());

                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(user.getUser()).exists()) {
                            Toast.makeText(MainActivity.this, "Username already exists",Toast.LENGTH_LONG).show();
                        } else{
                            users.child(user.getUser()).setValue(user);
                            Toast.makeText(MainActivity.this, "Success Registered", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {


                    }
                });
            }
        });

    }


}


