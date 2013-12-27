package cn.keeasy.mobilekeeasy.custom;

import android.app.Dialog;
import android.content.Context;
import android.view.MotionEvent;
import android.widget.ImageView;

import cn.keeasy.mobilekeeasy.R;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 显示提示消息dialog
 * 
 * @author Administrator
 * 
 */
public class ImageDialog extends Dialog {
	private ImageView imgview1;

	public ImageDialog(Context context) {
		super(context, R.style.dialog);
		setContentView(R.layout.dialog_image);
		imgview1 = (ImageView) findViewById(R.id.imgview1);
		setCanceledOnTouchOutside(true);
	}

	/**
	 * 显示当前消息
	 * 
	 * @param msg
	 */
	public void mInitShow(String url) {
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(url, imgview1);
		show();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		cancel();
		return super.onTouchEvent(event);
	}

}
