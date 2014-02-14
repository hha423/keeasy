package com.evan.eyesight;

import com.evan.eyesight.setting.Skip;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Activity父类
 * 
 * @author Evan
 * 
 */
public class BaseActivity extends Activity implements OnClickListener {

	ImageButton ibtn;
	TextView text;
	// 主功能项
	public String[] tabs = { "近视检测", "色盲检测", "眼保健操", "测试记录" };
	// 功能图标
	public int[] mimg = { R.drawable.jinshi, R.drawable.shemang,
			R.drawable.yanbao, R.drawable.jilu };
	// 功能跳转页
	public Class<?>[] page = { JinshiActivity.class, SemangActivity.class,
			YanbaoActivity.class, JiluActivity.class };

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		initView();
		initData();
		initListen();
	}

	void initView() {
		ibtn = (ImageButton) this.findViewById(R.id.top_back);
		text = (TextView) this.findViewById(R.id.top_title);
	}

	void initData() {
	}

	void initListen() {
		ibtn.setOnClickListener(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.top_back:
			Skip.mBack(this);
			break;
		}
	};

}
