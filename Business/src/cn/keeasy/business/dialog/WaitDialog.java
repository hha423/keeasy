package cn.keeasy.business.dialog;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;
import cn.keeasy.business.R;

public class WaitDialog extends Dialog {

	private TextView tvloadImage;

	public WaitDialog(Context context) {
		super(context, R.style.dialog);
		setContentView(R.layout.dialog);
		tvloadImage = (TextView) findViewById(R.id.d_text);
		setCanceledOnTouchOutside(false);
	}

	public void tvchange(int index) {
		tvloadImage.setText("加载中" + index + "%");
	}

	public void mInitShow(String str) {
		tvloadImage.setText(str);
		if (this != null && !isShowing()) {
			show();
		}
	}

}
