package cn.keeasy.mobilekeeasy.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

public class WarningTone {
	MediaPlayer mediaPlayer;
	AssetFileDescriptor afd;
	boolean isRun;

	/**
	 * @param context
	 * @param name
	 *            "msg.wav" system.wav msg.wav lines.wav
	 * @author Evan
	 */
	public WarningTone(Context context, String name) {
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setOnCompletionListener(listen);
		try {
			afd = context.getAssets().openFd(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void player() {
		if (!isRun) {
			isRun = true;
			mediaPlayer.reset();
			try {
				mediaPlayer.setDataSource(afd.getFileDescriptor(),
						afd.getStartOffset(), afd.getLength());
				mediaPlayer.prepare();
				mediaPlayer.seekTo(0);
				mediaPlayer.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	OnCompletionListener listen = new OnCompletionListener() {

		@Override
		public void onCompletion(MediaPlayer mp) {
			isRun = false;
		}
	};

}
