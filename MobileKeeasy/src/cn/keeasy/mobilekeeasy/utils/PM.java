package cn.keeasy.mobilekeeasy.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import cn.evan.tools.PhoneState;

import com.baidu.platform.comapi.basestruct.GeoPoint;
import cn.keeasy.mobilekeeasy.ChatActivity;
import cn.keeasy.mobilekeeasy.LoadingActivity;
import cn.keeasy.mobilekeeasy.LoginActivity;
import cn.keeasy.mobilekeeasy.R;
import cn.keeasy.mobilekeeasy.data.Sources;
import cn.keeasy.mobilekeeasy.entity.ContactBean;
import cn.keeasy.mobilekeeasy.entity.Poi;

/**
 * 通用方法
 * 
 * @author Administrator
 * 
 */
public class PM {

	public static boolean requestFocus(View view) {
		view.setFocusable(true);
		view.setFocusableInTouchMode(true);
		return view.requestFocus();
	}

	/**
	 * 拍照获取图片
	 * 
	 */
	public static File doTakePhoto(Activity context) {
		File mCurrentPhotoFile = null;
		try {
			// Launch camera to take photo for selected contact
			Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
			mCurrentPhotoFile = new File("mnt/sdcard/DCIM/Camera/",
					System.currentTimeMillis() + ".jpg");
			intent.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(mCurrentPhotoFile));
			context.startActivityForResult(intent, Activity.DEFAULT_KEYS_DIALER);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mCurrentPhotoFile;
	}

	/**
	 * 从相册中获取图片
	 */
	public static void doPickPhotoFromGallery(Activity context) {
		try {
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
			intent.setType("image/*");
			context.startActivityForResult(intent, 3021);
		} catch (ActivityNotFoundException e) {
			PhoneState.showToast(context, "没有发现相册！");
		}
	}

