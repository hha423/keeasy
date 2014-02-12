package cn.keeasy.mobilekeeasy.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;
import cn.keeasy.mobilekeeasy.MainActivity;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则： 1) 默认用户会打开主界面 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "MyReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		// Log.d(TAG, "onReceive - " + intent.getAction() + ", extras: "
		// + printBundle(bundle));
		if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
			// String regId = bundle
			// .getString(JPushInterface.EXTRA_REGISTRATION_ID);
			// Log.d(TAG, "接收Registration Id : " + regId);
			// send the Registration Id to your server...
		} else if (JPushInterface.ACTION_UNREGISTER.equals(intent.getAction())) {
//			String regId = bundle
//					.getString(JPushInterface.EXTRA_REGISTRATION_ID);
//			Log.d(TAG, "接收UnRegistration Id : " + regId);
			// send the UnRegistration Id to your server...
		} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent
				.getAction())) {
			// Log.d(TAG,
			// "接收到推送下来的自定义消息: "
			// + bundle.getString(JPushInterface.EXTRA_MESSAGE));

		} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent
				.getAction())) {
			int notifactionId = bundle
					.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
			Log.d(TAG, "接收到推送下来的通知的ID: " + notifactionId);

		} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent
				.getAction())) {
			// Log.d(TAG, "用户点击打开了通知");
			Intent i = null;
			// 打开自定义的Activity
			if (bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE)
					.equals("新消息提醒")) {
				// ContactBean cbean = new ContactBean();
				// cbean.contactName = Sources.CURRENTSHOP.shopName;
				// cbean.contactAccount = Sources.CURRENTSHOP.bAccount;
				// Sources.CURRENTIC = cbean;
				i = new Intent(context, MainActivity.class);
			} else if (bundle
					.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE).equals(
							"版本更新")) {
				i = new Intent(context, MainActivity.class);
				// Uri uri = Uri.parse("http://www.keeasy.com/before_down");
				// i = new Intent(Intent.ACTION_VIEW, uri);
			} else {
				i = new Intent(context, MainActivity.class);
			}
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);

		} else {
			Log.d(TAG, "Unhandled intent - " + intent.getAction());
		}
	}

	// 打印所有的 intent extra 数据
	public static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}

}