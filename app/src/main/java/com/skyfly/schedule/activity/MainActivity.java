package com.skyfly.schedule.activity;
import com.skyfly.schedule.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView tv_main_title;
	private ImageView iv_info;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}
	private void init() {
		tv_main_title = (TextView) findViewById(R.id.tv_main_title);
		iv_info = (ImageView) findViewById(R.id.iv_info);
		iv_info.setVisibility(View.VISIBLE);
		tv_main_title.setText("首页");
		iv_info.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
	                    //已登录跳转到个人资料界面
						Intent intent=new Intent(MainActivity.this,MyInfoActivity.class);
						startActivity(intent);
			}
		});
	}
}
