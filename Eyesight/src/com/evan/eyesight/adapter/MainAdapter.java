package com.evan.eyesight.adapter;

/**
 * 主页适配类
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.evan.eyesight.R;

public class MainAdapter extends BaseAdapter {

	private String[] tabb;
	private int[] imgg;
	private LayoutInflater inflatr;

	public MainAdapter(Context context, String[] tab, int[] img) {
		inflatr = LayoutInflater.from(context);
		this.tabb = tab;
		this.imgg = img;
	}

	@Override
	public int getCount() {
		return tabb.length;
	}

	@Override
	public Object getItem(int arg0) {
		return tabb[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		arg1 = inflatr.inflate(R.layout.gridview_list, null);
		ImageView img = (ImageView) arg1.findViewById(R.id.glist_img);
		TextView text = (TextView) arg1.findViewById(R.id.glist_text);
		img.setImageResource(imgg[arg0]);
		text.setText(tabb[arg0]);
		return arg1;
	}
}
