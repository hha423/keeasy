package cn.keeasy.mobilekeeasy.dao;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import cn.keeasy.mobilekeeasy.data.IMod;
import cn.keeasy.mobilekeeasy.data.Sources;
import cn.keeasy.mobilekeeasy.entity.AreaBean;
import cn.keeasy.mobilekeeasy.entity.NextAreaBean;

public class AreaMod extends BaseNetMod {

	public AreaMod(Context mContext, IMod mod) {
		super(mContext, mod);
	}

	public void mGetLocalAddressListUrl() {
		mGetArea(IP + "getAllAddress");
	}

	@Override
	public void mGetInfo(String content) {
		super.mGetInfo(content);
		if (content != null && !content.equals("null")) {
			List<AreaBean> list = new ArrayList<AreaBean>();
			try {
				JSONObject json = new JSONObject(content);
				int flg = json.names().length();
				for (int i = 0; i < flg; i++) {
					AreaBean aBean = new AreaBean();
					List<NextAreaBean> nList = new ArrayList<NextAreaBean>();
					String name = json.names().get(i).toString();
					String values = json.getString(name);
					String point[] = values.split(",");
					for (int j = 0; j < point.length; j++) {
						NextAreaBean nbean = new NextAreaBean();
						nbean.name = point[j];
						nList.add(nbean);
					}
					aBean.area = name;
					aBean.list = nList;
					list.add(aBean);
				}
				Sources.AREA.clear();
				Sources.AREA.addAll(list);
				// mod.mSuccess1();
			} catch (JSONException e) {
				// PhoneState.showToast(context, "服务器连接错误，请退出重试！");
				e.printStackTrace();
			}
		}
	}
}
