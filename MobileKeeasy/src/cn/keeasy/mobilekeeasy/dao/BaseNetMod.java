package cn.keeasy.mobilekeeasy.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import cn.evan.tools.PhoneState;
import cn.evan.tools.RequestTask;
import cn.evan.tools.base.ITask;

import cn.keeasy.mobilekeeasy.custom.WaitDialog;
import cn.keeasy.mobilekeeasy.data.IMod;
import cn.keeasy.mobilekeeasy.data.Sources;

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
	public int MAX_RESULT = 5;
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
			try {
				waitdg.cancel();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 将json 数组转换为Map 对象
	public Map<String, Object> getMap(String content) {
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(content);
			@SuppressWarnings("unchecked")
			Iterator<String> keyIter = jsonObject.keys();
			String key;
			Object value;
			Map<String, Object> valueMap = new HashMap<String, Object>();
			while (keyIter.hasNext()) {
				key = (String) keyIter.next();
				value = jsonObject.get(key);
				valueMap.put(key, value);
			}
			return valueMap;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 把json 转换为 ArrayList 形式
	public List<Map<String, Object>> getList(String content) {
		// JSONObject obj = new JSONObject();
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(content);
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				// System.out.println(jsonObject.toString());
				list.add(getMap(jsonObject.toString()));
			}
			return list;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

}
