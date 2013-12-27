package cn.keeasy.mobilekeeasy.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import cn.keeasy.mobilekeeasy.R;
import cn.keeasy.mobilekeeasy.data.Sources;
import cn.keeasy.mobilekeeasy.entity.ClassBean;

public class ClassExpandAdapter extends BaseExpandableListAdapter {

	private LayoutInflater layout;
	private List<ClassBean> slist;

	public ClassExpandAdapter(Context conte) {
		layout = LayoutInflater.from(conte);
		slist = Sources.DCLASS;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return slist.get(groupPosition).oneTwoList.get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		convertView = layout.inflate(R.layout.list_item, null);
		TextView text = (TextView) convertView.findViewById(R.id.item_text);
		text.setPadding(60, 10, 0, 10);
		text.setText(slist.get(groupPosition).oneTwoList.get(childPosition).twoName);
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return slist.get(groupPosition).oneTwoList.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return slist.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return slist.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		convertView = layout.inflate(R.layout.list_fenqu_item, null);
		TextView text = (TextView) convertView
				.findViewById(R.id.fenqu_item_name);
		TextView num = (TextView) convertView.findViewById(R.id.fenqu_item_num);
		text.setText(slist.get(groupPosition).oneName);
		num.setText("" + slist.get(groupPosition).oneTwoList.size());
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
