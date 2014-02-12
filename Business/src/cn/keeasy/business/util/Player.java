package cn.keeasy.business.util;

import java.io.File;

import android.content.Context;
import android.media.MediaPlayer;
import cn.evan.tools.PhoneState;
import cn.evan.tools.RequestTask;
import cn.evan.tools.base.IDownFile;

public class Player extends MediaPlayer implements IDownFile {

	private ImageTextButton anim;
	private RequestTask rtask;
	private Context mcontext;

	public Player() {
		setOnCompletionListener(listen);
		rtask = new RequestTask(this);
	}

	public void mPlayer(Context mContext, String file, ImageTextButton anim) {
		// 加入语音播放
		this.anim = anim;
		this.mcontext = mContext;
		File f = new File(PhoneState.getCachePath(mContext).getAbsolutePath(),
				file); // 2.1之前的获取路径的方法，跟录音目录要一致
		if (f.exists()) {
			player(f);
			anim.mStop();
		} else {
			rtask.mGetFile(mContext, Sources.RECORDURL, file);
		}
	}

	void player(File f) {
		try {
			setDataSource(f.getPath());
			prepare();
			start();
		} catch (Exception e) {
			anim.mStop();
			PhoneState.showToast(mcontext, "不支持的播放格式");
			e.printStackTrace();
		}
	}

	OnCompletionListener listen = new OnCompletionListener() {

		@Override
		public void onCompletion(MediaPlayer mp) {
			anim.mStop();
		}
	};

	@Override
	public void mTaskError() {
		System.out.println("Down Voice Err!");
	}

	@Override
	public void mTaskSuccess(File arg0) {
		player(arg0);
	}

}
