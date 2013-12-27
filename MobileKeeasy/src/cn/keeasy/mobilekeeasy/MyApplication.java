package cn.keeasy.mobilekeeasy;

import android.app.Application;
import android.content.Context;
import android.widget.TextView;
import cn.evan.tools.PhoneState;
import cn.jpush.android.api.JPushInterface;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;
import cn.keeasy.mobilekeeasy.data.Sources;
import cn.keeasy.mobilekeeasy.utils.CrashHandler;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class MyApplication extends Application {

	public static final String strKey = "66GtLmMoOHgQw2NT9kUpFqC0";
	private static MyApplication mInstance = null;
	public BMapManager mBMapManager = null;
	public TextView mTv;
	private String mData;
	public LocationClient mLocationClient = null;
	public MyLocationListenner myListener = new MyLocationListenner();

	@Override
	public void onCreate() {
		mLocationClient = new LocationClient(this);
		mLocationClient.registerLocationListener(myListener);
		super.onCreate();
		mInstance = this;
		initEngineManager(getApplicationContext());
		JPushInterface.setDebugMode(false); // 设置开启日志,发布时请关闭日志
		JPushInterface.init(getApplicationContext()); // 初始化 JPush
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext())
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
		// 异常处理，不需要处理时注释掉这两句即可！
//		CrashHandler crashHandler = CrashHandler.getInstance();
//		crashHandler.init(getApplicationContext());// 注册crashHandler
		// crashHandler.sendPreviousReportsToServer();
	}

	public void initEngineManager(Context context) {
		if (mBMapManager == null) {
			mBMapManager = new BMapManager(context);
		}

		if (!mBMapManager.init(strKey, new MyGeneralListener())) {
			PhoneState.showToast(MyApplication.getInstance()
					.getApplicationContext(), "BMapManager  初始化错误!");
		}
	}

	public static MyApplication getInstance() {
		return mInstance;
	}

	// 常用事件监听，用来处理通常的网络错误，授权验证错误等
	static class MyGeneralListener implements MKGeneralListener {

		@Override
		public void onGetNetworkState(int iError) {
			if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
				PhoneState.showToast(MyApplication.getInstance()
						.getApplicationContext(), "您的网络出错啦！");
			} else if (iError == MKEvent.ERROR_NETWORK_DATA) {
				PhoneState.showToast(MyApplication.getInstance()
						.getApplicationContext(), "输入正确的检索条件！");
			}
		}

		@Override
		public void onGetPermissionState(int iError) {
			if (iError == MKEvent.ERROR_PERMISSION_DENIED) {
				// 授权Key错误：
				System.out.println("请在MyApplication.java文件输入正确的授权Key！");
			}
		}
	}

	/**
	 * 监听函数，又新位置的时候，格式化成字符串，输出到屏幕中
	 */
	public class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			Sources.longitude = location.getLongitude();
			Sources.latitude = location.getLatitude();
			logMsg("当前地址: " + location.getAddrStr());
		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null)
				return;
			Sources.longitude = poiLocation.getLongitude();
			Sources.latitude = poiLocation.getLatitude();
			logMsg("当前地址: " + poiLocation.getAddrStr());
		}
	}

	/**
	 * 显示字符串
	 * 
	 * @param str
	 */
	public void logMsg(String str) {
		try {
			mData = str;
			if (mTv != null)
				mTv.setText(mData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
