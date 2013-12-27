package cn.keeasy.mobilekeeasy.dao;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import cn.keeasy.mobilekeeasy.data.IMod;
import cn.keeasy.mobilekeeasy.data.Sources;

public class ZheShopMod extends BaseNetMod {

	public ZheShopMod(Context mContext, IMod mod) {
		super(mContext, mod);
	}

	// 获取折扣店铺介绍
	public void mGetHdShopInfo(int sid) {
		mGetArea(IP + "shopInfoByIdIPhone?shopid=" + sid);
	}

	@Override
	public void mGetInfo(String content) {
		super.mGetInfo(content);
		if (content != null && !content.equals("null")) {
			try {
				JSONObject json = new JSONObject(content);
				Sources.JIANSHAO = json.getString("syn");
				Sources.XIUZHI = json.getString("time");
				Sources.PHOTO = json.getString("img");
				Sources.TICHENG = json.getDouble("bili");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			mod.mSuccess();
		}
	}
}
