package com.evan.eyesight;

import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.evan.eyesight.adapter.MainAdapter;
import com.evan.eyesight.setting.Skip;

/**
 * 视力检查主页面
 * 
 * @author Evan
 * 
 */
public class MainActivity extends BaseActivity {

	private GridView gridview;
	private MainAdapter adapter;
	private TextView vers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_main);
		super.onCreate(savedInstanceState);
	}

	@Override
	void initView() {
		super.initView();
		gridview = (GridView) findViewById(R.id.gridview);
		vers = (TextView) findViewById(R.id.ver);
		adapter = new MainAdapter(this, tabs, mimg);
	}

	@Override
	void initData() {
		ibtn.setImageResource(R.drawable.icon);
		text.setText("保护您的健康视力\n每日关注您的视力变化");
		String ver = "";
		try {
			ver = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		vers.setText("" + ver);
		gridview.setAdapter(adapter);
	}

	@Override
	void initListen() {
		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Skip.mNext(MainActivity.this, page[arg2]);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}