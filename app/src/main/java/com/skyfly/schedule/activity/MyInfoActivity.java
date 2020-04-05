package com.skyfly.schedule.activity;
import com.skyfly.schedule.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
public class MyInfoActivity extends Activity {
	private RelativeLayout rl_title_bar;
	private RelativeLayout rl_setting;
	private TextView tv_user_name,tv_back;
	private ImageView iv_head;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myinfo);
		init();
	}

	private void init() {
		rl_setting = (RelativeLayout) findViewById(R.id.rl_setting);
		rl_title_bar=(RelativeLayout) findViewById(R.id.title_bar);
		tv_user_name = (TextView) findViewById(R.id.tv_user_name);
		rl_title_bar.setBackgroundColor(Color.TRANSPARENT);
		tv_back=(TextView) findViewById(R.id.tv_back);
		tv_back.setVisibility(View.VISIBLE);
		iv_head=(ImageView) findViewById(R.id.iv_head_icon);
		tv_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MyInfoActivity.this.finish();
			}
		});
		if (readLoginStatus()) {
			tv_user_name.setText(readLoginUserName());
		} else {
			tv_user_name.setText("点击登录");
		}
		iv_head.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 判断是否已经登录
				if (readLoginStatus()) {
					// 已登录跳转到个人资料界面
					Intent intent = new Intent(MyInfoActivity.this, UserInfoActivity.class);
					startActivity(intent);
				} else {
					// 未登录跳转到登录界面
					Intent intent = new Intent(MyInfoActivity.this, LoginActivity.class);
					startActivityForResult(intent, 1);
				}
			}
		});
		tv_user_name.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 判断是否已经登录
				if (readLoginStatus()) {
					// 已登录跳转到个人资料界面
					Intent intent = new Intent(MyInfoActivity.this, UserInfoActivity.class);
					startActivity(intent);
				} else {
					// 未登录跳转到登录界面
					Intent intent = new Intent(MyInfoActivity.this, LoginActivity.class);
					startActivityForResult(intent, 1);
				}
			}
		});
		rl_setting.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (readLoginStatus()) {
					// 跳转到设置界面
					 Intent intent=new Intent(MyInfoActivity.this,SettingActivity.class);
					 startActivityForResult(intent,1);
				} else {
					Toast.makeText(MyInfoActivity.this, "您还未登录，请先登录", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});
	}

	/**
	 * 从SharedPreferences中读取登录用户名
	 */
	private String readLoginUserName() {
		SharedPreferences sp = getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
		String userName = sp.getString("loginUserName", "");// 获取登录时的用户名
		return userName;
	}

	/**
	 * 从SharedPreferences中读取登录状态
	 */
	private boolean readLoginStatus() {
		SharedPreferences sp = getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
		boolean isLogin = sp.getBoolean("isLogin", false);
		return isLogin;
	}
	 /**
     * 登录成功后设置我的界面
     */
    public void setLoginParams(boolean isLogin){
        if(isLogin){
            tv_user_name.setText(readLoginUserName());
        }else{
            tv_user_name.setText("点击登录");
        }
    }
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            //从设置界面或登录界面传递过来的登录状态
            boolean isLogin=data.getBooleanExtra("isLogin",false);
           setLoginParams(isLogin);
        }
    }
}
