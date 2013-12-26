package cn.keeasy.business.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.keeasy.business.R;
import cn.keeasy.business.bean.ProductBean;
import cn.keeasy.business.util.Sources;

public class ProductAdapter extends BaseAdapter {

	private List<ProductBean> list;
	private LayoutInflater inflater;

	public ProductAdapter(Context context, int index) {
		inflater = LayoutInflater.from(context);
		if (Sources.ORDERBEAN.get(index).pList != null) {
			list = Sources.ORDERBEAN.get(index).pList;
		} else {
			list = new ArrayList<ProductBean>();
		}
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(R.layout.product_item, null);
		TextView temp1 = (TextView) convertView.findViewById(R.id.temp1);
		TextView temp2 = (TextView) convertView.findViewById(R.id.temp2);
		TextView temp3 = (TextView) convertView.findViewById(R.id.temp3);
		ProductBean bean = list.get(position);
		temp1.setText(bean.pName);
		temp2.setText("" + bean.pMoney);
		temp3.setText("" + bean.pNum);
		return convertView;
	}

}
