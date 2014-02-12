package cn.keeasy.business.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.keeasy.business.R;
import cn.keeasy.business.bean.ContactBean;
import cn.keeasy.business.util.Sources;

public class FriendAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<ContactBean> contbean;

	public FriendAdapter(Context context) {
		inflater = LayoutInflater.from(context);
		contbean = Sources.CONTACTLIST;
	}

	@Override
	public int getCount() {
		return contbean.size();
	}

	@Override
	public Object getItem(int position) {
		return contbean.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		convertView = inflater.inflate(R.layout.list_friend_item, null);
		TextView name = (TextView) convertView.findViewById(R.id.lfi_name);
		TextView num = (TextView) convertView.findViewById(R.id.lfi_num);
		TextView msgnum = (TextView) convertView.findViewById(R.id.lfi_msgnum);
		ContactBean bean = contbean.get(position);
		name.setText(bean.contactName);
		num.setText(bean.contactAccount);
		if (Sources.NOTREADMSG.get(bean.contactAccount) != null) {
			msgnum.setText(Sources.NOTREADMSG.get(bean.contactAccount) + "");
			msgnum.setVisibility(View.VISIBLE);
		}
		return convertView;
	}
}
