package cn.keeasy.mobilekeeasy.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import cn.keeasy.mobilekeeasy.data.IMod;
import cn.keeasy.mobilekeeasy.data.Sources;
import cn.keeasy.mobilekeeasy.entity.ClassBean;
import cn.keeasy.mobilekeeasy.entity.NextClassBean;

public class ClassMod extends BaseNetMod {

	public ClassMod(Context mContext, IMod mod) {
		super(mContext, mod);
	}

	public void mGetShopTypeListUrl() {
		// waitdg.mInitShow("分类信息加载中…");
		mGetArea(IP + "getAllShopType");
	}

	@Override
	public void mGetInfo(String content) {
		super.mGetInfo(content);
		if (content != null && !content.equals("null")) {
			List<ClassBean> list = new ArrayList<ClassBean>();
			try {
				JSONObject json = new JSONObject(content);
				int flg = json.names().length();
				for (int i = 0; i < flg; i++) {
					ClassBean aBean = new ClassBean();
					List<NextClassBean> nList = new ArrayList<NextClassBean>();
					String name = json.names().get(i).toString();
					JSONObject bjson = new JSONObject(json.getString(name));
					int bflg = bjson.names().length();
					for (int j = 0; j < bflg; j++) {
						String bname = bjson.names().get(j).toString();
						if (bname.equals("oneId")) {
							aBean.oneId = bjson.getInt(bname);
						} else if (bname.equals("by")) {
							aBean.by = bjson.getInt(bname);
						} else if (bname.equals("oneName")) {
							aBean.oneName = bjson.getString(bname);
						} else if (bname.equals("oneTwoList")) {
							JSONObject cjson = new JSONObject(
									bjson.getString(bname));
							int cflg = cjson.names().length();
							for (int k = 0; k < cflg; k++) {
								NextClassBean nbean = new NextClassBean();
								String cname = cjson.names().get(k).toString();
								JSONObject djson = new JSONObject(
										cjson.getString(cname));
								int dflag = djson.names().length();
								for (int l = 0; l < dflag; l++) {
									String dname = djson.names().get(l)
											.toString();
									if (dname.equals("twoName")) {
										nbean.twoName = djson.getString(dname);
									} else if (dname.equals("by")) {
										nbean.by = djson.getInt(dname);
									} else if (dname.equals("twoVal")) {
										nbean.twoVal = djson.getString(dname);
									}
								}
								nList.add(nbean);
							}
						}
						aBean.oneTwoList = nList;
					}
					list.add(aBean);
				}
				Collections.sort(list, new Comparator<Object>() {
					public int compare(Object a, Object b) {
						int one = ((ClassBean) a).by;
						int two = ((ClassBean) b).by;
						return one - two;
					}
				});
				Sources.DCLASS.clear();
				Sources.DCLASS.addAll(list);
				// mod.mSuccess2();
			} catch (JSONException e) {
				// PhoneState.showToast(context, "服务器连接错误，请退出重试！");
				e.printStackTrace();
			}
		}
	}
}
