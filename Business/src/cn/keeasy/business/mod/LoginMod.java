package cn.keeasy.business.mod;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import cn.evan.tools.PhoneState;
import cn.keeasy.business.base.IMod;
import cn.keeasy.business.bean.UserBean;
import cn.keeasy.business.util.Sources;

public class LoginMod extends BaseNetMod {

	public LoginMod(Context mContext, IMod mod) {
		super(mContext, mod);
	}

	// 用户登录
	public void mGetLogin(String user, String pwd) {
		// waitdg.mInitShow("正在努力为您登录中…");
		mGetArea(IP + "shopBackLogin?userAccount=" + user + "&userPassword="
				+ pwd);
	}

	@Override
	public void mGetInfo(String content) {
		super.mGetInfo(content);
		if (content != null && !content.equals("null") && !content.equals("")) {
			try {
				JSONObject json = new JSONObject(content);
				String state = json.getString("loginFlag");
				if (state.equals("-1")) {
					PhoneState.showToast(context, "帐号不存在，请重试！");
				} else if (state.equals("-2")) {
					PhoneState.showToast(context, "账户或密码错误，请重试！");
				} else if (state.equals("-3")) {
					PhoneState.showToast(context, "您的帐号不是商家号，分组错误！");
				} else if (state.equals("-4")) {
					PhoneState.showToast(context, "服务器繁忙，请稍候重试！");
				} else if (state.equals("1")) {
					PhoneState.showToast(context, "您登录的帐号有异常，请重试！");
				} else {
					UserBean bean = new UserBean();
					for (int i = 0; i < json.names().length(); i++) {
						String name = json.names().get(i).toString();
						if (name.equals("type")) {
							bean.type = json.getInt(name);
						} else if (name.equals("subIC")) {
							bean.subIC = json.getString(name);
						} else if (name.equals("subQX")) {
							bean.subQX = json.getString(name);
						} else if (name.equals("userId")) {
							bean.userId = json.getInt(name);
						} else if (name.equals("shopId")) {
							bean.shopId = json.getInt(name);
						} else if (name.equals("shopIC")) {
							bean.shopIC = json.getString(name);
						} else if (name.equals("shopName")) {
							bean.shopName = json.getString(name);
						} else if (name.equals("shopAddr")) {
							bean.shopAddr = json.getString(name);
						}
					}
					Sources.ISLOGIN = true;
					Sources.USERBEAN = bean;
					mod.mSuccess();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}
