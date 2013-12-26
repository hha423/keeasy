package cn.keeasy.business;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import cn.evan.tools.PhoneState;
import cn.keeasy.business.mod.LBSMod;
import cn.keeasy.business.util.PM;
import cn.keeasy.business.util.Skip;
import cn.keeasy.business.util.Sources;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MKMapTouchListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.platform.comapi.basestruct.GeoPoint;

/**
 * GPS精确定位 －Evan
 */

public class LBSActivity extends BaseActivity {

	private Button location, subject;
	private LBSMod mod;
	private ImageView lbs_back;
	private TextView lbs_title;
	BMapManager mBMapMan = null;
	MapView mMapView = null;
	MapController mMapController = null;
	// 定位图层
	locationOverlay myLocationOverlay = null;
	private boolean flag = true;
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();
	private LocationManager locationManager;
	LocationData locData = null;
	private MyOverlay mOverlay = null;
	AlertDialog.Builder alertDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mBMapMan = new BMapManager(getApplication());
		mBMapMan.init(MyApp.strKey, null);
		setContentView(R.layout.lbs_main);
		super.onCreate(savedInstanceState);
	}

	@Override
	void initView() {
		super.initView();
		location = (Button) this.findViewById(R.id.lbs_lacbtn);
		subject = (Button) this.findViewById(R.id.lbs_okbtn);
		mMapView = (MapView) this.findViewById(R.id.mapView);
		lbs_back = (ImageView) this.findViewById(R.id.lbs_back);
		lbs_title = (TextView) this.findViewById(R.id.lbs_title);
	}

	@Override
	void initData() {
		lbs_title.setText("店铺定位");
		mLocationClient = new LocationClient(getApplicationContext()); // 声明LocationClient类
		mLocationClient.registerLocationListener(myListener); // 注册监听函数
		mMapView.regMapTouchListner(mapTouchListener);
		mMapView.setBuiltInZoomControls(true);
		mMapController = mMapView.getController();
		mMapController.setZoom(18);// 设置地图zoom级别
		mMapView.setBuiltInZoomControls(false);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		locData = new LocationData();
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setAddrType("all");// 返回的定位结果包含地址信息
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(5000);// 设置发起定位请求的间隔时间为5000ms
		option.disableCache(true);// 禁止启用缓存定位
		mLocationClient.setLocOption(option);
		mLocationClient.start();
		// 定位图层初始化
		myLocationOverlay = new locationOverlay(mMapView);
		// 设置定位数据
		myLocationOverlay.setData(locData);
		myLocationOverlay.enableCompass();
		// 修改定位数据后刷新图层生效
		mMapView.refresh();
		alertDialog = new AlertDialog.Builder(mContext);
		if (!locationManager
				.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
			alertDialog
					.setMessage("如果需要精确定位请到室外\n然后开启GPS卫星定位,是否\n现在打开GPS")
					.setPositiveButton("好的去开启",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									Intent intent = new Intent(
											Settings.ACTION_LOCATION_SOURCE_SETTINGS);
									startActivityForResult(intent, 0); // 此为设置完成后返回到获取界面
								}
							})
					.setNegativeButton("暂不开启",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
								}
							}).setCancelable(false).create().show();
		}
		mod = new LBSMod(mContext, this);
	}

	@Override
	void initListen() {
		super.initView();
		location.setOnClickListener(this);
		location.setOnTouchListener(PM.touchLight);
		subject.setOnClickListener(this);
		subject.setOnTouchListener(PM.touchLight);
		lbs_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == location) {
			flag = true;
			if (mLocationClient != null && mLocationClient.isStarted()) {
				mLocationClient.requestLocation();
				PhoneState.showToast(mContext, "正在定位中……");
				location.setVisibility(View.VISIBLE);
			}
		} else if (v == subject) {
			// double loo = 114.037989;
			// double laa = 22.663547;
			if (locData.latitude != 0) {
				mod.mSetPoint(Sources.USERBEAN.shopId, locData.latitude,
						locData.longitude, Sources.USERBEAN.shopIC);
			}
		} else if (v == lbs_back) {
			PM.onBtnAnim(lbs_back);
			Skip.mBack(this);
		}
	}

	MKMapTouchListener mapTouchListener = new MKMapTouchListener() {
		@Override
		public void onMapClick(GeoPoint point) {
			// 在此处理地图单事件
			location.setVisibility(View.GONE);
			mMapView.getOverlays().clear();
			mOverlay = new MyOverlay(getResources().getDrawable(
					R.drawable.color_pins), mMapView);
			GeoPoint p1 = new GeoPoint(point.getLatitudeE6(),
					point.getLongitudeE6());
			OverlayItem item1 = new OverlayItem(p1, "选中当前点", "");
			mOverlay.addItem(item1);
			mMapView.getOverlays().add(mOverlay);
			mMapView.refresh();
			setLocation(point);
		}

		@Override
		public void onMapDoubleClick(GeoPoint point) {
			// 在此处理地图双击事件
		}

		@Override
		public void onMapLongClick(GeoPoint point) {
			// 在此处理地图长按事件
		}
	};

	@Override
	public void mSuccess(String str) {
		PhoneState.showToast(mContext, str);
		Skip.mBack(this);
	}

	@Override
	protected void onDestroy() {
		// 退出时销毁定位
		if (mLocationClient != null)
			mLocationClient.stop();
		mMapView.destroy();
		if (mBMapMan != null) {
			mBMapMan.destroy();
			mBMapMan = null;
		}
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		if (mBMapMan != null) {
			mBMapMan.stop();
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		if (mBMapMan != null) {
			mBMapMan.start();
		}
		super.onResume();
	}

	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			locData.latitude = location.getLatitude();
			locData.longitude = location.getLongitude();
			// 如果不显示定位精度圈，将accuracy赋值为0即可
			locData.accuracy = location.getRadius();
			locData.direction = location.getDerect();
			myLocationOverlay.setData(locData);
			if (flag) {
				flag = false;
				mMapView.getOverlays().clear();
				mMapView.getOverlays().add(myLocationOverlay);
				// 更新定位数据
				try {
					mMapController.animateTo(new GeoPoint(
							(int) (locData.latitude * 1e6),
							(int) (locData.longitude * 1e6)));
				} catch (Exception e) {
					e.printStackTrace();
				}
				PhoneState.showToast(mContext, location.getAddrStr());
			}
			// 更新图层数据执行刷新后生效
			mMapView.refresh();
		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
		}
	}

	// 继承MyLocationOverlay重写dispatchTap实现点击处理
	public class locationOverlay extends MyLocationOverlay {
		public locationOverlay(MapView mapView) {
			super(mapView);
		}

		@Override
		protected boolean dispatchTap() {
			// 处理点击事件,弹出泡泡
			return true;
		}
	}

	@SuppressWarnings("rawtypes")
	public class MyOverlay extends ItemizedOverlay {

		public MyOverlay(Drawable defaultMarker, MapView mapView) {
			super(defaultMarker, mapView);
		}

		@Override
		public boolean onTap(int index) {
			final OverlayItem item = getItem(index);
			setLocation(item.getPoint());
			return true;
		}

		@Override
		public boolean onTap(GeoPoint pt, MapView mMapView) {
			return false;
		}

	}

	private void setLocation(final GeoPoint point) {
		alertDialog
				.setIcon(android.R.drawable.ic_menu_edit)
				.setTitle("手动定位地址")
				.setMessage(
						"当前经纬度:" + point.getLatitudeE6() / 1e6 + "-"
								+ point.getLongitudeE6() / 1e6
								+ "\n点取消关闭后可点定位当前位置更新")
				.setPositiveButton("提交当前位置",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								mod.mSetPoint(Sources.USERBEAN.shopId,
										point.getLatitudeE6() / 1e6,
										point.getLongitudeE6(),
										Sources.USERBEAN.shopIC);
							}
						}).setNegativeButton("取消",

				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				}).setCancelable(false).create().show();
	}

}