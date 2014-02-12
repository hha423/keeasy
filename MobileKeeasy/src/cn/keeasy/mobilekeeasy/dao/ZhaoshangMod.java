package cn.keeasy.mobilekeeasy.dao;

import android.content.Context;
import cn.evan.tools.PhoneState;
import cn.evan.tools.Utils;

import cn.keeasy.mobilekeeasy.data.IMod;

public class ZhaoshangMod extends BaseNetMod {

	public ZhaoshangMod(Context mContext, IMod mod) {
		super(mContext, mod);
	}

	public void addMessage(String name, String tel, String mail, String text) {
		// waitdg.mInitShow("正在提交您的申请信息…");
		mGetArea(IP + "addZhaoshangPhone?name=" + Utils.uniCode(name)
				+ "&tell=" + tel + "&mail=" + mail + "&text="
				+ Utils.uniCode(text));
	}

	@Override
	public void mGetInfo(String content) {
		if (content.equals("0")) {
			PhoneState.showToast(context, "提交成功！");
		} else if (content.equals("3")) {
			PhoneState.showToast(context, "网络异常，请重试！");
		} else if (content.equals("4")) {
			PhoneState.showToast(context, "添加失败，请稍候提交！");
		}
		super.mGetInfo(content);
	}

}
