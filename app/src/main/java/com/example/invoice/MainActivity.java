package com.example.invoice;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.quick).setOnClickListener(this);
        findViewById(R.id.scan).setOnClickListener(this);
        findViewById(R.id.ready).setOnClickListener(this);
        findViewById(R.id.type).setOnClickListener(this);
        findViewById(R.id.unready).setOnClickListener(this);
        findViewById(R.id.check).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.quick:
                Intent intent = new Intent(MainActivity.this, quick.class);
                startActivity(intent);
                break;
            case R.id.scan:
                Intent intent1 = new Intent(MainActivity.this, scan.class);
                startActivity(intent1);
                break;
            case R.id.ready:
                Intent intent2 = new Intent(MainActivity.this, read.class);
                startActivity(intent2);
                break;
            case R.id.type:
                Intent intent3 = new Intent(MainActivity.this, input.class);
                startActivity(intent3);
                break;
            case R.id.unready:
                Intent intent4 = new Intent(MainActivity.this, unread.class);
                startActivity(intent4);
                break;
            case R.id.check:
                Intent intent6 = new Intent(MainActivity.this, check.class);
                startActivity(intent6);
                break;
        }
    }
}