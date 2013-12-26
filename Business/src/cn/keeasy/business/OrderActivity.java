package cn.keeasy.business;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import cn.evan.tools.base.IListView;
import cn.keeasy.business.adapter.OrderAdapter;
import cn.keeasy.business.mod.OrderMod;
import cn.keeasy.business.util.PM;
import cn.keeasy.business.util.Skip;
import cn.keeasy.business.util.Sources;

public class OrderActivity extends BaseActivity implements IListView,
		OnItemClickListener {

	private ListView listview;
	private OrderAdapter adapter;
	private OrderMod mod;
	private int page;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.order_main);
		super.onCreate(savedInstanceState);
	}

	@Override
	void initView() {
		super.initView();
		listview = (ListView) this.findViewById(R.id.om_listview);
	}

	@Override
	void initData() {
		toptitle.setText("订单管理");
		mod = new OrderMod(mContext, this);
		page = 1;
		Sources.ORDERBEAN.clear();
		mod.mGetOrder(3, page, Sources.USERBEAN.shopIC);
		adapter = new OrderAdapter(mContext);
		listview.setAdapter(adapter);
	}

	@Override
	void initListen() {
		super.initListen();
		listview.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
	}

	@Override
	public void mSuccess() {
		adapter.notifyDataSetChanged();
	}

	@Override
	public void mListViewDataChange() {
		++page;
		mod.mGetOrder(3, page, Sources.USERBEAN.shopIC);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		PM.onBtnAnim(arg1);
		Sources.CURROBEAN = Sources.ORDERBEAN.get(arg2);
		Skip.mNext(OrderActivity.this, OrderInfoActivity.class);
	}

}
