package cn.keeasy.business.mod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import cn.evan.tools.PhoneState;
import cn.keeasy.business.base.IMod;
import cn.keeasy.business.bean.OrderBean;
import cn.keeasy.business.bean.ProductBean;
import cn.keeasy.business.util.Sources;

public class OrderMod extends BaseNetMod {

	public OrderMod(Context mContext, IMod mod) {
		super(mContext, mod);
	}

	// 订单获取
	public void mGetOrder(int searchStatus, int page, String account) {
		// waitdg.mInitShow("正在努力为您获取订单中…");
		mGetArea(IP + "shopBackqueryOrder?searchStatus=" + searchStatus
				+ "&page=" + page + "&maxNum=" + MAX_RESULT + "&account="
				+ account);
	}

	@Override
	public void mGetInfo(String content) {
		super.mGetInfo(content);
		if (content != null && !content.equals("null") && !content.equals("")) {
			try {
				List<OrderBean> list = new ArrayList<OrderBean>();
				JSONObject json = new JSONObject(content);
				String err = json.names().get(0).toString();
				if (err.equals("error")) {
					PhoneState.showToast(context, json.getString("error"));
				} else {
					int page = json.names().length();
					for (int i = 0; i < page; i++) {
						OrderBean bean = new OrderBean();
						String name = json.names().get(i).toString();
						JSONObject ojson = new JSONObject(json.getString(name));
						int opag = ojson.names().length();
						for (int j = 0; j < opag; j++) {
							String oname = ojson.names().get(j).toString();
							if (oname.equals("orderId")) {
								bean.orderId = ojson.getInt(oname);
							} else if (oname.equals("orderNum")) {
								bean.orderNum = ojson.getString(oname);
							} else if (oname.equals("orderTime")) {
								bean.orderTime = ojson.getString(oname);
							} else if (oname.equals("zongJia")) {
								bean.zongJia = ojson.getDouble(oname);
							} else if (oname.equals("xianJia")) {
								bean.xianJia = ojson.getDouble(oname);
							} else if (oname.equals("yiFu")) {
								bean.yingFu = ojson.getDouble(oname);
							} else if (oname.equals("yingShou")) {
								bean.yingShou = ojson.getDouble(oname);
							} else if (oname.equals("userName")) {
								bean.userName = ojson.getString(oname);
							} else if (oname.equals("orderState")) {
								bean.orderState = ojson.getString(oname);
							} else if (oname.equals("orderCode")) {
								bean.orderCode = ojson.getString(oname);
							} else if (oname.equals("by")) {
								bean.by = ojson.getInt(oname);
							} else if (oname.equals("pList")) {
								List<ProductBean> pplist = new ArrayList<ProductBean>();
								JSONObject pjson = new JSONObject(
										ojson.getString(oname));
								int ppag = pjson.names().length();
								for (int k = 0; k < ppag; k++) {
									ProductBean pbean = new ProductBean();
									String pname = pjson.names().get(k)
											.toString();
									JSONObject pej = new JSONObject(
											pjson.getString(pname));
									int pag = pej.names().length();
									for (int l = 0; l < pag; l++) {
										String pne = pej.names().get(l)
												.toString();
										if (pne.equals("pNum")) {
											pbean.pNum = pej.getInt(pne);
										} else if (pne.equals("pMoney")) {
											pbean.pMoney = pej.getDouble(pne);
										} else if (pne.equals("pName")) {
											pbean.pName = pej.getString(pne);
										}
									}
									pplist.add(pbean);
								}
								bean.pList = pplist;
							}
						}
						list.add(bean);
					}
					Collections.sort(list, new Comparator<Object>() {
						public int compare(Object a, Object b) {
							int one = ((OrderBean) a).by;
							int two = ((OrderBean) b).by;
							return one - two;
						}
					});
					Sources.ORDERBEAN.addAll(list);
					mod.mSuccess();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			PhoneState.showToast(context, "暂时没有消费成功的订单！");
		}
	}

}