package com.example.invoice;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.invoice.database.MyElement;
import com.example.invoice.database.userdata;
import com.example.invoice.enity.invoicemessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class quick extends AppCompatActivity {

    private static userdata musedata;
    private TextView tv;
    private CheckBox checkBox_1,checkBox_2,checkBox_3,checkBox_4;
    private ListView listView;
    private InvoiceAdapter mAdapter;
    private ArrayList<String> selectedOptions;
    private Button btn1,btn2;
    private EditText text;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick);
        musedata=userdata.getInstance(this);
        musedata.openReadLink();
        musedata.openWriteLink();
        tv = findViewById(R.id.tv1);
        text=findViewById(R.id.ed1);
        btn1=findViewById(R.id.sure);
        checkBox_1=findViewById(R.id.checkbox1);
        checkBox_2=findViewById(R.id.checkbox2);
        checkBox_3=findViewById(R.id.checkbox3);
        checkBox_4=findViewById(R.id.checkbox4);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str=text.getText().toString();
                float sum=Float.parseFloat(str);
               selectedOptions = new ArrayList<>();
                if (checkBox_1.isChecked()) {
                    selectedOptions.add("交通");
                }
                if (checkBox_2.isChecked()) {
                    selectedOptions.add("差旅");
                }
                if (checkBox_3.isChecked()) {
                    selectedOptions.add("耗材");
                }
                if (checkBox_4.isChecked()){
                    selectedOptions.add("招待");
                }
                if (selectedOptions.isEmpty())
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(quick.this);
                    builder.setTitle("警告");
                    builder.setMessage("您未选择报销类型，请选择至少一个报销类别");
                    builder.setPositiveButton("确定", null);
                    builder.show();
                }
                else{
                    excel_MC_combination(sum);
                }
            }
        });
        btn2=findViewById(R.id.return1);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void excel_MC_combination(float suma) {
        List<MyElement> data = new ArrayList<>();
        for(String item:selectedOptions){
            data = musedata.selectallstate(0,item);
        }
        ArrayList<Float> priceList = new ArrayList<>();
        ArrayList<Integer> idList = new ArrayList<>();
        ArrayList<Integer> idlist1 = new ArrayList<>();
        for (MyElement element : data) {
            priceList.add(element.getPrice());
            idList.add(element.getId());
        }
        int lendata = priceList.size();
        List<Float> resdata = new ArrayList<>();
        double resSt = 0.0; // 组合加和最大值
        double resErr = 10000.0;
        int flag = 0; // 用来判断是否有可以报销的发票
        Random rand = new Random();
        ArrayList<invoicemessage> list1 = new ArrayList<>();
        for (int i = 0; i < 20000; i++) { // 把0到19999依次赋给i，即重复20000次
            List<Float> data1 = new ArrayList<>(priceList);
            List<Float> data2 = new ArrayList<>();
            int k = rand.nextInt(lendata) + 1; // 从给定的范围返回随机项（1，lendata+1)
            // // 从list中随机获取k个元素，作为一个片断返回赋给data2
            for (int j = 0; j < k; j++) {
                int randomIndex = rand.nextInt(data1.size());
                data2.add(data1.get(randomIndex));
                data1.remove(randomIndex);
            }
            float sumt = data2.stream().reduce(Float::sum).orElse((float) 0);
            double ds = suma - sumt; // ds为给定值和组合最大值的差
            if (ds < 0) {
                continue;
            } else {
                flag = 1;
                if (ds < resErr) {
                    resErr = ds;
                    resdata = data2;
                    resSt = sumt;
                if (ds < 0.0000001) {
                        break;
                    }
                }
            }
        }
        int index = 0;
        int len = resdata.size();
        for (int m = 0; m < len; m++) {
            Float pri = resdata.get(m);
            int k1=0;
            for(k1=0;k1<lendata;k1++)
            {
                if(priceList.get(k1)==pri)
                {
                    index = k1;
                    break;}
            }
            idlist1.add(idList.get(index).intValue());
            invoicemessage invoice = musedata.selectbyid(idList.get(index).intValue());
            list1.add(invoice);
        }
        String result;
        if (flag == 0) {
            result = "根据给定金额，暂无可以报销的发票！";
            int num = 0;
            tv.setText(result);
        } else {
            result = "共筛选出" + resdata.size() + "张发票，总金额为：" + Math.round(resSt * 100.0) / 100.0 + "，总金额与给定值相差：" + Math.round(resErr);
            Intent intent = new Intent(quick.this, showinvoice2.class);
            intent.putExtra("list", (Serializable) list1);
            intent.putExtra("result", result);
            intent.putIntegerArrayListExtra("id", idlist1);
            startActivity(intent);
        }
    }
}
