package cn.keeasy.mobilekeeasy;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import cn.evan.tools.PhoneState;

import cn.keeasy.mobilekeeasy.config.AccessTokenKeeper;
import cn.keeasy.mobilekeeasy.config.AppConstants;
import cn.keeasy.mobilekeeasy.config.WeiboConstants;
import cn.keeasy.mobilekeeasy.dao.LoginMod;
import cn.keeasy.mobilekeeasy.dao.WeiboQQMod;
import cn.keeasy.mobilekeeasy.dao.WeiboUserMod;
import cn.keeasy.mobilekeeasy.data.Sources;
import cn.keeasy.mobilekeeasy.utils.MD5;
import cn.keeasy.mobilekeeasy.utils.PM;
import cn.keeasy.mobilekeeasy.utils.Skip;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.tencent.open.HttpStatusException;
import com.tencent.open.NetworkUnavailableException;
import com.tencent.tauth.Constants;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class LoginActivity extends BaseActivity {

	private AutoCompleteTextView user; // 用户名
	private EditText pswd; // 密码
	private ImageView img; // 清除密码
	private ImageView img1; // 显示隐藏密码
	private ImageButton imgbtn; // 关闭按钮
	private Button button1; // 登录
	private TextView button2; // 注册
	private ImageView button3; // qq登录
	private ImageView button4; // sina登录
	private boolean flag = false;
	private LoginMod mod;
	private String[] arr = new String[] {}; // 保存登录过的帐号
	private String cuser; // 当前帐号
	private String cpwd; // 当前密码
	private String uname;
	private WeiboQQMod wqmod;
	private WeiboUserMod wumod;
	/** 微博 Web 授权类，提供登陆等功能 */
	private WeiboAuth mWeiboAuth;
	/** 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能 */
	private Oauth2AccessToken mAccessToken;
	/** 注意：SsoHandler 仅当 SDK 支持 SSO 时有效 */
	private static SsoHandler mSsoHandler;
	/**
	 * QQ登录
	 */
	public Tencent mTencent;
	private boolean suess = false;
	private boolean tx = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.main_login);
		super.onCreate(savedInstanceState);
	}

	@Override
	void initView() {
		user = (AutoCompleteTextView) findViewById(R.id.login_user);
		pswd = (EditText) findViewById(R.id.login_pwd);
		img = (ImageView) findViewById(R.id.login_clean);
		img1 = (ImageView) findViewById(R.id.login_show);
		imgbtn = (ImageButton) findViewById(R.id.login_imgbtn);
		button1 = (Button) findViewById(R.id.login_btn);
		button2 = (TextView) findViewById(R.id.login_reg);
		button3 = (ImageView) findViewById(R.id.login_qq);
		button4 = (ImageView) findViewById(R.id.login_sina);
	}

	@Override
	void initData() {
		mod = new LoginMod(this, this, userInfo);
		wqmod = new WeiboQQMod(this, this);
		wumod = new WeiboUserMod(this, this);
		uname = userInfo.getString("users", "");
		if (!"".equals(uname)) {
			arr = uname.split(",");
		}
		String pic = userInfo.getString("", "");
		if (!"".equals(pic)) {
			// img2.DisplayImage(pic, true);
		}
	}

	@Override
	void initListen() {
		img.setOnClickListener(this);
		img1.setOnClickListener(this);
		imgbtn.setOnClickListener(this);
		button1.setOnClickListener(this);
		button1.setOnTouchListener(PM.touchLight);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);
		button4.setOnClickListener(this);
		pswd.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				mLogin();
				return false;
			}
		});
		user.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				pswd.setText("");
				img.setVisibility(View.INVISIBLE);
				user.setAdapter(new ArrayAdapter(LoginActivity.this,
						R.layout.list_item, R.id.item_text, arr));
			}
		});
		pswd.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (pswd.length() > 0) {
					img.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_imgbtn:
			Skip.mBack(LoginActivity.this, R.anim.activity_no_anim,
					R.anim.activity_push_down_out);
			break;
		case R.id.login_clean:
			pswd.setText("");
			img.setVisibility(View.INVISIBLE);
			break;
		case R.id.login_show:
			if (flag) {
				img1.setImageResource(R.drawable.minwen);
				pswd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);// 显示密码
			} else {
				img1.setImageResource(R.drawable.miwen);
				pswd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD
						| InputType.TYPE_CLASS_TEXT);// 隐藏密码
			}
			flag = !flag;
			break;
		case R.id.login_btn:
			mLogin();
			break;
		case R.id.login_reg:
			Skip.mNext(LoginActivity.this, RegisterActivity.class);
			break;
		case R.id.login_qq:
			PM.onBtnAnim(button3);
			tx = true;
			mTencent = Tencent.createInstance(AppConstants.APP_ID,
					LoginActivity.this.getApplicationContext());
			if (!mTencent.isSessionValid()) {
				IUiListener listener = new BaseUiListener() {
					@Override
					protected void doComplete(JSONObject values) {
						wqmod.mGetThirdLogin("tx", mTencent.getOpenId(),
								userInfo);
					}
				};
				mTencent.login(LoginActivity.this, "all", listener);
			} else {
				mTencent.logout(LoginActivity.this);
				// updateUserInfo();
			}
			break;
		case R.id.login_sina:
			PM.onBtnAnim(button4);
			// 创建授权认证信息
			mWeiboAuth = new WeiboAuth(LoginActivity.this,
					WeiboConstants.APP_KEY, WeiboConstants.REDIRECT_URL,
					WeiboConstants.SCOPE);
			mSsoHandler = new SsoHandler(LoginActivity.this, mWeiboAuth);
			mSsoHandler.authorize(new AuthListener());
			break;
		}
	}

	private void mLogin() {
		cuser = user.getText().toString().trim();
		cpwd = new MD5().toMD5(pswd.getText().toString().trim());
		if (checking()) {
			mod.mGetLoginUrlByAccount(cuser, cpwd);
		}
	}

	private boolean checking() {
		if (user.length() < 1) {
			user.setError("用户名不能为空");
			return false;
		} else if (pswd.length() < 1) {
			pswd.setError("密码不能为空");
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void mSuccess() {
		userInfo.edit().putString("account", cuser).commit();
		userInfo.edit().putString(cuser, cpwd).commit();
		boolean exits = false;
		for (String auser : arr) {
			if (cuser.equals(auser)) {
				exits = true;
			}
		}
		if (!exits) {
			if (arr.length > 0) {
				uname += ",";
			}
			uname += cuser;
			userInfo.edit().putString("users", uname).commit();
		}
		Skip.mBack(LoginActivity.this, R.anim.activity_no_anim,
				R.anim.activity_push_down_out);
	}

	@Override
	public void mSuccess1() {
		if (suess) {
			suess = false;
			if (tx) {
				tx = false;
				wqmod.mGetThirReg(Sources.WEIBOUSER.domain,
						Sources.WEIBOUSER.profile_image_url,
						Sources.WEIBOUSER.id, "tx");
			} else {
				wqmod.mGetThirReg(Sources.WEIBOUSER.domain,
						Sources.WEIBOUSER.profile_image_url,
						Sources.WEIBOUSER.id, "wb");
			}
		} else {
			suess = true;
			if (tx) {
				updateUserInfo();
			} else {
				wumod.mGetFriendListUrl(mAccessToken.getUid(),
						mAccessToken.getToken());
			}
		}
	}

	@Override
	public void mSuccess2() {
		Skip.mBack(LoginActivity.this, R.anim.activity_no_anim,
				R.anim.activity_push_down_out);
	}

	@Override
	protected void onResume() {
		super.onResume();
		user.setText(userInfo.getString("account", ""));
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Skip.mBack(LoginActivity.this, R.anim.activity_no_anim,
					R.anim.activity_push_down_out);
		}
		return super.onKeyDown(keyCode, event);
	}

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				JSONObject response = (JSONObject) msg.obj;
				try {
					Sources.WEIBOUSER.domain = response.getString("nickname");
					Sources.WEIBOUSER.profile_image_url = response
							.getString("figureurl_qq_2");
					Sources.WEIBOUSER.id = mTencent.getOpenId();
					mSuccess1();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}

	};

	/**
	 * QQ登录信息
	 */
	private void updateUserInfo() {
		if (mTencent != null && mTencent.isSessionValid()) {
			IRequestListener requestListener = new IRequestListener() {

				@Override
				public void onUnknowException(Exception e, Object state) {

				}

				@Override
				public void onSocketTimeoutException(SocketTimeoutException e,
						Object state) {

				}

				@Override
				public void onNetworkUnavailableException(
						NetworkUnavailableException e, Object state) {

				}

				@Override
				public void onMalformedURLException(MalformedURLException e,
						Object state) {

				}

				@Override
				public void onJSONException(JSONException e, Object state) {

				}

				@Override
				public void onIOException(IOException e, Object state) {

				}

				@Override
				public void onHttpStatusException(HttpStatusException e,
						Object state) {

				}

				@Override
				public void onConnectTimeoutException(
						ConnectTimeoutException e, Object state) {

				}

				@Override
				public void onComplete(final JSONObject response, Object state) {
					Message msg = new Message();
					msg.obj = response;
					msg.what = 0;
					mHandler.sendMessage(msg);
				}
			};
			mTencent.requestAsync(Constants.GRAPH_SIMPLE_USER_INFO, null,
					Constants.HTTP_GET, requestListener, null);
		} else {
			wqmod.mGetThirdLogin("tx", mTencent.getOpenId(), userInfo);
		}
	}

	private class BaseUiListener implements IUiListener {
		@Override
		public void onComplete(JSONObject response) {
			PhoneState.showToast(LoginActivity.this, "授权成功正在登录");
			doComplete(response);
		}

		protected void doComplete(JSONObject values) {
		}

		@Override
		public void onError(UiError e) {
			PhoneState.showToast(LoginActivity.this, "onError: "
					+ e.errorDetail);
		}

		@Override
		public void onCancel() {
			PhoneState.showToast(LoginActivity.this, "onCancel: ");
		}
	}

	/**
	 * 当 SSO 授权 Activity 退出时，该函数被调用。
	 * 
	 * @see {@link Activity#onActivityResult}
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// SSO 授权回调
		// 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResult
		if (mSsoHandler != null) {
			mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

	/**
	 * 微博认证授权回调类。 1. SSO 授权时，需要在 {@link #onActivityResult} 中调用
	 * {@link SsoHandler#authorizeCallBack} 后， 该回调才会被执行。 2. 非 SSO
	 * 授权时，当授权结束后，该回调就会被执行。 当授权成功后，请保存该 access_token、expires_in、uid 等信息到
	 * SharedPreferences 中。
	 */
	class AuthListener implements WeiboAuthListener {

		@Override
		public void onComplete(Bundle values) {
			// 从 Bundle 中解析 Token
			mAccessToken = Oauth2AccessToken.parseAccessToken(values);
			if (mAccessToken.isSessionValid()) {
				// 保存 Token 到 SharedPreferences
				AccessTokenKeeper.writeAccessToken(LoginActivity.this,
						mAccessToken);
				PhoneState.showToast(LoginActivity.this, ""
						+ R.string.weibosdk_demo_toast_auth_success);
				wqmod.mGetThirdLogin("wb", mAccessToken.getUid(), userInfo);
			} else {
				// 当您注册的应用程序签名不正确时，就会收到 Code，请确保签名正确
				String code = values.getString("code");
				String message = getString(R.string.weibosdk_demo_toast_auth_failed);
				if (!TextUtils.isEmpty(code)) {
					message = message + "\nObtained the code: " + code;
				}
				System.out.println(message);
			}
		}

		@Override
		public void onCancel() {
			PhoneState.showToast(LoginActivity.this, ""
					+ R.string.weibosdk_demo_toast_auth_canceled);
		}

		@Override
		public void onWeiboException(WeiboException e) {
			PhoneState.showToast(LoginActivity.this,
					"Auth exception : " + e.getMessage());

		}
	}

}
