package com.evan.eyesight;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

public class JinshiActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.jinshi_main);
		super.onCreate(savedInstanceState);
	}

	@Override
	void initView() {
		super.initView();
	}

	@Override
	void initData() {
		text.setText("近视视力表");
	}

	@Override
	void initListen() {
		super.initListen();
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}
}
