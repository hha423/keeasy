package cn.keeasy.mobilekeeasy.custom;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import cn.keeasy.mobilekeeasy.data.ExitdialogInterface;

/**
 * 退出dialog
 * 
 * @author Administrator
 * 
 */
public class ExitDialog extends AlertDialog.Builder {

	public ExitDialog(Context activity, final ExitdialogInterface edif) {
		super(activity);
		setTitle("温馨提示");
		setMessage("退出后将收不到消息推送，确定要退出?");
		setPositiveButton("确定", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				edif.okOnClickListen();
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
