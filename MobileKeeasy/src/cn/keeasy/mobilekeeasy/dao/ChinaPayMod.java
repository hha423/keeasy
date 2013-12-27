package cn.keeasy.mobilekeeasy.dao;

import android.content.Context;

import cn.keeasy.mobilekeeasy.data.IMod;

public class ChinaPayMod extends BaseNetMod {

	public ChinaPayMod(Context mContext, IMod mod) {
		super(mContext, mod);
	}

	// 请求银联支付流水号
	public void mGetUnionPayNum(String orderid, double money) {
		mGetArea(IP + "yinlianSendOrder?orderid=" + orderid + "&money=" + money);
	}

	@Override
	public void mGetInfo(String content) {
		super.mGetInfo(content);
		if (content != null && !content.equals("null")) {
			mod.mSuccess(content);
		}
	}
}
