package cn.keeasy.business.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.evan.tools.ui.ImageViews;
import cn.keeasy.business.R;
import cn.keeasy.business.base.ChatBean;
import cn.keeasy.business.dialog.ImageDialog;
import cn.keeasy.business.util.ImageTextButton;
import cn.keeasy.business.util.PM;
import cn.keeasy.business.util.Sources;
import cn.keeasy.business.util.WarningTone;

public class ChatAdapter extends BaseAdapter {

	ArrayList<ChatBean> beans;
	LayoutInflater layout;
	String contactAccount;
	Context mContext;
	ImageDialog idialog;
	private WarningTone warn;
	int num;

	public ChatAdapter(Context context) {
		this.mContext = context;
		layout = LayoutInflater.from(context);
		contactAccount = Sources.CURRENTIC.contactAccount;
		beans = Sources.CHATINFOMAP.get(contactAccount);
		if (beans == null) {
			beans = new ArrayList<ChatBean>();
			Sources.CHATINFOMAP.put(contactAccount, beans);
		}
		num = beans.size();
		idialog = new ImageDialog(context);
		warn = new WarningTone(context, "msg.wav");
	}

	@Override
	public int getCount() {
		return beans.size();
	}

	@Override
	public Object getItem(int arg0) {
		return beans.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View v, ViewGroup arg2) {
		ChatBean bean = beans.get(arg0);
		if (bean.chatContent.indexOf("[img") != -1) {
			return image(v, bean);
		} else if (bean.chatContent.indexOf("[audio") != -1) {
			return audio(v, bean);
		} else {
			return wenziView(v, bean);
		}
	}

	View wenziView(View v, ChatBean bean) {
		ImageViews imgview1;
		if (bean.userAccount.equals(contactAccount)) {
			v = layout.inflate(R.layout.list_say_he_item, null);
			imgview1 = (ImageViews) v.findViewById(R.id.photo);
			if (num < beans.size()) {
				num = beans.size();
				warn.player();
			}
			if (Sources.CURRENTIC.contactAccount.equals("1988")) {
				imgview1.setImageResource(R.drawable.kefu);
			} else {
				imgview1.DisplayImage(Sources.CURRENTIC.contactPhoto, false);
			}
		} else {
			v = layout.inflate(R.layout.list_say_me_item, null);
			imgview1 = (ImageViews) v.findViewById(R.id.photo);
			// imgview1.DisplayImage(sp.getString("pic", ""), false);
		}

		TextView talk_time = (TextView) v.findViewById(R.id.time);
		TextView messagedetail_row_text = (TextView) v.findViewById(R.id.text);
		talk_time.setText(bean.chatTime);
		messagedetail_row_text.setText(PM.getbiaoqian2(mContext,
				bean.chatContent));
		return v;
	}

	View image(View v, ChatBean bean) {
		ImageViews imgview1;
		if (bean.userAccount.equals(contactAccount)) {
			v = layout.inflate(R.layout.list_say_he_image, null);
			imgview1 = (ImageViews) v.findViewById(R.id.photo);
			if (num < beans.size()) {
				num = beans.size();
				warn.player();
			}
			if (Sources.CURRENTIC.contactAccount.equals("1988")) {
				imgview1.setImageResource(R.drawable.kefu);
			} else {
				imgview1.DisplayImage(Sources.CURRENTIC.contactPhoto, false);
			}
		} else {
			v = layout.inflate(R.layout.list_say_me_image, null);
			imgview1 = (ImageViews) v.findViewById(R.id.photo);
			// imgview1.DisplayImage(sp.getString("pic", ""), false);
		}
		TextView talk_time = (TextView) v.findViewById(R.id.time);
		talk_time.setText(bean.chatTime);
		ImageViews messagegedetail_images = (ImageViews) v
				.findViewById(R.id.img);
		final String name = bean.chatContent.substring(4,
				bean.chatContent.length() - 1);
		messagegedetail_images.DisplayImage(Sources.IMAGEURL + name, false);
		messagegedetail_images.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				idialog.mInitShow(Sources.IMAGEURL + name);
			}
		});
		return v;
	}

	View audio(View v, ChatBean bean) {
		ImageViews imgview1;
		if (bean.userAccount.equals(contactAccount)) {
			v = layout.inflate(R.layout.list_say_he_voice, null);
			imgview1 = (ImageViews) v.findViewById(R.id.photo);
			if (num < beans.size()) {
				num = beans.size();
				warn.player();
			}
			if (Sources.CURRENTIC.contactAccount.equals("1988")) {
				imgview1.setImageResource(R.drawable.kefu);
			} else {
				imgview1.DisplayImage(Sources.CURRENTIC.contactPhoto, false);
			}
		} else {
			v = layout.inflate(R.layout.list_say_me_voice, null);
			imgview1 = (ImageViews) v.findViewById(R.id.photo);
			// imgview1.DisplayImage(sp.getString("pic", ""), false);
		}
		TextView talk_time = (TextView) v.findViewById(R.id.time);
		talk_time.setText(bean.chatTime);
		ImageTextButton btn = (ImageTextButton) v.findViewById(R.id.button);
		btn.initView(mContext,
				bean.chatContent.substring(6, bean.chatContent.length() - 1));
		return v;
	}

}
