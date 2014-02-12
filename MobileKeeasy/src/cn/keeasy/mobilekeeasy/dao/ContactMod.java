package cn.keeasy.mobilekeeasy.dao;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import cn.keeasy.mobilekeeasy.data.IMod;
import cn.keeasy.mobilekeeasy.data.Sources;
import cn.keeasy.mobilekeeasy.entity.ContactBean;

public class ContactMod extends BaseNetMod {

	public ContactMod(Context mContext, IMod mod) {
		super(mContext, mod);
	}

	// 请求联系人
	public void mGetFriendListUrl(String userAccount, int userid) {
		// waitdg.mInitShow("好友列表加载中，请稍候！");
		mGetArea(IP + "getContactListById?account=" + userAccount + "&userid="
				+ userid);
	}

	@Override
	public void mGetInfo(String content) {
		super.mGetInfo(content);
		if (content != null && !content.equals("null")) {
			List<ContactBean> list = new ArrayList<ContactBean>();
			ContactBean sBean = null;
			try {
				JSONObject zjson = new JSONObject(content);
				for (int i = 0; i < zjson.names().length(); i++) {
					String names = zjson.names().get(i).toString();
					sBean = new ContactBean();
					JSONObject json = new JSONObject(zjson.getString(names));
					for (int j = 0; j < json.names().length(); j++) {
						String name = json.names().get(j).toString();
						if (name.equals("contactId")) {
							sBean.contact_Id = json.getInt(name);
						} else if (name.equals("contactName")) {
							sBean.contactName = json.getString(name);
						} else if (name.equals("contactAccount")) {
							sBean.contactAccount = json.getString(name);
						} else if (name.equals("contactLogo")) {
							sBean.contactPhoto = json.getString(name);
						}
					}
					list.add(sBean);
				}
				Sources.CONTACTLIST.clear();
				Sources.CONTACTLIST.addAll(list);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		mod.mSucfriend();
	}
}
