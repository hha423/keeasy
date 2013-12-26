package cn.keeasy.business.mod;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import cn.evan.tools.PhoneState;
import cn.keeasy.business.base.IMod;
import cn.keeasy.business.bean.OrderBean;
import cn.keeasy.business.bean.ProductBean;
import cn.keeasy.business.util.Sources;

public class ConfirmMod extends BaseNetMod {

	public ConfirmMod(Context mContext, IMod mod) {
		super(mContext, mod);
	}

	// 消费验证
	public void mOrderVerify(String userCode, String account) {
		mGetArea(IP + "shopBackSubscribeOrder?userCode=" + userCode
				+ "&account=" + account);
	}

	@Override
	public void mGetInfo(String content) {
		super.mGetInfo(content);
		if (content != null && !content.equals("null") && !content.equals("")) {
			try {
				JSONObject ojson = new JSONObject(content);
				String err = ojson.names().get(0).toString();
				if (err.equals("error")) {
					PhoneState.cenShowToast(context, ojson.getString("error"));
				} else {
					OrderBean bean = new OrderBean();
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
								String pname = pjson.names().get(k).toString();
								JSONObject pej = new JSONObject(
										pjson.getString(pname));
								int pag = pej.names().length();
								for (int l = 0; l < pag; l++) {
									String pne = pej.names().get(l).toString();
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
					Sources.ORDERBEAN.add(bean);
					mod.mSuccess();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			PhoneState.cenShowToast(context, "消费码已超过有效期或非该店铺消费码!");
		}
	}

}