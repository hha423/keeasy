package com.evan.eyesight;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.evan.eyesight.setting.EmailSender;
import com.evan.eyesight.setting.Skip;
import com.evan.eyesight.setting.Tools;

public class Dialog extends Activity implements OnClickListener {

	private TextView msg;
	private Button yes;
	private Button no;
	private EditText mail;
	private EditText nick;

	public Dialog() {
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog);
		msg = (TextView) findViewById(R.id.msg);
		no = (Button) findViewById(R.id.button_no);
		yes = (Button) findViewById(R.id.button_yes);
		mail = (EditText) findViewById(R.id.mail);
		nick = (EditText) findViewById(R.id.nick);
		initData();
	}

	private void initData() {
		msg.setText("记录已保存到sd卡根目录\n是否现在将记录结果发送到邮箱？");
		no.setOnClickListener(this);
		yes.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_no:
			Skip.mBack(this);
			break;
		case R.id.button_yes:
			if (mail.length() < 1) {
				mail.setError("邮箱不能为空");
			} else if (!mail.getText().toString().contains("@")) {
				mail.setError("邮箱格式不正确");
			} else if (!mail.getText().toString().contains(".")) {
				mail.setError("邮箱格式不正确");
			} else if (nick.length() < 1) {
				nick.setError("姓名不能为空");
				nick.requestFocus();
			} else {
				yes.requestFocus();
				EmailSender sender = new EmailSender();
				sender.sendMail(mail.getText().toString().trim(),
						Tools.getFilePath("") + "/EyeResult.xls", nick
								.getText().toString().trim());
				Toast.makeText(this, "发送成功！", Toast.LENGTH_SHORT).show();
				Skip.mBack(this);
			}
			break;
		}
	}
}
