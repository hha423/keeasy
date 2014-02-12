package cn.keeasy.business;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import cn.evan.tools.PhoneState;
import cn.keeasy.business.adapter.FriendAdapter;
import cn.keeasy.business.mod.ContactMod;
import cn.keeasy.business.util.PM;
import cn.keeasy.business.util.Skip;
import cn.keeasy.business.util.Sources;

public class FriendActivity extends BaseActivity implements OnItemClickListener {

	private TextView fm_text;
	private ListView friend_list;
	private ContactMod cmod;
	private FriendAdapter adapter;

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
		super.initData();
		toptitle.setText("通讯对话");
		fm_text.setText("通讯列表");
		cmod = new ContactMod(mContext, this);
		cmod.mGetFriendListUrl(Sources.USERBEAN.shopIC, Sources.USERBEAN.userId);
		adapter = new FriendAdapter(mContext);
		friend_list.setAdapter(adapter);
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
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		PM.onBtnAnim(arg1);
		if (!Sources.USERBEAN.shopIC
				.equals(Sources.CONTACTLIST.get(arg2).contactAccount)) {
			Sources.CURRENTIC = Sources.CONTACTLIST.get(arg2);
			Skip.mNext(FriendActivity.this, ChatActivity.class);
		} else {
			PhoneState.showToast(mContext, "不能与自己对话！");
		}
	}

	public void mSuccess() {
		adapter.notifyDataSetChanged();
	}

	@Override
	protected void onResume() {
		super.onResume();
		adapter.notifyDataSetChanged();
	}

}
