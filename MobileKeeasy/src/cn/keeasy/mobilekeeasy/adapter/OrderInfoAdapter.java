package cn.keeasy.mobilekeeasy.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import cn.keeasy.mobilekeeasy.R;
import cn.keeasy.mobilekeeasy.data.Sources;
import cn.keeasy.mobilekeeasy.entity.ProductInfoBean;

public class OrderInfoAdapter extends BaseAdapter {

	private LayoutInflater infalt;
	private List<ProductInfoBean> productList;

	public OrderInfoAdapter(Context conter) {
		infalt = LayoutInflater.from(conter);
		productList = Sources.ORDERDETABEAN.productList;
	}

	@Override
	public int getCount() {
		return productList.size();
	}

	@Override
	public Object getItem(int position) {
		return productList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = infalt.inflate(R.layout.list_me_order_info, null);
		TextView lmoif_name = (TextView) convertView
				.findViewById(R.id.lmoif_name);
		TextView lmoif_price = (TextView) convertView
				.findViewById(R.id.lmoif_price);
		TextView lmoif_num = (TextView) convertView
				.findViewById(R.id.lmoif_num);
		ProductInfoBean pbean = productList.get(position);
		lmoif_name.setText(pbean.productName);
		lmoif_price.setText("￥" + pbean.productPrice);
		lmoif_num.setText("×" + pbean.productNum);
		return convertView;
	}

}
