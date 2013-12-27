package cn.keeasy.mobilekeeasy.adapter;

import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import cn.keeasy.mobilekeeasy.R;
import cn.keeasy.mobilekeeasy.data.Sources;

public class FaceAdapter extends BaseAdapter {

	private LayoutInflater inflat;
	private HashMap<String, Integer> map;

	public FaceAdapter(Context context) {
		inflat = LayoutInflater.from(context);
		map = Sources.FACESMAP;
	}

	@Override
	public int getCount() {
		return map.size();
	}

	@Override
	public Object getItem(int position) {
		return map.get("ic_img_" + (position + 1));
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = inflat.inflate(R.layout.chat_face_item, null);
		ImageView face_item = (ImageView) convertView
				.findViewById(R.id.face_img);
		face_item.setImageResource(map.get("ic_img_" + (position + 1)));
		return convertView;
	}

}
