package cn.keeasy.mobilekeeasy;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cn.keeasy.mobilekeeasy.dao.ZhaoshangMod;
import cn.keeasy.mobilekeeasy.utils.PM;

public class ZhaoshangActivity extends BaseActivity {

	private EditText user, tel, mail, msg;
	private Button btn;
	private ZhaoshangMod zmod;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.main_more_zhao);
		super.onCreate(savedInstanceState);
	}

	@Override
	void initView() {
		super.initView();
		user = (EditText) findViewById(R.id.zs_edname);
		tel = (EditText) findViewById(R.id.zs_edtel);
		mail = (EditText) findViewById(R.id.zs_edmail);
		msg = (EditText) findViewById(R.id.zs_edmsg);
		btn = (Button) findViewById(R.id.zs_subject);
	}

	@Override
	void initData() {
		toptitle.setText("周边合伙人");
		topback.setVisibility(View.VISIBLE);
		zmod = new ZhaoshangMod(ZhaoshangActivity.this, this);
	}

	@Override
	void initListen() {
		super.initListen();
		btn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.zs_subject:
			if (check()) {
				zmod.addMessage(user.getText().toString().trim(), tel.getText()
						.toString().trim(), mail.getText().toString().trim(),
						msg.getText().toString().trim());
			}
			break;
		}
	}

	@Override
	public void mSuccess() {

	}

	private boolean check() {
		if (user.getText().length() < 1) {
			PM.requestFocus(user);
			user.setError("姓名不能为空");
			return false;
		} else if (tel.getText().length() < 1) {
			PM.requestFocus(tel);
			tel.setError("电话号码不能为空");
			return false;
		} else if (tel.getText().length() < 11 || tel.getText().length() > 13) {
			PM.requestFocus(tel);
			tel.setError("电话号码不正确");
			return false;
		} else if (mail.getText().length() < 1) {
			PM.requestFocus(mail);
			mail.setError("邮箱不能为空");
			return false;
		} else if (!mail.getText().toString().contains("@")) {
			PM.requestFocus(mail);
			mail.setError("邮箱地址不正确");
			return false;
		} else if (!mail.getText().toString().contains(".")) {
			PM.requestFocus(mail);
			mail.setError("邮箱地址不正确");
			return false;
		} else {
			return true;
		}
	}

}
