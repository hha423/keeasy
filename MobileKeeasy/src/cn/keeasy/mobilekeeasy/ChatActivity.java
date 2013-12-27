package cn.keeasy.mobilekeeasy;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.evan.tools.PhoneState;
import cn.keeasy.mobilekeeasy.adapter.ChatAdapter;
import cn.keeasy.mobilekeeasy.adapter.FaceAdapter;
import cn.keeasy.mobilekeeasy.dao.CurrContactMod;
import cn.keeasy.mobilekeeasy.dao.SendMsgMod;
import cn.keeasy.mobilekeeasy.data.Sources;
import cn.keeasy.mobilekeeasy.utils.PM;

public class ChatActivity extends BaseActivity implements OnTouchListener {

	private EditText msg;
	private ListView listview;
	private SendMsgMod sendmod;
	private GridView chat_face;
	private TextView chat_send;
	private ChatAdapter adapter;
	private ImageView chat_more;
	private CurrContactMod ccmod;
	private FaceAdapter fadapter;
	private InputMethodManager imm;
	private File mCurrentPhotoFile;// 图片文件
	private LinearLayout chat_layout1;
	private ImageView chat_btn_voice;
	private RelativeLayout chat_layout2;
	// private MediaRecorder mediaRecorder;
	private Button chat_imgbtn1, chat_imgbtn2, chat_imgbtn3, chat_imgbtn4;

