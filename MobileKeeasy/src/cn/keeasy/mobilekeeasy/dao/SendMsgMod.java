package cn.keeasy.mobilekeeasy.dao;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import cn.evan.tools.Utils;

import cn.keeasy.mobilekeeasy.data.IMod;
import cn.keeasy.mobilekeeasy.data.Sources;
import cn.keeasy.mobilekeeasy.entity.ChatBean;

public class SendMsgMod extends BaseNetMod {

	SharedPreferences sp;

	public SendMsgMod(Context mContext, IMod mod, SharedPreferences spp) {
		super(mContext, mod);
		this.sp = spp;
	}

	// 发送消息
	public void mSendMessage(Integer userid, String chatAccount, String type,
			String content, String userAccount) {
		mGetArea(IP + "sendMessage?userid=" + userid + "&chatAccount="
				+ chatAccount + "&type=" + type + "&content="
				+ Utils.uniCode(content) + "&account=" + userAccount);
	}

	// 发送多媒体消费
	public void mSendMidMessage(byte[] content, String type) {
		// waitdg.mInitShow("信息发送中…");
		com.keeasy.phone.bean.request.ChatBean bean = new com.keeasy.phone.bean.request.ChatBean();
		bean.setData(content);
		bean.setIsFlag(type);
		if (!sp.getString("account", "").equals("")) {
			bean.setUserAccount(sp.getString("account", ""));
			bean.setUserAccount_Id(sp.getInt("userId", -1));
		} else {
			bean.setUserAccount("4298660");
			bean.setUserAccount_Id(5861);
		}
		if (Sources.CURRENTIC != null) {
			bean.setContactAccount(Sources.CURRENTIC.contactAccount);
			bean.setContactAccount_Id(Sources.CURRENTIC.contact_Id);
		}
		mPostArea(IP + "sendMessageAndroid", bean);
	}

	@Override
	public void mGetInfo(String content) {
		super.mGetInfo(content);
		if (content != null && !content.equals("null") && !content.equals("")) {
			ArrayList<ChatBean> list = Sources.CHATINFOMAP
					.get(Sources.CURRENTIC.contactAccount);
			if (list == null) {
				list = new ArrayList<ChatBean>();
			}
			ChatBean cBean = null;
			try {
				cBean = new ChatBean();
				JSONObject json = new JSONObject(content);
				int flg = json.names().length();
				for (int j = 0; j < flg; j++) {
					String cont = json.names().get(j).toString();
					if (cont.equals("type")) {
						cBean.type = json.getString(cont);
					} else if (cont.equals("info")) {
						cBean.chatContent = json.getString(cont);
					} else if (cont.equals("time")) {
						cBean.chatTime = json.getString(cont);
					}
				}
				cBean.userAccount = sp.getString("account", "");
				list.add(cBean);
				Sources.CHATINFOMAP.put(Sources.CURRENTIC.contactAccount, list);
				if (mod != null) {
					mod.mSuccess();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

}
