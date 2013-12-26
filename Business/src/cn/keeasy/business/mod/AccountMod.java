package cn.keeasy.business.mod;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import cn.evan.tools.PhoneState;
import cn.keeasy.business.base.IMod;

public class AccountMod extends BaseNetMod {

	public AccountMod(Context mContext, IMod mod) {
		super(mContext, mod);
	}

	// 获取子帐号
	public void mGetAccount(int userid) {
		// waitdg.mInitShow("正在努力为您获取订单中…");
		mGetArea(IP + "shopGetSubAccount?userId=" + userid);
	}

	@Override
	public void mGetInfo(String content) {
		super.mGetInfo(content);
		if (content != null && !content.equals("null") && !content.equals("")) {
			try {
				JSONObject json = new JSONObject(content);
				mod.mSuccess(json.getString("subIC"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			PhoneState.showToast(context, "该帐户还没有生成子帐号！");
		}
	}

}