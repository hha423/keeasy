package cn.keeasy.mobilekeeasy;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import cn.evan.tools.PhoneState;
import cn.keeasy.mobilekeeasy.custom.SearchDialog;
import cn.keeasy.mobilekeeasy.dao.MapSearchMod;
import cn.keeasy.mobilekeeasy.data.Sources;
import cn.keeasy.mobilekeeasy.entity.BusinessBean;
import cn.keeasy.mobilekeeasy.utils.BMapUtil;
import cn.keeasy.mobilekeeasy.utils.PM;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MKMapViewListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;

public class MapActivity extends BaseActivity {
	// 定位相关
	LocationClient mapLocClient;
	LocationData locData = null;
	public MyLocaListenner myListener = new MyLocaListenner();
	boolean isRequest = false;// 是否手动触发请求定位
	boolean isFirstLoc = true;// 是否首次定位
	private MyApplication app;
	// 定位图层
	locationOverlay myLocationOverlay = null;
	// MapView 是地图主控件
	private MapView mMapView = null;
	// 用MapController完成地图控制
	private MapController mMapController = null;
	private MyOverlay mOverlay = null;
	private ArrayList<OverlayItem> mItems = null;
	private PopupOverlay pop = null;
	private View viewCache = null;
	private TextView popupIc = null;
	private TextView popupName = null;
	private TextView popupAdd = null;
	private TextView go = null;
	private Button button;

