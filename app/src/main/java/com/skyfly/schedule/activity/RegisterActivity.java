package com.skyfly.schedule.activity;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.skyfly.schedule.R;
import com.skyfly.schedule.utils.MD5Utils;

public class RegisterActivity extends Activity {
    private TextView tv_main_title; //标题
    private TextView tv_back;        //返回按钮
    private Button btn_register;    //注册按钮
    //用户名、密码、再次输入的密码的控件
    private EditText et_user_name,et_psw,et_psw_again;
    //用户名、密码、再次输入的密码的控件的获取值
    private String userName,psw,pswAgain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置页面布局
        setContentView(R.layout.activity_register);
        init();
    }
    private void init(){
        //从main_title_bar.xml页面布局中获得对应的UI控件
        tv_main_title=(TextView) findViewById(R.id.tv_main_title);
        tv_main_title.setText("注册");
        tv_back=(TextView) findViewById(R.id.tv_back);
        tv_back.setVisibility(View.VISIBLE);
        //从activity_register.xml页面布局中获得对应的UI控件
        btn_register=(Button) findViewById(R.id.btn_register);
        et_user_name=(EditText) findViewById(R.id.et_user_name);
        et_psw=(EditText) findViewById(R.id.et_psw);
        et_psw_again=(EditText) findViewById(R.id.et_psw_again);
        tv_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.this.finish();
            }
        });
        btn_register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入在相应控件中的字符串
                getEditString();
                if(TextUtils.isEmpty(userName)){
                    Toast.makeText(RegisterActivity.this, "请输入用户名",
                                      Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(psw)){
                    Toast.makeText(RegisterActivity.this, "请输入密码",
                                      Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(pswAgain)){
                    Toast.makeText(RegisterActivity.this, "请再次输入密码",
                                      Toast.LENGTH_SHORT).show();
                    return;
                }else if(!psw.equals(pswAgain)){
                    Toast.makeText(RegisterActivity.this, "输入两次的密码不一样",
                                      Toast.LENGTH_SHORT).show();
                    return;
                }else if(isExistUserName(userName)){
                    Toast.makeText(RegisterActivity.this, "此账户名已经存在",
                                      Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    Toast.makeText(RegisterActivity.this, "注册成功",
                                      Toast.LENGTH_SHORT).show();
                    //把用户名和密码保存到SharedPreferences中
                    saveRegisterInfo(userName, psw);
                    //注册成功后把用户名传递到LoginActivity.java中
                    Intent data =new Intent();
                    data.putExtra("userName", userName);
                    setResult(RESULT_OK, data);
                    RegisterActivity.this.finish();
                }
            }
        });
    }
    /**
     * 获取控件中的字符串
     */
    private void getEditString(){
        userName=et_user_name.getText().toString().trim();
        psw=et_psw.getText().toString().trim();
        pswAgain=et_psw_again.getText().toString().trim();
    }
    /**
     *从SharedPreferences中读取输入的用户名，判断SharedPreferences中是否有此用户名
     */
    private boolean isExistUserName(String userName){
        boolean has_userName=false;
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        String spPsw=sp.getString(userName, "");
        if(!TextUtils.isEmpty(spPsw)) {
            has_userName=true;
        }
        return has_userName;
    }
    /**
     * 保存用户名和密码到SharedPreferences中
     */
    private void saveRegisterInfo(String userName,String psw){
        String md5Psw=MD5Utils.md5(psw);           //把密码用MD5加密
        //loginInfo表示文件名
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        Editor editor=sp.edit();//获取编辑器
        //以用户名为key,密码为value保存到SharedPreferences中
        editor.putString(userName, md5Psw);
        editor.commit();//提交修改
    }
 }
