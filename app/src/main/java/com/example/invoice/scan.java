package com.example.invoice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class scan extends AppCompatActivity {
    private Button scan1;
    private Button return1;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        scan1=findViewById(R.id.scanButton);
        return1=findViewById(R.id.return1);
        return1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        scan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(
                        scan.this
                );
                intentIntegrator.setPrompt("For flash use volume up key");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(Capture.class);
                intentIntegrator.initiateScan();

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult=IntentIntegrator.parseActivityResult(
                requestCode,resultCode,data
        );
        if (intentResult.getContents() !=null){
            AlertDialog.Builder builder=new AlertDialog.Builder(
                    scan.this
            );
            builder.setTitle("Result");
            String str=intentResult.getContents().toString();
            Intent intent = new Intent(this, showinvoice.class);
            intent.putExtra("str", str);
            startActivity(intent);
        }else {
            Toast.makeText(getApplicationContext(),"没有扫描任何东西",Toast.LENGTH_SHORT).show();
        }
    }
}