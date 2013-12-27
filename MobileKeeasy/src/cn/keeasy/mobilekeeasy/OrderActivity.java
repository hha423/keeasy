package cn.keeasy.mobilekeeasy;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import cn.evan.tools.PhoneState;

import cn.keeasy.mobilekeeasy.adapter.OrderAdapter;
import cn.keeasy.mobilekeeasy.dao.MyOrderMod;
import cn.keeasy.mobilekeeasy.dao.OrderDetailMod;
import cn.keeasy.mobilekeeasy.data.IAdapter;
import cn.keeasy.mobilekeeasy.data.Sources;
import cn.keeasy.mobilekeeasy.entity.OrderBean;
import cn.keeasy.mobilekeeasy.utils.Skip;

public class OrderActivity extends BaseActivity implements IAdapter {

	private ListView mmo_list;
	private OrderAdapter oadapter;
	private MyOrderMod momod;
	private OrderDetailMod odmod;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.main_me_order);
		super.onCreate(savedInstanceState);
	}

	@Override
	void initView() {
		super.initView();
		mmo_list = (ListView) findViewById(R.id.mmo_list);
	}

	void initData() {
		topback.setVisibility(View.VISIBLE);
		toptitle.setText("我的订单");
		momod = new MyOrderMod(this, this);
		momod.mGetOrderListUrl(userInfo.getString("account", ""),
				userInfo.getInt("userId", -1));
		oadapter = new OrderAdapter(this, this);
		mmo_list.setAdapter(oadapter);
		odmod = new OrderDetailMod(this, this);
	};

	@Override
	void initListen() {
		super.initListen();
	}

	@Override
	public void mSuccess() {
		oadapter.notifyDataSetChanged();
		if (Sources.ORDERLIST.size() < 1) {
			PhoneState.showToast(this, "您暂时还没有订单");
			Skip.mBack(this);
		}
	}

	@Override
	public void mSuccess1() {
		Skip.mNext(this, OrderInfoActivity.class);
	}

	@Override
	public void orderItem(OrderBean obean) {
		odmod.mGetOrderDetailsUrlOrderId(obean.orderId);
	}

	@Override
	protected void onResume() {
		super.onResume();
		oadapter.notifyDataSetChanged();
	}
}