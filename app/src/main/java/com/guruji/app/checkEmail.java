package com.guruji.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class checkEmail extends AppCompatActivity {

    private FirebaseAuth mAuth;
    String email, password;
    Boolean emailchecker = false;
    Button button;
    TextView emailtitle, emaildesc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_email);
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");
        button = findViewById(R.id.buttonLoginemail);
        emailtitle = findViewById(R.id.emailTitle);
        emaildesc = findViewById(R.id.emailmsg);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setText("Login");
                emailtitle.setText("We have sent you an Email!");

                emaildesc.setText("Kindly verify your email to complete your login process");

                mAuth = FirebaseAuth.getInstance();
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    user.sendEmailVerification();
                                    if(user.isEmailVerified()){

                                        startActivity(new Intent(checkEmail.this, Home.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                        finish();
                                    }else{
                                        Boolean emailchecker = true;
                                        Toast.makeText(checkEmail.this, "Check your email to verify account!", Toast.LENGTH_LONG).show();

                                    }

                                }else{
                                    Toast.makeText(checkEmail.this, "User Sign In failed! Try again", Toast.LENGTH_LONG).show();

                                }

                            }
                        });
            }
        });




    }
}