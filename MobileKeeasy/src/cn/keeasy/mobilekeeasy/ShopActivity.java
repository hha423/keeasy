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

import cn.keeasy.mobilekeeasy.dao.ShopProductMod;
import cn.keeasy.mobilekeeasy.data.Sources;
import cn.keeasy.mobilekeeasy.entity.ContactBean;
import cn.keeasy.mobilekeeasy.utils.PM;
import cn.keeasy.mobilekeeasy.utils.Skip;

public class ShopActivity extends BaseActivity implements OnClickListener {

	private ShopProductMod spmod;
	private ImageViews dianpu_img;
	private LinearLayout dianpu_btn, dianpu_btnic;
	private TextView dianpu_zheke, dianpu_jianshao, dianpu_xiuzhi, dianpu_addr,
			dianpu_time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.main_dianpu);
		super.onCreate(savedInstanceState);
	}

	@Override
	void initView() {
		super.initView();
		dianpu_img = (ImageViews) findViewById(R.id.dianpu_img);
		dianpu_btn = (LinearLayout) findViewById(R.id.dianpu_btn);
		dianpu_btnic = (LinearLayout) findViewById(R.id.dianpu_btnic);
		dianpu_zheke = (TextView) findViewById(R.id.dianpu_zheke);
		dianpu_jianshao = (TextView) findViewById(R.id.dianpu_jianshao);
		dianpu_xiuzhi = (TextView) findViewById(R.id.dianpu_xiuzhi);
		dianpu_addr = (TextView) findViewById(R.id.dianpu_addr);
		dianpu_time = (TextView) findViewById(R.id.dianpu_time);
	}

	@Override
	void initData() {
		Sources.CARTLIST.clear();
		spmod = new ShopProductMod(this, this);
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				spmod.mGetHdShopInfo(Sources.CURRENTSHOP.dayType
						.get(Sources.CURRENTSHOP.item).sundayId);
				return null;
			}
		}.execute(null, null, null);
		topback.setVisibility(View.VISIBLE);
		toptitle.setText(Sources.CURRENTSHOP.shopName);
		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		params.weight = PhoneState.getScreenrp(this).widthPixels;
		params.height = PhoneState.getScreenrp(this).widthPixels / 3;
		dianpu_img.setLayoutParams(params);
		dianpu_img.DisplayImage(Sources.CURRENTSHOP.shopImg, false);
		dianpu_zheke
				.setText("全场"
						+ Sources.CURRENTSHOP.dayType
								.get(Sources.CURRENTSHOP.item).discount + "折");
		dianpu_addr.setText(Sources.CURRENTSHOP.shopAddress);
		dianpu_time.setText("营业时间:" + Sources.CURRENTSHOP.shopTime);
	}

	@Override
	void initListen() {
		super.initListen();
		dianpu_btn.setOnClickListener(this);
		dianpu_btnic.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.dianpu_btn:
			PM.onBtnAnim(dianpu_btn);
			Skip.mNext(this, ShopMenuActivity.class);
			break;
		case R.id.dianpu_btnic:
			PM.onBtnAnim(dianpu_btnic);
			if (userInfo.getBoolean("islogin", false)) {
				if (userInfo.getString("account", "").equals(
						Sources.CURRENTSHOP.bAccount)) {
					PhoneState.showToast(this, "不能对自己发起对话！");
					return;
				} else {
					ContactBean cbean = new ContactBean();
					// cbean.contact_Id = Sources.CURRENTSHOP.shopId;
					cbean.contactName = Sources.CURRENTSHOP.shopName;
					cbean.contactAccount = Sources.CURRENTSHOP.bAccount;
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
		dianpu_jianshao.setText(Sources.JIANSHAO);
		dianpu_xiuzhi.setText(Sources.XIUZHI);
	}

}