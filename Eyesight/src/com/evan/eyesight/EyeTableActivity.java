package com.evan.eyesight;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class EyeTableActivity extends BaseActivity {

	private EyeTable eyeTab;
	private MyHander hander;
	private String strTest;
	private ImageButton btn_up, btn_eye_test, btn_down;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(1);
		getWindow().setFlags(1024, 1024);
		setContentView(R.layout.jinshi_frame);
		super.onCreate(savedInstanceState);
		strTest = getString(R.string.str_test_result);
	}

	@Override
	void initView() {
		super.initView();
		eyeTab = (EyeTable) findViewById(R.id.eye_test);
		btn_up = (ImageButton) findViewById(R.id.btn_up);
		btn_eye_test = (ImageButton) findViewById(R.id.btn_eye_test);
		btn_down = (ImageButton) findViewById(R.id.btn_down);
	}

	@Override
	void initData() {
		super.initData();
	}

	@Override
	void initListen() {
		super.initListen();
		btn_up.setOnClickListener(this);
		btn_eye_test.setOnClickListener(this);
		btn_down.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.btn_up:
			if (eyeTab != null)
				eyeTab.scroolScreen(true);
			break;
		case R.id.btn_eye_test:
			Intent intent = new Intent();
			intent.setClass(EyeTableActivity.this, EyeTestActivity.class);
			intent.putExtra("junefsh_dm", eyeTab.getEyeDm());
			startActivityForResult(intent, 1);
			break;
		case R.id.btn_down:
			if (eyeTab != null)
				eyeTab.scroolScreen(false);
			break;
		}
	}

	private class MyHander extends Handler {

		public void handleMessage(Message message) {
			String s = message.getData().getString("result");
			Toast.makeText(
					EyeTableActivity.this,
					(new StringBuilder(String.valueOf(strTest))).append(s)
							.toString(), Toast.LENGTH_LONG).show();
		}

	}

	public EyeTableActivity() {
		hander = new MyHander();
		strTest = "Your Test Result:";
	}

	protected void onActivityResult(int i, int j, Intent intent) {
		super.onActivityResult(i, j, intent);
		if (j != -1) {
			String s = eyeTab.getTestResult(j);
			Message message = hander.obtainMessage();
			Bundle bundle = new Bundle();
			bundle.putString("result", s);
			message.setData(bundle);
			hander.sendMessageDelayed(message, 100L);
		}
	}

	protected void onPause() {
		super.onPause();
		if (eyeTab != null)
			eyeTab.setBNeedIni(false);
	}

	protected void onStop() {
		super.onStop();
		if (eyeTab != null)
			eyeTab.setBNeedIni(true);
	}

}
