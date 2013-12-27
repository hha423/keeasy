package cn.keeasy.mobilekeeasy.custom;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import cn.keeasy.mobilekeeasy.R;
import cn.keeasy.mobilekeeasy.adapter.ClassExpandAdapter;
import cn.keeasy.mobilekeeasy.adapter.ShiduangAdapter;
import cn.keeasy.mobilekeeasy.data.Sources;
import cn.keeasy.mobilekeeasy.utils.PM;

public class ShidianPopupWindow extends PopupWindow {

	private View view;
	private TextView text;
	private ExpandableListView listview;
	private ClassExpandAdapter cexadapter;
	private ShiduangAdapter sadapter;
	private ListView hp_list;
	private Handler handle;

	public ShidianPopupWindow(Context context) {
		super(context);
		view = LayoutInflater.from(context).inflate(R.layout.near_popup, null);
		listview = (ExpandableListView) view.findViewById(R.id.exlistview);
		hp_list = (ListView) view.findViewById(R.id.np_list);
		text = (TextView) view.findViewById(R.id.np_full);
		sadapter = new ShiduangAdapter(context);
		hp_list.setAdapter(sadapter);
		cexadapter = new ClassExpandAdapter(context);
		listview.setAdapter(cexadapter);
		setContentView(view);
	}

	public void showWindow(View parent, Handler handler) {
		handle = handler;
		if (parent.getId() == R.id.near_class) {
			listview.setVisibility(View.VISIBLE);
			hp_list.setVisibility(View.GONE);
			text.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					PM.onBtnAnim(v);
					Message msg = new Message();
					msg.what = 0;
					Bundle bundle = new Bundle();
					bundle.putString("name", "全部");
					msg.setData(bundle);
					handle.sendMessage(msg);
					Sources.nshopOneType = -1;
					Sources.nshopTwoType = "";
					dismiss();
				}
			});
			listview.setOnChildClickListener(new OnChildClickListener() {
				@Override
				public boolean onChildClick(ExpandableListView parent, View v,
						int groupPosition, int childPosition, long id) {
					Message msg = new Message();
					msg.what = 0;
					Bundle bundle = new Bundle();
					bundle.putString("name",
							Sources.DCLASS.get(groupPosition).oneTwoList
									.get(childPosition).twoName);
					Sources.nshopOneType = Sources.DCLASS.get(groupPosition).oneId;
					Sources.nshopTwoType = Sources.DCLASS.get(groupPosition).oneTwoList
							.get(childPosition).twoVal;
					msg.setData(bundle);
					handle.sendMessage(msg);
					dismiss();
					return false;
				}
			});

		} else {
			listview.setVisibility(View.GONE);
			hp_list.setVisibility(View.VISIBLE);
			text.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					PM.onBtnAnim(v);
					Message msg = new Message();
					msg.what = 1;
					Bundle bundle = new Bundle();
					bundle.putString("name", "全部");
					msg.setData(bundle);
					handle.sendMessage(msg);
					Sources.ncolor = "all";
					dismiss();
				}
			});
			hp_list.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					Message msg = new Message();
					msg.what = 1;
					Bundle bundle = new Bundle();
					switch (arg2) {
					case 0:
						bundle.putString("name", "red");
						Sources.ncolor = "red";
						break;
					case 1:
						bundle.putString("name", "yellow");
						Sources.ncolor = "yellow";
						break;
					case 2:
						bundle.putString("name", "blue");
						Sources.ncolor = "blue";
						break;
					}
					msg.setData(bundle);
					handle.sendMessage(msg);
					dismiss();
				}
			});
		}
		setFocusable(true); // 使其聚集
		setOutsideTouchable(false); // 设置允许在外点击消失
		setWidth(LayoutParams.FILL_PARENT);
		setHeight(LayoutParams.WRAP_CONTENT);
		setBackgroundDrawable(new BitmapDrawable()); // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
		setAnimationStyle(R.style.pop_anim);
		showAsDropDown(parent, 0, 0);
	}

}