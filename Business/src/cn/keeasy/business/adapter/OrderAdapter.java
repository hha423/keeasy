package cn.keeasy.business.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.keeasy.business.R;
import cn.keeasy.business.bean.OrderBean;
import cn.keeasy.business.util.Sources;

public class OrderAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<OrderBean> olist;

	public OrderAdapter(Context context) {
		inflater = LayoutInflater.from(context);
		olist = Sources.ORDERBEAN;
	}

	@Override
	public int getCount() {
		return olist.size();
	}

	@Override
	public Object getItem(int arg0) {
		return olist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(R.layout.list_me_order_item, null);
		TextView name = (TextView) convertView.findViewById(R.id.lmoi_text);
		TextView time = (TextView) convertView.findViewById(R.id.lmoi_time);

		OrderBean bean = olist.get(position);
		name.setText("昵称：" + bean.userName);
		time.setText("下单时间:" + bean.orderTime);
		return convertView;
	}

}
