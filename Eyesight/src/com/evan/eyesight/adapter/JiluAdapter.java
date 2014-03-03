package com.evan.eyesight.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
			holder.jilu_image = (ImageView) convertView
					.findViewById(R.id.image_item);
			holder.text_item = (TextView) convertView
					.findViewById(R.id.text_item);
			holder.j_time = (TextView) convertView.findViewById(R.id.j_time);
			holder.jilu_text1 = (TextView) convertView
					.findViewById(R.id.jilu_text1);
			holder.jilu_text2 = (TextView) convertView
					.findViewById(R.id.jilu_text2);
			holder.jilu_text3 = (TextView) convertView
					.findViewById(R.id.jilu_text3);
			holder.jilu_text4 = (TextView) convertView
					.findViewById(R.id.jilu_text4);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		DataBean dbean = bean.get(position);
		holder.j_time.setText("日期：" + dbean.time);
		if (dbean.type.equals("jinshi")) {
			holder.text_item.setText("视力测试");
			holder.jilu_image.setImageResource(R.drawable.jinshi);
			if (dbean.left != null)
				holder.jilu_text1.setText("左眼视力：" + dbean.left);
			if (dbean.point != null)
				holder.jilu_text2.setText("" + dbean.point);
			if (dbean.right != null)
				holder.jilu_text3.setText("右眼视力：" + dbean.right);
			if (dbean.result != null)
				holder.jilu_text4.setText("" + dbean.result);
		} else if (dbean.type.equals("semang")) {
			holder.text_item.setText("色盲测试");
			holder.jilu_image.setImageResource(R.drawable.shemang);
			if (dbean.point != null)
				holder.jilu_text1.setText("辨色分数：" + dbean.point);
			if (dbean.result != null)
				holder.jilu_text3.setText("结果：" + dbean.result);
		}
		return convertView;
	}

	class ViewHolder {
		TextView j_time, text_item, jilu_text1, jilu_text2, jilu_text3,
				jilu_text4;
		ImageView jilu_image;
	}

}
