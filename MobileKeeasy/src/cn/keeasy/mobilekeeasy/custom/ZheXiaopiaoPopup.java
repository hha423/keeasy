package cn.keeasy.mobilekeeasy.custom;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import cn.evan.tools.PhoneState;
import cn.keeasy.mobilekeeasy.R;
import cn.keeasy.mobilekeeasy.adapter.ShopCartAdapter;
import cn.keeasy.mobilekeeasy.dao.AddOrderMod;
import cn.keeasy.mobilekeeasy.data.IMod;
import cn.keeasy.mobilekeeasy.data.Sources;
import cn.keeasy.mobilekeeasy.utils.PM;

public class ZheXiaopiaoPopup extends PopupWindow {

	private View view;
	private Context contxt;
	private Handler handle;
	private ListView xp_list;
	private EditText xp_name, xp_tel;
	private ShopCartAdapter scadpter;
	private TextView xp_total, xp_zheke, xp_sunprice, xp_dingjin, xp_btn;
	double dis; // 折扣
	double sun;// 折后价
	double price; // 定金
	String pids;
	String pnames;
	String pprices;
	String pnumber;
	double sunprice;
	double sunprices = 0.0;
	private AddOrderMod addmod;
	StringBuffer pidsbf, pnamesbf, ppricesbf, pnumberbf;

	public ZheXiaopiaoPopup(Context context) {
		super(context);
		this.contxt = context;
		view = LayoutInflater.from(context).inflate(R.layout.xiaopiao, null);
		xp_btn = (TextView) view.findViewById(R.id.xp_btn);
		xp_total = (TextView) view.findViewById(R.id.xp_total);
		xp_zheke = (TextView) view.findViewById(R.id.xp_zheke);
		xp_sunprice = (TextView) view.findViewById(R.id.xp_sunprice);
		xp_dingjin = (TextView) view.findViewById(R.id.xp_dingjin);
		xp_name = (EditText) view.findViewById(R.id.xp_name);
		xp_tel = (EditText) view.findViewById(R.id.xp_tel);
		xp_list = (ListView) view.findViewById(R.id.xp_list);
		setContentView(view);
	}

	public void showPopup(View parent, final Handler handler,
			final SharedPreferences spp, IMod mod) {
		this.handle = handler;
		addmod = new AddOrderMod(contxt, mod);
		xp_name = (EditText) view.findViewById(R.id.xp_name);
		xp_tel = (EditText) view.findViewById(R.id.xp_tel);
		scadpter = new ShopCartAdapter(contxt, handle);
		xp_list.setAdapter(scadpter);
		if (spp.getBoolean("islogin", false)) {
			xp_name.setText(spp.getString("name", ""));
			xp_tel.setText(spp.getString("mobile", ""));
		}
		xp_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PM.onBtnAnim(xp_btn);
				if (xp_name.getText().length() < 1) {
					PhoneState.cenShowToast(contxt, "姓名不能为空");
				} else if (xp_tel.getText().length() < 11
						|| xp_tel.getText().length() > 13) {
					PhoneState.cenShowToast(contxt, "电话号码不正确");
				} else if (Sources.CARTLIST.size() < 1) {
					PhoneState.cenShowToast(contxt, "请先去选择菜单");
				} else if (Sources.ZHEBEAN.bAccount.equals(spp.getString(
						"account", ""))) {
					PhoneState.cenShowToast(contxt, "不能购买自己店铺的商品");
				} else {
					if (spp.getBoolean("islogin", false)) {
						spp.edit()
								.putString("mobile",
										xp_tel.getText().toString()).commit();
						spp.edit()
								.putString("name", xp_name.getText().toString())
								.commit();
						pidsbf = new StringBuffer();
						pnamesbf = new StringBuffer();
						ppricesbf = new StringBuffer();
						pnumberbf = new StringBuffer();
						for (int i = 0; i < Sources.CARTLIST.size(); i++) {
							if (i == 0) {
								pidsbf.append(Sources.CARTLIST.get(i).productId);
								pnamesbf.append(Sources.CARTLIST.get(i).productName);
								ppricesbf.append(Sources.CARTLIST.get(i).productPrice);
								pnumberbf.append(Sources.CARTLIST.get(i).productNum);
							} else {
								pidsbf.append(","
										+ Sources.CARTLIST.get(i).productId);
								pnamesbf.append(","
										+ Sources.CARTLIST.get(i).productName);
								ppricesbf.append(","
										+ Sources.CARTLIST.get(i).productPrice);
								pnumberbf.append(","
										+ Sources.CARTLIST.get(i).productNum);
							}
						}
						pids = pidsbf.toString();
						pnames = pnamesbf.toString().trim();
						pprices = ppricesbf.toString();
						pnumber = pnumberbf.toString();
						Sources.ORDERDETABEAN.productList = Sources.CARTLIST;
						addmod.mGetAddOrderUrlByCount(Sources.ZHEBEAN.bAccount,
								spp.getString("account", ""),
								spp.getInt("userId", -1),
								Sources.ZHEBEAN.shopId, 8,
								spp.getString("userName", ""), xp_name
										.getText().toString().trim(), xp_tel
										.getText().toString().trim(), pids,
								pnames, pprices, pnumber, sunprices, sun, dis,
								price, (sun - price));

					} else {
						handler.sendEmptyMessage(3);
					}
				}
			}
		});
		setTextInfo();
		setFocusable(true); // 使其聚集
		setOutsideTouchable(false); // 设置允许在外点击消失
		setWidth(LayoutParams.FILL_PARENT);
		setHeight(LayoutParams.WRAP_CONTENT);
		setBackgroundDrawable(new BitmapDrawable()); // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
		setAnimationStyle(R.style.pop_anim);
		showAsDropDown(parent, 0, 0);
	}

	public void setTextInfo() {
		sunprice = 0.0;
		for (int i = 0; i < Sources.CARTLIST.size(); i++) {
			sunprice += Sources.CARTLIST.get(i).productPrice
					* Sources.CARTLIST.get(i).productNum;
		}
		sunprices = Double.parseDouble(PhoneState.setDecimal(sunprice, 1));
		dis = Sources.ZHEBEAN.zhekou;
		sun = Double.parseDouble(PhoneState.setDecimal(sunprices * (dis / 10),
				1));
		price = Double.parseDouble(PhoneState.setDecimal(sun * Sources.TICHENG,
				1));
		xp_total.setText("合计：￥" + sunprices + "元");
		xp_zheke.setText("折扣 " + dis + " 折");
		xp_sunprice.setText("活动价：￥" + sun + "元");
		if (price <= 0.1) {
			if (sunprices == 0.0) {
				xp_dingjin.setText("支付金额：￥ " + price);
			} else {
				xp_dingjin.setText("支付金额：￥ 0.1");
				price = 0.1;
			}
		} else {
			xp_dingjin.setText("支付金额：￥ " + price);
		}
	}

}
