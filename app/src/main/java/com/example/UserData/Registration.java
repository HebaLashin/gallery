package com.example.UserData;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gallery.HomePage;
import com.example.gallery.R;
import com.example.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.widget.Toast.*;

public class Registration extends AppCompatActivity {

    private EditText Et_email;
    private EditText ET_name;
    private EditText ET_password;
    private Button   register;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private static final String USERS = "users";
    private User user;
    private FirebaseAuth mAuth;
    private String person_name, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        Et_email = findViewById(R.id.eT_EmailAddress);
        ET_name  = findViewById(R.id.eT_PersonName);
        ET_password = findViewById(R.id.eTPassword);
        register = findViewById(R.id.btn_register);

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference(USERS);
        mAuth = FirebaseAuth.getInstance();



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                person_name = ET_name.getText().toString();
                email = Et_email.getText().toString();
                password = ET_password.getText().toString();
                if(email!= null && person_name != null && password!=null) {
                   user = new User(email,person_name,password);
                    registerUser();
                }
            }
        });

    }

    public void registerUser() {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            makeText(Registration.this, "success",LENGTH_LONG).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            makeText(Registration.this, "failure",LENGTH_LONG).show();

                        }
                    }
                });
    }

    public void updateUI(FirebaseUser currentUser) {
        String keyid = mDatabase.push().getKey();
        mDatabase.child(keyid).setValue(user);
        Intent loginIntent = new Intent(this, HomePage.class);
        startActivity(loginIntent);
    }
}
