package cn.keeasy.mobilekeeasy.dao;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import cn.keeasy.mobilekeeasy.data.IMod;
import cn.keeasy.mobilekeeasy.data.Sources;

public class MoneyMod extends BaseNetMod {

	public MoneyMod(Context mContext, IMod mod) {
		super(mContext, mod);
	}

	public void mGetUserMoney(String userAccount) {
		mGetArea(IP + "userCouponMoney?userAccount=" + userAccount);
	}

	@Override
	public void mGetInfo(String content) {
		super.mGetInfo(content);
		if (content != null && !content.equals("null")) {
			try {
				JSONObject json = new JSONObject(content);
				int flg = json.names().length();
				for (int i = 0; i < flg; i++) {
					String name = json.names().get(i).toString();
					if (name.equals("unusedMoney")) {
						Sources.UnusedMoney = json.getDouble(name);
					} else if (name.equals("usedMoney")) {
						Sources.UsedMoney = json.getDouble(name);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		mod.mSumoney();
	}
}
