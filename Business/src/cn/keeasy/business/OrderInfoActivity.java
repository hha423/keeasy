package cn.keeasy.business;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import cn.keeasy.business.adapter.OrderInfoAdapter;
import cn.keeasy.business.util.PM;
import cn.keeasy.business.util.Sources;

public class OrderInfoActivity extends BaseActivity {

	private ListView mmoif_list;
	private TextView mmoif_time, mmoif_state, mmoif_bar, mmoif_total,
			mmoif_sun, mmoif_ding, mmoif_fu;
	private OrderInfoAdapter oiadapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.main_me_order_info);
		super.onCreate(savedInstanceState);

	}

	@Override
	void initView() {
		super.initView();
		mmoif_list = (ListView) findViewById(R.id.mmoif_list);
		mmoif_time = (TextView) findViewById(R.id.mmoif_time);
		mmoif_state = (TextView) findViewById(R.id.mmoif_state);
		mmoif_bar = (TextView) findViewById(R.id.mmoif_bar);
		mmoif_total = (TextView) findViewById(R.id.mmoif_total);
		mmoif_sun = (TextView) findViewById(R.id.mmoif_sun);
		mmoif_ding = (TextView) findViewById(R.id.mmoif_ding);
		mmoif_fu = (TextView) findViewById(R.id.mmoif_fu);
	}

	@Override
	void initData() {
		topback.setVisibility(View.VISIBLE);
		toptitle.setText("订单详情");
		oiadapter = new OrderInfoAdapter(this);
		mmoif_list.setAdapter(oiadapter);
		mmoif_time.setText("下单时间：" + Sources.CURROBEAN.orderTime);
		mmoif_state.setText("状态："
				+ PM.getStateName(Sources.CURROBEAN.orderState));
		mmoif_bar.setText("消费码：" + Sources.CURROBEAN.orderCode);
		mmoif_total.setText("总价：￥" + Sources.CURROBEAN.zongJia);
		mmoif_sun.setText("活动价：￥" + Sources.CURROBEAN.xianJia);
		mmoif_ding.setText("定金：￥" + Sources.CURROBEAN.yingFu);
		mmoif_fu.setText("到店支付：￥" + Sources.CURROBEAN.yingShou);
	}

	@Override
	void initListen() {
		super.initListen();
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
	}

}