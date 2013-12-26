package cn.keeasy.business.dialog;

import java.util.Calendar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import cn.keeasy.business.R;

public class DateTimeDialog extends AlertDialog.Builder {

	private String mDate;
	private String mTime;
	private DatePicker dpk;
	private TimePicker tpk;
	private Handler handler;

	public DateTimeDialog(Context context, Handler hadler) {
		super(context);
		this.handler = hadler;
		View view = LayoutInflater.from(context).inflate(R.layout.time_select,
				null);
		setView(view);
		setIcon(android.R.drawable.ic_menu_today);
		setTitle("折扣时段选择");
		setCancelable(true);
		dpk = (DatePicker) view.findViewById(R.id.datePicker1);
		tpk = (TimePicker) view.findViewById(R.id.timePicker1);
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int monthOfYear = calendar.get(Calendar.MONTH);
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		mDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
		mTime = hourOfDay + ":" + minute;
		tpk.setCurrentHour(20);
		dpk.init(year, monthOfYear, dayOfMonth, new OnDateChangedListener() {
			@Override
			public void onDateChanged(DatePicker view, int year,
					int monthOfYear, int dayOfMonth) {
				mDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
			}
		});
		tpk.setOnTimeChangedListener(new OnTimeChangedListener() {
			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				mTime = hourOfDay + ":" + minute;
			}
		});
		setPositiveButton("确定", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				Message msg = new Message();
				msg.what = 0;
				Bundle data = new Bundle();
				data.putString("dtime", mDate + "  " + mTime);
				msg.setData(data);
				handler.sendMessage(msg);
				System.out.println("时间：" + tpk.getCurrentMinute());
			}
		});
	}
}
