package com.ausoccer.ausoccerintramurlasadmin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

        EditText email, password;
        Button loginButton;
        FirebaseAuth firebaseAuth;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            email = findViewById(R.id.email_edit_text);
            password = findViewById(R.id.password_edit_text);
            loginButton = findViewById(R.id.login_button);

            firebaseAuth = FirebaseAuth.getInstance();

            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    loginAction();
                }
            });
        }

        // This method handles user login and authentication.
        public void loginAction() {

            String emailInput = email.getText().toString().trim();
            String passwordInput = password.getText().toString().trim();

            if (TextUtils.isEmpty(emailInput)) {
                Toast.makeText(this, "Please enter an email.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(passwordInput)) {
                Toast.makeText(this, "Please enter your password.", Toast.LENGTH_SHORT).show();
                return;
            }

            firebaseAuth.signInWithEmailAndPassword(emailInput, passwordInput).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Successfully logged in", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid credentials or no connection to the internet.", Toast.LENGTH_SHORT).show();


                    }

                }
            });

        }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is already logged in.
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        } else {
            // User is not logged in.
        }
    }
}

