package com.example.invoice;

import androidx.appcompat.app.AppCompatActivity;
import com.example.invoice.users;

import android.app.ActionBar;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

public class RegisterActivity extends AppCompatActivity {

    private Button mbt_register2;
    private EditText met_password1;
    private EditText met_password2;
    private EditText met_user2;

    private sqliteopenhelper sqliteopenhelper1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sqliteopenhelper1 = new sqliteopenhelper(this);
        mbt_register2 = findViewById(R.id.bt_register2);
        met_user2 = findViewById(R.id.et_user2);
        met_password1 = findViewById(R.id.et_password2);
        met_password2 = findViewById(R.id.et_repassword2);
        mbt_register2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("my_prefs", MODE_PRIVATE);
                Intent intent= null;
                String username = met_user2.getText().toString();
                String pword1 = met_password1.getText().toString();
                String pword2 = met_password2.getText().toString();
                //若两次输入密码一致
                if(pword1.equals(pword2)){
                    users u = new users(username,pword1);
                    sqliteopenhelper1.register(u);
                    Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                    intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(RegisterActivity.this, "密码两次输入不一致，请重新输入！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}