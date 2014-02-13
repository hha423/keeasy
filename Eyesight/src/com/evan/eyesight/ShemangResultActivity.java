package com.evan.eyesight;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ShemangResultActivity extends BaseActivity {

	private TextView text_state;
	private ProgressBar progress_jindu;
	private ImageView image_ceshi;
	private EditText semang_answer;
	private Button button_gotoceshi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activitysemangresult);
		super.onCreate(savedInstanceState);
	}

	@Override
	void initView() {
		super.initView();
	}

	@Override
	void initData() {
		text.setText("色盲检测");
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
