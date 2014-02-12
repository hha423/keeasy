package cn.keeasy.mobilekeeasy.adapter;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import cn.keeasy.mobilekeeasy.R;
import cn.keeasy.mobilekeeasy.data.Sources;
import cn.keeasy.mobilekeeasy.entity.ProductInfoBean;

public class ShopCartAdapter extends BaseAdapter {

	private LayoutInflater infalt;
	private Handler hand;
	private List<ProductInfoBean> productList;

	public ShopCartAdapter(Context conter, Handler hander) {
		infalt = LayoutInflater.from(conter);
		productList = Sources.CARTLIST;
		this.hand = hander;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		convertView = infalt.inflate(R.layout.list_xiaopiao_item, null);
		TextView xiaopiao_name = (TextView) convertView
				.findViewById(R.id.xiaopiao_name);
		TextView xiaopiao_price = (TextView) convertView
				.findViewById(R.id.xiaopiao_price);
		ImageButton xiaopiao_jian = (ImageButton) convertView
				.findViewById(R.id.xiaopiao_jian);
		ImageButton xiaopiao_jia = (ImageButton) convertView
				.findViewById(R.id.xiaopiao_jia);
		TextView xiaopiao_num = (TextView) convertView
				.findViewById(R.id.xiaopiao_num);
		ProductInfoBean pbean = productList.get(position);
		xiaopiao_name.setText(pbean.productName);
		xiaopiao_price.setText("￥" + pbean.productPrice);
		xiaopiao_jia.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				++Sources.CARTLIST.get(position).productNum;
				hand.sendEmptyMessage(0);
				notifyDataSetChanged();
			}
		});
		xiaopiao_jian.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				notifyDataSetChanged();
				if (Sources.CARTLIST.get(position).productNum < 2) {
					for (int i = 0; i < Sources.PROINFOLIST.size(); i++) {
						if (Sources.PROINFOLIST.get(i).productId == Sources.CARTLIST
								.get(position).productId) {
							Sources.PROINFOLIST.get(i).flag = false;
						}// 点击减少到没有时把菜单里的勾图标隐藏
					}
					Message msg = new Message();
					msg.what = 2;
					Bundle ban = new Bundle();
					ban.putString("id",
							Sources.CARTLIST.get(position).productId + "");
					msg.setData(ban);
					hand.sendMessage(msg);
					Sources.CARTLIST.remove(position);
					return;
				}
				--Sources.CARTLIST.get(position).productNum;
				hand.sendEmptyMessage(1);
			}
		});
		xiaopiao_num.setText("" + pbean.productNum);
		return convertView;
	}

}
