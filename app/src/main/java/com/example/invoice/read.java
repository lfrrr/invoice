package com.example.invoice;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.invoice.database.userdata;
import com.example.invoice.enity.invoicemessage;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class read extends AppCompatActivity {
    private userdata musedata;
    private InvoiceAdapter mAdapter;
    private List<invoicemessage> mInvoiceList,invoices;
    private ListView listview1;
    private TextView textView;
    private Button btn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
//        findViewById(R.id.btn_1).setOnClickListener(this);
        listview1 = findViewById(R.id.liner1);
        musedata=userdata.getInstance(this);
        musedata.openReadLink();
        musedata.openWriteLink();
        invoices = new ArrayList<>();
        invoices=musedata.selectbystate(1);
        float price=musedata.pricebystate(1);
        int size=invoices.size();
        DecimalFormat df = new DecimalFormat("#.00");
        String str = df.format(price);
        String show="共有发票 "+size+"张，共 "+str+" 元";
        textView=findViewById(R.id.text2);
        textView.setText(show);
        mInvoiceList = invoices;
        mAdapter = new InvoiceAdapter(this, mInvoiceList);
        //获取ListView并绑定Adapter
        listview1.setAdapter(mAdapter);
        btn=findViewById(R.id.btn_1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                TextView codeTextView = (TextView) view.findViewById(R.id.code_text_view);
                String code = codeTextView.getText().toString(); // 获取code值
                String newString = code.substring(5);
                AlertDialog.Builder builder = new AlertDialog.Builder(read.this);
                builder.setMessage("确定要删除发票代码为"+newString+"的发票吗？");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 使用code值从数据库中删除对应的数据
                        if(musedata.delectbycode(newString,1)>0){
                            Toast.makeText(getApplicationContext(),"删除成功",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"删除失败，这张发票已被移除",Toast.LENGTH_SHORT).show();
                        }
                        invoices=musedata.selectbystate(1);
                        float price=musedata.pricebystate(1);
                        int size=invoices.size();
                        DecimalFormat df = new DecimalFormat("#.00");
                        String str = df.format(price);
                        String show="共有发票 "+size+"张，共 "+str+" 元";
                        textView.setText(show);
                        mInvoiceList = invoices;
                        update();
                        listview1.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("No", null);
                builder.show();
            }
        });
    }
    public void update(){
        mAdapter = new InvoiceAdapter(this, mInvoiceList);
    }
}