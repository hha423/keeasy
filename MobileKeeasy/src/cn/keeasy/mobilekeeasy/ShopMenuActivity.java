package cn.keeasy.mobilekeeasy;

import java.util.HashMap;
import java.util.Map;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import cn.evan.tools.PhoneState;
import cn.keeasy.mobilekeeasy.adapter.CaidianAdapter;
import cn.keeasy.mobilekeeasy.custom.XiaopiaoPopup;
import cn.keeasy.mobilekeeasy.dao.ShopClassProductMod;
import cn.keeasy.mobilekeeasy.dao.ShopProductClassMod;
import cn.keeasy.mobilekeeasy.data.Sources;
import cn.keeasy.mobilekeeasy.entity.ClassProductBean;
import cn.keeasy.mobilekeeasy.utils.PM;
import cn.keeasy.mobilekeeasy.utils.Skip;

public class ShopMenuActivity extends BaseActivity {

	private int num;
	private View ov = null;
	private ImageView topcart;
	private TextView topcartnum;
	private XiaopiaoPopup popup;
	private CaidianAdapter cdadapter;
	private MyAdapter arradapter;
	private ShopProductClassMod spcmod;
	private ShopClassProductMod scpmod;
	private ListView caidan_nlist, caidan_xlist;
	private Map<String, String> selete;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.main_caidan);
		super.onCreate(savedInstanceState);
	}

	@Override
	void initView() {
		super.initView();
		topcart = (ImageView) findViewById(R.id.top_cart);
		topcartnum = (TextView) findViewById(R.id.top_cart_num);
		caidan_nlist = (ListView) findViewById(R.id.caidan_nlist);
		caidan_xlist = (ListView) findViewById(R.id.caidan_xlist);
	}

	@Override
	void initData() {
		selete = new HashMap<String, String>();
		spcmod = new ShopProductClassMod(this, this);
		scpmod = new ShopClassProductMod(this, this);
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				spcmod.mGetShoppingClasss(Sources.CURRENTSHOP.shopId,
						Sources.CURRENTSHOP.bAccount);
				return null;
			}
		}.execute(null, null, null);
		topback.setVisibility(View.VISIBLE);
		topcart.setVisibility(View.VISIBLE);
		toptitle.setText(Sources.CURRENTSHOP.shopName + "-菜单");
		popup = new XiaopiaoPopup(this);
		cdadapter = new CaidianAdapter(this, selete);
		caidan_xlist.setAdapter(cdadapter);
		arradapter = new MyAdapter();
		caidan_nlist.setAdapter(arradapter);
		num = Sources.CARTLIST.size();
		if (num > 0) {
			topcartnum.setText(num + "");
		}
	}

	@Override
	void initListen() {
		super.initListen();
		topcart.setOnClickListener(this);
		caidan_nlist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (ov == null) {
					arg1.setBackgroundColor(0xFFf6f6f8); // 选中高亮
					ov = arg1;
				} else {
					ov.setBackgroundColor(Color.TRANSPARENT); // 恢复之前的高亮
					arg1.setBackgroundColor(0xFFf6f6f8); // 设置当前高亮
					ov = arg1;
				}
				scpmod.mGetProductsByClassid(
						Sources.CLASSLIST.get(arg2).classId,
						Sources.CURRENTSHOP.shopId);
			}
		});
		caidan_xlist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Sources.PROINFOLIST.get(arg2).flag = true;
				selete.put(Sources.PROINFOLIST.get(arg2).productId + "", "1");
				cdadapter.setSelt(arg2);
				++num;
				topcartnum.setText(num + "");
				cdadapter.notifyDataSetChanged();
				for (int i = 0; i < Sources.CARTLIST.size(); i++) {
					if (Sources.CARTLIST.get(i).productId == Sources.PROINFOLIST
							.get(arg2).productId) {
						Sources.CARTLIST.get(i).productNum += 1;
						return;
					}
				}
				Sources.PROINFOLIST.get(arg2).productNum = 1;
				Sources.CARTLIST.add(Sources.PROINFOLIST.get(arg2));
			}
		});
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.top_cart:
			PM.onBtnAnim(topcart);
			popup.showPopup(topcart, handler, userInfo, this);
			break;
		}
	}

	@Override
	public void mSuccess() {
		Sources.PROINFOLIST.clear();
		scpmod.mGetProductsByClassid(Sources.CLASSLIST.get(0).classId,
				Sources.CURRENTSHOP.shopId);
		arradapter.notifyDataSetChanged();
	};

	@Override
	public void mSuccess1() {
		cdadapter.notifyDataSetChanged();
	};

	@Override
	public void mSuccess2() {
		popup.dismiss();
		if (Sources.ORDERBEAN.orderState.equals("1")) {
			PhoneState.showToast(ShopMenuActivity.this, "帐户余额抵扣成功!");
			Skip.mNext(ShopMenuActivity.this, OrderActivity.class);
		} else {
			Skip.mNext(this, AlixBuy.class);
		}
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				++num;
				break;
			case 1:
				--num;
				cdadapter.notifyDataSetChanged();
				break;
			case 2:
				selete.remove(msg.getData().getString("id"));
				--num;
				cdadapter.notifyDataSetChanged();
				break;
			case 3:
				Skip.mNext(ShopMenuActivity.this, LoginActivity.class,
						R.anim.activity_push_up_in, R.anim.activity_no_anim,
						false);
				break;
			}
			popup.setTextInfo();
			topcartnum.setText(num + "");
			super.handleMessage(msg);
		}
	};

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return Sources.CLASSLIST.size();
		}

		@Override
		public Object getItem(int position) {
			return Sources.CLASSLIST.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = getLayoutInflater().inflate(
					R.layout.list_item_center, null);
			TextView textView = (TextView) convertView
					.findViewById(R.id.itemc_text);
			ClassProductBean cpbean = Sources.CLASSLIST.get(position);
			textView.setText(cpbean.className);
			return convertView;
		}
	}
}