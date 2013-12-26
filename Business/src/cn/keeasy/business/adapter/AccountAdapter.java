package cn.keeasy.business.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.keeasy.business.R;

public class AccountAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private String[] name;

	public AccountAdapter(Context context, String[] name) {
		inflater = LayoutInflater.from(context);
		this.name = name;
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
	public View getView(int position, View convertView, ViewGroup arg2) {
		convertView = inflater.inflate(R.layout.list_account_item, null);
		TextView names = (TextView) convertView.findViewById(R.id.lci_name);
		names.setText(name[position]);
		return convertView;
	}
}
