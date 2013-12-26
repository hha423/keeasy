package cn.keeasy.business.mod;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import cn.keeasy.business.base.IMod;

public class LBSMod extends BaseNetMod {

	public LBSMod(Context mContext, IMod mod) {
		super(mContext, mod);
	}

	// 店铺坐标提交
	public void mSetPoint(int shopId, double lat, double log, String account) {
		waitdg.mInitShow("正在为您提交坐标位置…");
		mGetArea(IP + "shopBackupdatePoint?shopId=" + shopId + "&lat=" + lat
				+ "&log=" + log + "&account=" + account);
	}

	@Override
	public void mGetInfo(String content) {
		super.mGetInfo(content);
		if (content != null && !content.equals("null") && !content.equals("")) {
			try {
				JSONObject json = new JSONObject(content);
				mod.mSuccess(json.getString("error"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}
