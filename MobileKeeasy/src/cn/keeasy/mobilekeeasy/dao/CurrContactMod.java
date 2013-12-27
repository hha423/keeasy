package cn.keeasy.mobilekeeasy.dao;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;

import cn.keeasy.mobilekeeasy.data.IMod;
import cn.keeasy.mobilekeeasy.data.Sources;
import cn.keeasy.mobilekeeasy.entity.ChatBean;

public class CurrContactMod extends BaseNetMod {

	private Handler handler = new Handler();
	private boolean handlerisRun;
	private String userAccount;
	private String cAccount;
	private Integer userid;

	public CurrContactMod(Context mContext, IMod mod) {
		super(mContext, mod);
	}

	// 请求用户未读信息
	public void mGetMessageInfo(String userAccount, String cAccount,
			Integer chatId) {
		this.cAccount = cAccount;
		this.userAccount = userAccount;
		this.userid = chatId;
	}

	@Override
	public void mGetInfo(String content) {
		super.mGetInfo(content);
		if (content != null && !content.equals("null")) {
			ArrayList<ChatBean> list = Sources.CHATINFOMAP.get(cAccount);
			if (list == null) {
				list = new ArrayList<ChatBean>();
			}
			ChatBean cBean = null;
			try {
				JSONObject zjson = new JSONObject(content);
				int pag = zjson.names().length();
				for (int i = 0; i < pag; i++) {
					String name = zjson.names().get(i).toString();
					cBean = new ChatBean();
					JSONObject json = new JSONObject(zjson.getString(name));
					int flg = json.names().length();
					for (int j = 0; j < flg; j++) {
						String cont = json.names().get(j).toString();
						if (cont.equals("id")) {
							cBean.chatId = json.getInt(cont);
						} else if (cont.equals("info")) {
							cBean.chatContent = json.getString(cont);
						} else if (cont.equals("time")) {
							cBean.chatTime = json.getString(cont);
						}
					}
					cBean.userAccount = cAccount;
					list.add(cBean);
				}
				Sources.CHATINFOMAP.put(cAccount, list);
				mod.mSuccess();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	public void mStart() {
		if (!handlerisRun) {
			handlerisRun = true;
			handler.post(run);
		}
	}

	public void mStop() {
		if (handlerisRun) {
			handler.removeCallbacks(run);
			handlerisRun = false;
		}

	}

	Runnable run = new Runnable() {
		@Override
		public void run() {
			try {
				mGetArea(IP + "getUnreadMessageOfChat?userAccount="
						+ userAccount + "&chatAccount=" + cAccount + "&userid="
						+ userid);
				handler.postDelayed(run, 3000);
			} catch (Exception e) {

			}
		}
	};
}
