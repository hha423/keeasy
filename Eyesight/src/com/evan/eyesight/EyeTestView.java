// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi lnc 

package com.evan.eyesight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.evan.eyesight.setting.DrawObject;
import com.evan.eyesight.setting.Utils;

public class EyeTestView extends SurfaceView implements
		android.view.SurfaceHolder.Callback {

	private HashMap<Integer, ArrayList<DrawObject>> arrTest;
	private boolean bNeedReDraw;
	private DrawObject currDrawObj;
	private int currentPos;
	private float fDm[];
	private int height;
	private DrawThread thDraw;
	private SurfaceHolder sfHolder;
	private int width;

	@SuppressLint("UseSparseArrays")
	public EyeTestView(Context context) {
		super(context);
		fDm = new float[0];
		arrTest = new HashMap<Integer, ArrayList<DrawObject>>();
		currentPos = 0;
		bNeedReDraw = true;
		width = 240;
		height = 240;
		sfHolder = getHolder();
		sfHolder.addCallback(this);
	}

	@SuppressLint("UseSparseArrays")
	public EyeTestView(Context context, AttributeSet attributeset) {
		super(context, attributeset);
		fDm = new float[0];
		arrTest = new HashMap<Integer, ArrayList<DrawObject>>();
		currentPos = 0;
		bNeedReDraw = true;
		width = 240;
		height = 240;
		sfHolder = getHolder();// 获取holder
		sfHolder.addCallback(this);
	}

	@SuppressLint("UseSparseArrays")
	public EyeTestView(Context context, AttributeSet attributeset, int i) {
		super(context, attributeset, i);
		fDm = new float[0];
		arrTest = new HashMap<Integer, ArrayList<DrawObject>>();
		currentPos = 0;
		bNeedReDraw = true;
		width = 240;
		height = 240;
		sfHolder = getHolder();
		sfHolder.addCallback(this);
	}

	private class DrawThread extends Thread {
		private boolean bStop;
		private Paint mPaint;
		private SurfaceHolder srufaceHolder;

		public DrawThread(SurfaceHolder surfaceholder) {
			bStop = false;
			srufaceHolder = surfaceholder;
			mPaint = new Paint();
			mPaint.setAntiAlias(true);
			mPaint.setStyle(android.graphics.Paint.Style.FILL);
			mPaint.setColor(0xff000000);
		}

		public void oDraw(Canvas canvas) {
			if (currDrawObj != null) {
				canvas.drawColor(-1);
				Utils.drawEye_E(canvas, mPaint, currDrawObj.nDir,
						currDrawObj.x, currDrawObj.y, currDrawObj.dm, true);
				bNeedReDraw = false;
			}
		}

		@Override
		public void run() {
			Canvas canvas = null;
			do {
				if (bStop)
					return;
				canvas = srufaceHolder.lockCanvas(null);
				oDraw(canvas);
				if (canvas != null)
					srufaceHolder.unlockCanvasAndPost(canvas);
			} while (!bNeedReDraw && fDm.length > 1);
		}

		public void setStop(boolean flag) {
			bStop = flag;
		}

	}

	private void getDrawThd() {
		if (thDraw == null)
			thDraw = new DrawThread(sfHolder);
	}

	private void updateFDM() {
		int i = fDm.length;
		currentPos = i - 1;
		ArrayList<DrawObject> arraylist = null;
		int ai[];
		float f1;
		float f2;
		byte byte0;
		float f;
		int j = i - 1;
		while (true) {
			if (j < 0) {
				refreshEShap();
				return;
			}
			arraylist = (ArrayList<DrawObject>) arrTest.get(Integer.valueOf(j));
			if (arraylist == null) {
				arraylist = new ArrayList<DrawObject>();
			}
			arrTest.put(Integer.valueOf(j), arraylist);
			if (j < 5)
				byte0 = 8;
			else if (j < 6)
				byte0 = 7;
			else if (j < 7)
				byte0 = 6;
			else if (j < 8)
				byte0 = 5;
			else if (j < 11)
				byte0 = 4;
			else if (j < 15)
				byte0 = 3;
			else if (j < 19)
				byte0 = 2;
			else
				byte0 = 1;
			ai = Utils.getRandom_serial(byte0);
			f = Utils.formatValidDm(5F * fDm[j]);
			f1 = Utils.formatValidDm(((float) width - f) / 2.0F);
			if (f1 < 0.0F)
				f1 = 0.0F;
			f2 = Utils.formatValidDm(((float) height - f) / 2.0F);
			if (f2 < 0.0F)
				f2 = 0.0F;
			for (int k = 0; k < byte0; k++) {
				arraylist.add(new DrawObject(f1, f2, ai[k], fDm[j], j));
			}
			j--;
		}
	}

	public int getCurPos() {
		return 1 + currentPos;
	}

	protected void onSizeChanged(int i, int j, int k, int l) {
		super.onSizeChanged(i, j, k, l);
		width = i;
		height = j;
		updateFDM();
	}

	public boolean refreshEShap() {
		boolean flag;
		flag = true;
		
		return flag;
	}

	public void setFDm(float af[]) {
		fDm = af;
	}

	public void shutDown() {
		boolean flag;
		flag = true;
		thDraw.setStop(true);
		if (!flag)
			return;
		try {
			thDraw.join();
			flag = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void surfaceChanged(SurfaceHolder surfaceholder, int i, int j, int k) {
	}

	public void surfaceCreated(SurfaceHolder surfaceholder) {
		getDrawThd();
		thDraw.start();
	}

	public void surfaceDestroyed(SurfaceHolder surfaceholder) {
		boolean flag;
		flag = true;
		thDraw.setStop(true);
		if (!flag) {
			if (thDraw != null)
				thDraw = null;
			return;
		}
		try {
			thDraw.join();
			flag = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public boolean testIsOver(int i) {
		boolean flag;
		flag = true;
		if (i != 4) {

			if (currDrawObj.nDir == i) {
				flag = false;
			} else {
				return flag;
			}
		}
		currentPos--;
		refreshEShap();
		return flag;
	}
}
