// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi lnc 

package com.evan.eyesight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.evan.eyesight.setting.Utils;

public class EyeTestView extends SurfaceView implements
		android.view.SurfaceHolder.Callback {

	private HashMap arrTest;
	private boolean bNeedReDraw;
	private DrawObject currDrawObj;
	private int currentPos;
	private float fDm[];
	private int height;
	private SurfaceHolder sfHolder;
	private DrawThread thDraw;
	private int width;

	private class DrawThread extends Thread {

		private boolean bStop;
		private Paint mPaint;
		private SurfaceHolder srufaceHolder;

		private void onDraw(Canvas canvas) {
			if (currDrawObj != null) {
				canvas.drawColor(-1);
				Utils.drawEye_E(canvas, mPaint, currDrawObj.nDir,
						currDrawObj.x, currDrawObj.y, currDrawObj.dm, true);
				bNeedReDraw = false;
			}
		}

		public void run() {
			Canvas canvas = null;
			do {
				try {
					if (bStop)
						return;
					canvas = srufaceHolder.lockCanvas(null);
					System.out.println("dr");
					if (currDrawObj != null) {
						System.out.println("f");
						canvas.drawColor(-1);
						Utils.drawEye_E(canvas, mPaint, currDrawObj.nDir,
								currDrawObj.x, currDrawObj.y, currDrawObj.dm,
								true);
						bNeedReDraw = false;
					}
				} catch (Exception e) {
					if (canvas != null)
						srufaceHolder.unlockCanvasAndPost(canvas);
					e.printStackTrace();
				} finally {
					if (canvas != null)
						srufaceHolder.unlockCanvasAndPost(canvas);
				}
			} while (!bNeedReDraw && fDm.length < 1);
		}

		public void setStop(boolean flag) {
			bStop = flag;
		}

		public DrawThread(SurfaceHolder surfaceholder) {
			super("junefsh-drw");
			bStop = false;
			srufaceHolder = surfaceholder;
			mPaint = new Paint();
			mPaint.setAntiAlias(true);
			mPaint.setStyle(android.graphics.Paint.Style.FILL);
			mPaint.setColor(0xff000000);
		}
	}

	public EyeTestView(Context context) {
		super(context);
		System.out.println("ev");
		fDm = new float[0];
		arrTest = new HashMap();
		currentPos = 0;
		bNeedReDraw = true;
		width = 240;
		height = 240;
		sfHolder = getHolder();
		sfHolder.addCallback(this);
	}

	public EyeTestView(Context context, AttributeSet attributeset) {
		super(context, attributeset);
		System.out.println("ev1");
		fDm = new float[0];
		arrTest = new HashMap();
		currentPos = 0;
		bNeedReDraw = true;
		width = 240;
		height = 240;
		sfHolder = getHolder();
		sfHolder.addCallback(this);
	}

	public EyeTestView(Context context, AttributeSet attributeset, int i) {
		super(context, attributeset, i);
		System.out.println("evi");
		fDm = new float[0];
		arrTest = new HashMap();
		currentPos = 0;
		bNeedReDraw = true;
		width = 240;
		height = 240;
		sfHolder = getHolder();
		sfHolder.addCallback(this);
	}

	private void getDrawThd() {
		if (thDraw == null)
			thDraw = new DrawThread(sfHolder);
	}

	private void updateFDM() {
		int i = fDm.length;
		currentPos = i - 1;
		int j = i - 1;
		ArrayList arraylist = (ArrayList) arrTest.get(Integer.valueOf(j));
		int ai[];
		float f1;
		float f2;
		if (arraylist == null) {
			arraylist = new ArrayList();
			arrTest.put(Integer.valueOf(j), arraylist);
		}
		byte byte0 = 0;
		float f;
		for (int k = 0; k < byte0; k++) {
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
			arraylist.add(new DrawObject(f1, f2, ai[k], fDm[j], j));
			j--;
			if (j < 0) {
				refreshEShap();
				return;
			}
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
		System.out.println("re");
		boolean flag;
		int i = 0;
		Iterator iterator;
		flag = true;
		ArrayList arraylist = (ArrayList) arrTest.get(Integer
				.valueOf(currentPos));
		if (arraylist == null)
			arraylist = new ArrayList();
		i = arraylist.size();
		iterator = arraylist.iterator();
		for (Object object : arraylist) {
			System.out.println("hh:" + object);
		}
		if (iterator.hasNext()) {
			System.out.println("ff:" + iterator);
			DrawObject drawobject = (DrawObject) iterator.next();
			if (drawobject.rst != -1)
				// break label0;
				currDrawObj = drawobject;
		}
		if (i == 0) {
			currentPos = -1 + currentPos;
			System.out.println("cp：" + currentPos);
			if (currentPos > 0)
				currDrawObj = (DrawObject) ((ArrayList) arrTest.get(Integer
						.valueOf(currentPos))).get(0);
			else
				flag = false;
		}
		if (flag)
			bNeedReDraw = true;
		i--;
		flag = false;
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
		System.out.println("1");
	}

	public void surfaceCreated(SurfaceHolder surfaceholder) {
		getDrawThd();
		thDraw.start();
		System.out.println("dd");
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
		// if(currDrawObj == null)
		// break MISSING_BLOCK_LABEL_220;
		// if(arraylist != null)
		// k = arraylist.size();
		// if(k < flag && !refreshEShap())
		// {
		// flag;
		// }
		// l = 0;
		// i1 = 0;
		// iterator = arraylist.iterator();
		// }else{
		// return flag;}
		//
		// if(iterator.hasNext()){
		// DrawObject drawobject = (DrawObject)iterator.next();
		// if(drawobject.rst == -1)
		// l++;
		// else
		// if(drawobject.rst == flag)
		// i1++;
		// if(refreshEShap())
		// j = 0;
		// else
		// j = ((flag) ? 1 : 0);
		// flag = j;
		// if(i != 4){
		// int j;
		// int k;
		// int l;
		// int i1;
		// Iterator iterator;
		// ArrayList arraylist;
		// if(currDrawObj.nDir == i)
		// currDrawObj.rst = ((flag) ? 1 : 0);
		// else
		// currDrawObj.rst = 0;
		// arraylist =
		// (ArrayList)arrTest.get(Integer.valueOf(currentPos));}else{
		// return flag;}}else{
		// if(l > 0)
		// {
		// if(refreshEShap())
		// j = 0;
		// else
		// j = ((flag) ? 1 : 0);
		// } else
		// if((i1 * 100) / k > 80)
		// {
		// if(refreshEShap())
		// j = 0;
		// else
		// j = ((flag) ? 1 : 0);
		// } else
		// {
		// j = 1;
		// }
		return flag;
	}

}
