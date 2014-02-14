package com.evan.eyesight;

import com.evan.eyesight.setting.Skip;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class SemangActivity extends BaseActivity {

	private Button button_gotoceshi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.shemang_main);
		super.onCreate(savedInstanceState);
	}

	@Override
	void initView() {
		super.initView();
		button_gotoceshi = (Button) this.findViewById(R.id.button_gotoceshi);
	}

	@Override
	void initData() {
		text.setText("色盲检测");
	}

	@Override
	void initListen() {
		super.initListen();
		button_gotoceshi.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.button_gotoceshi:
			Skip.mNext(SemangActivity.this, SemangTestActivity.class,
					R.anim.activity_slide_in_right, R.anim.activity_no_anim,
					true);
			break;
		}
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
