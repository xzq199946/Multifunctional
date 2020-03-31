package com.example.myapplicationcalculator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class RegesiteActivity extends AppCompatActivity {

    Button ok, reset, back;
    EditText userName, userPassword;
    String stringUserName = "";
    String stringPassword = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_regesite);
        System.out.println("10011");
        ok = findViewById(R.id.RegesiteActivity_ok);
        reset = findViewById(R.id.RegesiteActivity_reset);
        back = findViewById(R.id.RegesiteActivity_back);
        userName = findViewById(R.id.RegesiteActivity_txtUserName);
        userPassword = findViewById(R.id.RegesiteActivity_txtUserPassword);
        System.out.println("10012");

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stringUserName = userName.getText().toString().trim();
                stringPassword = userPassword.getText().toString().trim();
                User user = new User(stringUserName, stringPassword);
                System.out.println("10013");
                DBHelper helper = new DBHelper(RegesiteActivity.this);
                System.out.println("10014");
                if(helper.insert(user)>0){
                    System.out.println("10015");
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegesiteActivity.this);
                    final AlertDialog alertDialog= builder.create();
                    alertDialog.setMessage("注册成功！");
                    alertDialog.show();
                    Intent intent = new Intent();
                    intent.setClass(RegesiteActivity.this , MainActivity.class);
                    startActivity(intent);
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegesiteActivity.this);
                    final AlertDialog alertDialog= builder.create();
                    alertDialog.setMessage("输入有误,请重新输入！");
                    alertDialog.show();
                    userName.setText("");
                    userPassword.setText("");
                }
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName.setText("");
                userPassword.setText("");
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(RegesiteActivity.this , MainActivity.class);
                startActivity(intent);
            }
        });



    }

}
