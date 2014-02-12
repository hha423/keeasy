package cn.keeasy.business;

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
import cn.evan.tools.PhoneState;
import cn.evan.tools.base.ITask;
import cn.keeasy.business.base.IAdapter;
import cn.keeasy.business.base.IDialog;
import cn.keeasy.business.base.IMod;
import cn.keeasy.business.util.PM;
import cn.keeasy.business.util.Skip;
import cn.keeasy.business.util.Sources;
import cn.keeasy.business.util.VersionUpdate;

/**
 * 程序完全退出的基类
 * 
 * @author Evan
 * 
 */
public class BaseActivity extends Activity implements IAdapter, IMod, ITask,
		IDialog, OnClickListener {
	public static List<Activity> activityList = new ArrayList<Activity>();
	public Context mContext;
	public SharedPreferences spp;
	public ImageView topback;
	public TextView toptitle;
	public VersionUpdate vu;

	private long exitTime = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		spp = getSharedPreferences("user", Context.MODE_PRIVATE);
		mContext = this;
		if (!mContext.equals("LoginActivity")) {
			activityList.add(this);
		}
		vu = new VersionUpdate(mContext);
		initView();
		initData();
		initListen();
	}

	void initView() {
		topback = (ImageView) findViewById(R.id.top_back);
		toptitle = (TextView) findViewById(R.id.top_title);
	}

	void initData() {

	}

	void initListen() {
		topback.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == topback) {
			PM.onBtnAnim(topback);
			Skip.mBack(this);
		}
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.main, menu);
	// return true;
	// }

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			mExit();
			return true;
			// } else if (keyCode == KeyEvent.KEYCODE_HOME) {
			// // UIHelper.showMsg(getApplicationContext(), "请双击返回键");
			// return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onAttachedToWindow()
	 */
	@Override
	public void onAttachedToWindow() {
		// 加此句可以屏幕HOME
		// this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
		super.onAttachedToWindow();
	}

	public void mExit() {
		overridePendingTransition(R.anim.right_in, R.anim.right_out);
		if (Sources.PAGE.equals("MainActivity")) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				PhoneState.showToast(getApplicationContext(), "再按一次退出程序");
				exitTime = System.currentTimeMillis();
				Sources.PAGE = "";
			} else {
				for (int i = 0; i < activityList.size(); i++) {
					if (null != activityList.get(i)) {
						activityList.get(i).finish();
					}
				}
				Sources.ISLOGIN = false;
				System.exit(0);
			}
		} else {
			Skip.mBack(this);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	public void mSuccess() {

	}

	@Override
	public void mSuccess1() {

	}

	@Override
	public void mSuccess2() {

	}

	@Override
	public void mSuccess3() {

	}

	@Override
	public void mSuccess4() {

	}

	@Override
	public void mSuccess(String str) {

	}

	@Override
	public void mAdapter(int index) {

	}

	@Override
	public void mAdapter(int postion, int index, int type) {

	}

	@Override
	public void mShowImage_Adapter(String str) {

	}

	@Override
	public void mTaskError(String arg0) {

	}

	@Override
	public void mTaskSuccess(String arg0) {

	}

	@Override
	public void mBiaoQing(String biaoqing) {

	}

	@Override
	public void mOk() {

	}

}
