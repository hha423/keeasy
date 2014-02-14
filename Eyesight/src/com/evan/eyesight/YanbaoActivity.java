package com.evan.eyesight;

import java.io.IOException;
import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class YanbaoActivity extends BaseActivity {

	private RelativeLayout button_play;
	private ImageView playsate;
	private boolean hasm;
	private boolean isplay;
	private boolean flag;
	private MediaPlayer m;
	private Handler mhandle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.yanbao_main);
		super.onCreate(savedInstanceState);
		m = new MediaPlayer();
		regest();
		isplay = false;
		hasm = false;
	}

	@Override
	void initView() {
		super.initView();
		playsate = (ImageView) findViewById(R.id.image_paly_state);
		button_play = (RelativeLayout) findViewById(R.id.button_play);
	}

	@Override
	void initData() {
		text.setText("眼保健操");
		playsate.setBackgroundResource(R.drawable.play);
	}

	@Override
	void initListen() {
		super.initListen();
		button_play.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.button_play:
			if (isplay) {
				isplay = false;
			} else {
				isplay = true;
			}
			initButtonPlay();
			break;
		}
	}

	private void initButtonPlay() {
		if (isplay) {
			playsate.setBackgroundResource(R.drawable.pause);
			if (hasm) {
				m.start();
			} else {
				play();
				hasm = true;
			}
		} else {
			playsate.setBackgroundResource(R.drawable.play);
			m.pause();
		}
	}

	private void play() {
		HandlerThread thread = new HandlerThread("Mythread");
		thread.start();
		mhandle = new Handler(thread.getLooper());
		mhandle.post(myrun);
	}

	private void regest() {
		if (m != null) {
			m.stop();
			m.release();
		}
	}

	@Override
	protected void onDestroy() {
		flag = false;
		mhandle.removeCallbacks(myrun);
		regest();
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@SuppressLint("HandlerLeak")
	Handler handle = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				playsate.setBackgroundResource(R.drawable.play);
				regest();
				isplay = false;
				hasm = false;
				flag = false;
				break;
			}
		}
	};

	Runnable myrun = new Runnable() {

		@Override
		public void run() {
			m = MediaPlayer.create(YanbaoActivity.this, R.raw.yanbao);
			try {
				m.prepare();
			} catch (IllegalStateException illegalstateexception) {
				illegalstateexception.printStackTrace();
			} catch (IOException ioexception) {
				ioexception.printStackTrace();
			}
			m.start();
			m.setVolume(10F, 0.0F);
			flag = true;
			while (flag) {
				if (m.getDuration() - m.getCurrentPosition() < 1000) {
					m.pause();
					handle.sendEmptyMessage(0);
					return;
				}
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	};

}