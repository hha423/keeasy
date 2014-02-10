package com.evan.eyesight;

import android.os.Bundle;
import android.view.Menu;
import android.widget.GridView;

public class MainActivity extends BaseActivity {
	
	private GridView gridview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_main);
		super.onCreate(savedInstanceState);
	}

	@Override
	void initView() {
		super.initView();
		gridview = (GridView)findViewById(R.id.gridview);
	}

	@Override
	void initData() {
		ibtn.setImageResource(R.drawable.icon);
		text.setText("眼睛检测－每日关注您的视力变化");
	}

	@Override
	void initListen() {

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
