package cn.keeasy.business;

import android.app.Application;
import cn.keeasy.business.util.CrashHandler;

public class MyApp extends Application {

	public static final String strKey = "oWl3cSFQ2x0zZh7sWe1RAQtI";

	@Override
	public void onCreate() {
		super.onCreate();
		// JPushInterface.setDebugMode(true); // 设置开启日志,发布时请关闭日志
		// JPushInterface.init(this); // 初始化 JPush
		// 异常处理，不需要处理时注释掉这两句即可！
		CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(getApplicationContext()); // 注册crashHandler
		// crashHandler.sendPreviousReportsToServer();
	}

}
