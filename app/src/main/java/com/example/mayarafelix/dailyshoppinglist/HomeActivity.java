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

public class HomeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Daily Shopping List");

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
            }
        });

        dialog.show();
    }
}
