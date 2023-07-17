package com.example.invoice;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.invoice.database.userdata;
import com.example.invoice.enity.invoicemessage;

import java.util.Calendar;

public class input extends AppCompatActivity implements View.OnClickListener {
    private userdata musedata;
    private EditText timetext,numbertext,codetext,pricetext;
    private RadioGroup radioGroup;
    String category1;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        findViewById(R.id.getin).setOnClickListener(this);
        findViewById(R.id.return1).setOnClickListener(this);
        timetext=findViewById(R.id.time);
        numbertext=findViewById(R.id.number);
        codetext=findViewById(R.id.code);
        pricetext=findViewById(R.id.price);
        musedata = userdata.getInstance(this);
        musedata.openReadLink();
        musedata.openWriteLink();
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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.return1:
                finish();
                break;
            case R.id.getin:
                invoicemessage textin = new invoicemessage();
                textin.time=timetext.getText().toString();
                String str = pricetext.getText().toString();
                float floatValue = Float.parseFloat(str);
                textin.price=floatValue;
                textin.code=codetext.getText().toString();
                textin.category=category1;
                textin.number=numbertext.getText().toString();
                textin.state=0;
                if (musedata.save(textin)>0){
                    AlertDialog.Builder builder=new AlertDialog.Builder(this);
                    builder.setTitle("尊敬的用户");
                    builder.setMessage("保存成功");
                    builder.setPositiveButton("确定",null);
                    AlertDialog dialog=builder.create();
                    dialog.show();
                }
                break;
        }
    }
    protected void onDestroy(){
        super.onDestroy();
        musedata.closeLink();
    }
}