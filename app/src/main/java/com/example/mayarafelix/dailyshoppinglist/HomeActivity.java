package com.example.mayarafelix.dailyshoppinglist;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mayarafelix.dailyshoppinglist.model.Data;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private DatabaseReference database;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Daily Shopping List");

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String id = firebaseUser.getUid();
        database = FirebaseDatabase.getInstance().getReference().child("Shopping List");

        fab = findViewById(R.id.activity_home_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchInputDialog();
            }
        });
    }

    private void launchInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        LayoutInflater inflater = LayoutInflater.from(HomeActivity.this);
        View view = inflater.inflate(R.layout.input_data, null);

        final AlertDialog dialog = builder.create();
        dialog.setView(view);

        final EditText typeView = view.findViewById(R.id.input_data_type);
        final EditText amountView = view.findViewById(R.id.input_data_amount);
        final EditText noteView = view.findViewById(R.id.input_data_note);
        Button buttonSave = view.findViewById(R.id.btn_save);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                String type = typeView.getText().toString().trim();
                String amount = amountView.getText().toString().trim();
                String note = noteView.getText().toString().trim();

                int amountInt = Integer.parseInt(amount);

                if (TextUtils.isEmpty(type)) {
                    typeView.setError("Required Field");
                    return;
                }

                if (TextUtils.isEmpty(amount)) {
                    amountView.setError("Required Field");
                    return;
                }

                if (TextUtils.isEmpty(note)) {
                    noteView.setError("Required Field");
                    return;
                }

                String id = database.push().getKey();
                String date = DateFormat.getDateInstance().format(new Date());

                Data data = new Data(id, type, amountInt, note, date);

                database.child(id).setValue(data);
                
                Toast.makeText(getApplicationContext(), "Data Added", Toast.LENGTH_SHORT).show();

            }
        });

        dialog.show();
    }
}
