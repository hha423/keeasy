package cn.keeasy.business.mod;

import android.content.Context;
import cn.evan.tools.PhoneState;
import cn.keeasy.business.base.IMod;

public class ModfiyAccountMod extends BaseNetMod {

	public ModfiyAccountMod(Context mContext, IMod mod) {
		super(mContext, mod);
	}

	// 修改子帐号密码
	public void mSetAccountPwd(int userid, String subAccount, String subpwd) {
		mGetArea(IP + "shopUpdateSubAccount?userId=" + userid + "&subAccount="
				+ subAccount + "&subPassWord=" + subpwd);
	}

	@Override
	public void mGetInfo(String content) {
		super.mGetInfo(content);
		if (content.equals("true")) {
			PhoneState.cenShowToast(context, "密码修改成功,请牢记！");
			mod.mSuccess();
		} else if (content.equals("false")) {
			PhoneState.cenShowToast(context, "密码修改失败！");
		} else {
			PhoneState.cenShowToast(context, "提交出错请重试！");
		}
	}

}