package cn.keeasy.mobilekeeasy.dao;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import cn.evan.tools.PhoneState;

import cn.keeasy.mobilekeeasy.data.IMod;

public class LoginMod extends BaseNetMod {

	private SharedPreferences sp;

	public LoginMod(Context mContext, IMod mod) {
		super(mContext, mod);
	}

	public LoginMod(Context mContext, IMod mod, SharedPreferences spp) {
		super(mContext, mod);
		sp = spp;
	}

	// 登陆请求
	public void mGetLoginUrlByAccount(String account, String pwd) {
		// waitdg.mInitShow("正在努力为您登录中…");
		mGetArea(IP + "userLogin?userAccount=" + account + "&userPassword="
				+ pwd);
	}

	@Override
	public void mGetInfo(String content) {
		super.mGetInfo(content);
		if (content != null && !content.equals("null")) {
			try {
				JSONObject json = new JSONObject(content);
				String state = json.getString("loginFlag");
				if (state.equals("-1")) {
					PhoneState.showToast(context, "帐号不存在！");
				} else if (state.equals("-2")) {
					PhoneState.showToast(context, "账户或密码错误！");
				} else if (state.equals("-3")) {
					PhoneState.showToast(context, "账户分组错误！");
				} else if (state.equals("-4")) {
					PhoneState.showToast(context, "服务器繁忙！");
				} else {
					for (int i = 0; i < json.names().length(); i++) {
						String name = json.names().get(i).toString();
						if (name.equals("userAccount")) {
							sp.edit()
									.putString("account", json.getString(name))
									.commit();
						} else if (name.equals("userid")) {
							sp.edit().putInt("userId", json.getInt(name))
									.commit();
						} else if (name.equals("userName")) {
							sp.edit()
									.putString("userName", json.getString(name))
									.commit();
						} else if (name.equals("userLogo")) {
							sp.edit().putString("pic", json.getString(name))
									.commit();
						}
					}
					sp.edit().putBoolean("islogin", true).commit();
					mod.mSuccess();
				}
			} catch (JSONException e) {
				PhoneState.showToast(context, "服务器连接错误，请退出重试！");
				e.printStackTrace();
			}
		}
	}

}
