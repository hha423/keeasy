package cn.keeasy.mobilekeeasy.dao;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import cn.keeasy.mobilekeeasy.data.IMod;
import cn.keeasy.mobilekeeasy.data.Sources;

public class MsgCountMod extends BaseNetMod {

	public MsgCountMod(Context mContext, IMod mod) {
		super(mContext, mod);
	}

	// 循环请求未读消息
	public void mGetMessageNum(String userAccount, Integer userid) {
		mGetArea(IP + "getUnreadMessageNum?account=" + userAccount + "&userid="
				+ userid);
	}

	@Override
	public void mGetInfo(String content) {
		super.mGetInfo(content);
		if (content != null && !content.equals("null")) {
			try {
				JSONObject json = new JSONObject(content);
				for (int i = 0; i < json.names().length(); i++) {
					String name = json.names().get(i).toString();
					Sources.NOTREADMSG.put(name, json.getString(name));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		mod.mSuccessMSg();
	}
}
