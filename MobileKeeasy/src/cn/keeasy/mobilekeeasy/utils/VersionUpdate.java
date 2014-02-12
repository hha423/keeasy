package cn.keeasy.mobilekeeasy.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import cn.evan.tools.PhoneState;
import cn.evan.tools.RequestTask;
import cn.evan.tools.base.ITask;

import cn.keeasy.mobilekeeasy.data.Sources;

/**
 * 版本检测，自动更新
 * 
 * @author Evan 1.通过Url检测更新 2.下载并安装更新 3.删除临时路径
 * 
 */
public class VersionUpdate implements ITask {

	public Context mContext;
	public RequestTask task;
	private Handler mhandl;
	private boolean down;

	private ProgressDialog pBar;
	private int newVerCode = 0;
	private String newVerName = "";

	int percent;
	boolean bool, isUpdate;// 是否提示,是否正在更新
	String vName;// 当前版本名称
	int vCode;// 当前版本号
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				pBar.setProgress(percent);
				break;
			case 2:
				isUpdate = false;
				pBar.cancel();
				percent = 0;
				installNewApk();
				break;
			case 3:
				notNewVersionShow();
				break;
			}
		}
	};

	/**
	 * 构造方法，获得当前版本信息
	 * 
	 * @param activity
	 */
	public VersionUpdate(Context contxt, Handler mhandl) {
		down = true;
		this.mContext = contxt;
		this.mhandl = mhandl;
		task = new RequestTask(this);
		vName = PhoneState.getVerName(contxt);
		vCode = PhoneState.getVerCode(contxt);
		pBar = new ProgressDialog(mContext);
		pBar.setTitle("版本更新");
		pBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pBar.setCancelable(false);
		// 弹出警告框 提示是否安装新的版本
	}

	/**
	 * 检查更新
	 * 
	 * @param b
	 *            是否出现提示信息
	 */
	public void checkUpdate(boolean b) {
		if (isUpdate) {
			if (percent <= 0) {
				if (b) {
					notNewVersionShow();
				}
			} else
				pBar.show();
		} else {
			isUpdate = true;
			this.bool = b;
			task.mRequest(RequestTask.DOWN,
					Sources.DOWNPATH + Sources.JSONNAME, null);
		}
	}

	private void notNewVersionShow() {
		StringBuffer sb = new StringBuffer();
		sb.append("当前版本:");
		sb.append(vName);
		sb.append(" Code:");
		sb.append(vCode);
		sb.append(",\n已是最新版,无需更新!");
		Dialog dialog = new AlertDialog.Builder(mContext).setTitle("软件更新")
				.setMessage(sb.toString())
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				}).create();
		dialog.show();
		// 创建
		// 显示对话框
	}

	/**
	 * 下载app文件
	 * 
	 * @param url
	 */
	protected void downAppFile(final String url) {
		if (down) {
			down = false;
			pBar.setProgress(percent);
			pBar.show();
			new Thread() {
				public void run() {
					HttpClient client = new DefaultHttpClient();
					HttpGet get = new HttpGet(url);
					HttpResponse response;
					try {
						response = client.execute(get);
						HttpEntity entity = response.getEntity();
						long length = entity.getContentLength();
						Log.isLoggable("DownTag", (int) length);
						InputStream is = entity.getContent();
						FileOutputStream fileOutputStream = null;
						if (is == null) {
							throw new RuntimeException("isStream is null");
						}
						File file = new File(
								Environment.getExternalStorageDirectory()
										+ "/Download", Sources.APPNAME);
						if (file.exists()) {
							file.getAbsoluteFile().delete();
						}
						fileOutputStream = new FileOutputStream(file);
						byte[] buf = new byte[1024];
						int ch = -1;
						int count = 0;
						do {
							ch = is.read(buf);
							if (ch <= 0)
								break;
							fileOutputStream.write(buf, 0, ch);
							count += ch;
							if (length > 0) {
								percent = (int) (count * 100 / length);
								handler.sendEmptyMessage(1);
							}
						} while (true);
						is.close();
						fileOutputStream.close();
						System.gc();
						handler.sendEmptyMessage(2);
					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}.start();
		} else {
			installNewApk();
		}
	}

	// 安装新的应用
	protected void installNewApk() {
		handler.post(new Runnable() {
			public void run() {
				if (PhoneState.runRootCommand("echo test")) {
					if (PhoneState.installFile(
							new File(Environment.getExternalStorageDirectory()
									+ "/Download", Sources.APPNAME), mContext)) {
						PhoneState.showToast(mContext, "安装成功");
					} else {
						PhoneState.showToast(mContext, "安装失败请重试!");
					}
				} else {
					PhoneState.installApk(mContext,
							new File(Environment.getExternalStorageDirectory()
									+ "/Download", Sources.APPNAME));
				}
			}
		});
	}

	@Override
	public void mTaskError(String arg0) {
		PhoneState.showToast(mContext, "网络连接超时，请重试！");
	}

	@Override
	public void mTaskSuccess(String arg0) {
		try {
			JSONArray jsonArray = new JSONArray(arg0);

			if (jsonArray.length() > 0) {
				JSONObject obj = jsonArray.getJSONObject(0);
				newVerCode = Integer.parseInt(obj.getString("verCode"));
				newVerName = obj.getString("verName");
				int flag = obj.getInt("flag");
				pBar.setMessage("正在下载更新，请稍候...\n" + "更新项：\n" + newVerName);
				if (vCode < newVerCode) {
					mhandl.sendEmptyMessage(0);
					if (flag == 0) {
						downAppFile(Sources.DOWNPATH + Sources.APPNAME);
					} else {
						showUpdateDialog();
					}
				} else {
					mhandl.sendEmptyMessage(1);
					if (bool) {
						handler.sendEmptyMessage(3);// 提示当前为最新版本
					}
				}
			}
		} catch (Exception e) {
			newVerCode = -1;
			e.printStackTrace();
			// newVerName = "";
		}
	}

	// show Update Dialog
	private void showUpdateDialog() throws NameNotFoundException {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append("有新版本:");
		sb.append(newVerName);
		sb.append(" NewVCode:");
		sb.append(newVerCode);
		sb.append("\n");
		sb.append("更新项：\n" + newVerName);
		sb.append("\n");
		sb.append("是否更新？");
		Dialog dialog = new AlertDialog.Builder(mContext)
				.setTitle("软件更新")
				.setMessage(sb.toString())
				.setPositiveButton("现在更新",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								downAppFile(Sources.DOWNPATH + Sources.APPNAME);// 更新当前版本
							}
						})
				.setNegativeButton("暂不更新",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								// 点击"取消"按钮之后退出程序
								isUpdate = false;
								dialog.dismiss();
							}
						}).create();
		dialog.setCancelable(false);
		dialog.show();
	}
}