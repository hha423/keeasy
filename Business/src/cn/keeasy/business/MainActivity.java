package cn.keeasy.business;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.keeasy.business.mod.MsgCountMod;
import cn.keeasy.business.util.PM;
import cn.keeasy.business.util.Skip;
import cn.keeasy.business.util.Sources;
import cn.keeasy.business.util.WarningTone;

public class MainActivity extends BaseActivity {

	private TextView main_shop, main_ic, main_addr, am_num;
	private LinearLayout main_suba, main_verify, main_order, main_lbs,
			main_zhe, main_exit;
	private ImageView editok;
	private RelativeLayout main_icm;
	private int oldmsg = -1;
	private WarningTone warn;
	private Handler mHandler;
	private MsgCountMod mcmod;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_main);
		super.onCreate(savedInstanceState);
	}

	@Override
	void initView() {
		super.initView();
		main_shop = (TextView) findViewById(R.id.main_shop);
		main_ic = (TextView) findViewById(R.id.main_ic);
		main_addr = (TextView) findViewById(R.id.main_addr);
		am_num = (TextView) findViewById(R.id.am_num);
		main_suba = (LinearLayout) findViewById(R.id.main_suba);
		main_verify = (LinearLayout) findViewById(R.id.main_verify);
		main_order = (LinearLayout) findViewById(R.id.main_order);
		main_icm = (RelativeLayout) findViewById(R.id.main_icm);
		main_lbs = (LinearLayout) findViewById(R.id.main_lbs);
		main_zhe = (LinearLayout) findViewById(R.id.main_zhe);
		main_exit = (LinearLayout) findViewById(R.id.main_exit);
		editok = (ImageView) findViewById(R.id.editok);
	}

	@Override
	void initData() {
		topback.setVisibility(View.GONE);
		toptitle.setText("商家后台管理");
		mHandler = new Handler();
		warn = new WarningTone(this, "msg.wav");
		mcmod = new MsgCountMod(mContext, this);
	}

	@Override
	void initListen() {
		main_suba.setOnClickListener(this);
		main_verify.setOnClickListener(this);
		main_order.setOnClickListener(this);
		main_icm.setOnClickListener(this);
		main_lbs.setOnClickListener(this);
		main_exit.setOnClickListener(this);
		main_zhe.setOnClickListener(this);
		editok.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == main_suba) {
			PM.onBtnAnim(main_suba);
			Skip.mNext(MainActivity.this, SubAccActivity.class);
		} else if (v == main_verify) {
			PM.onBtnAnim(main_verify);
			Skip.mNext(MainActivity.this, VerifyActivity.class);
		} else if (v == main_order) {
			PM.onBtnAnim(main_order);
			Skip.mNext(MainActivity.this, OrderActivity.class);
		} else if (v == main_icm) {
			PM.onBtnAnim(main_icm);
			Skip.mNext(MainActivity.this, FriendActivity.class);
		} else if (v == main_lbs) {
			PM.onBtnAnim(main_lbs);
			Skip.mNext(MainActivity.this, LBSActivity.class);
		} else if (v == editok) {
			PM.onBtnAnim(editok);
			Skip.mNext(MainActivity.this, ModAddActivity.class);
		} else if (v == main_zhe) {
			PM.onBtnAnim(main_zhe);
			Skip.mNext(MainActivity.this, ZhekeActivity.class);
		} else if (v == main_exit) {
			PM.onBtnAnim(main_exit);
			new AlertDialog.Builder(mContext).setTitle("提示信息")
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setMessage("是否要退出登录?")
					.setPositiveButton("确定", new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							Sources.ISLOGIN = false;
							Skip.mNext(MainActivity.this, LoginActivity.class,
									R.anim.activity_no_anim,
									R.anim.activity_slide_out_right, true);
						}
					}).setNegativeButton("取消", null).create().show();
		}
	}

	@Override
	protected void onResume() {
		vu.checkUpdate(false);
		super.onResume();
		if (Sources.USERBEAN != null && Sources.USERBEAN.type == 0) {
			mHandler.post(runable);
			main_ic.setText(Sources.USERBEAN.shopIC);
			main_suba.setVisibility(View.VISIBLE);
			main_verify.setVisibility(View.VISIBLE);
			main_order.setVisibility(View.VISIBLE);
			main_icm.setVisibility(View.VISIBLE);
			main_lbs.setVisibility(View.VISIBLE);
			main_zhe.setVisibility(View.VISIBLE);
		} else if (Sources.USERBEAN.type == 1) {
			mHandler.removeCallbacks(runable);
			editok.setVisibility(View.GONE);
			main_ic.setText("子帐号/" + Sources.USERBEAN.subIC);
			String[] auth = Sources.USERBEAN.subQX.split(",");
			for (int i = 0; i < auth.length; i++) {
				if (auth[i].equals("zzhgl")) {
					main_suba.setVisibility(View.VISIBLE);
					continue;
				} else if (auth[i].equals("yzdd")) {
					main_verify.setVisibility(View.VISIBLE);
					continue;
				} else if (auth[i].equals("ckdd")) {
					main_order.setVisibility(View.VISIBLE);
					continue;
				} else if (auth[i].equals("ictx")) {
					main_icm.setVisibility(View.VISIBLE);
					continue;
				} else if (auth[i].equals("dpdw")) {
					main_lbs.setVisibility(View.VISIBLE);
					continue;
				} else if (auth[i].equals("xsqggl")) {
					main_zhe.setVisibility(View.VISIBLE);
					continue;
				} else {
					main_suba.setVisibility(View.GONE);
					main_verify.setVisibility(View.GONE);
					main_order.setVisibility(View.GONE);
					main_icm.setVisibility(View.GONE);
					main_lbs.setVisibility(View.GONE);
					main_zhe.setVisibility(View.GONE);
				}
			}
		} else {
			Skip.mNext(this, LoginActivity.class,
					R.anim.activity_slide_in_right, R.anim.activity_no_anim,
					true);
		}
		if (Sources.USERBEAN.shopName != null) {
			main_shop.setText(Sources.USERBEAN.shopName);
			main_addr.setText("地址：" + Sources.USERBEAN.shopAddr);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Sources.PAGE = "MainActivity";
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onPause() {
		super.onPause();
		Sources.PAGE = "";
	}

	@Override
	protected void onDestroy() {
		mHandler.removeCallbacks(runable);
		super.onDestroy();
	}

	Runnable runable = new Runnable() {
		@Override
		public void run() {
			mcmod.mGetMessageNum(Sources.USERBEAN.shopIC,
					Sources.USERBEAN.userId);
			mHandler.postDelayed(runable, 3000);
		}
	};

	@Override
	public void mSuccess1() {
		int msgcount = 0;
		if (Sources.NOTREADMSG.size() != 0) {
			for (String element : Sources.NOTREADMSG.values()) {
				msgcount += Integer.parseInt(element);
			}
			if (msgcount != 0) {
				if (msgcount > oldmsg) {
					warn.player();
				}
				am_num.setVisibility(View.VISIBLE);
				am_num.setText("" + msgcount);
				oldmsg = msgcount;
			} else {
				am_num.setVisibility(View.INVISIBLE);
			}
		} else {
			am_num.setVisibility(View.INVISIBLE);
		}
	}

}
