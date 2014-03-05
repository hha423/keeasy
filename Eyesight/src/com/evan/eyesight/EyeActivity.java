package com.evan.eyesight;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.evan.eyesight.setting.Skip;
import com.evan.eyesight.setting.Utils;

public class EyeActivity extends BaseActivity {

	private TextView text_eye;
	private ImageView iamge_eye;
	private Button button_gotoceshi;
	private float fDimen[];
	private boolean flag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activityjinshieye);
		super.onCreate(savedInstanceState);
	}

	@Override
	void initView() {
		super.initView();
		text_eye = (TextView) findViewById(R.id.eyetext_eye);
		iamge_eye = (ImageView) findViewById(R.id.eyeiamge_eye);
		button_gotoceshi = (Button) findViewById(R.id.button_gotoceshi);
	}

	@Override
	void initData() {
		super.initData();
		button_gotoceshi.setText(R.string.lefttest);
		text_eye.setText(R.string.lefteye);
		iamge_eye.setBackgroundResource(R.drawable.ceshileft);
		flag = false;
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
			Skip.mResult(this, EyeTestActivity.class, flag);
			if (flag) {
				this.finish();
			}
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != -1) {
			button_gotoceshi.setText(R.string.righttest);
			text_eye.setText(R.string.righteye);
			iamge_eye.setBackgroundResource(R.drawable.ceshiright);
			flag = true;
		}
	}
}