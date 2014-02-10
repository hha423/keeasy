package cn.keeasy.mobilekeeasy;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.evan.tools.PhoneState;
import cn.evan.tools.ui.MListView1;
import cn.jpush.android.api.JPushInterface;
import cn.keeasy.mobilekeeasy.adapter.FriendAdapter;
import cn.keeasy.mobilekeeasy.adapter.HomeAdapter;
import cn.keeasy.mobilekeeasy.adapter.NearbyAdapter;
import cn.keeasy.mobilekeeasy.adapter.ZhekeAdapter;
import cn.keeasy.mobilekeeasy.config.AccessTokenKeeper;
import cn.keeasy.mobilekeeasy.config.AppConstants;
import cn.keeasy.mobilekeeasy.custom.ExitDialog;
import cn.keeasy.mobilekeeasy.custom.MyPopupWindow;
import cn.keeasy.mobilekeeasy.custom.ReListView;
import cn.keeasy.mobilekeeasy.custom.ReListView.OnLoadMoreListener;
import cn.keeasy.mobilekeeasy.custom.ReListView.OnRefreshListener;
import cn.keeasy.mobilekeeasy.custom.ScrollLayout;
import cn.keeasy.mobilekeeasy.custom.SearchDialog;
import cn.keeasy.mobilekeeasy.custom.ShidianPopupWindow;
import cn.keeasy.mobilekeeasy.custom.XListView;
import cn.keeasy.mobilekeeasy.custom.XListView.IXListViewListener;
import cn.keeasy.mobilekeeasy.dao.AreaMod;
import cn.keeasy.mobilekeeasy.dao.ClassMod;
import cn.keeasy.mobilekeeasy.dao.ContactMod;
import cn.keeasy.mobilekeeasy.dao.MoneyMod;
import cn.keeasy.mobilekeeasy.dao.MsgCountMod;
import cn.keeasy.mobilekeeasy.dao.ShopListMod;
import cn.keeasy.mobilekeeasy.dao.ShopListNearMod;
import cn.keeasy.mobilekeeasy.dao.ZhekeMod;
import cn.keeasy.mobilekeeasy.data.ExitdialogInterface;
import cn.keeasy.mobilekeeasy.data.Sources;
import cn.keeasy.mobilekeeasy.utils.PM;
import cn.keeasy.mobilekeeasy.utils.Skip;
import cn.keeasy.mobilekeeasy.utils.VersionUpdate;
import cn.keeasy.mobilekeeasy.utils.WarningTone;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.tencent.tauth.Tencent;

