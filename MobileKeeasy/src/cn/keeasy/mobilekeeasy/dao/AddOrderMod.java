package cn.keeasy.mobilekeeasy.dao;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import cn.evan.tools.PhoneState;
import cn.evan.tools.Utils;

import cn.keeasy.mobilekeeasy.data.IMod;
import cn.keeasy.mobilekeeasy.data.Sources;

public class AddOrderMod extends BaseNetMod {

	public AddOrderMod(Context mContext, IMod mod) {
		super(mContext, mod);
	}

	/**
	 * 添加消费类订单
	 * 
	 * @param businessAccount
	 *            //商家账号
	 * @param userAccount
	 *            //用户账号
	 * @param userAccount_Id
	 *            //用户id
	 * @param sundayId
	 *            //星期天id
	 * @param activityType
	 *            //活动类型 7
	 * @param userName
	 *            //用户名
	 * @param userMessage
	 *            //用户信息
	 * @param userPhone
	 *            //用户电话
	 * @param pids
	 *            //产品id
	 * @param pnames
	 *            //产品名称
	 * @param pprices
	 *            //产品价格
	 * @param pnumber
	 *            //产品数量
	 * @param totalPrice
	 *            //消费总价
	 * @param serviceTotalPrice
	 *            //消费折扣总价
	 * @param serviceDiscount
	 *            //折扣
	 * @param serviceSubscription
	 *            //订金
	 * @param serviceBalance
	 *            //余额
	 */

	public void mGetAddOrderUrlByCount(String businessAccount,
			String userAccount, int userAccount_Id, int sundayId,
			int activityType, String userName, String userMessage,
			String userPhone, String pids, String pnames, String pprices,
			String pnumber, double totalPrice, double serviceTotalPrice,
			double serviceDiscount, double serviceSubscription,
			double serviceBalance) {
		// waitdg.mInitShow("正在向服务器提交订单中…");
		mGetArea(IP + "addSundayOrder?businessAccount=" + businessAccount
				+ "&userAccount=" + userAccount + "&userName=" + userName
				+ "&userAccount_Id=" + userAccount_Id + "&totalPrice="
				+ totalPrice + "&serviceTotalPrice=" + serviceTotalPrice
				+ "&userMessage=" + Utils.uniCode(userMessage) + "&userPhone="
				+ userPhone + "&serviceDiscount=" + serviceDiscount + "&pids="
				+ pids + "&pnames=" + pnames + "&pprices=" + pprices
				+ "&pnumber=" + pnumber + "&serviceSubscription="
				+ serviceSubscription + "&serviceBalance=" + serviceBalance
				+ "&sundayId=" + sundayId + "&activityType=" + activityType);
	}

	@Override
	public void mGetInfo(String content) {
		super.mGetInfo(content);
		if (content != null && !content.equals("null")) {
			try {
				JSONObject json = new JSONObject(content);
				for (int i = 0; i < json.names().length(); i++) {
					String name = json.names().get(i).toString();
					if (name.equals("idNumber")) {
						Sources.ORDERBEAN.orderId = json.getString(name);
					} else if (name.equals("name")) {
						Sources.ORDERBEAN.shopName = json.getString(name);
					} else if (name.equals("state")) {
						Sources.ORDERBEAN.orderState = json.getString(name);
					} else if (name.equals("price")) {
						Sources.ORDERBEAN.payPrice = json.getDouble(name);
					}
				}
				mod.mSuccess2();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			PhoneState.showToast(context, "当前订单产品异常，请联系商家后重试！");
		}
	}
}
