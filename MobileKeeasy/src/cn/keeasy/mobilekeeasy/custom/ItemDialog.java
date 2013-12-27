package cn.keeasy.mobilekeeasy.custom;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import cn.keeasy.mobilekeeasy.R;
import cn.keeasy.mobilekeeasy.ShopActivity;
import cn.keeasy.mobilekeeasy.ShopTcActivity;
import cn.keeasy.mobilekeeasy.adapter.HomeItemAdapter;
import cn.keeasy.mobilekeeasy.data.Sources;
import cn.keeasy.mobilekeeasy.entity.ShopBean;
import cn.keeasy.mobilekeeasy.utils.PM;
import cn.keeasy.mobilekeeasy.utils.Skip;

public class ItemDialog extends Dialog {

	private TextView ds_name;
	private ListView ds_list;
	private HomeItemAdapter hiadapter;
	private Activity context;

	public ItemDialog(Activity context) {
		super(context, R.style.dialog);
		setContentView(R.layout.dialog_shop);
		this.context = context;
		ds_name = (TextView) findViewById(R.id.ds_name);
		ds_list = (ListView) findViewById(R.id.ds_list);
	}

	public void mInitShow(final ShopBean item) {
		ds_name.setText(item.shopName);
		hiadapter = new HomeItemAdapter(context, item.dayType);
		ds_list.setAdapter(hiadapter);
		ds_list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				PM.onBtnAnim(arg1);
				Sources.CURRENTSHOP = item;
				Sources.CURRENTSHOP.item = arg2;
				if (Sources.CURRENTSHOP.dayType.get(arg2).tcType == 1) {
					Skip.mNext(context, ShopTcActivity.class);
				} else {
					Skip.mNext(context, ShopActivity.class);
				}
				dismiss();
			}
		});
		if (!isShowing()) {
			show();
		}
	}

}
