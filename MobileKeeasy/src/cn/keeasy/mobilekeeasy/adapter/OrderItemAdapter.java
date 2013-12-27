package cn.keeasy.mobilekeeasy.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import cn.keeasy.mobilekeeasy.R;
import cn.keeasy.mobilekeeasy.entity.OrderBean;

public class OrderItemAdapter extends BaseAdapter {

	private LayoutInflater inflat;
	private List<OrderBean> list;

	public OrderItemAdapter(Context context, List<OrderBean> list0) {
		inflat = LayoutInflater.from(context);
		this.list = list0;
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
		convertView = inflat.inflate(R.layout.list_me_order_item, null);
		TextView name = (TextView) convertView.findViewById(R.id.lmoi_text);
		TextView time = (TextView) convertView.findViewById(R.id.lmoi_time);

		OrderBean bean = list.get(position);
		name.setText(bean.shopName);
		time.setText(bean.orderTime);
		return convertView;
	}

}
