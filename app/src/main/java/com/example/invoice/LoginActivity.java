package com.example.invoice;

import androidx.appcompat.app.AppCompatActivity;
import com.example.invoice.users;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    //声明控件
    private Button mbt_register;
    private Button mbt_login;
    private EditText met_user;
    private EditText met_password;
    private sqliteopenhelper sqliteopenhelper2;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mbt_register = findViewById(R.id.bt_register);//找到控件
        mbt_login = findViewById(R.id.bt_login);
        met_user = findViewById(R.id.et_user);
        met_password = findViewById(R.id.et_password);
        sqliteopenhelper2 = new sqliteopenhelper(this);
        /**点击注册则跳转到注册界面**/
        mbt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        /**点击登录**/
        mbt_login.setOnClickListener(this::Login);
    }
    private void Login(View v)
    {
        String username = met_user.getText().toString();
        String  pword= met_password.getText().toString();
        boolean login = sqliteopenhelper2.login(username, pword);
        Intent intent = null;
        if(login){
            intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "身份验证错误，禁止访问", Toast.LENGTH_SHORT).show();
        }
    }
}