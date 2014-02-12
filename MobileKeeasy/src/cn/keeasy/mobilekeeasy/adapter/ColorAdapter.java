package cn.keeasy.mobilekeeasy.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import cn.keeasy.mobilekeeasy.R;
import cn.keeasy.mobilekeeasy.entity.SundayShopBean;

public class ColorAdapter extends BaseAdapter {

	private List<SundayShopBean> list;
	private LayoutInflater inflat;

	public ColorAdapter(Context contex, List<SundayShopBean> lists) {
		this.list = lists;
		inflat = LayoutInflater.from(contex);
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
		convertView = inflat.inflate(R.layout.list_color_item, null);
		TextView zheke = (TextView) convertView.findViewById(R.id.color_zheke);
		TextView scolor = (TextView) convertView
				.findViewById(R.id.color_shidian);
		TextView sheng = (TextView) convertView
				.findViewById(R.id.color_shengyu);
		TextView shou = (TextView) convertView.findViewById(R.id.color_yishou);

		SundayShopBean bean = list.get(position);
		if (bean.color.equals("red")) {
			zheke.setTextColor(0xFFE84967);
			scolor.setText(R.string.zhongwu);
			scolor.setTextColor(0xFFE84967);
			pubmod(zheke, sheng, shou, bean);
		} else if (bean.color.equals("blue")) {
			zheke.setTextColor(0xFF1E5691);
			scolor.setText(R.string.zhoumou);
			scolor.setTextColor(0xFF1E5691);
			pubmod(zheke, sheng, shou, bean);
		} else if (bean.color.equals("yellow") || bean.color.equals("yellow1")) {
			zheke.setTextColor(0xFFDB8F1F);
			scolor.setText(R.string.gongzuo);
			scolor.setTextColor(0xFFDB8F1F);
			pubmod(zheke, sheng, shou, bean);
		}
		return convertView;
	}

	private void pubmod(TextView zheke, TextView sheng, TextView shou,
			SundayShopBean bean) {
		if (bean.tcType == 0) {
			zheke.setText("全场 " + bean.discount + " 折");
		} else {
			zheke.setText("套餐 " + bean.discount + " 折");
		}
		sheng.setText("还剩 " + bean.payNum + " 单");
		shou.setText("共计销售 " + bean.lastNum + " 单");
	}

}
