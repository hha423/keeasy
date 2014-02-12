package cn.keeasy.mobilekeeasy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import cn.keeasy.mobilekeeasy.R;

public class ShiduangAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private String[] name = { "红色：购买后30日内中午使用", "黄色：购买后30日内周一到周五使用",
			"蓝色：购买后30日内全时段使用" };

	public ShiduangAdapter(Context conte) {
		inflater = LayoutInflater.from(conte);
	}

	@Override
	public int getCount() {
		return name.length;
	}

	@Override
	public Object getItem(int position) {
		return name[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(R.layout.list_item, null);
		TextView text = (TextView) convertView.findViewById(R.id.item_text);
		String shi = name[position];
		text.setText(shi);
		switch (position) {
		case 0:
			text.setTextColor(0xFFE84967);
			break;
		case 1:
			text.setTextColor(0xFFDB8F1F);
			break;
		case 2:
			text.setTextColor(0xFF1E5691);
			break;
		}
		return convertView;
	}

}
