package cn.keeasy.business.mod;

import android.content.Context;
import cn.evan.tools.PhoneState;
import cn.evan.tools.RequestTask;
import cn.evan.tools.base.ITask;
import cn.keeasy.business.base.IMod;
import cn.keeasy.business.dialog.WaitDialog;
import cn.keeasy.business.util.Sources;

/**
 * 网络连接请求基类
 * 
 * @author Evan
 * 
 */
public class BaseNetMod implements ITask {

	public IMod mod;
	public WaitDialog waitdg;
	public Context context;
	public RequestTask task;
	public int MAX_RESULT = 8;
	public String IP;

	public BaseNetMod(Context mContext, IMod mod) {
		this.context = mContext;
		this.mod = mod;
		waitdg = new WaitDialog(context);
		task = new RequestTask(this);
		IP = Sources.IP;
	}

	void mGetArea(String url) {
		task.mRequest(RequestTask.DOWN, url, null);
	}

	void mPostArea(String url,
			com.keeasy.phone.bean.request.ChatBean nameValuePairs) {
		task.mRequest(RequestTask.UPDATE, url, nameValuePairs);
	}

	@Override
	public void mTaskError(String arg0) {
		if (waitdg.isShowing()) {
			waitdg.cancel();
		}
		PhoneState.showToast(context, arg0);
	}

	@Override
	public void mTaskSuccess(String content) {
		mGetInfo(content);
	}

	public void mGetInfo(String content) {
		if (waitdg.isShowing()) {
			waitdg.cancel();
		}
	}

}
