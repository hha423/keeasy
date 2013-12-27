package cn.keeasy.mobilekeeasy;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cn.keeasy.mobilekeeasy.dao.ModifyMod;
import cn.keeasy.mobilekeeasy.data.Sources;
import cn.keeasy.mobilekeeasy.utils.MD5;
import cn.keeasy.mobilekeeasy.utils.PM;
import cn.keeasy.mobilekeeasy.utils.Skip;

public class ModifyActivity extends BaseActivity {

	private EditText edtext1, edtext2, edtext3, edtext4;
	private TextView button;
	private String name, opwd, npwd1, npwd2;
	private ModifyMod mfmod;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.modify_info);
		super.onCreate(savedInstanceState);
	}

	@Override
	void initView() {
		edtext1 = (EditText) findViewById(R.id.mi_edtext1);
		edtext2 = (EditText) findViewById(R.id.mi_edtext2);
		edtext3 = (EditText) findViewById(R.id.mi_edtext3);
		edtext4 = (EditText) findViewById(R.id.mi_edtext4);
		button = (TextView) findViewById(R.id.mi_btnok);
	}

	@Override
	void initData() {
		mfmod = new ModifyMod(this, this);
	}

	@Override
	void initListen() {
		button.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == button) {
			opwd = edtext1.getText().toString().trim();
			npwd1 = edtext2.getText().toString().trim();
			npwd2 = edtext3.getText().toString().trim();
			name = edtext4.getText().toString().trim();
			if (mValid()) {
				if (npwd2.length() > 0) {
					npwd2 = new MD5().toMD5(npwd2);
				}
				mfmod.mChangeInfo(userInfo.getInt("userId", -1), name,
						new MD5().toMD5(opwd), npwd2);
			}
		}
	}

	@Override
	public void mSuccess(String str) {
		Sources.NICK = str;
		Skip.mBack(this);
	}

	@Override
	public void mSuccess() {
		Sources.NICK = "exit";
		Skip.mBack(this);
	}

	private boolean mValid() {
		if (edtext1.getText().length() > 0) {
			if (opwd.length() < 6) {
				edtext1.setError("密码长度不合法！");
				PM.requestFocus(edtext1);
				return false;
			} else if (edtext1.getText().length() > 12) {
				edtext1.setError("密码长度不合法！");
				PM.requestFocus(edtext1);
				return false;
			} else {
				if (edtext2.getText().length() != 0) {
					if (edtext2.getText().length() > 5
							&& edtext2.getText().length() < 13) {
						if (npwd1.equals(opwd)) {
							edtext2.setError("新旧密码相同，请换新密码修改！");
							PM.requestFocus(edtext2);
							return false;
						} else if (!npwd2.equals(npwd1)) {
							edtext3.setError("密码输入不一致！");
							PM.requestFocus(edtext3);
							return false;
						}
					} else {
						edtext2.setError("密码输入长度不合法！");
						PM.requestFocus(edtext2);
						return false;
					}
				} else {
					edtext2.setError("新密码不能为空！");
					PM.requestFocus(edtext2);
					return false;
				}
			}
		} else {
			if (edtext4.getText().length() == 0) {
				edtext4.setError("昵称不能为空，或至少修改一项");
				PM.requestFocus(edtext4);
				return false;
			} else if (edtext4.getText().length() < 2) {
				edtext4.setError("昵称输入长度不合法！");
				PM.requestFocus(edtext4);
				return false;
			} else if (edtext4.getText().length() > 16) {
				edtext4.setError("昵称输入长度不合法！");
				PM.requestFocus(edtext4);
				return false;
			}
		}
		return true;
	}

}
