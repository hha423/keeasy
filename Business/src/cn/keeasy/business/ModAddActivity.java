package cn.keeasy.business;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import cn.keeasy.business.mod.ModfiyAddMod;
import cn.keeasy.business.util.PM;
import cn.keeasy.business.util.Skip;
import cn.keeasy.business.util.Sources;

public class ModAddActivity extends BaseActivity {

	private EditText mm_nadd;
	private ModfiyAddMod mamod;
	private TextView mm_oadd, mm_btnok;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.main_modfadd);
		super.onCreate(savedInstanceState);
	}

	@Override
	void initView() {
		super.initView();
		mm_nadd = (EditText) findViewById(R.id.mm_nadd);
		mm_oadd = (TextView) findViewById(R.id.mm_oadd);
		mm_btnok = (TextView) findViewById(R.id.mm_btnok);
	}

	@Override
	void initData() {
		toptitle.setText("地址修改");
		mm_oadd.setText(Sources.USERBEAN.shopAddr);
		mamod = new ModfiyAddMod(mContext, this);
	}

	@Override
	void initListen() {
		super.initListen();
		mm_btnok.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		if (v == mm_btnok) {
			PM.onBtnAnim(mm_btnok);
			if (mm_nadd.getText().length() < 1) {
				mm_nadd.setError("地址不能为空");
			} else {
				mamod.mSetShopAdd(Sources.USERBEAN.shopId, mm_nadd.getText()
						.toString());
			}
		}
	}

	@Override
	public void mSuccess() {
		Sources.USERBEAN.shopAddr = mm_nadd.getText().toString();
		Skip.mBack(ModAddActivity.this);
	}

}
