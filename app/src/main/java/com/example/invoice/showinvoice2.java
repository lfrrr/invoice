package com.example.invoice;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.invoice.database.userdata;
import com.example.invoice.enity.invoicemessage;

import java.util.ArrayList;
import java.util.List;

public class showinvoice2 extends AppCompatActivity {
    private String result;
    private TextView tv;
    private userdata musedata;
    private ListView listView;
    private InvoiceAdapter mAdapter;
    private List<invoicemessage> list1;
    private ArrayList<Integer> idlist;
    private Button btn1,btn2;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showinvoice2);
        getSupportActionBar().hide();//隐藏标题栏
        Intent intent = getIntent();
        musedata=userdata.getInstance(this);
        musedata.openReadLink();
        musedata.openWriteLink();
        result=intent.getStringExtra("result");
        list1 = (List<invoicemessage>) getIntent().getSerializableExtra("list");
        idlist=getIntent().getIntegerArrayListExtra("id");
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("尊敬的用户");
        builder.setMessage(result);
        builder.setPositiveButton("确定",null);
        AlertDialog dialog=builder.create();
        dialog.show();
        mAdapter = new InvoiceAdapter(this, list1);
        //获取ListView并绑定Adapter
        listView = findViewById(R.id.liner1);
        listView.setAdapter(mAdapter);
        tv=findViewById(R.id.tv1);
        tv.setText(result);
        btn1=findViewById(R.id.sure);
        btn2=findViewById(R.id.return1);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int flag=1;
                for (Integer item : idlist) {
                    if(musedata.updatebyid(item)>0){
                         flag=flag*flag;
                    }
                    else{
                        flag=0;
                    }
                }
                if(flag!=0){
                    Toast.makeText(getApplicationContext(),"移动成功！",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"移动失败！",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}