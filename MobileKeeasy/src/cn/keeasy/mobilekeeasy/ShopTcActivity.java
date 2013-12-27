package cn.keeasy.mobilekeeasy;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import cn.evan.tools.PhoneState;
import cn.evan.tools.ui.ImageViews;

import cn.keeasy.mobilekeeasy.dao.AddOrderMod;
import cn.keeasy.mobilekeeasy.dao.ShopProductMod;
import cn.keeasy.mobilekeeasy.dao.SundayTcShopMod;
import cn.keeasy.mobilekeeasy.data.Sources;
import cn.keeasy.mobilekeeasy.entity.ProductInfoBean;
import cn.keeasy.mobilekeeasy.utils.PM;
import cn.keeasy.mobilekeeasy.utils.Skip;

public class ShopTcActivity extends BaseActivity implements OnClickListener {

	private boolean flag;
	private ShopProductMod spmod;
	private SundayTcShopMod stsmod;
	private AddOrderMod addmod;
	private ImageViews shoptc_img;
	private EditText shoptc_name, shoptc_tel;
	private TextView shoptc_zheke, shoptc_jianshao, shoptc_xiuzhi,
			shoptc_total, shoptc_sun, shoptc_ding, shoptc_fu, shoptc_info,
			shoptc_go;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.main_shoptc);
		super.onCreate(savedInstanceState);
	}

	@Override
	void initView() {
		super.initView();
		shoptc_img = (ImageViews) findViewById(R.id.shoptc_img);
		shoptc_name = (EditText) findViewById(R.id.shoptc_name);
		shoptc_tel = (EditText) findViewById(R.id.shoptc_tel);
		shoptc_zheke = (TextView) findViewById(R.id.shoptc_zheke);
		shoptc_jianshao = (TextView) findViewById(R.id.shoptc_jianshao);
		shoptc_xiuzhi = (TextView) findViewById(R.id.shoptc_xiuzhi);
		shoptc_total = (TextView) findViewById(R.id.shoptc_total);
		shoptc_sun = (TextView) findViewById(R.id.shoptc_sun);
		shoptc_ding = (TextView) findViewById(R.id.shoptc_ding);
		shoptc_fu = (TextView) findViewById(R.id.shoptc_fu);
		shoptc_info = (TextView) findViewById(R.id.shoptc_info);
		shoptc_go = (TextView) findViewById(R.id.shoptc_go);
	}

	@Override
	void initData() {
		spmod = new ShopProductMod(this, this);
		stsmod = new SundayTcShopMod(this, this);
		addmod = new AddOrderMod(this, this);
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				spmod.mGetHdShopInfo(Sources.CURRENTSHOP.dayType
						.get(Sources.CURRENTSHOP.item).sundayId);
				stsmod.mGetSunShopTC(Sources.CURRENTSHOP.dayType
						.get(Sources.CURRENTSHOP.item).sundayId);
				return null;
			}

		}.execute(null, null, null);
		topback.setVisibility(View.VISIBLE);
		toptitle.setText(Sources.CURRENTSHOP.shopName + "-套餐");
		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		params.weight = PhoneState.getScreenrp(this).widthPixels;
		params.height = PhoneState.getScreenrp(this).widthPixels / 3;
		shoptc_img.setLayoutParams(params);
		shoptc_img.DisplayImage(Sources.CURRENTSHOP.shopImg, false);
		shoptc_zheke
				.setText("套餐 "
						+ Sources.CURRENTSHOP.dayType
								.get(Sources.CURRENTSHOP.item).discount + " 折");
	}

	@Override
	void initListen() {
		super.initListen();
		shoptc_info.setOnClickListener(this);
		shoptc_go.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.shoptc_info:
			PM.onBtnAnim(shoptc_info);
			Skip.mNext(this, ShopTcInfoActivity.class);
			break;
		case R.id.shoptc_go:
			PM.onBtnAnim(shoptc_go);
			if (shoptc_name.getText().length() < 1) {
				PM.requestFocus(shoptc_name);
				shoptc_name.setError("姓名不能为空");
			} else if (shoptc_tel.getText().length() < 11
					|| shoptc_tel.getText().length() > 13) {
				PM.requestFocus(shoptc_tel);
				shoptc_tel.setError("电话号码不正确");
			} else {
				if (userInfo.getBoolean("islogin", false)) {
					userInfo.edit()
							.putString("mobile",
									shoptc_tel.getText().toString()).commit();
					userInfo.edit()
							.putString("name", shoptc_name.getText().toString())
							.commit();
					if (flag) {
						flag = false;
						ProductInfoBean beans = new ProductInfoBean();
						beans.productId = Sources.CURRENTSHOP.dayType
								.get(Sources.CURRENTSHOP.item).sundayId;
						beans.productNum = 1;
						beans.productPrice = Sources.SHOPTCBEAN.tcYuanJia;
						beans.productName = Sources.CURRENTSHOP.shopName
								+ "-套餐产品";
						if (Sources.ORDERDETABEAN.productList == null) {
							List<ProductInfoBean> df = new ArrayList<ProductInfoBean>();
							Sources.ORDERDETABEAN.productList = df;
						}
						Sources.ORDERDETABEAN.productList.add(beans);
						addmod.mGetAddOrderUrlByCount(
								Sources.CURRENTSHOP.bAccount,
								userInfo.getString("account", ""),
								userInfo.getInt("userId", -1),
								Sources.CURRENTSHOP.dayType
										.get(Sources.CURRENTSHOP.item).sundayId,
								7,
								shoptc_name.getText().toString(),
								userInfo.getString("userName", ""),
								shoptc_tel.getText().toString(),
								"" + Sources.SHOPTCBEAN.tcId,
								Sources.CURRENTSHOP.shopName + "-套餐产品",
								"" + Sources.SHOPTCBEAN.tcYuanJia,
								"" + 1,
								Sources.SHOPTCBEAN.tcYuanJia,
								Sources.SHOPTCBEAN.tcXianJia,
								Sources.CURRENTSHOP.dayType
										.get(Sources.CURRENTSHOP.item).discount,
								Sources.SHOPTCBEAN.tcDingJin,
								Sources.SHOPTCBEAN.tcYuE);
					}
				} else {
					Skip.mNext(this, LoginActivity.class,
							R.anim.activity_push_up_in,
							R.anim.activity_no_anim, false);
				}
			}
			break;
		}
	}

	@Override
	public void mSuccess() {
		shoptc_xiuzhi.setText(Sources.XIUZHI);
	}

	@Override
	public void mSuccess1() {
		flag = true;
		shoptc_jianshao.setText(Sources.SHOPTCBEAN.tcName);
		shoptc_total.setText("原价：￥" + Sources.SHOPTCBEAN.tcYuanJia);
		shoptc_sun.setText("套餐价：￥" + Sources.SHOPTCBEAN.tcXianJia);
		shoptc_ding.setText("定金：￥" + Sources.SHOPTCBEAN.tcDingJin);
		shoptc_fu.setText("到店支付：￥" + Sources.SHOPTCBEAN.tcYuE);
	}

	@Override
	public void mSuccess2() {
		Skip.mNext(this, AlixBuy.class);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (userInfo.getBoolean("islogin", false)) {
			shoptc_name.setText(userInfo.getString("name", ""));
			shoptc_tel.setText(userInfo.getString("mobile", ""));
		}
	}
}