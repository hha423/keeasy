package cn.keeasy.business;

import java.util.Calendar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import cn.evan.tools.PhoneState;
import cn.keeasy.business.mod.ZhekouAddMod;
import cn.keeasy.business.mod.ZhekouMod;
import cn.keeasy.business.util.PM;
import cn.keeasy.business.util.Sources;

public class ZhekeActivity extends BaseActivity {

	private ZhekouMod azmod;
	private EditText mz_dsedit;
	private ZhekouAddMod zkamod;
	private int year, monthOfYear, dayOfMonth, dtime;
	private Spinner mz_edit, mz_stedit, mz_ptedit;
	private LinearLayout mz_layout, stlayout, ptlayout, ll_dian;
	private TextView mz_zhe, mz_startime, mz_stoptime, mz_btnok, tv_dian;
	private String[] zheke = { "请选折扣", "1.5 折", "2.0 折", "2.5 折", "3.0 折",
			"3.5 折", "4.0 折", "4.5 折", "5.0 折", "5.5 折", "6.0 折", "6.5 折",
			"7.0 折", "7.5 折", "8.0 折", "8.5 折", "9.0 折", "9.5 折" };
	private String[] ztime = { "00:00", "01:00", "02:00", "03:00", "04:00",
			"05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00",
			"12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00",
			"19:00", "20:00", "21:00", "22:00", "23:00" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.main_zhe);
		super.onCreate(savedInstanceState);
	}

	@Override
	void initView() {
		super.initView();
		mz_zhe = (TextView) findViewById(R.id.mz_zhe);
		mz_startime = (TextView) findViewById(R.id.mz_startime);
		mz_stoptime = (TextView) findViewById(R.id.mz_stoptime);
		mz_btnok = (TextView) findViewById(R.id.mz_btnok);
		tv_dian = (TextView) findViewById(R.id.tv_dian);
		// mz_ok = (TextView) findViewById(R.id.mz_ok);
		mz_dsedit = (EditText) findViewById(R.id.mz_dsedit);
		mz_edit = (Spinner) findViewById(R.id.mz_edit);
		mz_stedit = (Spinner) findViewById(R.id.mz_stedit);
		mz_ptedit = (Spinner) findViewById(R.id.mz_ptedit);
		mz_layout = (LinearLayout) findViewById(R.id.mz_layout);
		stlayout = (LinearLayout) findViewById(R.id.stlayout);
		ptlayout = (LinearLayout) findViewById(R.id.ptlayout);
		ll_dian = (LinearLayout) findViewById(R.id.ll_dian);
	}

	@Override
	void initData() {
		toptitle.setText("限时折扣设置");
		azmod = new ZhekouMod(mContext, this);
		azmod.mGetZheInfo(Sources.USERBEAN.shopId);
		zkamod = new ZhekouAddMod(mContext, this);
		Calendar calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		monthOfYear = calendar.get(Calendar.MONTH);
		dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		dtime = calendar.get(Calendar.HOUR_OF_DAY);
		mz_zhe.setText("设置折扣");
		mz_startime.setText("设置开始时间");
		mz_stoptime.setText("设置结束时间");
		mz_edit.setAdapter(new ArrayAdapter<String>(mContext,
				android.R.layout.simple_spinner_item, zheke));
		mz_stedit.setAdapter(new ArrayAdapter<String>(mContext,
				android.R.layout.simple_spinner_item, ztime));
		mz_ptedit.setAdapter(new ArrayAdapter<String>(mContext,
				android.R.layout.simple_spinner_item, ztime));
	}

	@Override
	void initListen() {
		super.initListen();
		mz_zhe.setOnClickListener(this);
		mz_btnok.setOnClickListener(this);
		mz_startime.setOnClickListener(this);
		mz_stoptime.setOnClickListener(this);
		mz_edit.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (arg2 != 0) {
					Sources.ZHEBEAN.zheKou = zheke[arg2].substring(0, 3);
					mz_zhe.setVisibility(View.VISIBLE);
					mz_layout.setVisibility(View.GONE);
					mz_zhe.setText("当前折扣：" + Sources.ZHEBEAN.zheKou + " 折");
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				mz_zhe.setVisibility(View.VISIBLE);
				mz_layout.setVisibility(View.GONE);
			}
		});
		mz_stedit.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (arg2 != 0) {
					Sources.ZHEBEAN.begin = ztime[arg2];
					mz_startime.setVisibility(View.VISIBLE);
					stlayout.setVisibility(View.GONE);
					mz_startime.setText("开始时间：" + year + "-"
							+ (monthOfYear + 1) + "-" + dayOfMonth + " "
							+ Sources.ZHEBEAN.begin);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				mz_startime.setVisibility(View.VISIBLE);
				stlayout.setVisibility(View.GONE);
			}
		});
		mz_ptedit.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (arg2 != 0) {
					if (Double.parseDouble(ztime[arg2].substring(0, 2)) > dtime
							&& Double.parseDouble(Sources.ZHEBEAN.begin
									.substring(0, 2)) < Double
									.parseDouble(ztime[arg2].substring(0, 2))) {
						Sources.ZHEBEAN.end = ztime[arg2];
						mz_stoptime.setVisibility(View.VISIBLE);
						ptlayout.setVisibility(View.GONE);
						mz_stoptime.setText("结束时间：" + year + "-"
								+ (monthOfYear + 1) + "-" + dayOfMonth + " "
								+ Sources.ZHEBEAN.end);
					} else {
						PhoneState.showToast(mContext, "结束时间不能小于开始时间和当前时间");
					}
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				mz_stoptime.setVisibility(View.VISIBLE);
				ptlayout.setVisibility(View.GONE);
			}
		});
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.mz_zhe:
			PM.onBtnAnim(mz_zhe);
			mz_zhe.setVisibility(View.GONE);
			mz_layout.setVisibility(View.VISIBLE);
			break;
		case R.id.mz_startime:
			PM.onBtnAnim(mz_startime);
			mz_stoptime.setEnabled(true);
			mz_startime.setVisibility(View.GONE);
			stlayout.setVisibility(View.VISIBLE);
			break;
		case R.id.mz_stoptime:
			PM.onBtnAnim(mz_stoptime);
			mz_stoptime.setVisibility(View.GONE);
			ptlayout.setVisibility(View.VISIBLE);
			// new DateTimeDialog(mContext, mHandler).create().show();
			break;
		case R.id.mz_btnok:
			PM.onBtnAnim(mz_btnok);
			if (Sources.ZHEBEAN != null && Sources.ZHEBEAN.zheKou != null
					&& Sources.ZHEBEAN.begin != null
					&& Sources.ZHEBEAN.end != null
					&& ptlayout.getVisibility() == View.GONE
					&& mz_dsedit.getText().length() > 0) {
				new AlertDialog.Builder(mContext)
						.setMessage(
								"您设置的折扣信息如下：\n限时折扣：" + Sources.ZHEBEAN.zheKou
										+ " 折\n开始时间：" + Sources.ZHEBEAN.begin
										+ "\n结束时间：" + Sources.ZHEBEAN.end
										+ "\n限时单数："
										+ mz_dsedit.getText().toString())
						.setPositiveButton("没错", new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								zkamod.mSetZheInfo(Sources.USERBEAN.shopId,
										Sources.ZHEBEAN.zheKou,
										Sources.ZHEBEAN.begin.substring(0, 2),
										Sources.ZHEBEAN.end.substring(0, 2),
										mz_dsedit.getText().toString().trim());
							}
						}).setNegativeButton("不对", null).create().show();
			} else {
				PhoneState.showToast(mContext, "请先设置完整再提交！");
			}
			break;
		}
	}

	// Handler mHandler = new Handler() {
	// @Override
	// public void handleMessage(Message msg) {
	// super.handleMessage(msg);
	// switch (msg.what) {
	// case 0:
	// if (flag == 0) {
	// startime = msg.getData().getString("dtime");
	// mz_startime.setText("开始时间：" + startime);
	// } else if (flag == 1) {
	// stoptime = msg.getData().getString("dtime");
	// mz_stoptime.setText("结束时间：" + stoptime);
	// }
	// break;
	// }
	// }
	// };

	@Override
	protected void onResume() {
		mz_stoptime.setEnabled(false);
		super.onResume();
	}

	@Override
	public void mSuccess() {
		if (Sources.ZHEBEAN != null && !"false".equals(Sources.ZHEBEAN.type)
				&& !"0".equals(Sources.ZHEBEAN.type)) {
			ll_dian.setVisibility(View.GONE);
			tv_dian.setVisibility(View.VISIBLE);
			if (mz_dsedit.getText().length() > 0) {
				tv_dian.setText("总单数：" + mz_dsedit.getText().toString() + "单");
			}
			if (!"true".equals(Sources.ZHEBEAN.type)) {
				mz_zhe.setText("当前折扣：" + Sources.ZHEBEAN.zheKou + " 折");
				mz_startime.setText("开始时间：" + Sources.ZHEBEAN.begin);
				mz_stoptime.setText("结束时间：" + Sources.ZHEBEAN.end);
				tv_dian.setText("总单数：" + Sources.ZHEBEAN.sumNum + "单  -  剩余单数："
						+ Sources.ZHEBEAN.endNum + "单");
			}
			mz_zhe.setEnabled(false);
			mz_startime.setEnabled(false);
			mz_stoptime.setEnabled(false);
			mz_btnok.setVisibility(View.GONE);
		} else {
			mz_zhe.setText("设置折扣");
			mz_startime.setText("设置开始时间");
			mz_stoptime.setText("设置结束时间");
			ll_dian.setVisibility(View.VISIBLE);
			tv_dian.setVisibility(View.GONE);
			mz_btnok.setVisibility(View.VISIBLE);
		}
	}
}