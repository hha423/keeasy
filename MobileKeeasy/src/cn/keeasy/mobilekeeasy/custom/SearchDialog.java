package cn.keeasy.mobilekeeasy.custom;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import cn.evan.tools.PhoneState;
import cn.keeasy.mobilekeeasy.R;
import cn.keeasy.mobilekeeasy.adapter.HomeAdapter;
import cn.keeasy.mobilekeeasy.custom.ReListView.OnLoadMoreListener;
import cn.keeasy.mobilekeeasy.dao.SearchMod;
import cn.keeasy.mobilekeeasy.data.IMod;
import cn.keeasy.mobilekeeasy.data.Sources;
import cn.keeasy.mobilekeeasy.utils.PM;

public class SearchDialog extends Dialog implements
		android.view.View.OnClickListener, OnLoadMoreListener, IMod {

	private TextView texttop;
	private ImageView imgtop;
	private EditText edit;
	private TextView sreah, button2;
	private LinearLayout button3;
	private GridView list;
	private ReListView list1;
	private HomeAdapter adapter;
	private Activity act;
	private int page;
	private SharedPreferences prefer;
	private String[] keyName = new String[] {};
	private String name;
	private InputMethodManager imm;
	private SearchMod smod;

	public SearchDialog(Activity context) {
		super(context);
		mInit(context);
	}

	public SearchDialog(Activity context, int theme) {
		super(context, theme);
		mInit(context);
	}

	public SearchDialog(Activity context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		mInit(context);
	}

	private void mInit(Activity context) {
		setContentView(R.layout.search_popup);
		setCanceledOnTouchOutside(false);
		this.act = context;
		texttop = (TextView) findViewById(R.id.top_title);
		texttop.setText("店铺搜索");
		imgtop = (ImageView) findViewById(R.id.top_back);
		imgtop.setVisibility(View.VISIBLE);
		edit = (EditText) findViewById(R.id.find_key);
		sreah = (TextView) findViewById(R.id.find_btn);
		button2 = (TextView) findViewById(R.id.find_clean);
		button3 = (LinearLayout) findViewById(R.id.find_his);
		list = (GridView) findViewById(R.id.find_his_gview);
		list1 = (ReListView) findViewById(R.id.find_list);
		imm = (InputMethodManager) context
				.getSystemService(Activity.INPUT_METHOD_SERVICE);
		list1.setOnLoadMoreListener(this);
	}

	public void mShow() {
		smod = new SearchMod(act, this);
		if (!"".equals(Sources.SCANTEXT)) {
			edit.setText(Sources.SCANTEXT);
			Sources.SCANTEXT = "";
			mSearch();
		}
		prefer = act.getSharedPreferences("sreach", Context.MODE_PRIVATE);
		name = prefer.getString("key", "");
		if ("".equals(name)) {
			button3.setVisibility(View.GONE);
		} else {
			button3.setVisibility(View.VISIBLE);
			keyName = name.split(",");
			list.setAdapter(new ArrayAdapter(act, R.layout.list_item_center,
					R.id.itemc_text, keyName));
			list.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					edit.setText(keyName[arg2]);
					mSearch();
				}
			});
		}
		edit.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				mSearch();
				return false;
			}
		});
		adapter = new HomeAdapter(act, true);
		sreah.setOnClickListener(this);
		imgtop.setOnClickListener(this);
		button2.setOnClickListener(this);
		list1.setAdapter(adapter);
		if (this != null && !isShowing()) {
			show();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.find_btn:
			mSearch();
			break;
		case R.id.top_back:
			PM.onBtnAnim(imgtop);
			Sources.SREACHINFO.clear();
			cancel();
			dismiss();
			break;
		case R.id.find_clean:
			prefer.edit().putString("key", "").commit();
			name = "";
			keyName = new String[] {};
			button3.setVisibility(View.GONE);
			break;
		}
	}

	private void mSearch() {
		if (edit.length() < 1) {
			edit.setError("关键字不能为空");
		} else {
			imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);
			Sources.SREACHINFO.clear();
			page = 1;
			smod.getSearchShop(edit.getText().toString().trim(), page);
		}
	}

	public void refalt() {
		if (Sources.SREACHINFO.size() < 1) {
			PhoneState.cenShowToast(act, "没有搜索到该关键字的相关内容！");
		} else {
			boolean same = true;
			for (String iterable : keyName) {
				if (edit.getText().toString().trim().equals(iterable)) {
					same = false;
				}
			}
			if (same) {
				prefer.edit()
						.putString("key",
								name += edit.getText().toString().trim() + ",")
						.commit();
			}
		}
		adapter.notifyDataSetChanged();
	}

	public void openShop() {
		if (Sources.SREACHINFO.size() < 1) {
			PhoneState.cenShowToast(act, "没有搜索到该关键字的相关内容！");
		}
		adapter.notifyDataSetChanged();
		imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);
	}

	@Override
	public void onLoadMore() {
		// 加载更多数据
		new AsyncTask<Void, Void, Void>() {
			// 延迟，doInBackground
			protected Void doInBackground(Void... params) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				++page;
				smod.getSearchShop(edit.getText().toString().trim(), page);
				if (adapter.getCount() > 40) {
					list1.setCanLoadMore(false);
				} else {
					list1.onLoadMoreComplete();// 加载跟多完成！
				}
			}
		}.execute(null, null, null);
	}

	@Override
	public void mSuccess() {
		openShop();
	}

	@Override
	public void mSumoney() {

	}

	@Override
	public void mSuccess1() {

	}

	@Override
	public void mSuccess2() {

	}

	@Override
	public void mSucfriend() {

	}

	@Override
	public void mSuccessMSg() {

	}

	@Override
	public void mSuccess(String str) {

	}
}