package com.evan.eyesight;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import com.evan.eyesight.setting.Skip;

public class EyeTestActivity extends BaseActivity {

	private static final int ALERT_DIALOG = 1;
	private EyeTestView etv;
	private float mPreviousX;
	private float mPreviousY;
	private ImageButton btnTestUp, btnTestLeft, btnTestUnsee, btnTestRight,
			btnTestDown;

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
		float af[] = getIntent().getFloatArrayExtra("junefsh_dm");
		etv.setFDm(af);
	}

	@Override
	void initData() {
		super.initData();
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
			setResult(-1);
			etv.shutDown();
			Skip.mBack(this);
			break;
		}
	}

	public void finishMe() {
		setResult(etv.getCurPos());
		etv.shutDown();
		Skip.mBack(this);
	}

	public boolean onKeyDown(int i, KeyEvent keyevent) {
		boolean flag = true;
		// if (i == 4)
		// showDialog(flag);
		// else
		flag = super.onKeyDown(i, keyevent);
		return flag;
	}

	public boolean onTouchEvent(MotionEvent motionevent) {
		boolean flag;
		float f;
		float f1;
		flag = true;
		f = motionevent.getX();
		f1 = motionevent.getY();
		motionevent.getAction();
		flag = super.onTouchEvent(motionevent);
		mPreviousX = f;
		mPreviousY = f1;
		float f2;
		float f3;
		f2 = f - mPreviousX;
		f3 = f1 - mPreviousY;
		if (Math.abs(f3 / f2) < 1.0F && etv.testIsOver(3))
			finishMe();
		if (f3 <= 0.0F)
			// break; /* Loop/switch isn't completed */
			if (Math.abs(f2 / f3) < 1.0F && etv.testIsOver(2))
				finishMe();
		// continue; /* Loop/switch isn't completed */
		if (f2 < 0.0F && Math.abs(f3 / f2) < 1.0F && etv.testIsOver(1))
			finishMe();
		if (f3 < 0.0F && Math.abs(f2 / f3) < 1.0F && etv.testIsOver(0))
			finishMe();
		return flag;
	}
}
