package cn.keeasy.business;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import cn.evan.tools.PhoneState;
import cn.evan.tools.ui.MListView1;
import cn.keeasy.business.adapter.VerifyAdapter;
import cn.keeasy.business.mod.ConfirmMod;
import cn.keeasy.business.util.PM;
import cn.keeasy.business.util.Sources;

public class VerifyActivity extends BaseActivity {

	private EditText edtext1;
	private TextView vm_okbtn;
	private ImageView vm_ecode;
	private MListView1 listview;
	private ConfirmMod mod;

	private VerifyAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.verify_main);
		super.onCreate(savedInstanceState);
	}

	@Override
	void initView() {
		super.initView();
		vm_okbtn = (TextView) this.findViewById(R.id.vm_okbtn);
		edtext1 = (EditText) this.findViewById(R.id.vm_edtext);
		vm_ecode = (ImageView) this.findViewById(R.id.vm_ecode);
		listview = (MListView1) this.findViewById(R.id.vm_listview);
	}

	@Override
	void initData() {
		toptitle.setText("消费验证");
		Sources.ORDERBEAN.clear();
		mod = new ConfirmMod(mContext, this);
		adapter = new VerifyAdapter(mContext);
		listview.setAdapter(adapter);
	}

	@Override
	void initListen() {
		super.initListen();
		vm_okbtn.setOnClickListener(this);
		vm_ecode.setOnClickListener(this);
		edtext1.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				toVerify();
				return false;
			}
		});
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		if (v == vm_okbtn) {
			PM.onBtnAnim(vm_okbtn);
			toVerify();
		} else if (v == vm_ecode) {
			PM.onBtnAnim(vm_ecode);
			Intent intent = new Intent();
			intent.setClass(VerifyActivity.this, CaptureActivity.class);
			startActivityForResult(intent, 0);
		}
	}

	private void toVerify() {
		if (edtext1.length() < 1) {
			edtext1.setError("请先输入一个验证码");
			PM.requestFocus(edtext1);
		} else {
			Sources.ORDERBEAN.clear();
			adapter.notifyDataSetChanged();
			mod.mOrderVerify(edtext1.getText().toString().trim(),
					Sources.USERBEAN.shopIC);
		}
	}

	@Override
	public void mSuccess() {
		PhoneState.cenShowToast(mContext, "验证成功!");
		edtext1.setText("");
		adapter.notifyDataSetChanged();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 0:
			if (data != null) {
				edtext1.setText(data.getExtras().getString("key"));
				toVerify();
			}
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
