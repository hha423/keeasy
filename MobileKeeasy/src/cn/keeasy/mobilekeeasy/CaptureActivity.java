package cn.keeasy.mobilekeeasy;

import java.io.IOException;
import java.util.Vector;

import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import cn.keeasy.mobilekeeasy.custom.SearchDialog;
import cn.keeasy.mobilekeeasy.data.Sources;
import cn.keeasy.mobilekeeasy.utils.Skip;
import cn.keeasy.mobilekeeasy.zing.CameraManager;
import cn.keeasy.mobilekeeasy.zing.CaptureActivityHandler;
import cn.keeasy.mobilekeeasy.zing.InactivityTimer;
import cn.keeasy.mobilekeeasy.zing.ViewfinderView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

public class CaptureActivity extends BaseActivity implements Callback {

	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.10f;
	private SurfaceView surfaceView;
	private SurfaceHolder surfaceHolder;

	private Camera camera;
	private SearchDialog dialog;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.main_scan);
		super.onCreate(savedInstanceState);
	}

	@Override
	void initView() {
		super.initView();
		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
	}

	@Override
	void initData() {
		topback.setVisibility(View.VISIBLE);
		toptitle.setText("条码扫描");
		CameraManager.init(getApplication());
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
	}

	@Override
	void initListen() {
		super.initListen();
	}

	@Override
	protected void onResume() {
		super.onResume();

		surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;

		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
	}

	/**
	 * onPause比surfaceDestroyed() 先调用
	 */
	@Override
	protected void onPause() {
		super.onPause();

		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();

	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		if (camera != null) {
			CameraManager.stopPreview();
		}
		super.onDestroy();
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(this, decodeFormats,
					characterSet);
		}
	}

	/**
	 * 在surfaceCreated后调用，当surface发生变化也会触发该方法 这个方法一般至少被调用一次
	 */
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	/**
	 * surface只能够由一个线程操作，一旦被操作，其他线程就无法操作surface
	 */
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}

	}

	/**
	 * 这里释放资源
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;
		if (camera != null) {
			CameraManager.stopPreview();
		}
	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();
	}

	public void handleDecode(Result obj) {
		inactivityTimer.onActivity();
		playBeepSoundAndVibrate();
		String url = null;
		if (obj.getText().contains("www.") || obj.getText().contains("http")
				|| obj.getText().contains(".com")
				|| obj.getText().contains(".cn")
				|| obj.getText().contains(".org")
				|| obj.getText().contains(".cc")) {
			if (obj.getText().indexOf("http") == -1) {
				url = "http://" + obj.getText();
			} else {
				url = obj.getText();
			}
			Uri uri = Uri.parse(url);
			startActivity(new Intent(Intent.ACTION_VIEW, uri));
		} else {
			Sources.SCANTEXT = obj.getText();
			dialog = new SearchDialog(CaptureActivity.this,
					android.R.style.Theme_NoTitleBar_Fullscreen);
			onPause();
			dialog.mShow();
			dialog.setOnDismissListener(new OnDismissListener() {
				@Override
				public void onDismiss(DialogInterface dialog) {
					onResume();
				}
			});
		}
		// this.finish();
	}

	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(
					R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(),
						file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		// if (vibrate) {
		// Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		// vibrator.vibrate(VIBRATE_DURATION);
		// }
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Skip.mBack(this);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}