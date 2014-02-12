package cn.keeasy.mobilekeeasy.dao;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import cn.evan.tools.PhoneState;
import cn.evan.tools.Utils;

import cn.keeasy.mobilekeeasy.data.IMod;

public class ModifyMod extends BaseNetMod {

	int wrang = -1;
	private String nick;

	public ModifyMod(Context mContext, IMod mod) {
		super(mContext, mod);
	}

	public void mChangeInfo(int userId, String content, String oldPwd,
			String newPwd) {
		this.nick = content;
		mGetArea(IP + "changeUserInfo?oldmima=" + oldPwd + "&newmima=" + newPwd
				+ "&name=" + Utils.uniCode(content) + "&userid=" + userId);
	}

	@Override
	public void mGetInfo(String content) {
		super.mGetInfo(content);
		if (content != null && !content.equals("null")) {
			try {
				JSONObject json = new JSONObject(content);
				int pag = json.names().length();
				for (int i = 0; i < pag; i++) {
					String name = json.names().get(i).toString();
					if (name.equals("mima")) {
						wrang = json.getInt(name);
						switch (wrang) {
						case 1:
							PhoneState.showToast(context, "密码修改成功！");
							mod.mSuccess();
							break;
						case 2:
							PhoneState.showToast(context, "旧密码输入不正确，请重试！");
							break;
						}
					} else if (name.equals("name")) {
						wrang = json.getInt(name);
						switch (wrang) {
						case 1:
							PhoneState.showToast(context, "昵称修改成功！");
							mod.mSuccess(nick);
							break;
						case 2:
							PhoneState.showToast(context, "昵称修改失败，请重试！");
							break;
						}
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			PhoneState.showToast(context, "修改失败，请重试！");
		}
	}
}
