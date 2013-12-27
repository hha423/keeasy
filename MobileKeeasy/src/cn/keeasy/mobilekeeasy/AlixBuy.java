/*
 * Copyright (C) 2010 The MobileSecurePay Project
 * All right reserved.
 * author: shiqun.shi@alipay.com
 */

package cn.keeasy.mobilekeeasy;

import java.net.URLEncoder;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import cn.evan.tools.PhoneState;

import cn.keeasy.mobilekeeasy.adapter.OrderInfoAdapter;
import cn.keeasy.mobilekeeasy.alipay.AlixId;
import cn.keeasy.mobilekeeasy.alipay.BaseHelper;
import cn.keeasy.mobilekeeasy.alipay.MobileSecurePayHelper;
import cn.keeasy.mobilekeeasy.alipay.MobileSecurePayer;
import cn.keeasy.mobilekeeasy.alipay.PartnerConfig;
import cn.keeasy.mobilekeeasy.alipay.ResultChecker;
import cn.keeasy.mobilekeeasy.alipay.Rsa;
import cn.keeasy.mobilekeeasy.dao.ChinaPayMod;
import cn.keeasy.mobilekeeasy.data.Sources;
import cn.keeasy.mobilekeeasy.entity.OrderBean;
import cn.keeasy.mobilekeeasy.utils.PM;
import cn.keeasy.mobilekeeasy.utils.Skip;
import com.unionpay.UPPayAssistEx;

/**
 * 实际商户应用交易步骤。
 * 
 * 1. 将商户ID，收款帐号，外部订单号，商品名称，商品介绍，价格，通知地址封装成订单信息 2. 对订单信息进行签名 3.
 * 将订单信息，签名，签名方式封装成请求参数 4. 调用pay方法
 * 
 * @version v4_0413 2013-04-03 Evan
 */
public class AlixBuy extends BaseActivity {

	private ListView msc_list;
	private TextView msc_id, msc_state, msc_name, msc_pay, msc_alipay,
			msc_unpay;
	private OrderInfoAdapter oiadapter;
	private ProgressDialog mProgress = null;
	private OrderBean bean;
	private String serverMode = "00"; // 00 银联正式环境 01 测试环境，不产生真实交易
	private ChinaPayMod payMod;

