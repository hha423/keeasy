package com.evan.eyesight.setting;

import java.io.File;

import android.graphics.ColorMatrixColorFilter;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class Tools {

	/**
	 * 判断SD卡是否存在
	 * 
	 * @return
	 */
	public static boolean hasSDCard() {
		String status = Environment.getExternalStorageState();
		if (!status.equals(Environment.MEDIA_MOUNTED)) {
			return false;
		}
		return true;
	}

	/**
	 * 获取SDCard路径
	 * 
	 * @name 文件夹名称
	 * @return
	 */
	public static String getFilePath(String name) {
		if (hasSDCard()) {
			if (name.equals("")) {
				return Environment.getExternalStorageDirectory()
						.getAbsolutePath() + File.separator;
			} else
				return Environment.getExternalStorageDirectory()
						.getAbsolutePath() + File.separator + name;// filePath:/sdcard/
		} else {
			return Environment.getDataDirectory().getAbsolutePath() + "/data/"
					+ name; // filePath:/data/data/
		}
	}

	/**
	 * 按钮点击高亮效果
	 */
	public final static OnTouchListener touchLight = new OnTouchListener() {
		public final float[] BT_SELECTED = new float[] { 1, 0, 0, 0, 50, 0, 1,
				0, 0, 50, 0, 0, 1, 0, 50, 0, 0, 0, 1, 0 };
		public final float[] BT_NOT_SELECTED = new float[] { 1, 0, 0, 0, 0, 0,
				1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0 };

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				v.getBackground().setColorFilter(
						new ColorMatrixColorFilter(BT_SELECTED));
				v.setBackgroundDrawable(v.getBackground());
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				v.getBackground().setColorFilter(
						new ColorMatrixColorFilter(BT_NOT_SELECTED));
				v.setBackgroundDrawable(v.getBackground());
			}
			return false;
		}
	};

}