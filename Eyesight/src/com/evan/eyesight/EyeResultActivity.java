package com.evan.eyesight;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.evan.eyesight.dao.ShouSql;
import com.evan.eyesight.setting.SetConfig;
import com.evan.eyesight.setting.Skip;

public class EyeResultActivity extends BaseActivity {

	private Button button_gotoback;
	private Button button_gotoceshi;
	private boolean isjilu;
	private ProgressBar progress_left1;
	private ProgressBar progress_left2;
	private ProgressBar progress_left3;
	private ProgressBar progress_right1;
	private ProgressBar progress_right2;
	private ProgressBar progress_right3;
	private TextView text_lefteye_shuzhi;
	private TextView text_lefteye_state;
	private TextView text_righteye_shuzhi;
	private TextView text_righteye_state;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activityjinshiresult);
		super.onCreate(savedInstanceState);
	}

	@Override
	void initView() {
		super.initView();
		progress_left1 = (ProgressBar) findViewById(R.id.progress_left1);
		progress_left2 = (ProgressBar) findViewById(R.id.progress_left2);
		progress_left3 = (ProgressBar) findViewById(R.id.progress_left3);
		progress_right1 = (ProgressBar) findViewById(R.id.progress_right1);
		progress_right2 = (ProgressBar) findViewById(R.id.progress_right2);
		progress_right3 = (ProgressBar) findViewById(R.id.progress_right3);
		text_lefteye_shuzhi = (TextView) findViewById(R.id.text_lefteye_shuzhi);
		text_righteye_shuzhi = (TextView) findViewById(R.id.text_righteye_shuzhi);
		text_lefteye_state = (TextView) findViewById(R.id.text_lefteye_state);
		text_righteye_state = (TextView) findViewById(R.id.text_righteye_state);
		button_gotoceshi = (Button) findViewById(R.id.button_gotoceshi);
		button_gotoback = (Button) findViewById(R.id.button_gotoback);
	}

	@Override
	void initData() {
		super.initData();
		isjilu = getIntent().getBooleanExtra("isjilu", false);
		if (isjilu) {
			initStatejilu();
			button_gotoceshi.setVisibility(8);
		} else {
			if (SetConfig.lefteye != 0) {
				initState();
			}
			button_gotoceshi.setVisibility(0);
		}
	}

	@Override
	void initListen() {
		super.initListen();
		button_gotoceshi.setOnClickListener(this);
		button_gotoback.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.button_gotoceshi:
			Skip.mNext(this, EyeActivity.class, R.anim.activity_slide_in_right,
					R.anim.activity_no_anim, true);
			break;
		case R.id.button_gotoback:
			Skip.mBack(this);
			break;
		}
	}

	private void initState() {
		getResources();
		text_lefteye_shuzhi.setText((new StringBuilder("左眼：")).append(
				SetConfig.lefteye).toString());
		progress_left1.setProgress((int) (1.5F * SetConfig.lefteye));
		progress_left2.setProgress((int) (1.5F * SetConfig.lefteye));
		progress_left3.setProgress((int) (1.5F * SetConfig.lefteye));
		progress_right1.setProgress((int) (1.5F * SetConfig.righteye));
		progress_right2.setProgress((int) (1.5F * SetConfig.righteye));
		progress_right3.setProgress((int) (1.5F * SetConfig.righteye));
		text_righteye_shuzhi.setText((new StringBuilder("右眼：")).append(
				SetConfig.righteye).toString());
		if ((int) (10F * SetConfig.righteye) < 1.2) {
			text_lefteye_state.setText("你的左眼视力为：严重近视");
			progress_left3.setVisibility(0);
			progress_left2.setVisibility(8);
			progress_left1.setVisibility(8);
		} else if ((int) (10F * SetConfig.lefteye) < 6) {
			progress_left2.setVisibility(0);
			progress_left3.setVisibility(8);
			progress_left1.setVisibility(8);
			text_lefteye_state.setText("你的左眼视力为：近视");
		} else {
			progress_left1.setVisibility(0);
			progress_left2.setVisibility(8);
			progress_left3.setVisibility(8);
			text_lefteye_state.setText("你的左眼视力为：正常");
		}
		if ((int) (10F * SetConfig.righteye) < 1.2) {
			text_righteye_state.setText("你的右眼视力为：严重近视");
			progress_right3.setVisibility(0);
			progress_right2.setVisibility(8);
			progress_right1.setVisibility(8);
		} else if ((int) (10F * SetConfig.righteye) < 6) {
			progress_right2.setVisibility(0);
			progress_right3.setVisibility(8);
			progress_right1.setVisibility(8);
			text_righteye_state.setText("你的右眼视力为：近视");
		} else {
			progress_right1.setVisibility(0);
			progress_right2.setVisibility(8);
			progress_right3.setVisibility(8);
			text_righteye_state.setText("你的右眼视力为：正常");
		}
		String s = (new SimpleDateFormat("yyyy-MM-dd HH:mm")).format(new Date(
				System.currentTimeMillis()));
		ContentValues contentvalues = new ContentValues();
		contentvalues.put("leixing", "jinshi");
		contentvalues.put("time", s);
		contentvalues.put("left",
				(new StringBuilder()).append(SetConfig.lefteye).toString());
		contentvalues.put("right",
				(new StringBuilder()).append(SetConfig.righteye).toString());
		contentvalues.put(
				"str1",
				(new StringBuilder()).append(
						text_lefteye_state.getText().toString()).toString());
		contentvalues.put(
				"str2",
				(new StringBuilder()).append(
						text_righteye_state.getText().toString()).toString());
		(new ShouSql(getApplicationContext())).insert(contentvalues);
	}

	private void initStatejilu() {
		getResources();
		text_lefteye_shuzhi.setText((new StringBuilder("左眼：")).append(
				SetConfig.lefteye).toString());
		progress_left1.setProgress((int) (1.5F * SetConfig.lefteye));
		progress_left2.setProgress((int) (1.5F * SetConfig.lefteye));
		progress_left3.setProgress((int) (1.5F * SetConfig.lefteye));
		progress_right1.setProgress((int) (1.5F * SetConfig.righteye));
		progress_right2.setProgress((int) (1.5F * SetConfig.righteye));
		progress_right3.setProgress((int) (1.5F * SetConfig.righteye));
		text_righteye_shuzhi.setText((new StringBuilder("右眼：")).append(
				SetConfig.righteye).toString());
		if ((int) (10F * SetConfig.lefteye) < 1.2) {
			text_lefteye_state.setText("你的左眼视力为：严重近视");
			progress_left3.setVisibility(0);
			progress_left2.setVisibility(8);
			progress_left1.setVisibility(8);
		} else if ((int) (10F * SetConfig.lefteye) < 6) {
			progress_left2.setVisibility(0);
			progress_left3.setVisibility(8);
			progress_left1.setVisibility(8);
			text_lefteye_state.setText("你的左眼视力为：近视");
		} else {
			progress_left1.setVisibility(0);
			progress_left2.setVisibility(8);
			progress_left3.setVisibility(8);
			text_lefteye_state.setText("你的左眼视力为：正常");
		}
		if ((int) (10F * SetConfig.righteye) < 1.2) {
			text_righteye_state.setText("你的右眼视力为：严重近视");
			progress_right3.setVisibility(0);
			progress_right2.setVisibility(8);
			progress_right1.setVisibility(8);
		} else if ((int) (10F * SetConfig.righteye) < 6) {
			progress_right2.setVisibility(0);
			progress_right3.setVisibility(8);
			progress_right1.setVisibility(8);
			text_righteye_state.setText("你的右眼视力为：近视");
		} else {
			progress_right1.setVisibility(0);
			progress_right2.setVisibility(8);
			progress_right3.setVisibility(8);
			text_righteye_state.setText("你的右眼视力为：正常");
		}
	}

}