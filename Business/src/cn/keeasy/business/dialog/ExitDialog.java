package cn.keeasy.business.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import cn.keeasy.business.BaseActivity;

/**
 * 退出dialog
 * 
 * @author Administrator
 * 
 */
public class ExitDialog extends AlertDialog.Builder {

	public ExitDialog(final Activity activity) {
		super(activity);
		setTitle("温馨提示");
		setMessage("退出后将收不到消息推送，确定要退出?");
		setPositiveButton("确定", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				SharedPreferences sp = activity.getSharedPreferences("userIn",
						Context.MODE_PRIVATE);
				sp.edit().putBoolean("auto", false).commit();
				for (int i = 0; i < BaseActivity.activityList.size(); i++) {
					if (null != BaseActivity.activityList.get(i)) {
						BaseActivity.activityList.get(i).finish();
					}
				}
				System.exit(0);
			}
		});
		setNegativeButton("取消", new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				arg0.cancel();
			}
		});

	}

}
