package cn.keeasy.mobilekeeasy;

import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import cn.evan.tools.PhoneState;
import cn.keeasy.mobilekeeasy.utils.PM;

public class AboutActivity extends BaseActivity {

	private String data = "http://www.keeasy.com:8082/ke/keeasymovie.swf";
	// private String data =
	// "http://player.youku.com/player.php/sid/XNjIzMTAzNDU2/v.swf";
	private FrameLayout mFullscreenContainer;
	private FrameLayout mContentView;
	private View mCustomView = null;
	private ImageView about_youku;
	private WebView webView;
	private Builder dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.main_more_about);
		if (getPhoneAndroidSDK() >= 14) {// 4.0需打开硬件加速
			getWindow().setFlags(0x1000000, 0x1000000);
		}
		super.onCreate(savedInstanceState);
	}

	@Override
	void initView() {
		super.initView();
		webView = (WebView) findViewById(R.id.webview);
		about_youku = (ImageView) findViewById(R.id.about_youku);
		mContentView = (FrameLayout) findViewById(R.id.main_content);
		mFullscreenContainer = (FrameLayout) findViewById(R.id.fullscreen_custom_content);
	}

	@Override
	void initData() {
		dialog = new AlertDialog.Builder(this);
		topback.setVisibility(View.VISIBLE);
		toptitle.setText("关于周边生活");
		// 设置WebView属性，能够执行JavaScript脚本
		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setJavaScriptCanOpenWindowsAutomatically(true);
		// settings.setPluginState(PluginState.ON);
		settings.setPluginsEnabled(true);
		settings.setAllowFileAccess(true);
		settings.setLoadWithOverviewMode(true);

		// 设置web视图客户端
		webView.setWebChromeClient(new MyWebChromeClient());
		webView.setWebViewClient(new MyWebViewClient());
	}

	@Override
	void initListen() {
		super.initListen();
		about_youku.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.about_youku:
			PM.onBtnAnim(about_youku);
			play();
			break;
		}
	}

	class MyWebChromeClient extends WebChromeClient {

		private CustomViewCallback mCustomViewCallback;
		private int mOriginalOrientation = 1;

		@Override
		public void onShowCustomView(View view, CustomViewCallback callback) {
			// TODO Auto-generated method stub
			onShowCustomView(view, mOriginalOrientation, callback);
			super.onShowCustomView(view, callback);

		}

		public void onShowCustomView(View view, int requestedOrientation,
				WebChromeClient.CustomViewCallback callback) {
			if (mCustomView != null) {
				callback.onCustomViewHidden();
				return;
			}
			if (getPhoneAndroidSDK() >= 14) {
				mFullscreenContainer.addView(view);
				mCustomView = view;
				mCustomViewCallback = callback;
				mOriginalOrientation = getRequestedOrientation();
				mContentView.setVisibility(View.INVISIBLE);
				mFullscreenContainer.setVisibility(View.VISIBLE);
				mFullscreenContainer.bringToFront();

				setRequestedOrientation(mOriginalOrientation);
			}

		}

		public void onHideCustomView() {
			mContentView.setVisibility(View.VISIBLE);
			if (mCustomView == null) {
				return;
			}
			mCustomView.setVisibility(View.GONE);
			mFullscreenContainer.removeView(mCustomView);
			mCustomView = null;
			mFullscreenContainer.setVisibility(View.GONE);
			try {
				mCustomViewCallback.onCustomViewHidden();
			} catch (Exception e) {
			}
			// Show the content view.

			setRequestedOrientation(mOriginalOrientation);
		}

	}

	// web视图客户端
	public class MyWebViewClient extends WebViewClient {
		public boolean shouldOverviewUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return super.shouldOverrideUrlLoading(view, url);
		}
	}

	public static int getPhoneAndroidSDK() {
		int version = 0;
		try {
			version = Integer.valueOf(android.os.Build.VERSION.SDK);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return version;

	}

	private boolean check() {
		PackageManager pm = getPackageManager();
		List<PackageInfo> infoList = pm
				.getInstalledPackages(PackageManager.GET_SERVICES);
		for (PackageInfo info : infoList) {
			if ("com.adobe.flashplayer".equals(info.packageName)) {
				return true;
			}
		}
		return false;
	}

	Handler handler = new Handler();

	private void goMarket() {
		handler.post(new Runnable() {
			public void run() {
				Intent installIntent = new Intent("android.intent.action.VIEW");
				installIntent.setData(Uri
						.parse("market://details?id=com.adobe.flashplayer"));
				startActivity(installIntent);
			}
		});
	}

	@Override
	protected void onResume() {
		if (PhoneState.getConnectTypeName(this).equals("WIFI")) {
			play();
		} else {
			PhoneState.cenShowToast(AboutActivity.this,
					"温馨提示：您当前的网络不是WiFi接入\n在线视频流量较大，确定收看请点击播放。");
		}
		super.onResume();
		// webView.onResume();
		// webView.resumeTimers();
	}

	private void play() {
		if (check()) {
			about_youku.setVisibility(View.GONE);
			webView.loadUrl(data);
		} else {
			dialog.setTitle("插件安装")
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setMessage("检测到您的手机没有安装flash插件\n无法播放视频，请先安装插件！\n是否现在安装")
					.setPositiveButton("安装",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									goMarket();
								}
							})
					.setNegativeButton("不安装",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									return;
								}
							}).setCancelable(false).create().show();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		// webView.onPause();
		// webView.pauseTimers();
		// webView.loadUrl("about:blank");
		// if (isFinishing()) {
		// setContentView(new FrameLayout(this));
		// }
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		webView.destroy();
	}

	// 设置回退
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
			webView.goBack();
		}
		return super.onKeyDown(keyCode, event);
	}

}
