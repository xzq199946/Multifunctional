package com.example.myapplicationcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnLongClickListener {

    Button btnOk, btnCancel,btnInfo, btnRegiste;
    TextView txtShow;
    EditText txtCount = null, txtPassword=null;
    CheckBox cb_autologin, cb_rb_password,cb_for_password;
    private SharedPreferences spSetting = null;
    private static final String PREFS_NAME = "123";

    static final String[] LOCATIONGPS = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE};


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        getCompoent();
        setListener();
        getData();
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PERMISSION_GRANTED) {// 没有权限，申请权限。
            ActivityCompat.requestPermissions(this, LOCATIONGPS,1);


        }

    }
    protected void onResume(){
        super.onResume();
        getData();
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnOk://查询功能
//                if(txtCount == null || txtPassword == null){
//                    Toast.makeText(MainActivity.this,"请输入用户名和密码",Toast.LENGTH_LONG).show();
//                }else{
                    DBHelper helper = new DBHelper(MainActivity.this);
                    User user = helper.query(txtCount.getText().toString().trim());
                    if(user.getUsername().equals(txtCount.getText().toString().trim())&&user.getPassword().
                            equals(txtPassword.getText().toString().trim())){
                        if(cb_rb_password.isChecked()){
                            spSetting =getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                            SharedPreferences.Editor edit = spSetting.edit();
                            edit.putBoolean("isKeep", true);
                            edit.putString("username", txtCount.getText().toString().trim());
                            edit.putString("password", txtPassword.getText().toString().trim());
                            edit.apply();
                        }else {
                            spSetting =getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                            SharedPreferences.Editor edit = spSetting.edit();
                            edit.putBoolean("isKeep", false);
                            edit.putString("username", "");
                            edit.putString("password", "");
                            edit.apply();
                        }
                        Toast.makeText(MainActivity.this,"登录成功",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this,MainMenuActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(MainActivity.this,"用户名或密码错误",Toast.LENGTH_LONG).show();
                        txtCount.setText("");
                        txtPassword.setText("");
                    }
//                }
                break;
            case R.id.btnCancel:
                Toast.makeText(MainActivity.this,"退出",Toast.LENGTH_LONG).show();
                finish();
                break;
            case R.id.btnInfo:
                Toast.makeText(MainActivity.this,"第一组\n201852180328谢志强软件三班\n201852180328付豪软件三班\n201852180301崔智博软件三班\n201852180315张越翔软件三班",Toast.LENGTH_LONG).show();
                break;
            case R.id.btnRegiste:
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,RegesiteActivity.class);
                startActivity(intent);
                break;
        }
    }
    public boolean onLongClick(View view){
        if(view.getId() == R.id.btnRegiste){
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,RegesiteActivity.class);
            startActivity(intent);
        }
        return false;
    }
    public void getData(){
        spSetting = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if(spSetting.getBoolean("isKeep", false)){
            txtCount.setText(spSetting.getString("username",""));
            txtPassword.setText(spSetting.getString("password",""));
        }else {
            txtCount.setText("");
            txtPassword.setText("");
        }
    }
    public void setListener(){
        btnOk.setOnClickListener(this);
        btnInfo.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnRegiste.setOnClickListener(this);
        btnRegiste.setOnLongClickListener(this);
        cb_for_password.setOnClickListener(this);
    }
    public void getCompoent(){
        txtShow = findViewById(R.id.txtShow);
        txtCount = findViewById(R.id.txtCount);
        txtPassword = findViewById(R.id.txtPassword);
        btnOk = findViewById(R.id.btnOk);
        btnCancel = findViewById(R.id.btnCancel);
        btnInfo = findViewById(R.id.btnInfo);
        btnRegiste = findViewById(R.id.btnRegiste);
        cb_autologin = findViewById(R.id.cb_autologin);
        cb_rb_password = findViewById(R.id.cb_remeberpassword);
        cb_for_password = findViewById(R.id.cb_forgetpassword);
    }

}