	// 8KHz录制MP3,记得要加上录音权限哦~~
	private MP3Recorder recorder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.main_me_chat);
		super.onCreate(savedInstanceState);
	}

	@Override
	void initView() {
		recorder = new MP3Recorder(PhoneState.getCachePath(ChatActivity.this)
				.getAbsolutePath() + "/talk.mp3", 8000);
		super.initView();
		chat_more = (ImageView) findViewById(R.id.chat_more);
		listview = (ListView) findViewById(R.id.chat_listview);
		msg = (EditText) findViewById(R.id.chat_text);
		chat_send = (TextView) findViewById(R.id.chat_send);
		chat_layout1 = (LinearLayout) findViewById(R.id.chat_layout1);
		chat_layout2 = (RelativeLayout) findViewById(R.id.chat_layout2);
		chat_imgbtn1 = (Button) findViewById(R.id.chat_imgbtn1);
		chat_imgbtn2 = (Button) findViewById(R.id.chat_imgbtn2);
		chat_imgbtn3 = (Button) findViewById(R.id.chat_imgbtn3);
		chat_imgbtn4 = (Button) findViewById(R.id.chat_imgbtn4);
		chat_face = (GridView) findViewById(R.id.chat_face);
		chat_btn_voice = (ImageView) findViewById(R.id.chat_btn_voice);
	}

	@Override
	void initData() {
		Sources.mInitFace(this);// 初始化表情
		ccmod = new CurrContactMod(ChatActivity.this, this);
		sendmod = new SendMsgMod(ChatActivity.this, this, userInfo);
		adapter = new ChatAdapter(ChatActivity.this, userInfo);
		fadapter = new FaceAdapter(this);
		listview.setAdapter(adapter);
		chat_face.setAdapter(fadapter);
		toptitle.setText("与" + Sources.CURRENTIC.contactName + "对话中…");
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	}

	@Override
	void initListen() {
		super.initListen();
		topback.setVisibility(View.VISIBLE);
		chat_send.setOnClickListener(this);
		chat_more.setOnClickListener(this);
		msg.setOnClickListener(this);
		chat_imgbtn1.setOnClickListener(this);
		chat_imgbtn2.setOnClickListener(this);
		chat_imgbtn3.setOnClickListener(this);
		chat_imgbtn4.setOnClickListener(this);
		chat_btn_voice.setOnTouchListener(this);
		chat_imgbtn1.setOnTouchListener(PM.touchLight);
		chat_imgbtn2.setOnTouchListener(PM.touchLight);
		chat_imgbtn3.setOnTouchListener(PM.touchLight);
		chat_imgbtn4.setOnTouchListener(PM.touchLight);
		chat_face.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				StringBuffer sb = new StringBuffer(msg.getText().toString());
				sb.append("[bq" + "ic_img_" + (arg2 + 1) + "]");
				msg.setText(PM.getbiaoqian2(ChatActivity.this, sb.toString()));
			}
		});
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.chat_send:
			PM.onBtnAnim(chat_send);
			sendMsg();
			break;
		case R.id.chat_more:
			PM.onBtnAnim(chat_more);
			msg.setVisibility(View.VISIBLE);
			chat_send.setVisibility(View.VISIBLE);
			imm.hideSoftInputFromWindow(msg.getWindowToken(), 0);
			chat_layout1
					.setVisibility(chat_layout1.getVisibility() == View.GONE ? View.VISIBLE
							: View.GONE);
			chat_layout2.setVisibility(View.GONE);
			chat_face.setVisibility(View.GONE);
			break;
		case R.id.chat_imgbtn1:
			chat_layout1.setVisibility(View.GONE);
			chat_face.setVisibility(View.VISIBLE);
			break;
		case R.id.chat_imgbtn2:
			sendImageforFile();
			break;
		case R.id.chat_imgbtn3:
			sendImageForCame();
			break;
		case R.id.chat_imgbtn4:
			chat_layout1.setVisibility(View.GONE);
			chat_face.setVisibility(View.GONE);
			chat_layout2.setVisibility(View.VISIBLE);
			msg.setVisibility(View.INVISIBLE);
			chat_send.setVisibility(View.INVISIBLE);
			break;
		case R.id.chat_text:
			chat_layout1.setVisibility(View.GONE);
			chat_face.setVisibility(View.GONE);
			chat_layout2.setVisibility(View.GONE);
			break;
		}
	}

	@Override
	public void mSuccess() {
		adapter.notifyDataSetChanged();
		listview.setSelection(adapter.getCount());
		Sources.NOTREADMSG.remove(Sources.CURRENTIC.contactAccount);
	}

	private void sendMsg() {
		if (msg.getText().toString().length() > 0) {
			sendmod.mSendMessage(userInfo.getInt("userId", -1),
					Sources.CURRENTIC.contactAccount, "text", msg.getText()
							.toString(), userInfo.getString("account", ""));
			msg.setText("");
		} else {
			PhoneState.showToast(this, "消息内容不能为空!");
		}
	}

	/**
	 * 从文件获取图片发送
	 */
	private void sendImageforFile() {
		PM.doPickPhotoFromGallery(this);
	}

	/**
	 * 从相机获取图片发送
	 */
	private void sendImageForCame() {
		mCurrentPhotoFile = PM.doTakePhoto(this);
	}

	/**
	 * 显示录音按钮
	 */
	void showRecord() {
		if (msg.getVisibility() == View.GONE) {
			hindRec();
			imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		} else {
			msg.setVisibility(View.GONE);
			chat_send.setVisibility(View.GONE);
			imm.hideSoftInputFromWindow(msg.getWindowToken(), 0);
		}
	}

	private void hindRec() {
		msg.setVisibility(View.VISIBLE);
		chat_send.setVisibility(View.VISIBLE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 1:
			if (mCurrentPhotoFile != null) {
				Uri fileUri = Uri.fromFile(mCurrentPhotoFile);
				PM.jq(this, fileUri, 132, 132);
			}
			break;
		case 3021:
			if (data != null) {
				Uri imagefile = data.getData();
				PM.jq(this, imagefile, 132, 132);
			}
			break;
		case 3:
			if (data != null) {
				Bitmap bit_log = (Bitmap) data.getExtras().get("data");
				sendmod.mSendMidMessage(PM.bitmapToBytes(bit_log), "img");
			}
			break;
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// if (v == talk_message_record) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// PhoneState.cenShowToast(this, "开始录音...");
			chat_btn_voice.setImageResource(R.drawable.chat_voice_press);
			recorder.start();
			// record();
			break;
		case MotionEvent.ACTION_UP:
			chat_btn_voice.setImageResource(R.drawable.chat_voice_m);
			if (recorder != null) {
				recorder.stop();
				sendmod.mSendMidMessage(
						PM.filePathToByte(PhoneState.getCachePath(
								ChatActivity.this).getAbsolutePath()
								+ "/talk.mp3"), "audio");
			}
			break;

		// }
		}
		return true;
	}

	// private void record() {
	// try {
	// mediaRecorder = new MediaRecorder();
	// mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
	// mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
	// mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
	// mediaRecorder.setOutputFile(PhoneState.getCachePath(
	// ChatActivity.this).getAbsolutePath()
	// + "/talk.mp3");
	// mediaRecorder.prepare();
	// mediaRecorder.start();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// }

	@Override
	protected void onResume() {
		ccmod.mGetMessageInfo(userInfo.getString("account", ""),
				Sources.CURRENTIC.contactAccount, userInfo.getInt("userId", -1));
		ccmod.mStart();
		super.onResume();
		recorder.restore();
	}

	@Override
	protected void onDestroy() {
		ccmod.mStop();
		super.onDestroy();
		recorder.stop();
	}

}