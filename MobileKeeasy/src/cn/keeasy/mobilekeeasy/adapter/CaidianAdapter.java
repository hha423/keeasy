package cn.keeasy.mobilekeeasy.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import cn.keeasy.mobilekeeasy.R;
import cn.keeasy.mobilekeeasy.data.Sources;
import cn.keeasy.mobilekeeasy.entity.ProductInfoBean;
import cn.keeasy.mobilekeeasy.utils.WarningTone;

public class CaidianAdapter extends BaseAdapter {

	private LayoutInflater infla;
	private List<ProductInfoBean> pilist;
	private Context context;
	private int item = -1;
	private boolean flags;
	private Map<String, String> maps;

	public CaidianAdapter(Context context, Map<String, String> map) {
		infla = LayoutInflater.from(context);
		pilist = Sources.PROINFOLIST;
		this.context = context;
		this.maps = map;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pilist.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return pilist.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vhold = null;
		if (convertView == null) {
			vhold = new ViewHolder();
			convertView = infla.inflate(R.layout.list_caiming_item, null);
			vhold.lci_caiming = (TextView) convertView
					.findViewById(R.id.lci_caiming);
			vhold.lci_price = (TextView) convertView
					.findViewById(R.id.lci_price);
			vhold.lci_select = (ImageView) convertView
					.findViewById(R.id.lci_select);
			convertView.setTag(vhold);
		} else {
			vhold = (ViewHolder) convertView.getTag();
		}
		ProductInfoBean pbean = pilist.get(position);
		vhold.lci_caiming.setText(pbean.productName);
		vhold.lci_price.setText("￥ " + pbean.productPrice);
		if (maps.get(pbean.productId + "") != null) {
			pbean.flag = true;
		}
		if (pbean.flag) {
			vhold.lci_select.setVisibility(View.VISIBLE);
			if (item == position && flags) {
				flags = false;
				Animation anm = AnimationUtils.makeOutAnimation(context, true);
				vhold.lci_select.setAnimation(anm);
				new WarningTone(context, "home_change_count.wav").player();
			}
		} else {
			vhold.lci_select.setVisibility(View.GONE);
		}
		return convertView;
	}

	class ViewHolder {
		TextView lci_caiming, lci_price;
		ImageView lci_select;
	}

	public void setSelt(int ite) {
		this.item = ite;
		this.flags = true;
	}

}
