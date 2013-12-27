package cn.keeasy.mobilekeeasy.dao;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import cn.keeasy.mobilekeeasy.data.IMod;
import cn.keeasy.mobilekeeasy.data.Sources;

public class WeiboUserMod extends BaseNetMod {

	public WeiboUserMod(Context mContext, IMod mod) {
		super(mContext, mod);
	}

	// 请求联系人
	public void mGetFriendListUrl(String userid, String mAccessToken) {
		// waitdg.mInitShow("好友列表加载中，请稍候！");
		mGetArea("https://api.weibo.com/2/users/show.json?uid=" + userid
				+ "&access_token=" + mAccessToken);
	}

	@Override
	public void mGetInfo(String content) {
		super.mGetInfo(content);
		if (content != null && !content.equals("null")) {
			try {
				JSONObject zjson = new JSONObject(content);
				Sources.WEIBOUSER.id = zjson.getString("id");
				Sources.WEIBOUSER.name = zjson.getString("name");
				Sources.WEIBOUSER.location = zjson.getString("location");
				Sources.WEIBOUSER.description = zjson.getString("description");
				Sources.WEIBOUSER.domain = zjson.getString("domain");
				Sources.WEIBOUSER.weihao = zjson.getString("weihao");
				Sources.WEIBOUSER.gender = zjson.getString("gender");
				Sources.WEIBOUSER.profile_image_url = zjson
						.getString("profile_image_url");
				mod.mSuccess1();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}
