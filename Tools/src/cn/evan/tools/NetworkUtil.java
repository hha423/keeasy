package cn.evan.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

/**
 * 网络相关工具类，网络检测、网络连接方式
 * 
 * @author Evan
 * 
 */
public class NetworkUtil {

	/**
	 * 检测网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Activity mActivity) {
		Context context = mActivity.getApplicationContext();
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					} else if (info[i].isAvailable()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 检测网络是否存在
	 * 
	 * @param mActivity
	 */
	public static void checkNetwork(final Activity mActivity) {
		if (!isNetworkAvailable(mActivity)) {
			new AlertDialog.Builder(mActivity)
					.setTitle("抱歉，当前的网络连接不可用，是否进行网络设置？")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									// 进入网络配置界面
									mActivity.startActivity(new Intent(
											Settings.ACTION_WIRELESS_SETTINGS));
									// startActivity(new
									// Intent(Settings.ACTION_WIFI_SETTINGS));
									// //进入手机中的wifi网络设置界面
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									// 关闭当前activity
									mActivity.finish();
								}
							}).show();
		}

	}

}