	/**
	 * 剪切成合适大小的图片
	 * 
	 * @param bitm
	 */
	public static void jq(Activity context, Uri uri, int width, int height) {
		final Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);// 裁剪框比例
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", width);
		intent.putExtra("outputY", height);
		intent.putExtra("return-data", true);
		context.startActivityForResult(intent, 3);
	}

	/**
	 * @Bitmap转Byte[]
	 */
	public static byte[] bitmapToBytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		return baos.toByteArray();
	}

	/**
	 * @byte[]转Bitmap
	 */
	public static Bitmap bytesToBimap(byte[] b) {
		if (b != null && b.length != 0) {
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		} else {
			return null;
		}
	}

	/**
	 * 将文件转成byte
	 * 
	 * @param filePath
	 * @return
	 */
	public static byte[] fileToByte(File file) {
		byte[] b = null;
		try {
			InputStream is = file.toURL().openStream();
			b = new byte[is.available()];
			is.read(b, 0, b.length);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return b;
	}

	/**
	 * 根据文件路径转成byte
	 * 
	 * @param filePath
	 * @return
	 */
	public static byte[] filePathToByte(String filePath) {
		File f = new File(filePath);
		if (f.isFile()) {
			byte[] b;
			try {
				InputStream is = f.toURL().openStream();
				b = new byte[is.available()];
				is.read(b, 0, b.length);
				is.close();
				return b;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static int getMyColor(String ss) {
		int r = Integer.parseInt(ss.substring(1, 3), 16);
		int g = Integer.parseInt(ss.substring(3, 5), 16);
		int b = Integer.parseInt(ss.substring(5, 7), 16);
		return Color.rgb(r, g, b);
	}

	/**
	 * @通过字符串截取到标签
	 */
	public static SpannableString getbiaoqian2(Context context, String string) {
		SpannableString ss = new SpannableString(string);
		int index = 0;
		while (index <= ss.length() - 10) {
			int start = string.indexOf("[bq", index);
			int last = string.indexOf(']', start);
			if (start != -1 && last != -1) {
				String biaoqian = string.substring(start + 3, last);
				Bitmap bit = BitmapFactory.decodeResource(
						context.getResources(), Sources.FACESMAP.get(biaoqian));
				ss.setSpan(new ImageSpan(bit), start, last + 1,
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				index = last;
			} else {
				break;
			}
		}
		return ss;
	}

	/**
	 * 计算折扣价 保留两位小数
	 * 
	 * @param yPrice
	 *            原价
	 * @param discount
	 *            折扣
	 * @return
	 */
	public static double mGetDiscountPrice(double yPrice, double discount) {
		double d = yPrice * discount / 10;
		d = Math.round(d * 100) / 100.0;
		return d;
	}

	/**
	 * 根据地图中心点和跨度获取上下左右坐标
	 * 
	 * @param point
	 *            地图中心点
	 * @param lat
	 *            上下跨度
	 * @param lon
	 *            左右跨度
	 * @return
	 */

	public static Poi mGetPoi(GeoPoint point, long lat, long log) {
		Poi poi = new Poi();
		int shangxia = (int) (lat / 2);
		int zuoyou = (int) (log / 2);
		poi.shang = (point.getLatitudeE6() + shangxia) / 1E6;
		poi.xia = (point.getLatitudeE6() - shangxia) / 1E6;
		poi.zuo = (point.getLongitudeE6() - zuoyou) / 1E6;
		poi.you = (point.getLongitudeE6() + zuoyou) / 1E6;
		return poi;
	}

	public static void listViewAnimation(final Context context,
			final ListView view) {
		Animation a = AnimationUtils.loadAnimation(context, R.anim.scale_in);
		a.setAnimationListener(new Animation.AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				view.startAnimation(AnimationUtils.loadAnimation(context,
						R.anim.scale_out));
			}
		});
		view.startAnimation(a);

	}

	public static void mToIC(Activity act) {
		SharedPreferences sp = act.getApplicationContext()
				.getSharedPreferences("userIn", Context.MODE_PRIVATE);
		if (sp.getBoolean("islogin", false)) {
			if (sp.getString("account", "") == Sources.CURRENTSHOP.bAccount) {
				// PhoneState.showToast(act, "不能对自己发起对话！");
				return;
			}
			ContactBean cbean = new ContactBean();
			cbean.contactAccount = Sources.CURRENTSHOP.bAccount;
			cbean.contactName = Sources.CURRENTSHOP.shopName + "(临时)";
			Sources.CURRENTIC = cbean;
			Skip.mNext(act, ChatActivity.class);
		} else {
			PhoneState.showToast(act, "您还未登陆！请先登陆！");
			Skip.mNext(act, LoginActivity.class, R.anim.activity_push_up_in,
					R.anim.activity_no_anim, false);
		}
	}

	/**
	 * 根据屏幕宽高来设定View的宽高 调用这个方法的使用范例： ImageView imgview = (ImageView)
	 * getDisplayMet(this,imgview, "", 0, "-", 54);
	 * 
	 * @param context
	 *            传过来的当前Activity
	 * @param view
	 *            传过来的View
	 * @param sun1
	 *            在屏幕的宽度基础上是否要加减剩除一定的长度，传String类型的"+" , "-" , "*" , "/" , ""
	 * @param width
	 *            要增减的宽度值，不修改传0
	 * @param sun2
	 *            在屏幕的高度基础上是否要加减剩除一定的长度，传String类型的"+" , "-" , "*" , "/" , "=" ,
	 *            "~" , ""
	 * @param height
	 *            要增减的高度值，不修改传0
	 * @return 返回传过来的View
	 */
	public static View getDisplayMet(Activity context, View view, String sun1,
			int width, String sun2, int height) {
		DisplayMetrics dm = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		LayoutParams para = view.getLayoutParams();
		if (sun1.equals("+")) {
			para.width = dm.widthPixels + width;
		} else if (sun1.equals("-")) {
			para.width = dm.widthPixels - width;
		} else if (sun1.equals("*")) {
			para.width = dm.widthPixels * width;
		} else if (sun1.equals("/")) {
			para.width = dm.widthPixels / width;
		} else if (sun1.equals("f")) {
			para.width = LayoutParams.FILL_PARENT;
		} else {
			para.width = dm.widthPixels;
		}
		if (sun2.equals("+")) {
			para.height = dm.widthPixels + height;
		} else if (sun2.equals("-")) {
			para.height = dm.widthPixels - height;
		} else if (sun2.equals("*")) {
			para.height = dm.widthPixels * height;
		} else if (sun2.equals("/")) {
			para.height = dm.widthPixels / height;
		} else if (sun2.equals("=")) {
			para.height = para.width;
		} else if (sun2.equals("~")) {
			para.height = para.width + height;
		} else {
			para.height = dm.widthPixels;
		}
		view.setLayoutParams(para);
		return view;
	}

	/**
	 * 按钮点击高亮效果
	 */
	public final static OnTouchListener touchLight = new OnTouchListener() {
		public final float[] BT_SELECTED = new float[] { 1, 0, 0, 0, 50, 0, 1,
				0, 0, 50, 0, 0, 1, 0, 50, 0, 0, 0, 1, 0 };
		public final float[] BT_NOT_SELECTED = new float[] { 1, 0, 0, 0, 0, 0,
				1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0 };

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				v.getBackground().setColorFilter(
						new ColorMatrixColorFilter(BT_SELECTED));
				v.setBackgroundDrawable(v.getBackground());
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				v.getBackground().setColorFilter(
						new ColorMatrixColorFilter(BT_NOT_SELECTED));
				v.setBackgroundDrawable(v.getBackground());
			}
			return false;
		}
	};

	/**
	 * 按钮点击动画
	 * 
	 * @param v
	 */
	public static void onBtnAnim(View v) {
		Animation tAnimation = new AlphaAnimation(0.1f, 1.0f);
		// Animation tAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
		// 200.0f, 0.0f);
		// 为动画设置完成所需时间
		tAnimation.setDuration(800);
		v.startAnimation(tAnimation);
	}

	/**
	 * 320图片取值
	 * 
	 * @param url
	 * @return
	 */
	public static String simllImg(String url) {
		if (url != null && !url.equals("null")) {
			String imgurl = url.substring(0, url.length() - 4);
			imgurl = imgurl + "_320.jpg";
			return imgurl;
		} else {
			return null;
		}
	}

	public static void initChut(final Activity act) {
		if (!hasShortcut(act)) {
			addShortcut(act);
		}
	}

	/**
	 * 为程序创建桌面快捷方式
	 */
	public static void addShortcut(Activity act) {
		Intent shortcut = new Intent(
				"com.android.launcher.action.INSTALL_SHORTCUT");
		// 快捷方式的名称
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME,
				act.getString(R.string.app_name));
		shortcut.putExtra("duplicate", false); // 不允许重复创建
		/*
		 * // 指定当前的Activity为快捷方式启动的对象: 如 // com.everest.video.VideoPlayer // 注意:
		 * ComponentName的第二个参数必须加上点号(.)，否则快捷方式无法启动相应程序 //
		 * 下面NewAppActivity是我这边当前的activity名字，用的时候可以适当改成你自己的activity名
		 * ComponentName comp = new ComponentName(this.getPackageName(), "." +
		 * this.getLocalClassName());
		 * shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(
		 * Intent.ACTION_MAIN).setComponent(comp));
		 */
		Intent intent = new Intent(act.getApplicationContext(),
				LoadingActivity.class);
		// 下面两个属性是为了当应用程序卸载时桌面 上的快捷方式会删除
		// intent.setAction("android.intent.action.MAIN");
		// intent.addCategory("android.intent.category.LAUNCHER");
		// 点击快捷图片，运行的程序主入口
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
		// 快捷方式的图标
		ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(
				act, R.drawable.icon);
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);
		// 发送广播。OK
		act.sendBroadcast(shortcut);
	}

	/**
	 * 判断桌面是否已添加快捷方式
	 * 
	 * @param cx
	 * @param titleName
	 *            快捷方式名称
	 * @return
	 */
	public static boolean hasShortcut(Activity act) {
		boolean result = false;
		// 获取当前应用名称
		String title = null;
		try {
			PackageManager pm = act.getPackageManager();
			title = pm.getApplicationLabel(
					pm.getApplicationInfo(act.getPackageName(),
							PackageManager.GET_META_DATA)).toString();
		} catch (Exception e) {
		}
		final String uriStr;
		if (android.os.Build.VERSION.SDK_INT < 8) {
			uriStr = "content://com.android.launcher.settings/favorites?notify=true";
		} else {
			uriStr = "content://com.android.launcher2.settings/favorites?notify=true";
		}
		final Uri CONTENT_URI = Uri.parse(uriStr);
		final Cursor c = act.getContentResolver().query(CONTENT_URI, null,
				"title=?", new String[] { title }, null);
		if (c != null && c.getCount() > 0) {
			result = true;
		}
		return result;
	}

	/**
	 * 订单状态
	 * @param num
	 * @return
	 */
	public static String getStateName(String num) {
		String name = null;
		if (num.equals("0")) {
			name = "等待付款";
		} else if (num.equals("1")) {
			name = "已付款";
		} else if (num.equals("3")) {
			name = "消费成功";
		} else if (num.equals("5")) {
			name = "退款成功";
		} else if (num.equals("6")) {
			name = "退款失败";
		} else if (num.equals("7")) {
			name = "同意退款处理中";
		} else if (num.equals("8")) {
			name = "同意退款付款中";
		} else if (num.equals("9")) {
			name = "预定订单";
		} else if (num.equals("10")) {
			name = "过期订单";
		} else if (num.equals("11")) {
			name = "正在付款";
		}
		return name;
	}

	/**
	 * 删除文件夹
	 * 
	 * @param filePathAndName
	 *            String 文件夹路径及名称 如c:/fqf
	 * @param fileContent
	 *            String
	 * @return boolean
	 */
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹

		} catch (Exception e) {
			System.out.println("删除文件夹操作出错");
			e.printStackTrace();

		}
	}

	/**
	 * 删除文件夹里面的所有文件
	 * 
	 * @param path
	 *            String 文件夹路径 如 c:/fqf
	 */
	public static void delAllFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
			}
		}
	}

	/**
	 * 以最省内存的方式读取图片
	 */
	public static Bitmap readBitmap(final String path) {
		try {
			FileInputStream stream = new FileInputStream(new File(path
					+ "test.jpg"));
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inSampleSize = 8;
			opts.inPurgeable = true;
			opts.inInputShareable = true;
			Bitmap bitmap = BitmapFactory.decodeStream(stream, null, opts);
			return bitmap;
		} catch (Exception e) {
			return null;
		}
	}

}