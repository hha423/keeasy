package com.evan.eyesight;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class BaseActivity extends Activity implements OnClickListener {

	ImageButton ibtn;
	TextView text;

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
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.top_back:
			this.finish();
			break;
		}
	};

}
