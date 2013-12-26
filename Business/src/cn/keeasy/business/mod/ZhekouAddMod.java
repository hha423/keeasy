package cn.keeasy.business.mod;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import cn.evan.tools.PhoneState;
import cn.keeasy.business.base.IMod;
import cn.keeasy.business.bean.ZheBean;
import cn.keeasy.business.util.Sources;

public class ZhekouAddMod extends BaseNetMod {

	public ZhekouAddMod(Context mContext, IMod mod) {
		super(mContext, mod);
	}

	// 添加折扣信息
	public void mSetZheInfo(int shopid, String zheKou, String beginTime,
			String endTime) {
		mGetArea(IP + "updateLimitBuyShop?shopId=" + shopid + "&zheKou="
				+ zheKou + "&beginTime=" + beginTime + "&endTime=" + endTime);
	}

	@Override
	public void mGetInfo(String content) {
		super.mGetInfo(content);
		if (content != null && !content.equals("null") && !content.equals("")) {
			try {
				JSONObject ojson = new JSONObject(content);
				ZheBean bean = new ZheBean();
				int pag = ojson.names().length();
				for (int i = 0; i < pag; i++) {
					String name = ojson.names().get(i).toString();
					if (name.equals("error")) {
						bean.err = ojson.getString(name);
					} else if (name.equals("type")) {
						bean.type = ojson.getString(name);
					} else if (name.equals("begin")) {
						bean.begin = ojson.getString(name);
					} else if (name.equals("end")) {
						bean.end = ojson.getString(name);
					} else if (name.equals("zheKou")) {
						bean.zheKou = ojson.getString(name);
					}
				}
				if (bean.type != null && "true".equals(bean.type)) {
					Sources.ZHEBEAN = bean;
					mod.mSuccess();
				}
				if (bean.err != null && !"".equals(bean.err)) {
					PhoneState.showToast(context, bean.err);
					return;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}