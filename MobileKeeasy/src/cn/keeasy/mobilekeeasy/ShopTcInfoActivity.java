package cn.keeasy.mobilekeeasy;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import cn.keeasy.mobilekeeasy.data.Sources;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ShopTcInfoActivity extends BaseActivity {

	private TextView msi_jianshao;
	private ListView msi_list;
	private MyAdapter madapter;
	private String[] img;
	private DisplayImageOptions options;
	private ImageLoader imageLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.main_shoptc_info);
		super.onCreate(savedInstanceState);
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.defa)
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.ic_error).cacheInMemory(true)
				.cacheOnDisc(true).considerExifParams(true).build();
		imageLoader = ImageLoader.getInstance();
	}

	@Override
	void initView() {
		super.initView();
		msi_jianshao = (TextView) findViewById(R.id.msi_jianshao);
		msi_list = (ListView) findViewById(R.id.msi_list);
	}

	@Override
	void initData() {
		topback.setVisibility(View.VISIBLE);
		toptitle.setText("套餐图文详情");
		msi_jianshao.setText(Sources.SHOPTCBEAN.tcName);
		img = Sources.SHOPTCBEAN.tcImgs.split(",");
		madapter = new MyAdapter();
		msi_list.setAdapter(madapter);
	}

	@Override
	void initListen() {
		super.initListen();
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
	}

	class MyAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return img.length;
		}

		@Override
		public Object getItem(int position) {
			return img[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ViewHolder hview = null;
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(
						R.layout.list_image_item, null);
				hview = new ViewHolder();
				hview.imgs = (ImageView) convertView.findViewById(R.id.lii_img);
				convertView.setTag(hview);
			} else {
				hview = (ViewHolder) convertView.getTag();
			}
			imageLoader.displayImage(img[position], hview.imgs, options);
			return convertView;
		}

		class ViewHolder {
			ImageView imgs;
		}
	}
}