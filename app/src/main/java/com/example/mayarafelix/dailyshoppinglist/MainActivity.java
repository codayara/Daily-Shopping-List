package com.example.mayarafelix.dailyshoppinglist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnCompleteListener {

    private EditText emailView;
    private EditText passwordView;
    private TextView signUpLinkView;
    private Button loginButton;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        // Views
        emailView = findViewById(R.id.activity_main_email);
        passwordView = findViewById(R.id.activity_main_password);
        signUpLinkView = findViewById(R.id.activity_main_signup_link);
        loginButton = findViewById(R.id.activity_main_login_button);

        // Click Listeners
        signUpLinkView.setOnClickListener(this);
        loginButton.setOnClickListener(this);

        // Set Focus
        setFocusToTitle();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main_signup_link:
                launchRegistrationActivity();
                break;
            case R.id.activity_main_login_button:
                login();
                break;
        }
    }

    private void login() {
        final String email = emailView.getText().toString().trim();
        final String password = passwordView.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            emailView.setError("Required Field");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordView.setError("Required Field");
            return;
        }

        progressDialog.setMessage("Processing...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this);
    }

    private void launchRegistrationActivity() {
        startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
    }

    private void launchHomeActivity() {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
    }

    @Override
    public void onComplete(@NonNull Task task) {
        if (task.isSuccessful()) {
            launchHomeActivity();
            Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        } else {
            Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }

    private void setFocusToTitle() {
        final TextView titleView = findViewById(R.id.activity_main_title);
        if (titleView != null) {
            titleView.requestFocus();
        }
    }
}
