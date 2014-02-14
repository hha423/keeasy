package com.evan.eyesight;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.evan.eyesight.setting.Skip;

public class SemangTestActivity extends BaseActivity {

	private TextView text_state;
	private ProgressBar progress_jindu;
	private ImageView image_ceshi;
	private EditText semang_answer;
	private Button button_gotoceshi;
	private int question_image[];
	public static List list = new ArrayList();
	public static int right_answer_count;
	private int question_numble;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activitysemangceshi);
		super.onCreate(savedInstanceState);
		question_numble = 1;
		right_answer_count = 0;
		list.clear();
		setState(question_numble);
	}

	@Override
	void initView() {
		super.initView();
		text_state = (TextView) findViewById(R.id.text_state);
		progress_jindu = (ProgressBar) findViewById(R.id.progress_jindu);
		image_ceshi = (ImageView) findViewById(R.id.image_ceshi);
		semang_answer = (EditText) findViewById(R.id.semang_answer);
		button_gotoceshi = (Button) findViewById(R.id.button_gotoceshi);
		int ai[] = new int[10];
		ai[0] = R.drawable.semangti1;
		ai[1] = R.drawable.semangti2;
		ai[2] = R.drawable.semangti3;
		ai[3] = R.drawable.semangti4;
		ai[4] = R.drawable.semangti5;
		ai[5] = R.drawable.semangti6;
		ai[6] = R.drawable.semangti7;
		ai[7] = R.drawable.semangti8;
		ai[8] = R.drawable.semangti9;
		ai[9] = R.drawable.semangti10;
		question_image = ai;
	}

	@Override
	void initData() {
		text.setText("色盲检测");
	}

	@Override
	void initListen() {
		super.initListen();
		button_gotoceshi.setOnClickListener(this);
		semang_answer.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				gotoceshi();
				return false;
			}
		});
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.button_gotoceshi:
			gotoceshi();
			break;
		}
	}

	private void gotoceshi() {
		question_numble = 1 + question_numble;
		if (question_numble == 11) {
			getRightAnswerCount(-1 + question_numble);
			Skip.mNext(this, SemangResultActivity.class,
					R.anim.activity_no_anim, R.anim.activity_slide_out_right,
					true);
		} else {
			getRightAnswerCount(question_numble - 1);
			setState(question_numble);
		}
	}

	private void setState(int i) {
		if (i == 10)
			text_state.setText((new StringBuilder("题目：")).append(i)
					.append("/10").toString());
		else
			text_state.setText((new StringBuilder(" 题目：")).append(i)
					.append("/10").toString());
		image_ceshi.setBackgroundResource(question_image[i - 1]);
		progress_jindu.setProgress(i);
		semang_answer.setText(null);
	}

	private void getRightAnswerCount(int i) {
		if (i == 1) {
			String s9 = semang_answer.getText().toString();
			if (s9.length() == 1 && s9.equals("6"))
				right_answer_count = 1 + right_answer_count;
			else
				list.add((new StringBuilder()).append(i).toString());
		} else if (i == 2) {
			String s8 = semang_answer.getText().toString();
			if (s8.length() == 2 && s8.contains("3") && s8.contains("6"))
				right_answer_count = 1 + right_answer_count;
			else
				list.add((new StringBuilder()).append(i).toString());

		} else if (i == 3) {
			String s7 = semang_answer.getText().toString();
			if (s7.length() == 3 && s7.contains("8") && s7.contains("9")
					&& s7.contains("6"))
				right_answer_count = 1 + right_answer_count;
			else
				list.add((new StringBuilder()).append(i).toString());

		} else if (i == 4) {
			String s6 = semang_answer.getText().toString();
			if (s6.length() == 4 && s6.contains("8") && s6.contains("9")
					&& s6.contains("6") && s6.contains("0"))
				right_answer_count = 1 + right_answer_count;
			else
				list.add((new StringBuilder()).append(i).toString());
		} else if (i == 5) {
			String s5 = semang_answer.getText().toString();
			if (s5.length() == 3 && s5.contains("8") && s5.contains("9")
					&& s5.contains("6"))
				right_answer_count = 1 + right_answer_count;
			else
				list.add((new StringBuilder()).append(i).toString());
		} else if (i == 6) {
			String s4 = semang_answer.getText().toString();
			if (s4.length() == 3 && s4.contains("8") && s4.contains("3")
					&& s4.contains("5"))
				right_answer_count = 1 + right_answer_count;
			else
				list.add((new StringBuilder()).append(i).toString());
		} else if (i == 7) {
			String s3 = semang_answer.getText().toString();
			if (s3.length() == 3 && s3.contains("6") && s3.contains("5")
					&& s3.contains("2"))
				right_answer_count = 1 + right_answer_count;
			else
				list.add((new StringBuilder()).append(i).toString());
		} else if (i == 8) {
			String s2 = semang_answer.getText().toString();
			if (s2.length() == 2 && s2.contains("8") && s2.contains("3"))
				right_answer_count = 1 + right_answer_count;
			else
				list.add((new StringBuilder()).append(i).toString());

		} else if (i == 9) {
			String s1 = semang_answer.getText().toString();
			if (s1.length() == 1 && s1.equals("6"))
				right_answer_count = 1 + right_answer_count;
			else
				list.add((new StringBuilder()).append(i).toString());

		} else if (i == 10) {
			String s = semang_answer.getText().toString();
			if (s.length() == 2 && s.contains("5") && s.contains("6"))
				right_answer_count = 1 + right_answer_count;
			else
				list.add((new StringBuilder()).append(i).toString());
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
}
