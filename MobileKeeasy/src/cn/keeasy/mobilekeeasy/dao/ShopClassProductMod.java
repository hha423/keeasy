package cn.keeasy.mobilekeeasy.dao;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import cn.keeasy.mobilekeeasy.data.IMod;
import cn.keeasy.mobilekeeasy.data.Sources;
import cn.keeasy.mobilekeeasy.entity.ProductInfoBean;

public class ShopClassProductMod extends BaseNetMod {

	public ShopClassProductMod(Context mContext, IMod mod) {
		super(mContext, mod);
	}

	// 消费店铺根据分类获取商品列表
	public void mGetProductsByClassid(String classid, int sid) {
		// waitdg.mInitShow("正在加载分类菜单，请稍候…");
		mGetArea(IP + "getsundayShopProductListById?shopId=" + sid
				+ "&classId=" + classid + "&dayType=" + 7);
	}

	@Override
	public void mGetInfo(String content) {
		super.mGetInfo(content);
		if (content != null && !content.equals("null")) {
			ProductInfoBean sBean = null;
			Sources.PROINFOLIST.clear();
			try {
				JSONObject json = new JSONObject(content);
				for (int i = 0; i < json.names().length(); i++) {
					JSONObject njson = new JSONObject(json.getString(json
							.names().get(i).toString()));
					sBean = new ProductInfoBean();
					for (int j = 0; j < njson.names().length(); j++) {
						String name = njson.names().get(j).toString();
						if (name.equals("productid")) {
							sBean.productId = njson.getInt(name);
						} else if (name.equals("productName")) {
							sBean.productName = njson.getString(name).trim();
						} else if (name.equals("productType")) {
							sBean.productType = njson.getString(name);
						} else if (name.equals("productPrice")) {
							sBean.productPrice = njson.getDouble(name);
						} else if (name.equals("productNum")) {
							sBean.productNum = njson.getInt(name);
						}
					}
					Sources.PROINFOLIST.add(sBean);
				}
				mod.mSuccess1();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}
