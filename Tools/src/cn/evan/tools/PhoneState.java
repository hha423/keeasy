package cn.evan.tools;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.Manifest;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * 手机状态获，注意：需要如下权限 <uses-permission
 * android:name="android.permission.READ_PHONE_STATE"/> <uses-permission
 * android:name="android.permission.ACCESS_NETWORK_STATE"/>
 * 
 * @author Evan
 * 
 */
public class PhoneState {

	/**
	 * Toask显示
	 * 
	 * @param context
	 *            上下文
	 * @param tip
	 *            提示内容
	 */
	public static void showToast(Context context, String tip) {
		Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
	}

	public static void cenShowToast(Context context, String tip) {
		Toast toast = Toast.makeText(context, tip, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	public static void showToast(Context context, String tip, int drawable) {
		Toast toast = Toast.makeText(context, tip, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		LinearLayout toastView = (LinearLayout) toast.getView();
		ImageView imageCodeProject = new ImageView(context);
		imageCodeProject.setImageResource(drawable);
		toastView.addView(imageCodeProject, 0);
		toast.show();
	}

	/**
	 * 判断SD卡是否存在
	 * 
	 * @return
	 */
	public static boolean hasSDCard() {
		String status = Environment.getExternalStorageState();
		if (!status.equals(Environment.MEDIA_MOUNTED)) {
			return false;
		}
		return true;
	}

	/**
	 * 获取SDCard路径
	 * 
	 * @name 文件夹名称
	 * @return
	 */
	public static String getFilePath(String name) {
		if (hasSDCard()) {
			if (name.equals("")) {
				return Environment.getExternalStorageDirectory()
						.getAbsolutePath() + File.separator;
			} else
				return Environment.getExternalStorageDirectory()
						.getAbsolutePath()
						+ File.separator
						+ name
						+ File.separator;// filePath:/sdcard/
		} else {
			return Environment.getDataDirectory().getAbsolutePath() + "/data/"
					+ name; // filePath:/data/data/
		}
	}

	/**
	 * 获取缓存路径
	 * 
	 * @name 文件夹名称
	 * @return
	 */
	public static File getCachePath(Context context) {
		if (hasSDCard()) {
			return context.getExternalCacheDir();
		} else {
			return context.getCacheDir();
		}
	}

	/**
	 * 获取网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean checkNetError(Context context) {
		try {
			NetworkInfo networkInfo = null;
			ConnectivityManager nw = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			networkInfo = nw.getActiveNetworkInfo();

			return networkInfo.isAvailable();// 网络是否可用
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 获得当前程序的版本信息
	 * 
	 * @param context
	 * @return
	 */
	public static int getVerCode(Context context) {
		int verCode = -1;
		try {
			verCode = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return verCode; // 版本号
	}

	/**
	 * 获得当前程序的版本信息
	 * 
	 * @param context
	 * @return
	 */
	public static String getVerName(Context context) {
		String verName = "";
		try {
			verName = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return verName; // 版本名称

	}

	/**
	 * 判断手机是否锁屏状态
	 * 
	 * @param context
	 * @return
	 */
	public final static boolean isScreenLocked(Context context) {
		android.app.KeyguardManager mKeyguardManager = (KeyguardManager) context
				.getSystemService(Context.KEYGUARD_SERVICE);
		// System.out.println("屏幕状态"
		// + !mKeyguardManager.inKeyguardRestrictedInputMode());
		return !mKeyguardManager.inKeyguardRestrictedInputMode();
	}

	/**
	 * 获取屏幕分辨率 宽 高 手机屏幕分辨率为：dm.widthPixels × dm.heightPixels;
	 * 
	 * @param act
	 * @return
	 */
	public static DisplayMetrics getScreenrp(Activity act) {
		DisplayMetrics dm = new DisplayMetrics();
		act.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm;
	}

	/**
	 * 获取屏幕宽高 display.getWidth() display.getHeight();
	 * 
	 * @param context
	 * @return
	 */
	public static Display getScreenSize(Context context) {
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		return manager.getDefaultDisplay();
	}

	/**
	 * 安装Apk
	 * 
	 * @param context
	 * @param file
	 */
	public static void installApk(Context context, File file) {
		Intent intent = new Intent();
		// 执行动作
		intent.setAction(Intent.ACTION_VIEW);
		// 执行的数据类型
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		context.startActivity(intent);
	}

	/**
	 * 静默安装Apk
	 * 
	 * @param context
	 * @param file
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	public static void installApkSilent(Context context, File file) {
		// 要安装的apk包名
		String packageName = context.getPackageName();
		try {
			Class packagemanage = Class
					.forName("android.content.pm.PackageManager");

			Class packageInstallObserver = Class
					.forName("android.content.pm.IPackageInstallObserver");

			Method installPackage = packagemanage.getMethod("installPackage",
					Uri.class, packageInstallObserver, int.class, String.class);
			// 0x00000002
			int INSTALL_REPLACE_EXISTING = packagemanage.getField(
					"INSTALL_REPLACE_EXISTING").getInt(null);
			Object iActivityManager = installPackage.invoke(
					context.getPackageManager(), Uri.fromFile(file), null,
					INSTALL_REPLACE_EXISTING, packageName);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取手机的IMEI码
	 * 
	 * @param context
	 * @return
	 */
	public static String getIMEI(Context context) {
		TelephonyManager manager = (TelephonyManager) context
				.getSystemService(Activity.TELEPHONY_SERVICE);
		// check if has the permission
		if (PackageManager.PERMISSION_GRANTED == context.getPackageManager()
				.checkPermission(Manifest.permission.READ_PHONE_STATE,
						context.getPackageName())) {
			return manager.getDeviceId();
		} else {
			return null;
		}
	}

	/**
	 * 获取手机系统版本
	 * 
	 * @return
	 */
	public static int getSysVersion() {
		return Build.VERSION.SDK_INT;
	}

	/**
	 * 判断网络是否连通
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isOnline(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Activity.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
		if (info != null && info.isConnected()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断网络连接类型：GPRS、WIFI、未连接
	 * 
	 * @param context
	 * @return
	 */
	public static String getConnectTypeName(Context context) {
		if (!isOnline(context)) {
			return "OFFLINE";
		}
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Activity.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
		if (info != null) {
			return info.getTypeName();
		} else {
			return "OFFLINE";
		}
	}

	/**
	 * 创建文件夹
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean createDirectory(String filePath) {
		if (null == filePath) {
			return false;
		}
		File file = new File(filePath);
		if (file.exists()) {
			return true;
		}
		return file.mkdirs();
	}

	/**
	 * 删除目录
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean deleteDirectory(String filePath) {
		if (null == filePath) {
			return false;
		}
		File file = new File(filePath);
		if (file == null || !file.exists()) {
			return false;
		}
		if (file.isDirectory()) {
			File[] list = file.listFiles();
			for (int i = 0; i < list.length; i++) {
				// Log.d(TAG, "delete filePath: " + list[i].getAbsolutePath());
				if (list[i].isDirectory()) {
					deleteDirectory(list[i].getAbsolutePath());
				} else {
					list[i].delete();
				}
			}
		}
		// Log.d(TAG, "delete filePath: " + file.getAbsolutePath());
		file.delete();
		return true;
	}

	/**
	 * 保留小数点位数
	 * 
	 * @param num
	 * @param wei
	 * @return
	 */
	public static String setDecimal(Object num, int wei) {
		NumberFormat format2 = null;
		switch (wei) {
		case 1:
			format2 = new DecimalFormat("0.0");
			break;
		case 2:
			format2 = new DecimalFormat("0.00");
			break;
		}
		return format2.format(num);
	}

	/**
	 * 获取系统当前时间
	 * 
	 * @return String
	 */
	public static String getTimeForString() {
		return new SimpleDateFormat("HH:mm:ss").format(new Date());
	}

	/**
	 * dip互转px
	 * 
	 * @param context
	 * @param dipValue
	 * @return
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 判断桌面是否已添加快捷方式
	 * 
	 * @param cx
	 * @param titleName
	 *            快捷方式名称
	 * @return
	 */
	public static boolean hasShortcut(Context cx) {
		boolean result = false;
		// 获取当前应用名称
		String title = null;
		try {
			final PackageManager pm = cx.getPackageManager();
			title = pm.getApplicationLabel(
					pm.getApplicationInfo(cx.getPackageName(),
							PackageManager.GET_META_DATA)).toString();
		} catch (Exception e) {
		}
		final String uriStr;
		if (android.os.Build.VERSION.SDK_INT < 8) {
			uriStr = "content://com.android.launcher.settings/favorites?notify=true";
		} else {
			uriStr = "content://com.android.launcher2.settings/favorites?notify=true";
		}
		final Uri CONTENT_URI = Uri.parse(uriStr);
		final Cursor c = cx.getContentResolver().query(CONTENT_URI, null,
				"title=?", new String[] { title }, null);
		if (c != null && c.getCount() > 0) {
			result = true;
		}
		return result;
	}

	/**
	 * 为当前应用添加桌面快捷方式
	 * 
	 * @param cx
	 * @param appName
	 *            快捷方式名称  
	 */
	public static void addShortcut(Context cx) {
		Intent shortcut = new Intent(
				"com.android.launcher.action.INSTALL_SHORTCUT");
		Intent shortcutIntent = cx.getPackageManager()
				.getLaunchIntentForPackage(cx.getPackageName());
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
		// 获取当前应用名称
		String title = null;
		try {
			final PackageManager pm = cx.getPackageManager();
			title = pm.getApplicationLabel(
					pm.getApplicationInfo(cx.getPackageName(),
							PackageManager.GET_META_DATA)).toString();
		} catch (Exception e) {
		}
		// 快捷方式名称
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
		// 不允许重复创建(不一定有效)
		shortcut.putExtra("duplicate", false);
		// 快捷方式的图标
		Parcelable iconResource = Intent.ShortcutIconResource.fromContext(cx,
				R.drawable.icon);
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconResource);
		cx.sendBroadcast(shortcut);
	}

	/**
	 * 删除当前应用的桌面快捷方式
	 * 
	 * @param cx
	 *             
	 */
	public static void delShortcut(Context cx, String pkname) {
		Intent shortcut = new Intent(
				"com.android.launcher.action.UNINSTALL_SHORTCUT");
		// 获取当前应用名称
		String title = null;
		try {
			final PackageManager pm = cx.getPackageManager();
			title = pm
					.getApplicationLabel(
							pm.getApplicationInfo(pkname,
									PackageManager.GET_META_DATA)).toString();
		} catch (Exception e) {
		}
		// 快捷方式名称
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
		Intent shortcutIntent = cx.getPackageManager()
				.getLaunchIntentForPackage(pkname);
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
		cx.sendBroadcast(shortcut);
	}

	/**
	 * 通过包名检测系统中是否安装某个应用程序
	 * 
	 * @param context
	 * @param packageName
	 *            ：应用程序的包名(QB:com.tencent.mtt)
	 * @return true : 系统中已经安装该应用程序
	 * @return false : 系统中未安装该应用程序
	 * */
	public static boolean checkApkExist(Context context, String packageName) {
		if (packageName == null || "".equals(packageName)) {
			return false;
		}
		try {
			context.getPackageManager().getApplicationInfo(packageName,
					PackageManager.GET_UNINSTALLED_PACKAGES);
			return true;
		} catch (NameNotFoundException e) {
			return false;
		}
	}

	// <uses-permission
	// android:name="com.android.launcher.permission.INSTALL_SHORTCUT" />
	// <uses-permission
	// android:name="com.android.launcher.permission.UNINSTALL_SHORTCUT" />
	// <uses-permission
	// android:name="com.android.launcher.permission.READ_SETTINGS" />

	// 执行linux命令但不关注结果输出
	public static int execRootCmdSilent(String paramString) {
		try {
			Process localProcess = Runtime.getRuntime().exec("su");
			Object localObject = localProcess.getOutputStream();
			DataOutputStream localDataOutputStream = new DataOutputStream(
					(OutputStream) localObject);
			String str = String.valueOf(paramString);
			localObject = str + "\n";
			localDataOutputStream.writeBytes((String) localObject);
			localDataOutputStream.flush();
			localDataOutputStream.writeBytes("exit\n");
			localDataOutputStream.flush();
			localProcess.waitFor();
			localObject = localProcess.exitValue();
			return Integer.parseInt(localObject.toString());
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return 0;
	}

	// 判断机器Android是否已经root，即是否获取root权限
	public static boolean haveRoot() {

		int i = execRootCmdSilent("echo test"); // 通过执行测试命令来检测
		if (i != -1)
			return true;
		return false;
	}

	public static boolean runRootCommand(String command) {
		Process process = null;
		DataOutputStream os = null;
		try {
			process = Runtime.getRuntime().exec("su");
			os = new DataOutputStream(process.getOutputStream());
			os.writeBytes(command + "\n");
			os.writeBytes("exit\n");
			os.flush();
			process.waitFor();
		} catch (Exception e) {
			// Log.d(TAG， "the device is not rooted， error message： " +
			// e.getMessage());
			return false;
		} finally {
			try {
				if (os != null) {
					os.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	/**
	 * 静默安装
	 * 
	 * @param file
	 * @return
	 */
	public static boolean slientInstall(File file) {
		boolean result = false;
		Process process = null;
		OutputStream out = null;
		try {
			process = Runtime.getRuntime().exec("su");
			out = process.getOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(out);
			dataOutputStream.writeBytes("chmod 777 " + file.getPath() + "\n");
			dataOutputStream
					.writeBytes("LD_LIBRARY_PATH=/vendor/lib:/system/lib pm install -r "
							+ file.getPath());
			// 提交命令
			dataOutputStream.flush();
			// 关闭流操作
			dataOutputStream.close();
			out.close();
			int value = process.waitFor();

			// 代表成功
			if (value == 0) {
				result = true;
			} else if (value == 1) { // 失败
				result = false;
			} else { // 未知情况
				result = false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return result;
	}

	// 注意代码中pm install -r
	// 前面的LD_LIBRARY_PATH=/vendor/lib:/system/lib，在4.0版本以上必须加上，不然会无法安装。

	public static boolean installFile(File path, Context context) {
		boolean result = false;
		Process process = null;
		OutputStream out = null;
		InputStream in = null;
		String state = null;
		try {
			// 请求root
			process = Runtime.getRuntime().exec("su");
			out = process.getOutputStream();

			// 调用安装，将文件写入到process里面
			out.write(("pm install -r " + path + "\n").getBytes());
			// 这里拿到输出流，开始安装操作
			in = process.getInputStream();
			int len = 0;
			byte[] bs = new byte[256];
			while (-1 != (len = in.read(bs))) {
				state = new String(bs, 0, len);
				if (state.equals("Success\n")) {
					// 安装成功后的操作
					result = true;
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
				if (in != null) {
					in.close();
				}
				if (process != null) {
					process.destroy();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/*
	 * 对要卸载的apk赋予权限
	 */
	public static void chmodApk(String busybox, String chmod) {
		try {

			Process process = null;
			DataOutputStream os = null;

			process = Runtime.getRuntime().exec("su");
			os = new DataOutputStream(process.getOutputStream());
			os.writeBytes(busybox);
			os.flush();

			os.writeBytes(chmod);
			os.flush();

			os.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * 卸载apk
	 */
	public static void uninstallApk(String uninstallapk) {
		try {

			Process process = null;
			DataOutputStream os = null;
			process = Runtime.getRuntime().exec("su");
			os = new DataOutputStream(process.getOutputStream());
			os.writeBytes(uninstallapk);
			os.flush();
			os.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}