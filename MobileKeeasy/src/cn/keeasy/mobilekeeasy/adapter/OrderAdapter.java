package cn.keeasy.mobilekeeasy.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.evan.tools.ui.MListView1;

import cn.keeasy.mobilekeeasy.R;
import cn.keeasy.mobilekeeasy.data.IAdapter;
import cn.keeasy.mobilekeeasy.data.Sources;
import cn.keeasy.mobilekeeasy.entity.OrderListBean;

public class OrderAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private Context context;
	private List<OrderListBean> olist;
	private OrderItemAdapter oiadapter;
	private IAdapter iap;

	public OrderAdapter(Context context, IAdapter iapt) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		olist = Sources.ORDERLIST;
		iap = iapt;
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
		convertView = inflater.inflate(R.layout.main_me_order_item, null);
		TextView title = (TextView) convertView.findViewById(R.id.mmoi_text);
		MListView1 list = (MListView1) convertView.findViewById(R.id.mmoi_list);

		final OrderListBean bean = olist.get(position);
		title.setText(bean.stateName);
		oiadapter = new OrderItemAdapter(context, bean.beanlist);
		list.setAdapter(oiadapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				iap.orderItem(bean.beanlist.get(arg2));
				Sources.ORDERBEAN = bean.beanlist.get(arg2);
			}
		});
		return convertView;
	}

}
