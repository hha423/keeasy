package com.evan.eyesight;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;

import com.evan.eyesight.setting.SetConfig;
import com.evan.eyesight.setting.Skip;
import com.evan.eyesight.setting.Utils;

public class EyeTestActivity extends BaseActivity {

	private EyeTestView etv;
	private ImageButton btnTestUp, btnTestLeft, btnTestUnsee, btnTestRight,
			btnTestDown;
	private boolean flag;
	private String strEyeTab[];
	private float fDimen[];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(1);
		getWindow().setFlags(1024, 1024);
		setContentView(R.layout.jinshi_test);
		super.onCreate(savedInstanceState);
	}

	@Override
	void initView() {
		super.initView();
		etv = (EyeTestView) findViewById(R.id.eyeT);
		btnTestUp = (ImageButton) findViewById(R.id.btnTestUp);
		btnTestLeft = (ImageButton) findViewById(R.id.btnTestLeft);
		btnTestUnsee = (ImageButton) findViewById(R.id.btnTestUnsee);
		btnTestRight = (ImageButton) findViewById(R.id.btnTestRight);
		btnTestDown = (ImageButton) findViewById(R.id.btnTestDown);
		flag = getIntent().getBooleanExtra("flag", false);
		fDimen = getEyeDm();
		etv.setFDm(fDimen);
	}

	@Override
	void initData() {
		super.initData();
		Resources resources = getResources();
		strEyeTab = resources.getStringArray(R.array.str_eye_table);
	}

	@Override
	void initListen() {
		super.initListen();
		btnTestUp.setOnClickListener(this);
		btnTestLeft.setOnClickListener(this);
		btnTestUnsee.setOnClickListener(this);
		btnTestRight.setOnClickListener(this);
		btnTestDown.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// super.onClick(v);
		switch (v.getId()) {
		case R.id.btnTestUp:
			if (etv.testIsOver(0))
				finishMe();
			break;
		case R.id.btnTestLeft:
			if (etv.testIsOver(1))
				finishMe();
			break;
		case R.id.btnTestUnsee:
			if (etv.testIsOver(4))
				finishMe();
			break;
		case R.id.btnTestRight:
			if (etv.testIsOver(3))
				finishMe();
			break;
		case R.id.btnTestDown:
			if (etv.testIsOver(2))
				finishMe();
			break;
		case R.id.top_back:
			showDialog(1);
			break;
		}
	}

	public void finishMe() {
		if (flag) {
			SetConfig.righteye = getTestResult(etv.getCurPos());
			etv.shutDown();
			Skip.mNext(this, EyeResultActivity.class, R.anim.activity_no_anim,
					R.anim.activity_slide_out_right, true);
		} else {
			setResult(etv.getCurPos());
			SetConfig.lefteye = getTestResult(etv.getCurPos());
			etv.shutDown();
			Skip.mBack(this);
		}
	}

	public float getTestResult(int i) {
		float s;
		if (i > 0 && i < fDimen.length)
			s = Float.parseFloat(strEyeTab[i]);
		else
			s = Float.parseFloat(strEyeTab[-1 + strEyeTab.length]);
		return s;
	}

	public boolean onKeyDown(int i, KeyEvent keyevent) {
		boolean flag = true;
		if (i == 4)
			showDialog(1);
		else
			flag = super.onKeyDown(i, keyevent);
		return flag;
	}

	protected Dialog onCreateDialog(int i) {
		android.app.AlertDialog alertdialog;
		if (i == 1) {
			alertdialog = (new android.app.AlertDialog.Builder(this))
					.setTitle("退出提示")
					.setMessage("测试未完成，你确定要退出？")
					.setPositiveButton(
							"确定",
							new android.content.DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface dialoginterface, int j) {
									setResult(-1);
									etv.shutDown();
									Skip.mBack(EyeTestActivity.this);
								}
							})
					.setNegativeButton(
							"取消",
							new android.content.DialogInterface.OnClickListener() {

								public void onClick(
										DialogInterface dialoginterface, int j) {
									dialoginterface.dismiss();
								}
							}).create();
		} else
			alertdialog = null;
		return alertdialog;
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
}