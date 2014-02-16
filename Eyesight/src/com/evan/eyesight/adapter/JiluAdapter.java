package com.evan.eyesight.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.evan.eyesight.R;
import com.evan.eyesight.dao.DataBean;

public class JiluAdapter extends BaseAdapter {

	private LayoutInflater inflate;
	private List<DataBean> bean;

	public JiluAdapter(Context conte, List<DataBean> dbean) {
		inflate = LayoutInflater.from(conte);
		this.bean = dbean;
	}

	@Override
	public int getCount() {
		return bean.size();
	}

	@Override
	public Object getItem(int arg0) {
		return bean.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflate.inflate(R.layout.jilu_item, null);
			holder.j_time = (TextView) convertView.findViewById(R.id.j_time);
			holder.j_left = (TextView) convertView.findViewById(R.id.j_left);
			holder.j_right = (TextView) convertView.findViewById(R.id.j_right);
			holder.j_fensu = (TextView) convertView.findViewById(R.id.j_fensu);
			holder.j_jiang = (TextView) convertView.findViewById(R.id.j_jiang);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		DataBean dbean = bean.get(position);
		holder.j_time.setText(dbean.time + "");
		if (dbean.left != null)
			holder.j_left.setText(dbean.left + "");
		if (dbean.right != null)
			holder.j_right.setText(dbean.right + "");
		if (dbean.point != null)
			holder.j_fensu.setText(dbean.point + "");
		if (dbean.result != null)
			holder.j_jiang.setText(dbean.result + "");
		return convertView;
	}

	class ViewHolder {
		TextView j_time, j_left, j_right, j_fensu, j_jiang;
	}

}
