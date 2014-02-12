package cn.keeasy.business.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.evan.tools.PhoneState;
import cn.evan.tools.ui.MListView1;
import cn.keeasy.business.R;
import cn.keeasy.business.bean.OrderBean;
import cn.keeasy.business.util.Sources;

public class VerifyAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater inflater;
	private List<OrderBean> list;
	private ProductAdapter adapter;

	public VerifyAdapter(Context context) {
		this.mContext = context;
		this.list = Sources.ORDERBEAN;
		inflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HoldView holaview;
		if (convertView == null) {
			holaview = new HoldView();
			convertView = inflater.inflate(R.layout.order_item, null);
			holaview.text1 = (TextView) convertView.findViewById(R.id.text1); // 订单编号：
			holaview.text2 = (TextView) convertView.findViewById(R.id.text2); // 下单时间：
			holaview.text3 = (MListView1) convertView.findViewById(R.id.text3); // 订单内容
			holaview.text4 = (TextView) convertView.findViewById(R.id.text4); // 总计：￥
			holaview.text5 = (TextView) convertView.findViewById(R.id.text5); // 星期天价
			holaview.text6 = (TextView) convertView.findViewById(R.id.text6); // 应付
			holaview.text7 = (TextView) convertView.findViewById(R.id.text7); // 应付
			holaview.edtext1 = (TextView) convertView
					.findViewById(R.id.edtext1); // 用户：
			holaview.edtext2 = (TextView) convertView
					.findViewById(R.id.edtext2); // 消费码：
			convertView.setTag(holaview);
		} else {
			holaview = (HoldView) convertView.getTag();
		}
		OrderBean bean = list.get(position);
		adapter = new ProductAdapter(mContext, position);
		holaview.text1.setText("订单编号:" + bean.orderNum);
		holaview.text2.setText("下单时间:" + bean.orderTime);
		holaview.text3.setAdapter(adapter);
		holaview.text4.setText("总价：￥" + PhoneState.setDecimal(bean.zongJia, 1));
		holaview.text5
				.setText("活动价：￥" + PhoneState.setDecimal(bean.xianJia, 1));
		holaview.text6.setText("还应付：￥"
				+ PhoneState.setDecimal(bean.yingShou, 1));
		holaview.text7
				.setText("用户已付：￥" + PhoneState.setDecimal(bean.yingFu, 1));
		holaview.edtext1.setText("用户：" + bean.userName);
		holaview.edtext2.setText("消费码：" + bean.orderCode);
		return convertView;
	}

	class HoldView {
		TextView text1, text2, text4, text5, text6, text7, edtext1, edtext2;
		MListView1 text3;
	}
}
