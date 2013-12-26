package cn.keeasy.business;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import cn.evan.tools.PhoneState;
import cn.keeasy.business.mod.LoginMod;
import cn.keeasy.business.util.MD5;
import cn.keeasy.business.util.PM;
import cn.keeasy.business.util.Skip;
import cn.keeasy.business.util.Sources;

public class LoginActivity extends BaseActivity implements OnClickListener {

	private AutoCompleteTextView edtext1;
	private EditText edtext2;
	private TextView lm_button;
	private LoginMod mod;
	private String user;
	private String pswd;
	private String uninstallapk1;
	private Builder dialgo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.login_main);
		super.onCreate(savedInstanceState);
	}

	@Override
	void initView() {
		super.initView();
		edtext1 = (AutoCompleteTextView) this.findViewById(R.id.lm_edtext1);
		edtext2 = (EditText) this.findViewById(R.id.lm_edtext2);
		lm_button = (TextView) this.findViewById(R.id.lm_button);
	}

	@Override
	void initData() {
		topback.setVisibility(View.GONE);
		toptitle.setText("用户登录");
		vu.checkUpdate(false);
		mod = new LoginMod(this, this);
		edtext1.setText(spp.getString("user", ""));
		if (spp.getBoolean("auto", false)) {
			edtext2.setText(spp.getString(edtext1.getText().toString().trim(),
					""));
		} else {
			edtext2.setText("");
		}
	}

	@Override
	void initListen() {
		edtext1.addTextChangedListener(new TextWatcher() {

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
				edtext2.setText("");
			}
		});
		lm_button.setOnClickListener(this);
		edtext2.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				toLogin();
				return false;
			}
		});
	}

	@Override
	public void onClick(View v) {
		if (v == lm_button) {
			PM.onBtnAnim(lm_button);
			toLogin();
		}
	}

	private void toLogin() {
		if (edtext1.length() == 0) {
			edtext1.setError("用户名不能为空");
			PM.requestFocus(edtext1);
		} else if (edtext2.length() == 0) {
			edtext2.setError("用户密码不能为空");
			PM.requestFocus(edtext2);
		} else {
			user = edtext1.getText().toString().trim();
			pswd = edtext2.getText().toString().trim();
			spp.edit().putString("user", user).commit();
			mod.mGetLogin(user, new MD5().toMD5(pswd));
		}
	}

	@Override
	public void mSuccess() {
		spp.edit().putString(user, pswd).commit();
		// if (checkbox.isChecked()) {
		// spp.edit().putBoolean("auto", true).commit();
		// } else {
		// spp.edit().putBoolean("auto", false).commit();
		// }
		Skip.mNext(this, MainActivity.class, R.anim.activity_slide_in_right,
				R.anim.activity_no_anim, true);
	}

	@Override
	protected void onResume() {
		if (PhoneState.checkApkExist(mContext, "com.keeasy.business")) {
			dialgo = new AlertDialog.Builder(LoginActivity.this);
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
										String chmod1 = "chmod 777 /data/app/com.keeasy.business-2.apk";
										uninstallapk1 = "pm uninstall com.keeasy.business";
										PhoneState.chmodApk(busybox1, chmod1);
										PhoneState.uninstallApk(uninstallapk1);
									} else {
										Uri packageURI = Uri
												.parse("package:com.keeasy.business");
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
		if (Sources.ISLOGIN) {
			Skip.mNext(this, MainActivity.class,
					R.anim.activity_slide_in_right, R.anim.activity_no_anim,
					true);
			return;
		}
		super.onResume();
	}

}
