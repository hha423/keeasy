package cn.keeasy.mobilekeeasy;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import cn.keeasy.mobilekeeasy.data.IDialog;
import cn.keeasy.mobilekeeasy.data.IMod;
import cn.keeasy.mobilekeeasy.utils.PM;
import cn.keeasy.mobilekeeasy.utils.Skip;

public class BaseActivity extends Activity implements IMod, IDialog,
		OnClickListener {

	public static List<Activity> activityList = new ArrayList<Activity>();
	public SharedPreferences userInfo;
	public ImageView topback;
	public TextView toptitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		userInfo = getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE
				+ Context.MODE_WORLD_WRITEABLE);
		if (!this.equals("LoginActivity")) {
			activityList.add(this);
		}
		initView();
		initData();
		initListen();
	}

	void initView() {
		toptitle = (TextView) findViewById(R.id.top_title);
		topback = (ImageView) findViewById(R.id.top_back);
	};

	void initData() {
	};

	void initListen() {
		topback.setOnClickListener(this);
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Skip.mBack(this);
			return true;
			// } else if (keyCode == KeyEvent.KEYCODE_HOME) {
			// // UIHelper.showMsg(getApplicationContext(), "请双击返回键");
			// return true;
		}
		return super.onKeyDown(keyCode, event);
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
	public void mSuccess() {

	}

	@Override
	public void mSuccess(String str) {

	}

	@Override
	public void mSuccess1() {

	}

	@Override
	public void mSuccess2() {

	}

	@Override
	public void mSearch(int page) {

	}

	@Override
	public void mSuccessMSg() {

	}

	@Override
	public void mSucfriend() {

	}

	@Override
	public void mSumoney() {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.top_back:
			PM.onBtnAnim(topback);
			Skip.mBack(this);
			break;
		}
	}

}