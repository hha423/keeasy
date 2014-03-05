package com.evan.eyesight;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.evan.eyesight.adapter.JiluAdapter;
import com.evan.eyesight.dao.DataBean;
import com.evan.eyesight.dao.ShouSql;
import com.evan.eyesight.setting.Tools;

public class JiluActivity extends BaseActivity {

	private Button button_clean;
	private Button button_share;
	private Button button_export;
	private ListView jilu_listview;
	private TextView ltext, del;
	private LinearLayout layout;
	private JiluAdapter adapter;
	private ShouSql sqlite;
	private Cursor cr;
	private List<DataBean> dbean = new ArrayList<DataBean>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.jilu_main);
		super.onCreate(savedInstanceState);
	}

	@Override
	void initView() {
		super.initView();
		ltext = (TextView) findViewById(R.id.ltext);
		del = (TextView) findViewById(R.id.del);
		layout = (LinearLayout) findViewById(R.id.layout);
		button_clean = (Button) findViewById(R.id.button_clean);
		button_share = (Button) findViewById(R.id.button_share);
		button_export = (Button) findViewById(R.id.button_export);
		jilu_listview = (ListView) findViewById(R.id.jilu_listview);
	}

	@Override
	void initData() {
		text.setText("测试记录查看");
		sqlite = new ShouSql(getApplicationContext());
		cr = sqlite.query();
		while (cr.moveToNext()) {
			DataBean bean = new DataBean();
			bean.id = cr.getInt(cr.getColumnIndex("_id"));
			bean.time = cr.getString(cr.getColumnIndex("time"));
			bean.type = cr.getString(cr.getColumnIndex("leixing"));
			bean.left = cr.getString(cr.getColumnIndex("left"));
			bean.right = cr.getString(cr.getColumnIndex("right"));
			bean.point = cr.getString(cr.getColumnIndex("str1"));
			bean.result = cr.getString(cr.getColumnIndex("str2"));
			dbean.add(bean);
		}
		if (dbean.size() < 1) {
			ltext.setVisibility(View.VISIBLE);
			jilu_listview.setVisibility(View.GONE);
			layout.setVisibility(View.GONE);
			del.setVisibility(View.GONE);
		} else {
			ltext.setVisibility(View.GONE);
			jilu_listview.setVisibility(View.VISIBLE);
			layout.setVisibility(View.VISIBLE);
			del.setVisibility(View.VISIBLE);
		}
		adapter = new JiluAdapter(this, dbean);
		jilu_listview.setAdapter(adapter);
		jilu_listview.setSelection(dbean.size());
	}

	@Override
	void initListen() {
		super.initListen();
		button_clean.setOnClickListener(this);
		button_clean.setOnTouchListener(Tools.touchLight);
		button_share.setOnClickListener(this);
		button_share.setOnTouchListener(Tools.touchLight);
		button_export.setOnClickListener(this);
		button_export.setOnTouchListener(Tools.touchLight);
		jilu_listview.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				sqlite.del(dbean.get(arg2).id);
				dbean.remove(arg2);
				adapter.notifyDataSetChanged();
				Toast.makeText(JiluActivity.this, "删除成功", Toast.LENGTH_SHORT)
						.show();
				return false;
			}
		});
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.button_clean:
			new AlertDialog.Builder(JiluActivity.this).setMessage("是否清空数据")
					.setPositiveButton("清空", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							sqlite.delAll();
							dbean.clear();
							adapter.notifyDataSetChanged();
							Toast.makeText(JiluActivity.this, "已清空",
									Toast.LENGTH_SHORT).show();
						}
					}).setNegativeButton("取消", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

						}
					}).create().show();
			break;
		case R.id.button_share:
			Toast.makeText(JiluActivity.this, "此功能待集成", Toast.LENGTH_SHORT)
					.show();
			break;
		case R.id.button_export:
			if (dbean.size() > 0) {
				toExcel();
				Toast.makeText(JiluActivity.this, "记录已保存到sd卡根目录",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(JiluActivity.this, "暂无记录，无法导出",
						Toast.LENGTH_SHORT).show();
			}
			break;
		}
	}

	private void toExcel() {
		// 新建Excel文件
		File myFilePath = new File(Tools.getFilePath(""), "视力结果.xls");
		try {
			if (myFilePath.exists())
				myFilePath.delete();
			myFilePath.createNewFile();
			// 用JXL向新建的文件中添加内容
			OutputStream outf = new FileOutputStream(myFilePath);
			jxl.write.WritableWorkbook wwb = Workbook.createWorkbook(outf);
			jxl.write.WritableSheet ws = wwb.createSheet("视力表", 0);
			if (dbean != null) {
				for (int i = 0; i < dbean.size(); i++) {
					if (i == 0) {
						ws.addCell(new Label(0, i, "" + "测试日期"));
						ws.addCell(new Label(1, i, "" + "测试项目"));
					}
					Label time = new Label(0, i + 1, "" + dbean.get(i).time);
					ws.addCell(time);
					if (dbean.get(i).type != null) {
						Label type = null;
						if (dbean.get(i).type.equals("jinshi")) {
							type = new Label(1, i + 1, "视力测试");
						} else if (dbean.get(i).type.equals("semang")) {
							type = new Label(1, i + 1, "色盲测试");
						}
						ws.addCell(type);
					}
					if (dbean.get(i).left != null) {
						Label left = new Label(2, i + 1, "左眼视力："
								+ dbean.get(i).left);
						ws.addCell(left);
					}
					if (dbean.get(i).point != null) {
						Label str1 = null;
						if (dbean.get(i).type.equals("jinshi")) {
							str1 = new Label(3, i + 1, "视力情况："
									+ dbean.get(i).point);
						} else if (dbean.get(i).type.equals("semang")) {
							str1 = new Label(2, i + 1, "辨色分数："
									+ dbean.get(i).point);
						}
						ws.addCell(str1);
					}
					if (dbean.get(i).right != null) {
						Label right = new Label(4, i + 1, "左眼视力："
								+ dbean.get(i).right);
						ws.addCell(right);
					}
					if (dbean.get(i).result != null) {
						Label str2 = null;
						if (dbean.get(i).type.equals("jinshi")) {
							str2 = new Label(5, i + 1, "视力情况："
									+ dbean.get(i).result);
						} else if (dbean.get(i).type.equals("semang")) {
							str2 = new Label(3, i + 1, "辨色结果："
									+ dbean.get(i).result);
						}
						ws.addCell(str2);
					}
				}
			}
			wwb.write();
			wwb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onDestroy() {
		sqlite.close();
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}

}
