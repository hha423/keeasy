package cn.keeasy.mobilekeeasy;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import cn.keeasy.mobilekeeasy.dao.RegisterMod;
import cn.keeasy.mobilekeeasy.utils.PM;
import cn.keeasy.mobilekeeasy.utils.Skip;

public class RegisterActivity extends BaseActivity {

	private EditText user;
	private EditText pwd;
	private EditText rpwd;
	private ImageView img;
	private ImageView img1;
	private ImageView img2;
	private Button btn;
	private ImageView qq;
	private ImageView sina;
	private RegisterMod mod;
	private boolean flag;
	private String name; // 用户名
	private String pasword; // 密码
	private String rpaswd; // 确认码

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.main_register);
		super.onCreate(savedInstanceState);
	}

	@Override
	void initView() {
		super.initView();
		user = (EditText) findViewById(R.id.reg_user);
		pwd = (EditText) findViewById(R.id.reg_pwd);
		rpwd = (EditText) findViewById(R.id.reg_pswd);
		img = (ImageView) findViewById(R.id.reg_clean);
		img1 = (ImageView) findViewById(R.id.reg_clean1);
		img2 = (ImageView) findViewById(R.id.reg_show);
		btn = (Button) findViewById(R.id.reg_btn);
		qq = (ImageView) findViewById(R.id.reg_qq);
		sina = (ImageView) findViewById(R.id.reg_sina);
	}

	@Override
	void initData() {
		topback.setVisibility(View.VISIBLE);
		toptitle.setText("用户注册");
		mod = new RegisterMod(this, this, userInfo);
	}

	@Override
	void initListen() {
		super.initListen();
		img.setOnClickListener(this);
		img1.setOnClickListener(this);
		img2.setOnClickListener(this);
		btn.setOnClickListener(this);
		btn.setOnTouchListener(PM.touchLight);
		qq.setOnClickListener(this);
		sina.setOnClickListener(this);
		pwd.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (pwd.length() > 0) {
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
		rpwd.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (rpwd.length() > 0) {
					img1.setVisibility(View.VISIBLE);
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
		super.onClick(v);
		switch (v.getId()) {
		case R.id.reg_btn:
			name = user.getText().toString().trim();
			pasword = pwd.getText().toString().trim();
			rpaswd = rpwd.getText().toString().trim();
			if (mCheck()) {
				mod.mGetRegUrl(name, rpaswd);
			}
			break;
		case R.id.reg_clean:
			pwd.setText("");
			img.setVisibility(View.INVISIBLE);
			break;
		case R.id.reg_clean1:
			rpwd.setText("");
			img1.setVisibility(View.INVISIBLE);
			break;
		case R.id.reg_show:
			if (flag) {
				img2.setImageResource(R.drawable.minwen);
				pwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);// 显示密码
				rpwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);// 显示密码
			} else {
				img2.setImageResource(R.drawable.miwen);
				pwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD
						| InputType.TYPE_CLASS_TEXT);// 隐藏密码
				rpwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD
						| InputType.TYPE_CLASS_TEXT);// 隐藏密码
			}
			flag = !flag;
			break;
		case R.id.reg_qq:

			break;
		case R.id.reg_sina:

			break;
		}
	}

	private boolean mCheck() {
		if ("".equals(name)) {
			user.setError("用户名不能为空");
			PM.requestFocus(user);
			return false;
		} else if (name.length() < 3) {
			user.setError("用户名长度不符合要求");
			PM.requestFocus(user);
			return false;
		} else if ("".equals(pasword)) {
			pwd.setError("密码不能为空");
			PM.requestFocus(pwd);
			return false;
		} else if (pasword.length() < 6 || pasword.length() > 17) {
			pwd.setError("密码长度不符合要求");
			PM.requestFocus(pwd);
			return false;
		} else if ("".equals(rpaswd)) {
			rpwd.setError("密码不能为空");
			PM.requestFocus(rpwd);
			return false;
		} else if (!pasword.equals(rpaswd)) {
			rpwd.setError("两次输入的密码不一致");
			PM.requestFocus(rpwd);
			return false;
		}
		return true;
	}

	@Override
	public void mSuccess() {
		super.mSuccess();
		userInfo.edit().putString("account", name).commit();
		Skip.mBack(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Skip.mBack(RegisterActivity.this);
		}
		return super.onKeyDown(keyCode, event);
	}

}
