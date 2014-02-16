package com.evan.eyesight.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ShouSql extends SQLiteOpenHelper {

	public ShouSql(Context context) {
		super(context, "eyeData.db", null, 2);
	}

	public void close() {
		if (db != null)
			db.close();
	}

	public void del(int i) {
		if (db == null)
			db = getWritableDatabase();
		SQLiteDatabase sqlitedatabase = db;
		String as[] = new String[1];
		as[0] = String.valueOf(i);
		sqlitedatabase.delete("ResulTbl", "_id=?", as);
	}

	public void delAll() {
		if (db == null)
			db = getWritableDatabase();
		SQLiteDatabase sqlitedatabase = db;
		sqlitedatabase.delete("ResulTbl", null, null); // 清空数据
	}

	public void insert(ContentValues contentvalues) {
		SQLiteDatabase sqlitedatabase = getWritableDatabase();
		sqlitedatabase.insert("ResulTbl", null, contentvalues);
		sqlitedatabase.close();
	}

	public void onCreate(SQLiteDatabase sqlitedatabase) {
		db = sqlitedatabase;
		sqlitedatabase
				.execSQL(" create table  ResulTbl(_id integer primary key autoincrement,leixing text,time text, left text,right text, str1 text,str2 text) ");
	}

	public void onUpgrade(SQLiteDatabase sqlitedatabase, int i, int j) {
	}

	public Cursor query() {
		return getWritableDatabase().query("ResulTbl", null, null, null, null,
				null, null);
	}

	private static final String CREATE_TBL = " create table  ResulTbl(_id integer primary key autoincrement,leixing text,time text, left text,right text,str1 text,str2 text) ";
	private static final String DB_NAME = "eyeData.db";
	private static final String TBL_NAME = "ResulTbl";
	private SQLiteDatabase db;
}
