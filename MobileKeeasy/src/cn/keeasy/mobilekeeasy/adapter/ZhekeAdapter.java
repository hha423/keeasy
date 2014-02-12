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
import cn.keeasy.mobilekeeasy.entity.ZhekeBean;

public class ZhekeAdapter extends BaseAdapter {

	private LayoutInflater inflt;
	private List<ZhekeBean> zlist;

	public ZhekeAdapter(Context cont) {
		inflt = LayoutInflater.from(cont);
		this.zlist = Sources.TIMZHEKE;
	}

	@Override
	public int getCount() {
		return zlist.size();
	}

	@Override
	public Object getItem(int arg0) {
		return zlist.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = inflt.inflate(R.layout.list_zhe_item, null);
		TextView sname = (TextView) convertView.findViewById(R.id.lzi_name);
		TextView btime = (TextView) convertView.findViewById(R.id.lzi_btime);
		TextView etime = (TextView) convertView.findViewById(R.id.lzi_etime);
		TextView zhe = (TextView) convertView.findViewById(R.id.lzi_zhe);
		TextView dian = (TextView) convertView.findViewById(R.id.lzi_dian);
		TextView addr = (TextView) convertView.findViewById(R.id.lzi_addr);

		ZhekeBean zbean = zlist.get(position);
		sname.setText(zbean.shopName + " (" + zbean.bAccount + ")");
		btime.setText(zbean.begTime);
		etime.setText(zbean.endTime);
		dian.setText(zbean.endNum + " 单");
		addr.setText("地址:" + zbean.shopAddress);
		zhe.setText("" + zbean.zhekou);
		return convertView;
	}
}
