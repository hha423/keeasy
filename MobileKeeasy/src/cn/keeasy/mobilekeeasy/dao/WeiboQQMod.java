package cn.keeasy.mobilekeeasy.dao;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import cn.evan.tools.PhoneState;
import cn.evan.tools.Utils;

import cn.keeasy.mobilekeeasy.data.IMod;

public class WeiboQQMod extends BaseNetMod {

	private SharedPreferences sp;

	public WeiboQQMod(Context mContext, IMod mod) {
		super(mContext, mod);
	}

	// 微博QQ登录
	public void mGetThirdLogin(String type, String userid, SharedPreferences spp) {
		// waitdg.mInitShow("好友列表加载中，请稍候！");
		this.sp = spp;
		mGetArea(IP + "weiboQQLogin?openId=" + userid + "&type=" + type);
	}

	// 注册用户请求
	public void mGetThirReg(String account, String img, String id, String type) {
		mGetArea(IP + "weiboQQRegister?name=" + Utils.uniCode(account)
				+ "&img=" + img + "&openId=" + id + "&type=" + type);
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
				} else if (state.equals("-5")) {
					PhoneState.showToast(context, "QQ登陆信息获取不完整!\n错误编码:-5");
				} else if (state.equals("-6")) {
					PhoneState.showToast(context, "QQ登陆信息获取不完整!\n错误编码:-6");
				} else if (state.equals("-7")) {
					mod.mSuccess1();
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
					mod.mSuccess2();
				}
			} catch (JSONException e) {
				PhoneState.showToast(context, "服务器连接错误，请退出重试！");
				e.printStackTrace();
			}
		}
	}
}