public class MainActivity extends BaseActivity implements OnRefreshListener,
		OnLoadMoreListener, ExitdialogInterface, IXListViewListener {

	private int page = 1; // 翻页页码
	private long exitTime = 0;
	private ScrollLayout mPager;
	private SearchDialog dialog;
	private RadioGroup main_group;
	private RadioButton img1, img2, img3; // 页卡
	private ImageView topfind;

	private Handler homeHandler;
	private Handler meHandler;
	private Handler moreHandler;
	private Handler nearbyHandler;

	private AreaMod amod;
	private ClassMod cmod;
	private ImageView selicon;
	private ShopListMod slmod;
	private boolean flag = true;
	private XListView listview;
	private Handler lHander;
	private HomeAdapter homeAdapter;
	private MyPopupWindow popupWindow;
	private RelativeLayout home_selet;
	private TextView home_quyu, home_fenlie, home_shiduan;

	private int pages = 1;
	private ReListView rlist;
	private TextView near_dingwei = null;
	private ImageView nc_img, ns_img;
	private TextView near_class, near_shidian;
	private ShopListNearMod snMod;
	private NearbyAdapter nearadapter;
	private ShidianPopupWindow spwin;
	// 定位相关
	private LocationClient mLocClient = null;
	// private MyLocationListenner myListener = new MyLocationListenner();

	private boolean newvs;
	private TextView cache;
	private TextView weibo;
	private TextView about;
	private TextView weixin;
	private TextView version;
	private ImageView zhaoshang;
	private LinearLayout update;

	private ZhekeMod zhemod;
	private ReListView zhelist;
	private TextView mz_no;
	private ZhekeAdapter zheadapter;
	private int zpage = 1;

	private MoneyMod mmod;
	private TextView me_num;
	private ImageView editok;
	private TextView me_user;
	private MListView1 me_list;
	private ImageView me_item;
	private TextView me_msgnum;
	private MsgCountMod msgmod;
	private ContactMod fridmod;
	private boolean frid = true;
	private TextView me_moneyuser;
	private LinearLayout me_exits;
	private LinearLayout me_order;
	private ExitDialog exitdialog;
	private TextView me_moneytotla;
	private TextView me_moneyexiter;
	private FriendAdapter meadapter;
	private RelativeLayout mefriend;
	private WarningTone warn;
	int oldmsg = -1;

	private String uninstallapk1;
	private Builder dialgo;
	public VersionUpdate vupdate;

	// TODO Auto-generated method stub
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.main_activity);
		super.onCreate(savedInstanceState);
		setTop();
	}

	@Override
	void initView() {
		super.initView();
		mPager = (ScrollLayout) findViewById(R.id.main_scrolllayout);
		main_group = (RadioGroup) findViewById(R.id.main_rgroup);
		img1 = (RadioButton) findViewById(R.id.btn_home);
		img2 = (RadioButton) findViewById(R.id.btn_naerby);
		img3 = (RadioButton) findViewById(R.id.btn_zhe);
		// img4 = (RadioButton) findViewById(R.id.btn_more);
		topfind = (ImageView) findViewById(R.id.top_cart);
	}

	@Override
	void initData() {
		img1.setChecked(true);
		mPager.setIsScroll(false);
		InitViewPager();
		msgmod = new MsgCountMod(this, this);
		vupdate = new VersionUpdate(this, moreHandler);
		mLocClient = ((MyApplication) getApplication()).mLocationClient;
		((MyApplication) getApplication()).mTv = near_dingwei;
		setLocationOption();
		mLocClient.requestLocation();
	}

	// TODO Auto-generated method stub
	@Override
	void initListen() {
		super.initListen();
		topfind.setOnClickListener(this);
		main_group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.btn_home:
					mPager.setToScreen(0);
					setTop();
					break;
				case R.id.btn_naerby:
					mPager.setToScreen(1);
					topback.setVisibility(View.GONE);
					topfind.setVisibility(View.VISIBLE);
					topfind.setImageResource(R.drawable.map);
					toptitle.setText("附近生活");
					nearbyData();
					break;
				case R.id.btn_zhe:
					mPager.setToScreen(2);
					topback.setVisibility(View.GONE);
					topfind.setVisibility(View.GONE);
					toptitle.setText("限时折扣");
					zheData();
					break;
				case R.id.btn_me:
					mPager.setToScreen(3);
					topback.setVisibility(View.GONE);
					topfind.setVisibility(View.GONE);
					toptitle.setText("用户中心");
					if (!userInfo.getBoolean("islogin", false)) {
						Skip.mNext(MainActivity.this, LoginActivity.class,
								R.anim.activity_push_up_in,
								R.anim.activity_no_anim, false);
					}
					meData();
					break;
				case R.id.btn_more:
					mPager.setToScreen(4);
					topback.setVisibility(View.GONE);
					topfind.setVisibility(View.GONE);
					toptitle.setText("更多项");
					moreData();
					break;
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.top_back:
			PM.onBtnAnim(topback);
			Skip.mNext(this, CaptureActivity.class);
			break;
		case R.id.top_cart:
			PM.onBtnAnim(topfind);
			if (img1.isChecked()) {
				dialog = new SearchDialog(this,
						android.R.style.Theme_NoTitleBar_Fullscreen);
				dialog.mShow();
			} else {
				Skip.mNext(MainActivity.this, MapActivity.class);
			}
			break;

		case R.id.more_zhaoshang:
			PM.onBtnAnim(zhaoshang);
			Skip.mNext(MainActivity.this, ZhaoshangActivity.class);
			break;
		case R.id.more_weixin:
			PM.onBtnAnim(weixin);
			if (PhoneState.checkApkExist(MainActivity.this, "com.tencent.mm")) {
				startActivity(new Intent(
						Intent.ACTION_VIEW,
						Uri.parse("http://weixin.qq.com/r/ZnXfx03EqhDVrQmk9yB0")));
			} else {
				PhoneState.showToast(this, "请先安装微信客户端！");
			}
			break;
		case R.id.more_weibo:
			PM.onBtnAnim(weibo);
			startActivity(new Intent(
					Intent.ACTION_VIEW,
					Uri.parse("http://weibo.cn/attention/add?uid=1784486383&rl=0")));
			break;
		case R.id.more_cache:
			PM.onBtnAnim(cache);
			new AlertDialog.Builder(this)
					.setTitle("清除缓存")
					.setIcon(android.R.drawable.ic_menu_delete)
					.setMessage("如果SD卡空间不足请点击清除，建议保留！")
					.setPositiveButton("清除",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									PM.delFolder(Environment
											.getExternalStorageDirectory()
											.getAbsolutePath());
									if (AboutActivity.getPhoneAndroidSDK() >= 8) {
										PM.delFolder(PhoneState.getCachePath(
												MainActivity.this)
												.getAbsolutePath());
									}
									PM.delFolder(MainActivity.this
											.getCacheDir().getAbsolutePath());
									PM.delFolder(PhoneState
											.getFilePath("keeasy"));
								}
							}).setNegativeButton("保留", null).create().show();
			break;
		case R.id.more_update:
			PM.onBtnAnim(update);
			vupdate.checkUpdate(true);
			break;
		case R.id.more_about:
			PM.onBtnAnim(about);
			Skip.mNext(MainActivity.this, AboutActivity.class);
			break;

		case R.id.me_exits:
			PM.onBtnAnim(me_exits);
			me_list.setVisibility(View.GONE);
			frid = true;
			exitdialog.show();
			break;
		case R.id.me_order:
			PM.onBtnAnim(me_order);
			if (userInfo.getBoolean("islogin", false)) {
				Skip.mNext(MainActivity.this, OrderActivity.class);
			} else {
				Skip.mNext(MainActivity.this, LoginActivity.class,
						R.anim.activity_push_up_in, R.anim.activity_no_anim,
						false);
			}
			break;
		case R.id.me_friend:
			PM.onBtnAnim(mefriend);
			if (userInfo.getBoolean("islogin", false)) {
				if (frid) {
					if (Sources.CONTACTLIST.size() < 1) {
						fridmod.mGetFriendListUrl(
								userInfo.getString("account", ""),
								userInfo.getInt("userId", -1));
					}
					me_item.setImageResource(R.drawable.item_up);
					me_list.setVisibility(View.VISIBLE);
				} else {
					me_item.setImageResource(R.drawable.item_down);
					me_list.setVisibility(View.GONE);
				}
				frid = !frid;
			} else {
				Skip.mNext(MainActivity.this, LoginActivity.class,
						R.anim.activity_push_up_in, R.anim.activity_no_anim,
						false);
			}
			break;
		case R.id.near_class:
			PM.onBtnAnim(near_class);
			spwin.showWindow(near_class, nearbyHandler);
			nc_img.setImageResource(R.drawable.item_up);
			break;
		case R.id.near_shidian:
			PM.onBtnAnim(near_shidian);
			spwin.showWindow(near_shidian, nearbyHandler);
			ns_img.setImageResource(R.drawable.item_up);
			break;
		case R.id.editok:
			PM.onBtnAnim(editok);
			Skip.mNext(this, ModifyActivity.class);
			break;
		}
		spwin.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				nc_img.setImageResource(R.drawable.item_down);
				ns_img.setImageResource(R.drawable.item_down);
			}
		});
	}

	private void setTop() {
		topback.setVisibility(View.VISIBLE);
		topback.setImageResource(R.drawable.barcode);
		toptitle.setText("周边生活");
		topfind.setVisibility(View.VISIBLE);
		topfind.setImageResource(R.drawable.search);
		homeData();
		Sources.RANGE.clear();
		requtions(pages);
	}

	/**
	 * 初始化每页数据
	 */
	// TODO Auto-generated method stub
	private void InitViewPager() {
		initHome();
		initNearby();
		initZhe();
		initMe();
		initMore();
	}

	@Override
	public void onLoadMore() {
		// 加载更多数据
		// 需要数据时，修改下面代码！
		new AsyncTask<Void, Void, Void>() {
			// 延迟，doInBackground
			protected Void doInBackground(Void... params) {
				// try {
				// Thread.sleep(500);
				// } catch (InterruptedException e) {
				// e.printStackTrace();
				// }
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				if (img2.isChecked()) {
					++pages;
					requtions(pages);
					if (nearadapter.getCount() > 50) {
						rlist.setCanLoadMore(false);
					} else {
						rlist.onLoadMoreComplete();// 加载跟多完成！
					}
				} else if (img3.isChecked()) {
					++zpage;
					zhereqution(zpage);
					if (nearadapter.getCount() > 50) {
						zhelist.setCanLoadMore(false);
					} else {
						zhelist.onLoadMoreComplete();// 加载跟多完成！
					}
				}
			}
		}.execute(null, null, null);
	}

	@Override
	public void onLoadMores() {
		lHander.postDelayed(new Runnable() {
			@Override
			public void run() {
				++page;
				reqution(page);
				onLoad();
			}
		}, 1000);
	}

	private void onLoad() {
		listview.stopRefresh();
		listview.stopLoadMore();
		listview.setRefreshTime("刚刚");
	}

	@Override
	public void onRefreshs() {
		lHander.postDelayed(new Runnable() {
			@Override
			public void run() {
				Sources.SUNDAYINFO.clear();
				page = 1;
				reqution(page);
				onLoad();
			}
		}, 500);
	}

	@Override
	public void onRefresh() {
		// 刷新数据
		// 需要数据时，修改下面代码！
		new AsyncTask<Void, Void, Void>() {
			// 刷新延迟，doInBackground
			protected Void doInBackground(Void... params) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				Sources.RANGE.clear();
				pages = 1;
				requtions(pages);
				rlist.setCanLoadMore(true);// 可以加载更多
				rlist.onRefreshComplete();// 隐藏刷新！
			}
		}.execute(null, null, null);
	}

	public void reqution(int page) {
		slmod.getShoplist(Sources.area, Sources.region, Sources.shopOneType,
				Sources.shopTwoType, 7, Sources.color, page);
	}

	private void requtions(int pags) {
		snMod.mSunShopListJuli(Sources.longitude, Sources.latitude, 999999999,
				Sources.nshopOneType, Sources.nshopTwoType, 7, Sources.ncolor,
				pags);
	}

	private void zhereqution(int pags) {
		zhemod.mZheShopList(pags, "");
	}

	// TODO Auto-generated method stub
	@Override
	public void mSuccess() {
		if (Sources.SUNDAYINFO.size() < 1) {
			PhoneState.showToast(this, "没有搜索到相应的店铺，请切换重试！");
		}
		homeAdapter.notifyDataSetChanged();
	}

	@Override
	public void mSuccess1() {
		zheadapter.notifyDataSetChanged();
		if (Sources.TIMZHEKE.size() > 0) {
			mz_no.setVisibility(View.GONE);
		} else {
			mz_no.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void mSuccess2() {
		if (Sources.RANGE.size() < 1) {
			PhoneState.showToast(this, "附近范围内没有找到您要的店铺！");
			// try {
			// Thread.sleep(2000);
			// } catch (InterruptedException e) {
			// e.printStackTrace();
			// }
			// img1.setChecked(true);
		}
		nearadapter.notifyDataSetChanged();
	}

	@Override
	public void mSuccessMSg() {
		meadapter.notifyDataSetChanged();
		int msgcount = 0;
		if (Sources.NOTREADMSG.size() != 0) {
			for (String element : Sources.NOTREADMSG.values()) {
				msgcount += Integer.parseInt(element);
			}
			if (msgcount != 0) {
				if (msgcount > oldmsg) {
					warn.player();
				}
				me_msgnum.setVisibility(View.VISIBLE);
				me_msgnum.setText("" + msgcount);
				oldmsg = msgcount;
			} else {
				me_msgnum.setVisibility(View.INVISIBLE);
			}
		} else {
			me_msgnum.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public void mSucfriend() {
		meadapter.notifyDataSetChanged();
		if (Sources.CONTACTLIST.size() < 1) {
			PhoneState.showToast(this, "您暂时还没有好友");
			me_item.setImageResource(R.drawable.item_down);
			me_list.setVisibility(View.GONE);
		}
	}

	@Override
	public void mSumoney() {
		me_moneytotla.setText("共获赠送总额：￥"
				+ (Sources.UnusedMoney + Sources.UsedMoney) + "元");
		me_moneyexiter.setText("可使用的余额：￥" + Sources.UnusedMoney + "元");
		me_moneyuser.setText("已使用的金额：￥" + Sources.UsedMoney + "元");
	}

	/**
	 * 初始化首页
	 */
	// TODO Auto-generated method stub
	private void initHome() {
		listview = (XListView) findViewById(R.id.home_listview);
		home_selet = (RelativeLayout) findViewById(R.id.home_selet);
		home_quyu = (TextView) findViewById(R.id.home_quyu);
		home_fenlie = (TextView) findViewById(R.id.home_fenlie);
		home_shiduan = (TextView) findViewById(R.id.home_shiduan);
		lHander = new Handler();
		listview.setPullLoadEnable(true);
		homeAdapter = new HomeAdapter(this, false);
		listview.setAdapter(homeAdapter);
		// listview.setPullRefreshEnable(false);
		listview.setXListViewListener(this);
		// listview.startRefresh();
		popupWindow = new MyPopupWindow(this);
		slmod = new ShopListMod(this, this);
		amod = new AreaMod(this, this);
		cmod = new ClassMod(this, this);
	}

	/**
	 * 初始化首页数据
	 */
	private void homeData() {
		if (flag) {
			flag = false;
			reqution(page);
		}
		home_selet.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PM.onBtnAnim(home_selet);
				if (popupWindow.isShowing()) {
					selicon.setImageResource(R.drawable.item_down);
					popupWindow.dismiss();
				} else {
					selicon.setImageResource(R.drawable.item_up);
					popupWindow.showWindow(v, homeHandler);
				}
			}
		});
		popupWindow.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				selicon.setImageResource(R.drawable.item_down);
				if (popupWindow.selarea || popupWindow.selclass) {
					popupWindow.selarea = false;
					popupWindow.selclass = false;
					page = 1;
					Sources.SUNDAYINFO.clear();
					reqution(page);
				}
			}
		});
		home_quyu.setText("全部区域｜");
		home_fenlie.setText("全部分类｜");
		home_shiduan.setText("全部时段");
		homeHandler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 0:
					if (msg.getData().getString("name").equals("全部")) {
						home_quyu.setText("全部区域｜");
					} else {
						home_quyu
								.setText(msg.getData().getString("name") + "｜");
					}
					break;
				case 1:
					if (msg.getData().getString("name").equals("全部")) {
						home_fenlie.setText("全部分类｜");
					} else {
						home_fenlie.setText(msg.getData().getString("name")
								+ "｜");
					}
					break;
				case 2:
					if (msg.getData().getString("name").equals("全部")) {
						home_shiduan.setTextColor(0xFF000000);
						home_shiduan.setText("全部时段");
					} else {
						if (msg.getData().getString("name").equals("red")) {
							home_shiduan.setTextColor(0xFFE84967);
							home_shiduan.setText("购买后30日内中午使用");
						} else if (msg.getData().getString("name")
								.equals("yellow")) {
							home_shiduan.setTextColor(0xFFDB8F1F);
							home_shiduan.setText("购买后30日内周一到周五使用");
						} else if (msg.getData().getString("name")
								.equals("blue")) {
							home_shiduan.setTextColor(0xFF1E5691);
							home_shiduan.setText("购买后30日内全时段使用");
						}
					}
					page = 1;
					Sources.SUNDAYINFO.clear();
					reqution(page);
					break;
				}
			};
		};

		new AsyncTask<Void, Void, Void>() {
			@Override
			protected void onPostExecute(Void result) { // 请求区域和分类
				amod.mGetLocalAddressListUrl();
				cmod.mGetShopTypeListUrl();
			}

			@Override
			protected Void doInBackground(Void... params) {

				return null;
			}
		}.execute(null, null, null);
	}

	/**
	 * 初始化附近
	 */
	// TODO Auto-generated method stub
	private void initNearby() {
		near_dingwei = (TextView) findViewById(R.id.near_dingwei);
		near_class = (TextView) findViewById(R.id.near_class);
		near_shidian = (TextView) findViewById(R.id.near_shidian);
		ns_img = (ImageView) findViewById(R.id.ns_img);
		nc_img = (ImageView) findViewById(R.id.nc_img);
		rlist = (ReListView) findViewById(R.id.near_listview);
		snMod = new ShopListNearMod(this, this);
		nearadapter = new NearbyAdapter(this);
		rlist.setAdapter(nearadapter);
		rlist.setOnRefreshListener(this);
		rlist.setOnLoadMoreListener(this);
		near_class.setText("选择分类");
		near_shidian.setText("选择时段");
		spwin = new ShidianPopupWindow(this);
	}

	/**
	 * 初始化附近数据
	 */
	private void nearbyData() {
		near_class.setOnClickListener(this);
		near_shidian.setOnClickListener(this);
		near_dingwei.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PM.onBtnAnim(near_dingwei);
				setLocationOption();
				mLocClient.requestLocation();
			}
		});

		nearbyHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 0:
					if (msg.getData().getString("name").equals("全部")) {
						near_class.setText("全部分类");
					} else {
						near_class.setText(msg.getData().getString("name"));
					}
					Sources.RANGE.clear();
					pages = 1;
					requtions(pages);
					break;
				case 1:
					if (msg.getData().getString("name").equals("全部")) {
						near_shidian.setTextColor(0xFF000000);
						near_shidian.setText("全部时段");
					} else {
						if (msg.getData().getString("name").equals("red")) {
							near_shidian.setTextColor(0xFFE84967);
							near_shidian.setText("购买后30日内中午使用");
						} else if (msg.getData().getString("name")
								.equals("yellow")) {
							near_shidian.setTextColor(0xFFDB8F1F);
							near_shidian.setText("购买后30日内周一到周五使用");
						} else if (msg.getData().getString("name")
								.equals("blue")) {
							near_shidian.setTextColor(0xFF1E5691);
							near_shidian.setText("购买后30日内全时段使用");
						}
					}
					Sources.RANGE.clear();
					pages = 1;
					requtions(pages);
					break;
				}
				super.handleMessage(msg);
			}
		};
	}

	/**
	 * 初始化限时折扣
	 */
	// TODO Auto-generated method stub
	private void initZhe() {
		zhelist = (ReListView) findViewById(R.id.zhe_listview);
		mz_no = (TextView) findViewById(R.id.mz_no);
		zhemod = new ZhekeMod(MainActivity.this, this);
		zheadapter = new ZhekeAdapter(MainActivity.this);
		zhelist.setAdapter(zheadapter);
		zhelist.setOnLoadMoreListener(this);
	}

	private void zheData() {
		zhereqution(zpage);
		mz_no.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PM.onBtnAnim(mz_no);
				zhereqution(zpage);
			}
		});
		zhelist.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Sources.setCurrentZhe(arg2 - 1);
				Skip.mNext(MainActivity.this, ZheShopActivity.class);
			}
		});
	}

	/**
	 * 初始化用户中心
	 */
	// TODO Auto-generated method stub
	private void initMe() {
		me_user = (TextView) findViewById(R.id.me_user);
		me_num = (TextView) findViewById(R.id.me_num);
		editok = (ImageView) findViewById(R.id.editok);
		me_list = (MListView1) findViewById(R.id.me_list);
		selicon = (ImageView) findViewById(R.id.home_selicon);
		me_order = (LinearLayout) findViewById(R.id.me_order);
		me_moneytotla = (TextView) findViewById(R.id.me_moneytotla);
		me_moneyexiter = (TextView) findViewById(R.id.me_moneyexiter);
		me_moneyuser = (TextView) findViewById(R.id.me_moneyuser);
		me_msgnum = (TextView) findViewById(R.id.me_msgnum);
		me_exits = (LinearLayout) findViewById(R.id.me_exits);
		me_item = (ImageView) findViewById(R.id.me_item);
		mefriend = (RelativeLayout) findViewById(R.id.me_friend);
		me_order.setOnClickListener(this);
		me_exits.setOnClickListener(this);
		editok.setOnClickListener(this);
		mefriend.setOnClickListener(this);
		fridmod = new ContactMod(this, this);
		meadapter = new FriendAdapter(this);
		mmod = new MoneyMod(this, this);
		me_list.setAdapter(meadapter);
		meHandler = new Handler();
		warn = new WarningTone(this, "msg.wav");
	}

	/**
	 * 用户中心数据
	 */
	private void meData() {
		exitdialog = new ExitDialog(this, this);
		if (userInfo.getBoolean("islogin", false)) {
			me_user.setText("昵称：" + userInfo.getString("userName", ""));
			me_num.setText("IC号：" + userInfo.getString("account", ""));
			me_exits.setVisibility(View.VISIBLE);
		} else {
			me_user.setText("昵称：");
			me_num.setText("IC号：");
			me_moneytotla.setText("共获赠送总额：￥0元");
			me_moneyexiter.setText("可使用的余额：￥0元");
			me_moneyuser.setText("已使用的金额：￥0元");
		}
		me_list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (userInfo.getString("account", "").equals(
						Sources.CONTACTLIST.get(arg2).contactAccount)) {
					PhoneState.showToast(MainActivity.this, "不能对自己发起对话！");
				} else {
					Sources.CURRENTIC = Sources.CONTACTLIST.get(arg2);
					Skip.mNext(MainActivity.this, ChatActivity.class);
				}
			}
		});
	}

	/**
	 * 初始化更多
	 */
	// TODO Auto-generated method stub
	private void initMore() {
		zhaoshang = (ImageView) findViewById(R.id.more_zhaoshang);
		weixin = (TextView) findViewById(R.id.more_weixin);
		weibo = (TextView) findViewById(R.id.more_weibo);
		cache = (TextView) findViewById(R.id.more_cache);
		update = (LinearLayout) findViewById(R.id.more_update);
		version = (TextView) findViewById(R.id.more_version);
		about = (TextView) findViewById(R.id.more_about);
		moreHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 0:
					newvs = true;
					break;

				case 1:
					newvs = false;
					break;
				}
				super.handleMessage(msg);
			}
		};
	}

	/**
	 * 更多数据
	 */
	private void moreData() {
		zhaoshang.setOnClickListener(this);
		weixin.setOnClickListener(this);
		weibo.setOnClickListener(this);
		cache.setOnClickListener(this);
		update.setOnClickListener(this);
		about.setOnClickListener(this);
		if (newvs) {
			version.setText("发现新版本，点击下载更新");
		} else {
			version.setText("当前版本:v" + PhoneState.getVerName(this) + "，已是最新版");
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				PhoneState.showToast(getApplicationContext(), "再按一次退出程序");
				exitTime = System.currentTimeMillis();
			} else {
				for (int i = 0; i < activityList.size(); i++) {
					if (null != activityList.get(i)) {
						activityList.get(i).finish();
					}
				}
				Skip.mBack(this);
			}
			return true;
		}
		return false;
	}

	@Override
	protected void onResume() {
		if (PhoneState.checkApkExist(MainActivity.this,
				"com.keeasy.mobilekeeasy")) {
			dialgo = new AlertDialog.Builder(MainActivity.this);
			dialgo.setCancelable(false);
			dialgo.setTitle("卸载提示").setIcon(android.R.drawable.ic_dialog_alert)
					.setMessage("检测到该程序还有旧版本存在,请卸载旧版本!");
			dialgo.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							new AsyncTask<Void, Void, Void>() {
								@Override
								protected Void doInBackground(Void... params) {
									if (PhoneState.runRootCommand("echo test")) {
										// 下面3句是静默卸载第三方软件命令
										String busybox1 = "mount -o remount rw /data";
										String chmod1 = "chmod 777 /data/app/com.keeasy.mobilekeeasy-2.apk";
										uninstallapk1 = "pm uninstall com.keeasy.mobilekeeasy";
										PhoneState.chmodApk(busybox1, chmod1);
										PhoneState.uninstallApk(uninstallapk1);
									} else {
										Uri packageURI = Uri
												.parse("package:com.keeasy.mobilekeeasy");
										Intent uninstallIntent = new Intent(
												Intent.ACTION_DELETE,
												packageURI);
										startActivity(uninstallIntent);
									}
									return null;
								}
							}.execute(null, null, null);
						}
					});
			dialgo.create().show();
		}
		mSuccessMSg();
		super.onResume();
		if (userInfo.getBoolean("islogin", false)) {
			editok.setVisibility(View.VISIBLE);
			JPushInterface.init(getApplicationContext());
			JPushInterface.setAliasAndTags(getApplicationContext(),
					userInfo.getString("account", ""), null);
			me_exits.setVisibility(View.VISIBLE);
			meHandler.post(runable);
			mmod.mGetUserMoney(userInfo.getString("account", ""));
			me_user.setText("昵称：" + userInfo.getString("userName", ""));
			me_num.setText("IC号：" + userInfo.getString("account", ""));
			if (!"".equals(Sources.NICK)) {
				if ("exit".equals(Sources.NICK)) {
					okOnClickListen();
				} else {
					me_user.setText("昵称：" + Sources.NICK);
				}
				Sources.NICK = "";
			}
		} else {
			editok.setVisibility(View.GONE);
			me_exits.setVisibility(View.GONE);
			meHandler.removeCallbacks(runable);
			JPushInterface.setAliasAndTags(getApplicationContext(), "", null);
		}
		vupdate.checkUpdate(false);
	}

	@Override
	protected void onDestroy() {
		meHandler.removeCallbacks(runable);
		if (mLocClient != null && mLocClient.isStarted()) {
			mLocClient.stop();
			((MyApplication) getApplication()).mTv = null;
			mLocClient = null;
		}
		super.onDestroy();
	}

	// 设置相关参数
	private void setLocationOption() {
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		// option.setServiceName("com.baidu.location.service_v2.9");
		option.setPoiExtraInfo(true);
		option.setAddrType("all");
		option.setScanSpan(5000); // 设置定位模式，小于1秒则一次定位;大于等于1秒则定时定位
		// option.setScanSpan(3000);
		option.setPriority(LocationClientOption.NetWorkFirst); // 设置网络优先
		// option.disableCache(true);
		mLocClient.setLocOption(option);
		mLocClient.start();
	}

	@Override
	public void okOnClickListen() {
		userInfo.edit().putBoolean("islogin", false).commit();
		JPushInterface.setAliasAndTags(getApplicationContext(), "", null);
		JPushInterface.stopPush(getApplicationContext());
		AccessTokenKeeper.clear(MainActivity.this);
		Sources.NOTREADMSG.clear();
		Sources.CONTACTLIST.clear();
		meHandler.removeCallbacks(runable);
		Tencent.createInstance(AppConstants.APP_ID, getApplicationContext())
				.logout(getApplicationContext());
		img1.setChecked(true);
	}

	Runnable runable = new Runnable() {
		@Override
		public void run() {
			msgmod.mGetMessageNum(userInfo.getString("account", ""),
					userInfo.getInt("userId", -1));
			meHandler.postDelayed(runable, 3000);
		}
	};

}