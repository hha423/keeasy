package cn.keeasy.mobilekeeasy.dao;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import cn.keeasy.mobilekeeasy.data.IMod;
import cn.keeasy.mobilekeeasy.data.Sources;

public class ShopProductMod extends BaseNetMod {

	public ShopProductMod(Context mContext, IMod mod) {
		super(mContext, mod);
	}

	// 获取活动店铺介绍
	public void mGetHdShopInfo(int sid) {
		mGetArea(IP + "getSundayShopSynByIdTo?sundayId=" + sid);
	}

	@Override
	public void mGetInfo(String content) {
		super.mGetInfo(content);
		if (content != null && !content.equals("null")) {
			try {
				JSONObject json = new JSONObject(content);
				Sources.JIANSHAO = json.getString("jianjie");
				Sources.XIUZHI = json.getString("xuzhi");
				Sources.TICHENG = json.getDouble("bili");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			mod.mSuccess();
		}
	}
}
