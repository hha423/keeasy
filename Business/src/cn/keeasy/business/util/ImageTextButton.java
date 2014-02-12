package cn.keeasy.business.util;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.keeasy.business.R;

public class ImageTextButton extends RelativeLayout {
	private TextView tv_audio_time;
	private ImageView iv_audio_play;
	AnimationDrawable ad;
	private boolean isPlay;
	String file = "";

	public ImageTextButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void initView(Context context, String file) {
		this.file = file;
		View view = inflate(context, R.layout.mybutton, this);
		tv_audio_time = (TextView) view.findViewById(R.id.mbtn_text);
		iv_audio_play = (ImageView) findViewById(R.id.mbtn_img);

		view.setOnClickListener(new MyOnClickListener());

	}

	private class MyOnClickListener implements OnClickListener {

		public void onClick(View v) {

			if (isPlay) {
				mStop();
			} else {
				// 加入语音播放
				new Player().mPlayer(getContext(), file, ImageTextButton.this);
				// 动画开始播放
				iv_audio_play.setImageResource(R.anim.frame);
				ad = (AnimationDrawable) iv_audio_play.getDrawable();
				ad.start();
				isPlay = true;
			}

		}

	}

	public void setText(String str) {
		tv_audio_time.setText(str);
	}

	public void mStop() {
		ad.stop();
		iv_audio_play.setImageResource(R.drawable.feed_main_player_play_normal);
		isPlay = false;
	}
}