	// Called when the activity is first created.
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// check to see if the MobileSecurePay is already installed.
		// 检测安全支付服务是否被安装
		MobileSecurePayHelper mspHelper = new MobileSecurePayHelper(this);
		mspHelper.detectMobile_sp();
		setContentView(R.layout.main_shop_cart);
		payMod = new ChinaPayMod(this, this);
		super.onCreate(savedInstanceState);
	}

	@Override
	void initView() {
		super.initView();
		msc_list = (ListView) findViewById(R.id.msc_list);
		msc_id = (TextView) findViewById(R.id.msc_id);
		msc_state = (TextView) findViewById(R.id.msc_state);
		msc_name = (TextView) findViewById(R.id.msc_name);
		msc_pay = (TextView) findViewById(R.id.msc_pay);
		msc_alipay = (TextView) findViewById(R.id.msc_alipay);
		msc_unpay = (TextView) findViewById(R.id.msc_unpay);
	}

	@Override
	void initData() {
		topback.setVisibility(View.VISIBLE);
		toptitle.setText("定金在线支付");
		oiadapter = new OrderInfoAdapter(this);
		msc_list.setAdapter(oiadapter);
		bean = Sources.ORDERBEAN;
		if (bean.orderNum != null) {
			msc_id.setText("订单编号：" + bean.orderNum);
		} else {
			msc_id.setText("订单ID：" + bean.orderId);
		}
		msc_state.setText("订单状态：" + PM.getStateName(bean.orderState));
		msc_name.setText("商户名称：" + bean.shopName);
		msc_pay.setText("支付金额：￥：" + bean.payPrice + "元");
	}

	@Override
	void initListen() {
		super.initListen();
		msc_alipay.setOnClickListener(this);
		msc_unpay.setOnClickListener(this);
	}

	/**
	 * get the selected order info for pay. 获取商品订单信息
	 * 
	 * @return
	 */
	String getOrderInfo() {
		String strOrderInfo = "partner=" + "\"" + PartnerConfig.PARTNER + "\"";
		strOrderInfo += "&";
		strOrderInfo += "seller=" + "\"" + PartnerConfig.SELLER + "\"";
		strOrderInfo += "&";
		strOrderInfo += "out_trade_no=" + "\"" + bean.orderId + "\"";
		strOrderInfo += "&";
		strOrderInfo += "subject=" + "\"" + "消费类星期天订单" + "\"";
		strOrderInfo += "&";
		strOrderInfo += "body=" + "\"" + bean.orderState + "\"";
		strOrderInfo += "&";
		strOrderInfo += "total_fee=" + "\"" + bean.payPrice + "\"";
		strOrderInfo += "&";
		strOrderInfo += "notify_url=" + "\"" + Sources.SUNPAIDSER + "\"";

		return strOrderInfo;
	}

	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param signType
	 *            签名方式
	 * @param content
	 *            待签名订单信息
	 * @return
	 */
	String sign(String signType, String content) {
		return Rsa.sign(content, PartnerConfig.RSA_PRIVATE);
	}

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 * @return
	 */
	String getSignType() {
		String getSignType = "sign_type=" + "\"" + "RSA" + "\"";
		return getSignType;
	}

	/**
	 * get the char set we use. 获取字符集
	 * 
	 * @return
	 */
	String getCharset() {
		String charset = "charset=" + "\"" + "utf-8" + "\"";
		return charset;
	}

	/**
	 * check some info.the partner,seller etc. 检测配置信息
	 * partnerid商户id，seller收款帐号不能为空
	 * 
	 * @return
	 */
	private boolean checkInfo() {
		String partner = PartnerConfig.PARTNER;
		String seller = PartnerConfig.SELLER;
		if (partner == null || partner.length() <= 0 || seller == null
				|| seller.length() <= 0)
			return false;

		return true;
	}

	//
	// the handler use to receive the pay result.
	// 这里接收支付结果，支付宝手机端同步通知
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			try {
				String strRet = (String) msg.obj;

				// Log.e(TAG, strRet); //
				// strRet范例：resultStatus={9000};memo={};result={partner="2088201564809153"&seller="2088201564809153"&out_trade_no="050917083121576"&subject="123456"&body="2010新款NIKE 耐克902第三代板鞋 耐克男女鞋 386201 白红"&total_fee="0.01"&notify_url="http://notify.java.jpxx.org/index.jsp"&success="true"&sign_type="RSA"&sign="d9pdkfy75G997NiPS1yZoYNCmtRbdOP0usZIMmKCCMVqbSG1P44ohvqMYRztrB6ErgEecIiPj9UldV5nSy9CrBVjV54rBGoT6VSUF/ufjJeCSuL510JwaRpHtRPeURS1LXnSrbwtdkDOktXubQKnIMg2W0PreT1mRXDSaeEECzc="}
				switch (msg.what) {
				case AlixId.RQF_PAY: {
					//
					closeProgress();

					// BaseHelper.log(TAG, strRet);

					// 处理交易结果
					try {
						// 获取交易状态码，具体状态代码请参看文档
						String tradeStatus = "resultStatus={";
						int imemoStart = strRet.indexOf("resultStatus=");
						imemoStart += tradeStatus.length();
						int imemoEnd = strRet.indexOf("};memo=");
						tradeStatus = strRet.substring(imemoStart, imemoEnd);

						// 先验签通知
						ResultChecker resultChecker = new ResultChecker(strRet);
						int retVal = resultChecker.checkSign();
						// 验签失败
						if (retVal == ResultChecker.RESULT_CHECK_SIGN_FAILED) {
							BaseHelper.showDialog(
									AlixBuy.this,
									"提示",
									getResources().getString(
											R.string.check_sign_failed),
									android.R.drawable.ic_dialog_alert, false);
						} else {// 验签成功。验签成功后再判断交易状态码
							if (tradeStatus.equals("9000"))// 判断交易状态码，只有9000表示交易成功
							{
								BaseHelper.showDialog(AlixBuy.this, "提示",
										"支付成功。交易状态码：" + tradeStatus,
										R.drawable.infoicon, true);
								Sources.CARTLIST.clear();
							} else
								BaseHelper.showDialog(AlixBuy.this, "提示",
										"支付失败。交易状态码:" + tradeStatus,
										R.drawable.infoicon, false);
						}

					} catch (Exception e) {
						e.printStackTrace();
						BaseHelper.showDialog(AlixBuy.this, "提示", strRet,
								R.drawable.infoicon, false);
					}
				}
					break;
				}

				super.handleMessage(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	//
	//
	/**
	 * the OnCancelListener for lephone platform. lephone系统使用到的取消dialog监听
	 */
	public static class AlixOnCancelListener implements
			DialogInterface.OnCancelListener {
		Activity mcontext;

		public AlixOnCancelListener(Activity context) {
			mcontext = context;
		}

		public void onCancel(DialogInterface dialog) {
			mcontext.onKeyDown(KeyEvent.KEYCODE_BACK, null);
		}
	}

	//
	// close the progress bar
	// 关闭进度框
	void closeProgress() {
		try {
			if (mProgress != null) {
				mProgress.dismiss();
				mProgress = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 返回键监听事件
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// BaseHelper.log(TAG, "onKeyDown back");
			Skip.mBack(this);
			return true;
		}

		return false;
	}

	@Override
	protected void onResume() {
		super.onResume();
		msc_alipay.setEnabled(true);
	}

	//
	@Override
	public void onDestroy() {
		super.onDestroy();
		try {
			mProgress.dismiss();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.msc_alipay:
			msc_alipay.setEnabled(false);
			PM.onBtnAnim(msc_alipay);
			// check to see if the MobileSecurePay is already installed.
			// 检测安全支付服务是否安装
			MobileSecurePayHelper mspHelper = new MobileSecurePayHelper(this);
			boolean isMobile_spExist = mspHelper.detectMobile_sp();
			if (!isMobile_spExist)
				return;

			// check some info.
			// 检测配置信息
			if (!checkInfo()) {
				BaseHelper
						.showDialog(
								AlixBuy.this,
								"提示",
								"缺少partner或者seller，请在src/com/alipay/android/appDemo4/PartnerConfig.java中增加。",
								R.drawable.infoicon, false);
				return;
			}

			// start pay for this order.
			// 根据订单信息开始进行支付
			try {
				// prepare the order info.
				// 准备订单信息
				String orderInfo = getOrderInfo();
				// 这里根据签名方式对订单信息进行签名
				String signType = getSignType();
				String strsign = sign(signType, orderInfo);
				Log.v("sign:", strsign);
				// 对签名进行编码
				strsign = URLEncoder.encode(strsign);
				// 组装好参数
				String info = orderInfo + "&sign=" + "\"" + strsign + "\""
						+ "&" + getSignType();
				Log.v("orderInfo:", info);
				// start the pay.
				// 调用pay方法进行支付
				MobileSecurePayer msp = new MobileSecurePayer();
				boolean bRet = msp.pay(info, mHandler, AlixId.RQF_PAY, this);

				if (bRet) {
					// show the progress bar to indicate that we have
					// started
					// paying.
					// 显示“正在支付”进度条
					closeProgress();
					mProgress = BaseHelper.showProgress(this, null, "正在支付",
							false, true);
				} else
					;
			} catch (Exception ex) {
				PhoneState.cenShowToast(AlixBuy.this, ""
						+ R.string.remote_call_failed);
			}
			break;
		case R.id.msc_unpay:
			PM.onBtnAnim(msc_unpay);
			mProgress = ProgressDialog.show(AlixBuy.this, // context
					"", // title
					"正在努力的获取交易号中,请稍候...", // message
					true); // 进度是否是不确定的，这只和创建进度条有关
			payMod.mGetUnionPayNum(bean.orderId, bean.payPrice);
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		/*************************************************
		 * 
		 * 步骤3：处理银联手机支付控件返回的支付结果
		 * 
		 ************************************************/
		if (data == null) {
			return;
		}
		String msg = "";
		/*
		 * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
		 */
		final String str = data.getExtras().getString("pay_result");
		if (str.equalsIgnoreCase("success")) {
			msg = "支付成功！";
		} else if (str.equalsIgnoreCase("fail")) {
			msg = "支付失败！";
		} else if (str.equalsIgnoreCase("cancel")) {
			msg = "用户取消了支付";
		}

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("支付结果通知");
		builder.setMessage(msg);
		builder.setInverseBackgroundForced(true);
		// builder.setCustomTitle();
		builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				if (str.equalsIgnoreCase("success")) {
					Skip.mNext(AlixBuy.this, OrderActivity.class,
							R.anim.activity_slide_in_right,
							R.anim.activity_no_anim, true);
				}
			}
		});
		builder.create().show();
	}

	@Override
	public void mSuccess(String str) {
		if (mProgress.isShowing())
			mProgress.dismiss();
		if (str != null && !str.equals("")) {
			// String tn = "201307190931280000372";
			int ret = UPPayAssistEx.startPay(AlixBuy.this, null, null,
					str.trim(), serverMode);
			if (ret == UPPayAssistEx.PLUGIN_NOT_FOUND) {
				// 需要重新安装控件
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("提示");
				builder.setMessage("完成购买需要安装银联支付控件，是否安装？");

				builder.setNegativeButton("确定",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								UPPayAssistEx.installUPPayPlugin(AlixBuy.this);
								dialog.dismiss();
							}
						});

				builder.setPositiveButton("取消",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						});
				builder.create().show();
			}
		} else {
			PhoneState.showToast(this, "获取交易号失败，请重试！");
		}
	}

}