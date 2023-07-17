package com.example.invoice;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.invoice.database.userdata;
import com.example.invoice.enity.invoicemessage;

public class showinvoice extends AppCompatActivity{
    private TextView code1,number1,time1,price1;
    private Button button,button1,save2;
    private RadioGroup radioGroup;
    private String category1;
    private userdata musedata;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showinvoice);
        getSupportActionBar().hide();//隐藏标题栏
        Intent intent = getIntent();
        String str1 = intent.getStringExtra("str");
        String[] strArray = str1.split(",");
        code1=findViewById(R.id.code);
        number1=findViewById(R.id.number);
        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton1 = findViewById(R.id.radioButton1);
                RadioButton radioButton2 = findViewById(R.id.radioButton2);
                RadioButton radioButton3 = findViewById(R.id.radioButton3);
                RadioButton radioButton4 = findViewById(R.id.radioButton4);
                if (checkedId == R.id.radioButton1) {
                    radioButton2.setChecked(false);
                    radioButton3.setChecked(false);
                    radioButton4.setChecked(false);
                    category1="交通";
                } else if (checkedId == R.id.radioButton2) {
                    radioButton1.setChecked(false);
                    radioButton3.setChecked(false);
                    radioButton4.setChecked(false);
                    category1="差旅";
                } else if (checkedId == R.id.radioButton3) {
                    radioButton1.setChecked(false);
                    radioButton2.setChecked(false);
                    radioButton4.setChecked(false);
                    category1="耗材";
                } else if (checkedId == R.id.radioButton4) {
                    radioButton1.setChecked(false);
                    radioButton2.setChecked(false);
                    radioButton3.setChecked(false);
                    category1="招待";
                }
            }
        });
        time1=findViewById(R.id.time);
        price1=findViewById(R.id.price);
        code1.setText(strArray[2]);
        number1.setText(strArray[3]);
        price1.setText(strArray[4]);
        time1.setText(strArray[5]);
        save2=findViewById(R.id.save1);
        musedata = userdata.getInstance(this);
        musedata.openReadLink();
        musedata.openWriteLink();
        save2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                invoicemessage textin = new invoicemessage();
                textin.time=strArray[5];
                float floatValue = Float.parseFloat(strArray[4]);
                textin.price=floatValue;
                textin.code=strArray[2];

                textin.category=category1;
                textin.number=strArray[3];
                int state=0;
                textin.state=state;
                if (musedata.save(textin)>0){
                    Toast.makeText(getApplicationContext(),"保存成功",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"保存失败,请选择发票种类",Toast.LENGTH_SHORT).show();
                }
            }
        });
        button=findViewById(R.id.return1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        button1=findViewById(R.id.return2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(showinvoice.this,MainActivity.class);
                startActivity(intent1);
            }
        });
    }
    protected void onDestroy(){
        super.onDestroy();
        musedata.closeLink();
    }
}