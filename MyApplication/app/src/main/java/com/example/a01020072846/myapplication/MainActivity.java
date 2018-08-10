package com.example.a01020072846.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();

        Button btnItem = (Button) findViewById(R.id.btn_item);
        Button btnMyPage = (Button) findViewById(R.id.btn_my_page);

        btnMyPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        btnMyPage.setOnClickListener(new View.OnClickListener() {
            Intent intent = getIntent();
            Intent updateIntent = null;
            String id = null;

            @Override
            public void onClick(View v) {
                id = intent.getStringExtra("id");
                updateIntent = new Intent(MainActivity.this, InfoUpdateActivity.class);

                updateIntent.putExtra("id", id);
                Toast.makeText(MainActivity.this, id, Toast.LENGTH_SHORT).show();
                startActivity(updateIntent);
            }
        });
    }
}