	private MapSearchMod msMod;
	private SearchDialog dialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// 使用地图sdk前需先初始化BMapManager. BMapManager是全局的，可为多个MapView共用，它需要地图模块创建前创建，
		// 并在地图地图模块销毁后销毁，只要还有地图模块在使用，BMapManager就不应该销毁
		app = (MyApplication) this.getApplication();
		if (app.mBMapManager == null) {
			app.mBMapManager = new BMapManager(this);
			// 如果BMapManager没有初始化则初始化BMapManager
			app.mBMapManager.init(MyApplication.strKey,
					new MyApplication.MyGeneralListener());
		}
		// 由于MapView在setContentView()中初始化,所以它需要在BMapManager初始化之后
		setContentView(R.layout.main_map);
		super.onCreate(savedInstanceState);
	}

	@Override
	void initView() {
		super.initView();
		mMapView = (MapView) findViewById(R.id.bmapView);
		button = (Button) findViewById(R.id.location);
		mMapView.regMapViewListener(app.mBMapManager, mapViewListener); // 注册监听
		viewCache = getLayoutInflater().inflate(R.layout.dialog_location, null);
		popupIc = (TextView) viewCache.findViewById(R.id.text2);
		popupName = (TextView) viewCache.findViewById(R.id.text1);
		popupAdd = (TextView) viewCache.findViewById(R.id.text3);
		go = (TextView) viewCache.findViewById(R.id.text4);
		pop = new PopupOverlay(mMapView, popListener);
	}

	@Override
	void initData() {
		topback.setVisibility(View.VISIBLE);
		toptitle.setText("地图模式");
		dialog = new SearchDialog(this);
		msMod = new MapSearchMod(this, this);
		// 地图初始化
		mMapController = mMapView.getController(); // 获取地图控制器
		mMapController.enableClick(true);// 设置地图是否响应点击事件 .
		mMapController.setZoom(17);// 设置地图缩放级别
		mMapView.setBuiltInZoomControls(true);// 显示内置缩放控件

		// 定位初始化
		mapLocClient = new LocationClient(this);
		locData = new LocationData();
		mapLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(5000);
		mapLocClient.setLocOption(option);
		mapLocClient.start();

		// 定位图层初始化
		myLocationOverlay = new locationOverlay(mMapView);
		myLocationOverlay.setData(locData);// 设置定位数据
		mMapView.getOverlays().add(myLocationOverlay);// 添加定位图层
		myLocationOverlay.enableCompass();

		// 修改定位数据后刷新图层生效
		mMapView.refresh();
	}

	@Override
	void initListen() {
		super.initListen();
		button.setOnClickListener(this);
		button.setOnTouchListener(PM.touchLight);
		go.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		if (v == button) {
			requestLocClick();
		} else if (v == go) {
			PM.onBtnAnim(go);
			dialog = new SearchDialog(MapActivity.this,
					android.R.style.Theme_NoTitleBar_Fullscreen);
			dialog.mShow();
		}
	}

	// 手动触发一次定位请求
	public void requestLocClick() {
		isRequest = true;
		mapLocClient.requestLocation();
		PhoneState.showToast(MapActivity.this, "正在定位当前位置……");
	}

	// 创建一个popupoverlay
	PopupClickListener popListener = new PopupClickListener() {
		@Override
		public void onClickedPopup(int index) {
			if (!Sources.SCANTEXT.equals("")) {
				dialog = new SearchDialog(MapActivity.this,
						android.R.style.Theme_NoTitleBar_Fullscreen);
				dialog.mShow();
			}
		}
	};

	@Override
	public void mSuccess() {
		mOverlay = new MyOverlay(
				getResources().getDrawable(R.drawable.ditu_01), mMapView);
		try {
			mOverlay.removeAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (pop != null) {
			pop.hidePop();
		}
		mMapView.refresh();
		List<BusinessBean> lt = Sources.MAPBUESLIST;
		for (BusinessBean b : lt) {
			// 准备overlay 数据
			OverlayItem smb = new OverlayItem(new GeoPoint(
					(int) (b.addrY * 1e6), (int) (b.addrX * 1e6)), b.shopName
					+ "," + b.account, b.shopAddr);
			mOverlay.addItem(smb);
		}
		// 保存所有item，以便overlay在reset后重新添加
		mItems = new ArrayList<OverlayItem>();
		mItems.addAll(mOverlay.getAllItem());
		try {
			mMapView.getOverlays().add(mOverlay);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mMapView.invalidate();
		mMapView.refresh();
	}

	// 地图事件监听
	MKMapViewListener mapViewListener = new MKMapViewListener() {

		@Override
		public void onMapMoveFinish() {
			// 此处可以实现地图移动完成事件的状态监听
			msMod.mGetShopListUrl(mMapView.getMapCenter(),
					mMapView.getLatitudeSpan(), mMapView.getLongitudeSpan(), 1);
		}

		@Override
		public void onClickMapPoi(MapPoi arg0) {
			// 此处可实现地图点击事件的监听
			popupName.setBackgroundResource(R.drawable.popup);
			popupName.setText(arg0.strText);
			pop.showPopup(BMapUtil.getBitmapFromView(popupName), arg0.geoPt, 8);
		}

		@Override
		public void onGetCurrentMap(Bitmap b) {
			// 用MapView.getCurrentMap()发起截图后，在此处理截图结果.
		}

		@Override
		public void onMapAnimationFinish() {
			// 地图完成带动画的操作（如: animationTo()）后，此回调被触发
			msMod.mGetShopListUrl(mMapView.getMapCenter(),
					mMapView.getLatitudeSpan(), mMapView.getLongitudeSpan(), 1);
		}

		@Override
		public void onMapLoadFinish() {
			// 地图初始化完成时，此回调被触发.
		}
	};

	// 定位SDK监听函数
	public class MyLocaListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			locData.latitude = location.getLatitude();
			locData.longitude = location.getLongitude();
			// 如果不显示定位精度圈，将accuracy赋值为0即可
			locData.accuracy = location.getRadius();
			// 此处可以设置 locData的方向信息, 如果定位 SDK 未返回方向信息，用户可以自己实现罗盘功能添加方向信息。
			locData.direction = location.getDerect();
			// 更新定位数据
			myLocationOverlay.setData(locData);
			// 更新图层数据执行刷新后生效
			mMapView.refresh();
			// 是手动触发请求或首次定位时，移动到定位点
			if (isRequest || isFirstLoc) {
				// 移动地图到定位点
				mMapController.animateTo(new GeoPoint(
						(int) (locData.latitude * 1e6),
						(int) (locData.longitude * 1e6)));
				isRequest = false;
				// myLocationOverlay.setLocationMode(LocationMode.FOLLOWING);
			}
			// 首次定位完成
			isFirstLoc = false;
		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
		}

	}

	// 定位 继承MyLocationOverlay重写dispatchTap实现点击处理
	public class locationOverlay extends MyLocationOverlay {

		public locationOverlay(MapView mapView) {
			super(mapView);
		}

		@Override
		protected boolean dispatchTap() {
			// 处理点击事件,弹出泡泡
			popupName.setBackgroundResource(R.drawable.popup);
			popupName.setText("我的位置");
			pop.showPopup(BMapUtil.getBitmapFromView(popupName), new GeoPoint(
					(int) (locData.latitude * 1e6),
					(int) (locData.longitude * 1e6)), 8);
			return true;
		}

	}

	// 覆盖物 继承ItemizedOverlay重写Tap实现点击处理
	@SuppressWarnings("rawtypes")
	public class MyOverlay extends ItemizedOverlay {

		public MyOverlay(Drawable defaultMarker, MapView mapView) {
			super(defaultMarker, mapView);
		}

		@Override
		public boolean onTap(int index) {
			OverlayItem item = getItem(index);
			popupName.setBackgroundResource(android.R.color.white);
			String[] title = getItem(index).getTitle().split(",");
			popupName.setText(title[0]);
			Sources.SCANTEXT = title[0];
			// popupIc.setText(title[1]);
			popupAdd.setText(getItem(index).getSnippet());
			Bitmap bitMaps = BMapUtil.getBitmapFromView(viewCache);
			pop.showPopup(bitMaps, item.getPoint(), 39);
			return true;
		}

		@Override
		public boolean onTap(GeoPoint pt, MapView mMapView) {
			if (pop != null) {
				pop.hidePop();
				Sources.SCANTEXT = "";
			}
			return false;
		}
	}

	@Override
	protected void onPause() {
		// MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		// MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
		mMapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// 退出时销毁定位
		if (mapLocClient != null)
			mapLocClient.stop();
		// MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
		mMapView.destroy();
		super.onDestroy();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mMapView.onSaveInstanceState(outState);

	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		mMapView.onRestoreInstanceState(savedInstanceState);
	}
}