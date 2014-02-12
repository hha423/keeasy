package cn.keeasy.mobilekeeasy.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;

public class NetReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		boolean success = false;
		// 获得网络连接服务
		ConnectivityManager connManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		// State state = connManager.getActiveNetworkInfo().getState();
		State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.getState(); // 获取网络连接状态
		if (State.CONNECTED == state) { // 判断是否正在使用WIFI网络
			success = true;
		}
		state = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.getState(); // 获取网络连接状态
		if (State.CONNECTED == state) { // 判断是否正在使用GPRS网络
			success = true;
		}
		if (!success) {
			// new NetSetDialog(context).show();
			// PhoneState.showToast(context, "您的网络连接已中断，请检查后重试！");
		}

	}

}
