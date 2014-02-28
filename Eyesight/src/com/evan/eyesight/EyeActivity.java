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
			Skip.mResult(this, EyeTestActivity.class, getEyeDm(), flag);
			if (flag) {
				this.finish();
			}
			break;
		}
	}

	private float[] getEyeDm() {
		float af[] = new float[23];
		Resources resources = getResources();
		af[0] = resources.getDimension(R.dimen.eye_5_2);
		af[1] = resources.getDimension(R.dimen.eye_5_1);
		af[2] = resources.getDimension(R.dimen.eye_5_0);
		af[3] = resources.getDimension(R.dimen.eye_4_9);
		af[4] = resources.getDimension(R.dimen.eye_4_8);
		af[5] = resources.getDimension(R.dimen.eye_4_7);
		af[6] = resources.getDimension(R.dimen.eye_4_6);
		af[7] = resources.getDimension(R.dimen.eye_4_5);
		af[8] = resources.getDimension(R.dimen.eye_4_4);
		af[9] = resources.getDimension(R.dimen.eye_4_3);
		af[10] = resources.getDimension(R.dimen.eye_4_2);
		af[11] = resources.getDimension(R.dimen.eye_4_1);
		af[12] = resources.getDimension(R.dimen.eye_4_0);
		af[13] = resources.getDimension(R.dimen.eye_3_9);
		af[14] = resources.getDimension(R.dimen.eye_3_8);
		af[15] = resources.getDimension(R.dimen.eye_3_7);
		af[16] = resources.getDimension(R.dimen.eye_3_6);
		af[17] = resources.getDimension(R.dimen.eye_3_5);
		af[18] = resources.getDimension(R.dimen.eye_3_4);
		af[19] = resources.getDimension(R.dimen.eye_3_3);
		af[20] = resources.getDimension(R.dimen.eye_3_2);
		af[21] = resources.getDimension(R.dimen.eye_3_1);
		af[22] = resources.getDimension(R.dimen.eye_3_0);
		fDimen = af;
		int i = fDimen.length;
		int j = 0;
		do {
			if (j >= i)
				return fDimen;
			float f = Utils.formatDm(fDimen[j]);
			fDimen[j] = f;
			j++;
		} while (true);
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