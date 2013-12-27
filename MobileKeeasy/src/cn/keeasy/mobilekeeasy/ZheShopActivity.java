package cn.keeasy.mobilekeeasy;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import cn.evan.tools.PhoneState;
import cn.evan.tools.ui.ImageViews;
import cn.keeasy.mobilekeeasy.dao.ZheShopMod;
import cn.keeasy.mobilekeeasy.data.Sources;
import cn.keeasy.mobilekeeasy.entity.ContactBean;
import cn.keeasy.mobilekeeasy.utils.PM;
import cn.keeasy.mobilekeeasy.utils.Skip;

public class ZheShopActivity extends BaseActivity implements OnClickListener {

	private ZheShopMod spmod;
	private ImageViews zdianpu_img;
	private LinearLayout zdianpu_btn, zdianpu_btnic;
	private TextView zdianpu_zheke, zdianpu_jianshao, zdianpu_addr,
			zdianpu_time, zdianpu_begtime, zdianpu_endtime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.main_zdianpu);
		super.onCreate(savedInstanceState);
	}

	@Override
	void initView() {
		super.initView();
		zdianpu_img = (ImageViews) findViewById(R.id.zdianpu_img);
		zdianpu_btn = (LinearLayout) findViewById(R.id.zdianpu_btn);
		zdianpu_btnic = (LinearLayout) findViewById(R.id.zdianpu_btnic);
		zdianpu_zheke = (TextView) findViewById(R.id.zdianpu_zheke);
		zdianpu_jianshao = (TextView) findViewById(R.id.zdianpu_jianshao);
		zdianpu_addr = (TextView) findViewById(R.id.zdianpu_addr);
		zdianpu_time = (TextView) findViewById(R.id.zdianpu_time);
		zdianpu_begtime = (TextView) findViewById(R.id.zdianpu_begtime);
		zdianpu_endtime = (TextView) findViewById(R.id.zdianpu_endtime);
	}

	@Override
	void initData() {
		Sources.CARTLIST.clear();
		spmod = new ZheShopMod(this, this);
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				spmod.mGetHdShopInfo(Sources.ZHEBEAN.shopId);
				return null;
			}
		}.execute(null, null, null);
		topback.setVisibility(View.VISIBLE);
		toptitle.setText(Sources.ZHEBEAN.shopName);
		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		params.weight = PhoneState.getScreenrp(this).widthPixels;
		params.height = PhoneState.getScreenrp(this).widthPixels / 3;
		zdianpu_img.setLayoutParams(params);
		zdianpu_zheke.setText("限时全场" + Sources.ZHEBEAN.zhekou + "折");
		zdianpu_addr.setText(Sources.ZHEBEAN.shopAddress);
		zdianpu_begtime.setText(Sources.ZHEBEAN.begTime);
		zdianpu_endtime.setText(Sources.ZHEBEAN.endTime);
	}

	@Override
	void initListen() {
		super.initListen();
		zdianpu_btn.setOnClickListener(this);
		zdianpu_btnic.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.zdianpu_btn:
			PM.onBtnAnim(zdianpu_btn);
			Skip.mNext(this, ZheMenuActivity.class);
			break;
		case R.id.zdianpu_btnic:
			PM.onBtnAnim(zdianpu_btnic);
			if (userInfo.getBoolean("islogin", false)) {
				if (userInfo.getString("account", "").equals(
						Sources.ZHEBEAN.bAccount)) {
					PhoneState.showToast(this, "不能对自己发起对话！");
					return;
				} else {
					ContactBean cbean = new ContactBean();
					// cbean.contact_Id = Sources.CURRENTSHOP.shopId;
					cbean.contactName = Sources.ZHEBEAN.shopName;
					cbean.contactAccount = Sources.ZHEBEAN.bAccount;
					// cbean.contactPhoto = Sources.CURRENTSHOP.shopImg;
					Sources.CURRENTIC = cbean;
					Skip.mNext(this, ChatActivity.class);
				}
			} else {
				Skip.mNext(this, LoginActivity.class,
						R.anim.activity_push_up_in, R.anim.activity_no_anim,
						false);
			}
			break;
		}
	}

	@Override
	public void mSuccess() {
		zdianpu_img.DisplayImage(Sources.PHOTO, false);
		zdianpu_time.setText("营业时间:" + Sources.XIUZHI);
		zdianpu_jianshao.setText(Sources.JIANSHAO);
	}

}