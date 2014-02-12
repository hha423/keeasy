package cn.keeasy.mobilekeeasy.dao;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import cn.keeasy.mobilekeeasy.data.IMod;
import cn.keeasy.mobilekeeasy.data.Sources;
import cn.keeasy.mobilekeeasy.entity.ProductInfoBean;

public class OrderDetailMod extends BaseNetMod {

	public OrderDetailMod(Context mContext, IMod mod) {
		super(mContext, mod);
	}

	// 获取订单详情
	public void mGetOrderDetailsUrlOrderId(String oid) {
		// waitdg.mInitShow("正在获取订单详情…");
		mGetArea(IP + "getServiceOrderInfo?orderid=" + oid);
	}

	@Override
	public void mGetInfo(String content) {
		super.mGetInfo(content);
		if (content != null && !content.equals("null")) {
			try {
				JSONObject json = new JSONObject(content);
				int ii = json.names().length();
				for (int i = 0; i < ii; i++) {
					String name = json.names().get(i).toString();
					if (name.equals("serviceBalance")) {
						Sources.ORDERDETABEAN.serviceBalance = json
								.getString(name);
					} else if (name.equals("serviceTotalPrice")) {
						Sources.ORDERDETABEAN.serviceTotalPrice = json
								.getDouble(name);
					} else if (name.equals("serviceCode")) {
						Sources.ORDERDETABEAN.serviceCode = json
								.getString(name);
					} else if (name.equals("serviceSubscription")) {
						Sources.ORDERDETABEAN.serviceSubscription = json
								.getDouble(name);
					} else if (name.equals("totalPrice")) {
						Sources.ORDERDETABEAN.totalPrice = json.getDouble(name);
					} else if (name.equals("productList")) {
						JSONObject strJson = new JSONObject(
								json.getString(name));
						List<ProductInfoBean> pList = new ArrayList<ProductInfoBean>();
						int jj = strJson.names().length();
						for (int j = 0; j < jj; j++) {
							String prname = strJson.names().get(j).toString();
							JSONObject pjson = new JSONObject(
									strJson.getString(prname));
							ProductInfoBean pdbean = new ProductInfoBean();
							int kk = pjson.names().length();
							for (int k = 0; k < kk; k++) {
								String pname = pjson.names().get(k).toString();
								if (pname.equals("productCount")) {
									pdbean.productNum = pjson.getInt(pname);
								} else if (pname.equals("productPrice")) {
									pdbean.productPrice = pjson
											.getDouble(pname);
								} else if (pname.equals("productName")) {
									pdbean.productName = pjson.getString(pname);
								} else if (pname.equals("productid")) {
									pdbean.productId = pjson.getInt(pname);
								} else if (pname.equals("productType")) {
									pdbean.productType = pjson.getString(pname);
								} else if (pname.equals("productNum")) {
									pdbean.productNum = pjson.getInt(pname);
								}
							}
							pList.add(pdbean);
						}
						Sources.ORDERDETABEAN.productList = pList;
					}
				}
				mod.mSuccess1();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}
