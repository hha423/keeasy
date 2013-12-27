package cn.keeasy.mobilekeeasy.dao;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import cn.keeasy.mobilekeeasy.data.IMod;
import cn.keeasy.mobilekeeasy.data.Sources;
import cn.keeasy.mobilekeeasy.entity.ClassProductBean;

public class ShopProductClassMod extends BaseNetMod {

	public ShopProductClassMod(Context mContext, IMod mod) {
		super(mContext, mod);
	}

	// 获取消费店铺分类 DayType活动类型星期天＝7
	public void mGetShoppingClasss(int shopid, String sAccount) {
		waitdg.mInitShow("正在加载分类菜单，请稍候…");
		mGetArea(IP + "getsundayShopFenLei?shopId=" + shopid + "&account="
				+ sAccount + "&dayType=" + 7);
	}

	@Override
	public void mGetInfo(String content) {
		super.mGetInfo(content);
		if (content != null && !content.equals("null")) {
			List<ClassProductBean> list = new ArrayList<ClassProductBean>();
			ClassProductBean sBean = null;
			try {
				JSONObject json = new JSONObject(content);
				for (int i = 0; i < json.names().length(); i++) {
					JSONObject njson = new JSONObject(json.getString(json
							.names().get(i).toString()));
					sBean = new ClassProductBean();
					for (int j = 0; j < njson.names().length(); j++) {
						String name = njson.names().get(j).toString();
						if (name.equals("classId")) {
							sBean.classId = njson.getString(name);
						} else if (name.equals("className")) {
							sBean.className = njson.getString(name);
						}
					}
					list.add(sBean);
				}
				Sources.CLASSLIST.clear();
				Sources.CLASSLIST.addAll(list);
				mod.mSuccess();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}
