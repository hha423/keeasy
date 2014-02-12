package cn.keeasy.business.mod;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import cn.evan.tools.PhoneState;
import cn.keeasy.business.base.IMod;

public class AddZhekeMod extends BaseNetMod {

	public AddZhekeMod(Context mContext, IMod mod) {
		super(mContext, mod);
	}

	// 限时折扣设置
	public void mSetZheke(int shopid, String shopAdd) {
		mGetArea(IP + "?shopId=" + shopid + "&shopAddr="
				+ shopAdd);
	}

	@Override
	public void mGetInfo(String content) {
		super.mGetInfo(content);
		if (content != null && !content.equals("null") && !content.equals("")) {
			try {
				JSONObject ojson = new JSONObject(content);
				if (ojson.getString("error").equals("店铺地址修改成功！")) {
					mod.mSuccess();
				}
				PhoneState.showToast(context, ojson.getString("error"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			PhoneState.showToast(context, "提交错误！");
		}
	}

}