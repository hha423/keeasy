package cn.keeasy.business.mod;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import cn.evan.tools.PhoneState;
import cn.keeasy.business.base.IMod;
import cn.keeasy.business.bean.ZheBean;
import cn.keeasy.business.util.Sources;

public class ZhekouMod extends BaseNetMod {

	public ZhekouMod(Context mContext, IMod mod) {
		super(mContext, mod);
	}

	// 查看修改折扣信息
	public void mGetZheInfo(int shopid) {
		mGetArea(IP + "limitBuyShopById?shopId=" + shopid);
	}

	@Override
	public void mGetInfo(String content) {
		super.mGetInfo(content);
		if (content != null && !content.equals("null") && !content.equals("")) {
			try {
				ZheBean bean = new ZheBean();
				JSONObject ojson = new JSONObject(content);
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
					} else if (name.equals("sumNum")) {
						bean.sumNum = ojson.getString(name);
					} else if (name.equals("endNum")) {
						bean.endNum = ojson.getString(name);
					}
				}
				if (bean.err != null && !"".equals(bean.err)) {
					PhoneState.showToast(context, bean.err);
					return;
				}
				Sources.ZHEBEAN = bean;
				if ("0".equals(bean.type)) {
					Sources.ZHEBEAN = new ZheBean();
					Sources.ZHEBEAN.type = ojson.getString("type");
				}
				mod.mSuccess();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			PhoneState.showToast(context, "请求错误！");
			mod.mSuccess();
		}
	}
}