package cn.keeasy.mobilekeeasy.dao;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.baidu.platform.comapi.basestruct.GeoPoint;
import cn.keeasy.mobilekeeasy.data.IMod;
import cn.keeasy.mobilekeeasy.data.Sources;
import cn.keeasy.mobilekeeasy.entity.BusinessBean;
import cn.keeasy.mobilekeeasy.entity.Poi;
import cn.keeasy.mobilekeeasy.utils.PM;

public class MapSearchMod extends BaseNetMod {

	public MapSearchMod(Context mContext, IMod mod) {
		super(mContext, mod);
	}

	// 根据地图范围搜索店铺
	public void mGetShopListUrl(GeoPoint point, long log, long lat, int page) {
		// waitdg.mInitShow("店铺信息加载中…");
		Poi poi = PM.mGetPoi(point, lat, log);
		mGetArea(IP + "getsundayShopByMapFangWei?east=" + poi.you + "&west="
				+ poi.zuo + "&south=" + poi.xia + "&north=" + poi.shang
				+ "&dayType=" + 7 + "&page=" + page // 一个页面显示多少家店铺
				+ "&maxNum=" + 50);
	}

	@Override
	public void mGetInfo(String content) {
		super.mGetInfo(content);
		if (content != null && !content.equals("null")) {
			List<BusinessBean> list = new ArrayList<BusinessBean>();
			BusinessBean sBean = null;
			try {
				JSONObject zjson = new JSONObject(content);
				for (int i = 0; i < zjson.names().length(); i++) {
					String namess = zjson.names().get(i).toString();
					sBean = new BusinessBean();
					JSONObject json = new JSONObject(zjson.getString(namess));
					for (int j = 0; j < json.names().length(); j++) {
						String name = json.names().get(j).toString();
						if (name.equals("account")) {
							sBean.account = json.getString(name);
						} else if (name.equals("shopName")) {
							sBean.shopName = json.getString(name);
						} else if (name.equals("shopAddr")) {
							sBean.shopAddr = json.getString(name);
						} else if (name.equals("addrX")) {
							sBean.addrX = json.getDouble(name);
						} else if (name.equals("addrY")) {
							sBean.addrY = json.getDouble(name);
						} else if (name.equals("dayType")) {
							sBean.dayType = json.getInt(name);
						} else if (name.equals("sumPage")) {
							sBean.sumPage = json.getInt(name);
						} else if (name.equals("by")) {
							sBean.by = json.getInt(name);
						}
					}
					list.add(sBean);
				}
				Sources.MAPBUESLIST.clear();
				Sources.MAPBUESLIST.addAll(list);
				mod.mSuccess();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}
