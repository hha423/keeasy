package com.evan.eyesight;

import com.evan.eyesight.adapter.MainAdapter;

import android.os.Bundle;
import android.view.Menu;
import android.widget.GridView;

/**
 * ���������ҳ��
 * 
 * @author Evan
 * 
 */
public class MainActivity extends BaseActivity {

	private GridView gridview;
	private MainAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_main);
		super.onCreate(savedInstanceState);
	}

	@Override
	void initView() {
		super.initView();
		gridview = (GridView) findViewById(R.id.gridview);
		adapter = new MainAdapter();
	}

	@Override
	void initData() {
		ibtn.setImageResource(R.drawable.icon);
		text.setText("�۾����--ÿ�չ�ע���������仯");
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
