package cn.keeasy.mobilekeeasy.dao;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import cn.evan.tools.PhoneState;

import cn.keeasy.mobilekeeasy.data.IMod;

public class RegisterMod extends BaseNetMod {

	private SharedPreferences sp;

	public RegisterMod(Context mContext, IMod mod) {
		super(mContext, mod);
	}

	public RegisterMod(Context mContext, IMod mod, SharedPreferences spp) {
		super(mContext, mod);
		this.sp = spp;
	}

	// 注册用户请求
	public void mGetRegUrl(String account, String pwd) {
		// waitdg.mInitShow("正在努力提交您的注册信息…");
		mGetArea(IP + "registerUser?account=" + account + "&password=" + pwd);
	}

	@Override
	public void mGetInfo(String content) {
		super.mGetInfo(content);
		if (content != null && !content.equals("null")) {
			try {
				JSONObject json = new JSONObject(content);
				String state = json.getString("loginFlag");
				if (state.equals("-1")) {
					PhoneState.showToast(context, "服务器繁忙！");
				} else if (state.equals("-2")) {
					PhoneState.showToast(context, "账号已存在！");
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
					mod.mSuccess();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}
