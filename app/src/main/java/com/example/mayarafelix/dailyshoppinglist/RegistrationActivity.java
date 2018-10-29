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

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText emailView;
    private EditText passwordView;
    private TextView signInLinkView;
    private Button registrationButton;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        // Views
        emailView = findViewById(R.id.activity_registration_email);
        passwordView = findViewById(R.id.activity_registration_password);
        signInLinkView = findViewById(R.id.activity_registration_signin_link);
        registrationButton = findViewById(R.id.activity_registration_button);

        // Click Listeners
        signInLinkView.setOnClickListener(this);
        registrationButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.activity_registration_signin_link) {
            launchMainActivity();
        } else if (v.getId() == R.id.activity_registration_button) {
            registerUser();
        }
    }

    private void registerUser() {
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

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    launchHomeActivity();
                    Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
    }

    private void launchMainActivity() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    private void launchHomeActivity() {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
    }
}
