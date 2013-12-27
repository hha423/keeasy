package cn.keeasy.mobilekeeasy.dao;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import cn.keeasy.mobilekeeasy.data.IMod;
import cn.keeasy.mobilekeeasy.data.Sources;
import cn.keeasy.mobilekeeasy.entity.ShopTcBean;

public class SundayTcShopMod extends BaseNetMod {

	public SundayTcShopMod(Context mContext, IMod mod) {
		super(mContext, mod);
	}

	public void mGetSunShopTC(int sundayId) {
		// waitdg.mInitShow("套餐信息加载中…");
		mGetArea(IP + "getsundayShopTaoCan?sundayId=" + sundayId);
	}

	@Override
	public void mGetInfo(String content) {
		super.mGetInfo(content);
		if (content != null && !content.equals("null")) {
			try {
				JSONObject json = new JSONObject(content);
				ShopTcBean tcbean = new ShopTcBean();
				tcbean.tcId = json.getInt("tcId");
				tcbean.tcName = json.getString("tcName");
				tcbean.tcYuanJia = json.getDouble("tcYuanJia");
				tcbean.tcXianJia = json.getDouble("tcXianJia");
				tcbean.tcDingJin = json.getDouble("tcDingJin");
				tcbean.tcYuE = json.getDouble("tcYuE");
				tcbean.tcImgs = json.getString("tcImgs");
				Sources.SHOPTCBEAN = tcbean;
				mod.mSuccess1();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}
