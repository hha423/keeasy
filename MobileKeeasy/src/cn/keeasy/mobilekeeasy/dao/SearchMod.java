package cn.keeasy.mobilekeeasy.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import cn.evan.tools.PhoneState;
import cn.evan.tools.Utils;
import cn.keeasy.mobilekeeasy.data.IMod;
import cn.keeasy.mobilekeeasy.data.Sources;
import cn.keeasy.mobilekeeasy.entity.ShopBean;
import cn.keeasy.mobilekeeasy.entity.SundayShopBean;

/**
 * 店铺列表搜索请求类
 * 
 * @author Evan
 * 
 */
public class SearchMod extends BaseNetMod {

	int maxPage = 2;
	boolean srch;

	public SearchMod(Context mContext, IMod mod) {
		super(mContext, mod);
	}

	/**
	 * 搜索星期天活动店铺列表
	 * 
	 * @param selName
	 *            店铺名称
	 * @param page
	 *            页数
	 * @param maxNum
	 *            每页多少条数据
	 */
	public void getSearchShop(String selName, int page) {
		// waitdg.mInitShow("正在努力为您加载中…");]
		if (maxPage != 0 && page > maxPage) {
			PhoneState.showToast(context, "已是最后一页，请往上划！");
		} else {
			mGetArea(IP + "getsundayShopByParamOnZheKou?selName="
					+ Utils.uniCode(selName) + "&page=" + page + "&maxNum="
					+ MAX_RESULT);
		}
	}

	@Override
	public void mGetInfo(String content) {
		super.mGetInfo(content);
		List<ShopBean> list = new ArrayList<ShopBean>();
		ShopBean sBean = null;
		JSONObject json;
		try {
			json = new JSONObject(content);
			JSONObject zjson = null;
			for (int i = 0; i < json.length(); i++) {
				zjson = json.getJSONObject("shop" + (i + 1));
				sBean = new ShopBean();
				sBean.shopId = zjson.getInt("shopId");
				sBean.bAccount = zjson.getString("account");
				sBean.shopImg = zjson.getString("shopImg");
				sBean.shopName = zjson.getString("shopName");
				sBean.shopTime = zjson.getString("shopTime");
				sBean.shopAddress = zjson.getString("shopAddr");
				sBean.by = zjson.getInt("by");
				this.maxPage = zjson.getInt("sumPage");
				JSONObject sjson = new JSONObject(zjson.getString("sunday"));
				JSONObject ojson = null;
				List<SundayShopBean> slist = new ArrayList<SundayShopBean>();
				SundayShopBean ssbean = null;
				for (int j = 0; j < sjson.length(); j++) {
					ojson = sjson.getJSONObject("sun" + (j + 1));
					ssbean = new SundayShopBean();
					ssbean.tcType = ojson.getInt("tcType");
					ssbean.discount = ojson.getDouble("discount");
					ssbean.color = ojson.getString("color");
					ssbean.payNum = ojson.getInt("payNum");
					ssbean.lastNum = ojson.getInt("lastNum");
					ssbean.sundayId = ojson.getInt("sundayId");
					slist.add(ssbean);
				}
				sBean.dayType = slist;
				list.add(sBean);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Collections.sort(list, new Comparator<Object>() {
			public int compare(Object a, Object b) {
				int one = ((ShopBean) a).by;
				int two = ((ShopBean) b).by;
				return one - two;
			}
		});
		Sources.SREACHINFO.addAll(list);
		mod.mSuccess();
	}

}