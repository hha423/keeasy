package cn.keeasy.mobilekeeasy.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import cn.evan.tools.PhoneState;
import cn.evan.tools.ui.ImageViews;
import cn.evan.tools.ui.MListView1;

import cn.keeasy.mobilekeeasy.R;
import cn.keeasy.mobilekeeasy.ShopActivity;
import cn.keeasy.mobilekeeasy.ShopTcActivity;
import cn.keeasy.mobilekeeasy.custom.ItemDialog;
import cn.keeasy.mobilekeeasy.data.Sources;
import cn.keeasy.mobilekeeasy.entity.ShopBean;
import cn.keeasy.mobilekeeasy.utils.PM;
import cn.keeasy.mobilekeeasy.utils.Skip;

public class NearbyAdapter extends BaseAdapter {

	private LayoutInflater layoutInflater;
	private List<ShopBean> list;
	private ItemDialog idialog;
	private Activity act;

	public NearbyAdapter(Activity context) {
		layoutInflater = LayoutInflater.from(context);
		idialog = new ItemDialog(context);
		this.act = context;
		list = Sources.RANGE;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = layoutInflater.inflate(R.layout.list_home_item, null);

			holder.img = (ImageViews) convertView.findViewById(R.id.lhi_img);
			LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.FILL_PARENT);
			params.weight = PhoneState.getScreenrp(act).widthPixels;
			params.height = PhoneState.getScreenrp(act).widthPixels / 3;
			holder.img.setLayoutParams(params);
			holder.text = (TextView) convertView.findViewById(R.id.lhi_name);
			holder.text1 = (TextView) convertView.findViewById(R.id.lhi_addr);
			holder.text2 = (TextView) convertView.findViewById(R.id.lhi_juli);
			holder.list = (MListView1) convertView.findViewById(R.id.lhi_list);
			holder.lhi_juli = (TextView) convertView
					.findViewById(R.id.lhi_juli);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final ShopBean bean = list.get(arg0);
		holder.img.DisplayImage(bean.shopImg, false);
		holder.img.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				idialog.mInitShow(bean);
			}
		});
		holder.text.setText(bean.shopName);
		holder.text1.setText("地址:" + bean.shopAddress);
		holder.lhi_juli.setText("距离" + setJuli(bean.distace));
		ColorAdapter adapter = new ColorAdapter(act, bean.dayType);
		holder.list.setAdapter(adapter);
		holder.list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				PM.onBtnAnim(arg1);
				Sources.CURRENTSHOP = bean;
				Sources.CURRENTSHOP.item = arg2;
				if (Sources.CURRENTSHOP.dayType.get(arg2).tcType == 1) {
					Skip.mNext(act, ShopTcActivity.class);
				} else {
					Skip.mNext(act, ShopActivity.class);
				}

			}
		});
		return convertView;
	}

	class ViewHolder {
		TextView text, text1, text2, lhi_juli;
		MListView1 list;
		ImageViews img;
	}

	private String setJuli(double dob) {
		if (dob < 1000) {
			return dob + "m";
		} else {
			return PhoneState.setDecimal(dob / 1000, 2) + "km";
		}
	}
}