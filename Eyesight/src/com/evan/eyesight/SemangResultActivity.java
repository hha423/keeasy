package com.evan.eyesight;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.evan.eyesight.dao.ShouSql;
import com.evan.eyesight.setting.Skip;

public class SemangResultActivity extends BaseActivity {

	private TextView text_state;
	private TextView text_point;
	private TextView text_semang_state;
	private TextView text_semang_zhonglei;
	private Button button_gotoceshi;
	private Button button_gotoback;
	private Intent intent;
	private boolean isjilu;
	public List list;
	public List listright;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activitysemangresult);
		super.onCreate(savedInstanceState);
		intent = getIntent();
		isjilu = intent.getBooleanExtra("isjilu", false);
		System.out.println("jj:" + isjilu);
		if (isjilu) {
			initStsatejilu();
			button_gotoceshi.setVisibility(8);
		} else {
			initStsate();
			button_gotoceshi.setVisibility(0);
		}
	}

	@Override
	void initView() {
		super.initView();
		text_state = (TextView) findViewById(R.id.text_state);
		text_point = (TextView) findViewById(R.id.text_point);
		text_semang_state = (TextView) findViewById(R.id.text_semang_state);
		text_semang_zhonglei = (TextView) findViewById(R.id.text_semang_zhonglei);
		button_gotoceshi = (Button) findViewById(R.id.button_gotoceshi);
		button_gotoback = (Button) findViewById(R.id.button_gotoback);
	}

	@Override
	void initData() {
		text.setText("色盲检测");
		text_state.setText("题目：10/10");
	}

	@Override
	void initListen() {
		super.initListen();
		button_gotoback.setOnClickListener(this);
		button_gotoceshi.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.button_gotoback:
			Skip.mBack(this);
			break;

		case R.id.button_gotoceshi:
			Intent intent1 = new Intent();
			intent1.setClass(getApplicationContext(), SemangTestActivity.class);
			startActivity(intent1);
			finish();
			overridePendingTransition(R.anim.activity_no_anim,
					R.anim.activity_slide_out_right);
			break;
		}
	}

	private void addleixing(String s) {
		boolean flag = true;
		int i = 0;
		do {
			if (i >= list.size()) {
				if (flag)
					list.add(s);
				return;
			}
			if (((String) list.get(i)).equals(s))
				flag = false;
			i++;
		} while (true);
	}

	private String geileixin() {
		String s;
		int i;
		s = "";
		i = 0;
		if (i >= list.size())
			return s;
		Log.e((new StringBuilder("n")).append(i).toString(),
				(String) list.get(i));
		if (!((String) list.get(i)).equals("1")) {
			// break;
		}
		if (s.equals(""))
			s = "蓝";
		else
			s = (new StringBuilder(String.valueOf(s))).append(",蓝").toString();
		i++;

		if (((String) list.get(i)).equals("2")) {
			if (s.equals(""))
				s = "紫";
			else
				s = (new StringBuilder(String.valueOf(s))).append(",紫")
						.toString();
		} else if (((String) list.get(i)).equals("3")) {
			if (s.equals(""))
				s = "红";
			else
				s = (new StringBuilder(String.valueOf(s))).append(",红")
						.toString();
		} else if (((String) list.get(i)).equals("4")) {
			if (s.equals(""))
				s = "绿";
			else
				s = (new StringBuilder(String.valueOf(s))).append(",绿")
						.toString();
		} else if (((String) list.get(i)).equals("5"))
			if (s.equals("黄"))
				s = "";
			else
				s = (new StringBuilder(String.valueOf(s))).append(",黄")
						.toString();
		return s;
	}

	private void getYiChang() {
		int i = 1;
		if (i < 11)
			;
		int k;
		list.clear();
		k = 0;
		if (k >= listright.size())
			return;
		String s;
		boolean flag;
		int j;
		s = (new StringBuilder()).append(i).toString();
		flag = true;
		j = 0;
		{
			if (j < SemangTestActivity.list.size())
				// break;
				if (flag)
					listright.add(s);
			i++;
		}
		if (((String) SemangTestActivity.list.get(j)).equals(s))
			flag = false;
		j++;
		if (((String) listright.get(k)).equals("1")) {
			addleixing("1");
			addleixing("2");
		} else if (((String) listright.get(k)).equals("2"))
			addleixing("3");
		else if (((String) listright.get(k)).equals("3")) {
			addleixing("2");
			addleixing("3");
			addleixing("4");
		} else if (((String) listright.get(k)).equals("4")) {
			addleixing("3");
			addleixing("4");
		} else if (((String) listright.get(k)).equals("5")) {
			addleixing("3");
			addleixing("4");
		} else if (((String) listright.get(k)).equals("6"))
			addleixing("3");
		else if (((String) listright.get(k)).equals("7"))
			addleixing("4");
		else if (((String) listright.get(k)).equals("8"))
			addleixing("2");
		else if (((String) listright.get(k)).equals("9"))
			addleixing("1");
		else if (((String) listright.get(k)).equals("10"))
			addleixing("5");
		// k++;
	}

	private void initStsate() {
		text_point.setText((new StringBuilder()).append(
				SemangTestActivity.right_answer_count).toString());
		String s;
		ContentValues contentvalues;
		if (SemangTestActivity.right_answer_count > 5) {
			text_semang_state.setText("正常");
			text_semang_zhonglei.setText("");
			text_semang_zhonglei.setVisibility(8);
		} else {
			text_semang_state.setText("异常");
			text_semang_zhonglei.setVisibility(0);
			getYiChang();
			if (list.size() == 5)
				text_semang_zhonglei.setText("您可能有全色盲");
			else
				text_semang_zhonglei.setText((new StringBuilder("您可能有"))
						.append(geileixin()).append("色盲").toString());
		}
		s = (new SimpleDateFormat("yyyy-MM-dd HH:mm")).format(new Date(System
				.currentTimeMillis()));
		contentvalues = new ContentValues();
		contentvalues.put("leixing", "semang");
		contentvalues.put("time", s);
		contentvalues.put("str1",
				(new StringBuilder()).append(text_point.getText().toString())
						.toString());
		if (text_semang_state.getText().toString().contains("正常"))
			contentvalues.put(
					"str2",
					(new StringBuilder()).append(
							text_semang_state.getText().toString()).toString());
		else
			contentvalues.put(
					"str2",
					(new StringBuilder())
							.append(text_semang_state.getText().toString())
							.append(",")
							.append(text_semang_zhonglei.getText().toString())
							.toString());
		(new ShouSql(getApplicationContext())).insert(contentvalues);
	}

	private void initStsatejilu() {
		text_point.setText((new StringBuilder()).append(
				SemangTestActivity.right_answer_count).toString());
		if (SemangTestActivity.right_answer_count > 5) {
			text_semang_state.setText("正常");
			text_semang_zhonglei.setText("");
			text_semang_zhonglei.setVisibility(8);
		} else {
			text_semang_state.setText("异常");
			text_semang_zhonglei.setVisibility(0);
			String s = intent.getStringExtra("yichang");
			text_semang_zhonglei.setText(s.substring(3));
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}

	public SemangResultActivity() {
		list = new ArrayList();
		listright = new ArrayList();
	}
}
