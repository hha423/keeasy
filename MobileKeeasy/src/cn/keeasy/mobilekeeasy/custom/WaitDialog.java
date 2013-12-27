package cn.keeasy.mobilekeeasy.custom;

import android.app.Dialog;
import android.content.Context;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import cn.keeasy.mobilekeeasy.R;

public class WaitDialog extends Dialog {

	private TextView tvload;
	private ProgressBar progress;
	private ImageView errimg;

	public WaitDialog(Context context) {
		super(context, R.style.dialog_nobg);
		setContentView(R.layout.wait_dialog);
		tvload = (TextView) findViewById(R.id.tixing);
		progress = (ProgressBar) findViewById(R.id.progress);
		errimg = (ImageView) findViewById(R.id.errimg);
		setCanceledOnTouchOutside(false);
	}

	public void tvchange(int index) {
		tvload.setText("加载中" + index + "%");
	}

	public void mInitShow(String str) {
		tvload.setText(str);
		if (this != null && !isShowing()) {
			try {
				show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
