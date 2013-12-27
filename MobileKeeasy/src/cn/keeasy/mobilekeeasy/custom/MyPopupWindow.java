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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import cn.keeasy.mobilekeeasy.R;
import cn.keeasy.mobilekeeasy.adapter.AreaExpandAdapter;
import cn.keeasy.mobilekeeasy.adapter.ClassExpandAdapter;
import cn.keeasy.mobilekeeasy.adapter.ShiduangAdapter;
import cn.keeasy.mobilekeeasy.data.Sources;
import cn.keeasy.mobilekeeasy.utils.PM;

public class MyPopupWindow extends PopupWindow implements OnClickListener {

	private View view;
	private RadioGroup button4;
	private RadioButton button1, button2, button3;
	// private TextView text1;
	private Context cont;
	private TextView text;
	private ExpandableListView listview;
	private AreaExpandAdapter exadapter;
	private ClassExpandAdapter cexadapter;
	private ShiduangAdapter sadapter;
	private ListView hp_list;
	private Handler handle;
	public boolean selarea = false;
	public boolean selclass = false;

	public MyPopupWindow(Context context) {
		super(context);
		cont = context;
		view = LayoutInflater.from(context).inflate(R.layout.home_popup, null);
		button4 = (RadioGroup) view.findViewById(R.id.hp_radiogroup);
		button1 = (RadioButton) view.findViewById(R.id.rb_quyu);
		button2 = (RadioButton) view.findViewById(R.id.rb_fenlei);
		button3 = (RadioButton) view.findViewById(R.id.rb_shidian);
		listview = (ExpandableListView) view
				.findViewById(R.id.expandablelistview);
		hp_list = (ListView) view.findViewById(R.id.hp_list);
		text = (TextView) view.findViewById(R.id.hp_full);
		// text1 = (TextView) view.findViewById(R.id.hp_full_num);
		sadapter = new ShiduangAdapter(context);
		hp_list.setAdapter(sadapter);
		exadapter = new AreaExpandAdapter(context);
		cexadapter = new ClassExpandAdapter(context);
		onclik();
		setContentView(view);
	}

	public void showWindow(View parent, Handler handler) {
		this.handle = handler;
		button1.setChecked(true);
		text.setOnClickListener(this);
		button4.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rb_quyu:
					PM.onBtnAnim(button1);
					listview.setVisibility(View.VISIBLE);
					hp_list.setVisibility(View.GONE);
					onclik();
					break;
				case R.id.rb_fenlei:
					PM.onBtnAnim(button2);
					listview.setVisibility(View.VISIBLE);
					hp_list.setVisibility(View.GONE);
					listview.setAdapter(cexadapter);
					listview.setOnChildClickListener(new OnChildClickListener() {
						@Override
						public boolean onChildClick(ExpandableListView parent,
								View v, int groupPosition, int childPosition,
								long id) {
							Message msg = new Message();
							msg.what = 1;
							Bundle bundle = new Bundle();
							bundle.putString("name", Sources.DCLASS
									.get(groupPosition).oneTwoList
									.get(childPosition).twoName);
							Sources.shopOneType = Sources.DCLASS
									.get(groupPosition).oneId;
							Sources.shopTwoType = Sources.DCLASS
									.get(groupPosition).oneTwoList
									.get(childPosition).twoVal;
							Sources.shopTwoName = Sources.DCLASS
									.get(groupPosition).oneTwoList
									.get(childPosition).twoName;
							msg.setData(bundle);
							handle.sendMessage(msg);
							button3.setChecked(true);
							selclass = true;
							return false;
						}
					});
					break;
				case R.id.rb_shidian:
					PM.onBtnAnim(button3);
					listview.setVisibility(View.GONE);
					hp_list.setVisibility(View.VISIBLE);
					hp_list.setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							Message msg = new Message();
							msg.what = 2;
							Bundle bundle = new Bundle();
							switch (arg2) {
							case 0:
								bundle.putString("name", "red");
								Sources.color = "red";
								break;
							case 1:
								bundle.putString("name", "yellow");
								Sources.color = "yellow";
								break;
							case 2:
								bundle.putString("name", "blue");
								Sources.color = "blue";
								break;
							}
							msg.setData(bundle);
							handle.sendMessage(msg);
							dismiss();
						}
					});
					break;
				}
			}
		});
		setFocusable(true); // 使其聚集
		setOutsideTouchable(false); // 设置允许在外点击消失
		setWidth(LayoutParams.FILL_PARENT);
		setHeight(LayoutParams.FILL_PARENT);
		setBackgroundDrawable(new BitmapDrawable()); // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
		setAnimationStyle(R.style.pop_anim);
		showAsDropDown(parent, 0, 0);
	}

	private void onclik() {
		listview.setAdapter(exadapter);
		listview.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				Message msg = new Message();
				msg.what = 0;
				Bundle bundle = new Bundle();
				bundle.putString(
						"name",
						Sources.AREA.get(groupPosition).list.get(childPosition).name);
				Sources.area = Sources.AREA.get(groupPosition).area;
				Sources.region = Sources.AREA.get(groupPosition).list
						.get(childPosition).name;
				msg.setData(bundle);
				handle.sendMessage(msg);
				button2.setChecked(true);
				selarea = true;
				return false;
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.hp_full:
			PM.onBtnAnim(text);
			if (button1.isChecked()) {
				PM.onBtnAnim(text);
				Message msg = new Message();
				msg.what = 0;
				Bundle bundle = new Bundle();
				bundle.putString("name", "全部");
				msg.setData(bundle);
				handle.sendMessage(msg);
				Sources.area = "";
				Sources.region = "";
				selarea = true;
				button2.setChecked(true);
			} else if (button2.isChecked()) {
				PM.onBtnAnim(text);
				Message msg = new Message();
				msg.what = 1;
				Bundle bundle = new Bundle();
				bundle.putString("name", "全部");
				msg.setData(bundle);
				handle.sendMessage(msg);
				Sources.shopOneType = -1;
				Sources.shopTwoType = "";
				Sources.shopTwoName = "";
				selclass = true;
				button3.setChecked(true);
			} else if (button3.isChecked()) {
				Message msg = new Message();
				msg.what = 2;
				Bundle bundle = new Bundle();
				bundle.putString("name", "全部");
				msg.setData(bundle);
				handle.sendMessage(msg);
				Sources.color = "all";
				dismiss();
			}
			break;
		}
	}

}