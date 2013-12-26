package cn.keeasy.business;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import cn.keeasy.business.adapter.AccountAdapter;
import cn.keeasy.business.mod.AccountMod;
import cn.keeasy.business.util.Skip;
import cn.keeasy.business.util.Sources;

public class SubAccActivity extends BaseActivity implements OnItemClickListener {

	private AccountMod amod;
	private TextView fm_text;
	private ListView friend_list;
	private AccountAdapter aadapter;
	private String[] accoun = new String[1];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.firend_main);
		super.onCreate(savedInstanceState);
	}

	@Override
	void initView() {
		super.initView();
		fm_text = (TextView) findViewById(R.id.fm_text);
		friend_list = (ListView) findViewById(R.id.friend_list);
	}

	@Override
	void initData() {
		toptitle.setText("子帐号管理");
		fm_text.setText("子帐号列表");
		amod = new AccountMod(mContext, this);
		amod.mGetAccount(Sources.USERBEAN.userId);
		aadapter = new AccountAdapter(mContext, accoun);
		friend_list.setAdapter(aadapter);
	}

	@Override
	void initListen() {
		super.initListen();
		friend_list.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
	}

	@Override
	public void mSuccess(String str) {
		accoun[0] = str;
		aadapter.notifyDataSetChanged();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Sources.ACCOUNT = accoun[arg2];
		Skip.mNext(SubAccActivity.this, AccInfoActivity.class);
	}
}
