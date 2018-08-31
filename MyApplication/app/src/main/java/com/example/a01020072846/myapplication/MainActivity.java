package com.example.a01020072846.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();

        Button btnItem = (Button) findViewById(R.id.btn_item);
        Button btnMyPage = (Button) findViewById(R.id.btn_my_page);
        Button importButton = (Button) findViewById(R.id.btn_import);
        Button exportButton = (Button) findViewById(R.id.btn_export);

        id = intent.getStringExtra("id");

        btnMyPage.setOnClickListener(new View.OnClickListener() {
            Intent updateIntent = null;

            @Override
            public void onClick(View v) {
                updateIntent = new Intent(MainActivity.this, InfoUpdateActivity.class);
                updateIntent.putExtra("id", id);
                Toast.makeText(MainActivity.this, id, Toast.LENGTH_SHORT).show();
                startActivity(updateIntent);
            }
        });

        btnItem.setOnClickListener(new View.OnClickListener() {
            Intent itemIntent = null;

            @Override
            public void onClick(View v) {
                itemIntent = new Intent(MainActivity.this, ItemListActivity.class);
                itemIntent.putExtra("id", id);
                startActivity(itemIntent);
            }
        });

        importButton.setOnClickListener(new View.OnClickListener() {
            Intent importIntent = null;

            @Override
            public void onClick(View v) {
                importIntent = new Intent(MainActivity.this, TransactionImportActivity.class);
                importIntent.putExtra("id", id);
                startActivity(importIntent);
            }
        });

        exportButton.setOnClickListener(new View.OnClickListener() {
            Intent exportIntent = null;

            @Override
            public void onClick(View v) {
                exportIntent = new Intent(MainActivity.this, TransactionExportActivity.class);
                exportIntent.putExtra("id", id);
                startActivity(exportIntent);
            }
        });
    }
}
