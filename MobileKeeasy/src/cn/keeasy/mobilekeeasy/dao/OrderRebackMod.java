package cn.keeasy.mobilekeeasy.dao;

import android.content.Context;
import cn.evan.tools.PhoneState;

import cn.keeasy.mobilekeeasy.data.IMod;

public class OrderRebackMod extends BaseNetMod {

	public OrderRebackMod(Context mContext, IMod mod) {
		super(mContext, mod);
	}

	// 申请退款
	public void mRefundSerivceOrder(String oid) {
		// waitdg.mInitShow("正在提交申请…");
		mGetArea(IP + "refundSerivceOrder?orderid=" + oid);
	}

	@Override
	public void mGetInfo(String content) {
		super.mGetInfo(content);
		if (content != null && content.equals("true")) {
			PhoneState.showToast(context, "申请退款成功，请等待资金处理…");
			mod.mSuccess();
		} else {
			PhoneState.showToast(context, "申请退款失败，请重试并与商家联系或申请第三方介入");
		}
	}
}
