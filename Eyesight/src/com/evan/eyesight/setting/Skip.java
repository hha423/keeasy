package com.evan.eyesight.setting;

import android.app.Activity;
import android.content.Intent;

import com.evan.eyesight.R;

public class Skip {

	public static void mNext(Activity context, Class<?> cls) {
		context.startActivity(new Intent(context, cls));
		context.overridePendingTransition(R.anim.activity_slide_in_right,
				R.anim.activity_no_anim);
	}

	/**
	 * 跳转到下一页面
	 * 
	 * @param context
	 * @param cls
	 *            转入页
	 * @param enterAnim
	 * @param exitAnim
	 * @param exit
	 *            是否关闭当前页
	 */
	public static void mNext(Activity context, Class<?> cls, int enterAnim,
			int exitAnim, boolean exit) {
		context.startActivity(new Intent(context, cls));
		if (exit) {
			context.finish();
		}
		context.overridePendingTransition(enterAnim, exitAnim);
	}

	public static void mBack(Activity context) {
		context.finish();
		context.overridePendingTransition(R.anim.activity_no_anim,
				R.anim.activity_slide_out_right);

	}

	/**
	 * 退出当前页返回到上一页
	 * 
	 * @param context
	 * @param enterAnim
	 * @param exitAnim
	 */
	public static void mBack(Activity context, int enterAnim, int exitAnim) {
		context.finish();
		context.overridePendingTransition(enterAnim, exitAnim);
	}

}
