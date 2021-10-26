
package com.example.UserData;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gallery.HomePage;
import com.example.gallery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText user_email, user_password;
    private String email , password;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button RegisterUser = (Button)findViewById(R.id.btnRegister);
        Button LoginUser = (Button)findViewById(R.id.btnLogin);

        user_email = findViewById(R.id.editTextemail);
        user_password = findViewById(R.id.editTextTextPassword);
        mAuth = FirebaseAuth.getInstance();


        LoginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = user_email.getText().toString();
                password = user_password.getText().toString();
                if (email != null && password != null) {
               signin_user();

                } else {
                    Toast.makeText(MainActivity.this, "Please Enter Ddata",Toast.LENGTH_LONG).show();

                }
            }});




        RegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Registration.class);
                startActivity(i);

            }
        });

    }


    public void  signin_user() {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "signIn:success",Toast.LENGTH_LONG).show();

                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent i = new Intent(MainActivity.this, HomePage.class);
                            startActivity(i);


                        } else {

                            Toast.makeText(MainActivity.this, "signIn:failure",Toast.LENGTH_LONG).show();
                        }
                    }
                });


    }


}