package cn.keeasy.mobilekeeasy;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import cn.keeasy.mobilekeeasy.utils.Skip;

/**
 * 应用程序启动类：显示欢迎界面并跳转到主界面
 * 
 * @author Evan
 * @version 1.0
 * @created 2013-11-15
 */
public class LoadingActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// 无标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// 竖屏幕
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		super.onCreate(savedInstanceState);
		// 加载进入图片
		ImageView view = new ImageView(this);
		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		view.setLayoutParams(params);
		view.setScaleType(ScaleType.FIT_XY);
		view.setImageResource(R.drawable.welcome);
		setContentView(view);

		// 渐变展示启动屏
		AlphaAnimation aa = new AlphaAnimation(0.7f, 1.0f);
		aa.setDuration(1500);
		view.startAnimation(aa);
		aa.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationEnd(Animation arg0) {
				redirectTo();
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationStart(Animation animation) {
			}
		});

	}

	/**
	 * 跳转到...
	 */
	private void redirectTo() {
		Skip.mNext(LoadingActivity.this, MainActivity.class,
				R.anim.activity_no_anim, R.anim.activity_no_anim, true);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Skip.mBack(LoadingActivity.this, R.anim.activity_no_anim,
				R.anim.back_exit);
		return super.onKeyDown(keyCode, event);
	}

}