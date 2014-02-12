package cn.keeasy.business;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import cn.keeasy.business.mod.ModfiyAccountMod;
import cn.keeasy.business.util.MD5;
import cn.keeasy.business.util.PM;
import cn.keeasy.business.util.Skip;
import cn.keeasy.business.util.Sources;

public class AccInfoActivity extends BaseActivity {

	private ModfiyAccountMod mamod;
	private TextView acc, btnok;
	private EditText pwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.main_accinfo);
		super.onCreate(savedInstanceState);
	}

	@Override
	void initView() {
		super.initView();
		acc = (TextView) findViewById(R.id.mc_acc);
		btnok = (TextView) findViewById(R.id.mc_btnok);
		pwd = (EditText) findViewById(R.id.mc_pwd);
	}

	@Override
	void initData() {
		toptitle.setText("子帐号详情");
		mamod = new ModfiyAccountMod(mContext, this);
		acc.setText("子帐号:" + Sources.ACCOUNT);
	}

	@Override
	void initListen() {
		super.initListen();
		btnok.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		if (v == btnok) {
			PM.onBtnAnim(btnok);
			if (pwd.getText().length() < 1) {
				PM.requestFocus(pwd);
				pwd.setError("密码不能为空");
			} else if (pwd.getText().length() < 6
					|| pwd.getText().length() > 16) {
				PM.requestFocus(pwd);
				pwd.setError("密码长度不符合要求");
			} else {
				mamod.mSetAccountPwd(Sources.USERBEAN.userId, Sources.ACCOUNT,
						new MD5().toMD5(pwd.getText().toString().trim()));
			}
		}
	}

	@Override
	public void mSuccess() {
		Skip.mBack(AccInfoActivity.this);
	}

}