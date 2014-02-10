package cn.keeasy.mobilekeeasy.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import cn.evan.tools.PhoneState;
import cn.keeasy.mobilekeeasy.data.IMod;
import cn.keeasy.mobilekeeasy.data.Sources;
import cn.keeasy.mobilekeeasy.entity.ZhekeBean;

/**
 * 店铺列表请求类
 * 
 * @author Evan
 * 
 */
public class ZhekeMod extends BaseNetMod {

	int maxPage = 2;

	public ZhekeMod(Context mContext, IMod mod) {
		super(mContext, mod);
	}

	/**
	 * 请求限时折扣店铺列表
	 * 
	 * @param page
	 * @param seachKey
	 */

	public void mZheShopList(int page, String seachKey) {
		if (maxPage != 0 && page > maxPage) {
			waitdg.dismiss();
			PhoneState.showToast(context, "已是最后一页，请往上划！");
		} else {
			// waitdg.mInitShow("店铺列表加载中…");
			mGetArea(IP + "getLimitBuyShop?page=" + page + "&seachKey="
					+ seachKey + "&maxNum=" + MAX_RESULT);
		}
	}

	@Override
	public void mGetInfo(String content) {
		super.mGetInfo(content);
		List<ZhekeBean> list = new ArrayList<ZhekeBean>();
		ZhekeBean sBean = null;
		JSONObject json;
		try {
			json = new JSONObject(content);
			JSONObject zjson = null;
			for (int i = 0; i < json.length(); i++) {
				zjson = json.getJSONObject("shop" + (i + 1));
				sBean = new ZhekeBean();
				sBean.shopId = zjson.getInt("shopId");
				sBean.bAccount = zjson.getString("account");
				sBean.shopName = zjson.getString("shopName");
				sBean.zhekou = zjson.getDouble("zheKou");
				sBean.begTime = zjson.getString("begTime");
				sBean.endTime = zjson.getString("endTime");
				sBean.endNum = zjson.getString("endNum");
				sBean.shopAddress = zjson.getString("shopAddr");
				this.maxPage = zjson.getInt("sumPage");
				sBean.by = zjson.getInt("by");
				list.add(sBean);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Collections.sort(list, new Comparator<Object>() {
			public int compare(Object a, Object b) {
				int one = ((ZhekeBean) a).by;
				int two = ((ZhekeBean) b).by;
				return one - two;
			}
		});
		Sources.TIMZHEKE.clear();
		Sources.TIMZHEKE.addAll(list);
		mod.mSuccess1();
	}

}