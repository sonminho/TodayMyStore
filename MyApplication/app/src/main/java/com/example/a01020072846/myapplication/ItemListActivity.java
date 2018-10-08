package com.example.a01020072846.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


public class ItemListActivity extends AppCompatActivity {

    ImportFragment importFragment;
    ExportFragment exportFragment;

    Button importButton, exportButton;
    ImageButton plusButton, minusButton;

    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        importFragment = new ImportFragment(this, id);
        exportFragment = new ExportFragment(this, id);

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_container, importFragment)
                    .commit();
        }

        importButton = (Button)findViewById(R.id.importBtn);
        exportButton = (Button)findViewById(R.id.exportBtn);
        plusButton = (ImageButton)findViewById(R.id.plus_btn);
        minusButton = (ImageButton)findViewById(R.id.minus_btn);

        importButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                importButton.setBackgroundColor(Color.WHITE);
                importButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.backgroundColor));
                exportButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.backgroundColor));
                exportButton.setTextColor(Color.WHITE);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.item_container, importFragment)
                        .commit();
            }
        });

        exportButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                exportButton.setBackgroundColor(Color.WHITE);
                exportButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.backgroundColor));
                importButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.backgroundColor));
                importButton.setTextColor(Color.WHITE);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.item_container, exportFragment)
                        .commit();
            }
        });

    }
}
