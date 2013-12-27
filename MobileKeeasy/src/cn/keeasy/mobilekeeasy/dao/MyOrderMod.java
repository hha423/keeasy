package cn.keeasy.mobilekeeasy.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import cn.keeasy.mobilekeeasy.data.IMod;
import cn.keeasy.mobilekeeasy.data.Sources;
import cn.keeasy.mobilekeeasy.entity.OrderBean;
import cn.keeasy.mobilekeeasy.entity.OrderListBean;
import cn.keeasy.mobilekeeasy.utils.PM;

public class MyOrderMod extends BaseNetMod {

	public MyOrderMod(Context mContext, IMod mod) {
		super(mContext, mod);
	}

	// 获取所有订单
	public void mGetOrderListUrl(String userAccount, Integer userid) {
		// waitdg.mInitShow("订单列表加载中，请稍候！");
		mGetArea(IP + "getUserServiceOrder?account=" + userAccount + "&userid="
				+ userid);
	}

	@Override
	public void mGetInfo(String content) {
		super.mGetInfo(content);
		if (content != null && !content.equals("null")) {
			List<OrderListBean> list = new ArrayList<OrderListBean>();
			OrderBean sBean = null;
			OrderListBean obean = null;
			try {
				JSONObject zjson = new JSONObject(content);
				for (int i = 0; i < zjson.names().length(); i++) {
					String zname = zjson.names().get(i).toString();
					sBean = new OrderBean();
					JSONObject json = new JSONObject(zjson.getString(zname));
					for (int j = 0; j < json.names().length(); j++) {
						String name = json.names().get(j).toString();
						if (name.equals("orderId")) {
							sBean.orderId = json.getString(name);
						} else if (name.equals("shopName")) {
							sBean.shopName = json.getString(name);
						} else if (name.equals("orderState")) {
							sBean.orderState = json.getString(name);
						} else if (name.equals("orderTime")) {
							sBean.orderTime = json.getString(name);
						} else if (name.equals("orderNum")) {
							sBean.orderNum = json.getString(name);
						} else if (name.equals("by")) {
							sBean.by = json.getInt(name);
						}
					}
					boolean flag = false;

					for (OrderListBean orderListBean : list) {
						if (orderListBean.state.equals(sBean.orderState)) {
							orderListBean.beanlist.add(sBean);
							flag = true;
						}
					}
					if (!flag) {
						obean = new OrderListBean();
						obean.state = sBean.orderState;
						obean.stateName = PM.getStateName(obean.state);
						obean.beanlist = new ArrayList<OrderBean>();
						obean.beanlist.add(sBean);
						list.add(obean);
					}

				}
				for (int i = 0; i < list.size(); i++) {
					Collections.sort(list.get(i).beanlist,
							new Comparator<Object>() {
								public int compare(Object a, Object b) {
									int one = ((OrderBean) a).by;
									int two = ((OrderBean) b).by;
									return one - two;
								}
							});
				}
				Sources.ORDERLIST.clear();
				Sources.ORDERLIST.addAll(list);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		mod.mSuccess();
	}
}
