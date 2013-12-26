package cn.evan.tools;

import android.content.Context;
import cn.evan.tools.base.IDownFile;
import cn.evan.tools.base.ITask;

import com.keeasy.phone.bean.request.ChatBean;

public class RequestTask {

	public static String UPDATE = "update";
	public static String DOWN = "download";

	private JsonHttpTask task = null;
	private ITask it;
	private LoadFileSaveTask lfstask = null;
	private IDownFile idf;

	public RequestTask(ITask it) {
		this.it = it;
	}

	public RequestTask(IDownFile idf) {
		this.idf = idf;
	}

	public void mRequest(String type, String url, ChatBean nameValuePairs) {
		if (task != null && !task.isCancelled()) {
			task.cancel(true);
		}
		task = new JsonHttpTask(it, nameValuePairs);
		task.execute(type, url);
	}

	public void mGetFile(Context context, String fileUrl, String fileName) {
		if (lfstask != null && !lfstask.isCancelled()) {
			lfstask.cancel(true);
		}
		lfstask = new LoadFileSaveTask(idf, fileUrl, fileName,
				PhoneState.getCachePath(context));
		lfstask.execute();
	}
	/*
	 * public void mRequest(String type, String url, List<NameValuePair>
	 * nameValuePairs) { if (task != null && !task.isCancelled()) {
	 * task.cancel(true); } task = new JsonHttpTask(it, nameValuePairs);
	 * task.execute(type, url); }
	 */
}
