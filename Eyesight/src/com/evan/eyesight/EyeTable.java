// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi lnc 

package com.evan.eyesight;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.evan.eyesight.setting.Utils;

// Referenced classes of package com.junefsh.app.eyeguard.free:
//            Utils, DrawObject

class EyeTable extends View {

	public EyeTable(Context context) {
		super(context);
		Max_Hight_E = 480F;
		Max_Width_E = 320F;
		bNeedIni = true;
		currentScreenWidth = 320;
		currentScreenHeight = 480;
		relativePosx = 0.0F;
		relativePosy = 0.0F;
		refeStr = "Reference";
		eyeGuardStr = "Eye E Tables";
		init(context);
	}

	public EyeTable(Context context, AttributeSet attributeset) {
		super(context, attributeset);
		Max_Hight_E = 480F;
		Max_Width_E = 320F;
		bNeedIni = true;
		currentScreenWidth = 320;
		currentScreenHeight = 480;
		relativePosx = 0.0F;
		relativePosy = 0.0F;
		refeStr = "Reference";
		eyeGuardStr = "Eye E Tables";
		init(context);
	}

	private void init(Context context) {
		Resources resources = getResources();
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setStyle(android.graphics.Paint.Style.FILL);
		mPaint.setColor(0xff000000);
		refeStr = resources.getString(0x7f070004);
		eyeGuardStr = resources.getString(0x7f070005);
		myDrawObject = new ArrayList();
		strEyeTab = resources.getStringArray(0x7f050000);
		float af[] = new float[23];
		af[0] = resources.getDimension(0x7f060016);
		af[1] = resources.getDimension(0x7f060015);
		af[2] = resources.getDimension(0x7f060014);
		af[3] = resources.getDimension(0x7f060013);
		af[4] = resources.getDimension(0x7f060012);
		af[5] = resources.getDimension(0x7f060011);
		af[6] = resources.getDimension(0x7f060010);
		af[7] = resources.getDimension(0x7f06000f);
		af[8] = resources.getDimension(0x7f06000e);
		af[9] = resources.getDimension(0x7f06000d);
		af[10] = resources.getDimension(0x7f06000c);
		af[11] = resources.getDimension(0x7f06000b);
		af[12] = resources.getDimension(0x7f06000a);
		af[13] = resources.getDimension(0x7f060009);
		af[14] = resources.getDimension(0x7f060008);
		af[15] = resources.getDimension(0x7f060007);
		af[16] = resources.getDimension(0x7f060006);
		af[17] = resources.getDimension(0x7f060005);
		af[18] = resources.getDimension(0x7f060004);
		af[19] = resources.getDimension(0x7f060003);
		af[20] = resources.getDimension(0x7f060002);
		af[21] = resources.getDimension(0x7f060001);
		af[22] = resources.getDimension(0x7f060000);
		fDimen = af;
		int i = fDimen.length;
		min_row_d = resources.getDimension(0x7f060017);
		nEye_Table_W = (3 * currentScreenWidth) / 4;
		int j = 0;
		do {
			if (j >= i)
				return;
			float f = Utils.formatDm(fDimen[j]);
			fDimen[j] = f;
			j++;
		} while (true);
	}

	public float[] getEyeDm() {
		return fDimen;
	}

	public String getTestResult(int i) {
		String s;
		if (i > 0 && i < fDimen.length)
			s = strEyeTab[i];
		else
			s = strEyeTab[-1 + strEyeTab.length];
		return s;
	}

	public void initDraw() {
		float f;
		int j;
		if (myDrawObject == null)
			myDrawObject = new ArrayList();
		int i = fDimen.length;
		f = 50F;
		j = i - 1;
		int ai[];
		float f1;
		float f3;
		int l;
		if (j < 0) {
			if (30F + nEye_Table_W > Max_Width_E)
				Max_Width_E = 30F + nEye_Table_W;
			if (50F + f > Max_Hight_E)
				Max_Hight_E = 50F + f;
			return;
		}
		int k;
		float f2;
		DrawObject drawobject;
		if (j < 5)
			k = 8;
		else if (j < 6)
			k = 7;
		else if (j < 7)
			k = 6;
		else if (j < 8)
			k = 5;
		else if (j < 11)
			k = 4;
		else if (j < 15)
			k = 3;
		else if (j < 19)
			k = 2;
		else
			k = 1;
		ai = Utils.getRandom_serial(k);
		f1 = nEye_Table_W / (float) k;
		f2 = Utils.formatValidDm(5F * fDimen[j]);
		f3 = Utils.formatValidDm((f1 - f2) / 2.0F);
		f = Utils.formatValidDm(f2 + (f + min_row_d));
		l = 0;
		label0: {
			if (l < k)
				break label0;
			drawobject = new DrawObject(5F + nEye_Table_W,
					Utils.formatValidDm(f - f2 / 2.0F), 4, fDimen[j], j);
			myDrawObject.add(drawobject);
			j--;
		}
		DrawObject drawobject1 = new DrawObject(Utils.formatValidDm(f3 + f1
				* (float) l), f, ai[l], fDimen[j], j);
		myDrawObject.add(drawobject1);
		l++;
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(-1);
		canvas.drawText(eyeGuardStr,
				5F + ((nEye_Table_W - 50F) / 2.0F - relativePosx),
				20F - relativePosy, mPaint);
		canvas.drawLine(nEye_Table_W - relativePosx, 0.0F, nEye_Table_W
				- relativePosx, Max_Hight_E, mPaint);
		canvas.drawText(refeStr, 5F + (nEye_Table_W - relativePosx),
				20F - relativePosy, mPaint);
		// if(myDrawObject == null)
		Iterator iterator = myDrawObject.iterator();
		if (iterator.hasNext())
			return;
		DrawObject drawobject = (DrawObject) iterator.next();
		if (drawobject.nDir == 4)
			canvas.drawText(strEyeTab[drawobject.strIndex], drawobject.x
					- relativePosx, drawobject.y - relativePosy, mPaint);
		else
			Utils.drawEye_E(canvas, mPaint, drawobject.nDir, drawobject.x
					- relativePosx, drawobject.y - relativePosy, drawobject.dm,
					false);
	}

	protected void onSizeChanged(int i, int j, int k, int l) {
		super.onSizeChanged(i, j, k, l);
		currentScreenHeight = j;
		currentScreenWidth = i;
		Max_Hight_E = j;
		Max_Width_E = i;
		nEye_Table_W = (3 * currentScreenWidth) / 4;
		if (bNeedIni)
			initDraw();
	}

	public boolean onTouchEvent(MotionEvent motionevent) {
		float f;
		float f1;
		f = motionevent.getX();
		f1 = motionevent.getY();
		motionevent.getAction();
		mPreviousX = f;
		mPreviousY = f1;
		// continue; /* Loop/switch isn't completed */
		float f2;
		float f3;
		boolean flag;
		f2 = f - mPreviousX;
		f3 = f1 - mPreviousY;
		flag = false;
		if (f2 > 0.0F) {
			if (Math.abs(f3 / f2) < 1.0F && relativePosx > 0.0F) {
				if (relativePosx < 28F)
					relativePosx = 0.0F;
				else
					relativePosx = relativePosx - 28F;
				flag = true;
			}
		} else if (f2 < 0.0F && Math.abs(f3 / f2) < 1.0F
				&& (float) currentScreenWidth + relativePosx < Max_Width_E) {
			float f4 = Max_Width_E - relativePosx - (float) currentScreenWidth;
			if (f4 < 28F)
				relativePosx = f4 + relativePosx;
			else
				relativePosx = 28F + relativePosx;
			flag = true;
		}
		if (f3 <= 0.0F)
			// break; /* Loop/switch isn't completed */
			if (Math.abs(f2 / f3) < 1.0F && relativePosy > 0.0F) {
				if (relativePosy < 28F)
					relativePosy = 0.0F;
				else
					relativePosy = relativePosy - 28F;
				flag = true;
			}
		if (flag) {
			mPreviousX = f;
			mPreviousY = f1;
			invalidate();
		}
		if (f3 >= 0.0F || Math.abs(f2 / f3) >= 1.0F
				|| (float) currentScreenHeight + relativePosy >= Max_Hight_E)
			;
		float f5 = Max_Hight_E - relativePosy - (float) currentScreenHeight;
		if (f5 < 28F)
			relativePosy = f5 + relativePosy;
		else
			relativePosy = 28F + relativePosy;
		return flag = true;
	}

	public void scroolScreen(boolean flag) {
		if (!flag) {
			if (relativePosy > 0.0F)
				if (relativePosy < 28F)
					relativePosy = 0.0F;
				else
					relativePosy = relativePosy - 28F;
		} else {
			if ((float) currentScreenHeight + relativePosy < Max_Hight_E) {
				float f = Max_Hight_E - relativePosy
						- (float) currentScreenHeight;
				if (f < 28F)
					relativePosy = f + relativePosy;
				else
					relativePosy = 28F + relativePosy;
			}
		}
		invalidate();
		return;
	}

	public void setBNeedIni(boolean flag) {
		bNeedIni = flag;
	}

	private static final int STEP_MOVE = 28;
	private float Max_Hight_E;
	private float Max_Width_E;
	private boolean bNeedIni;
	private int currentScreenHeight;
	private int currentScreenWidth;
	private String eyeGuardStr;
	private float fDimen[];
	private Paint mPaint;
	private float mPreviousX;
	private float mPreviousY;
	private float min_row_d;
	private ArrayList myDrawObject;
	private float nEye_Table_W;
	private String refeStr;
	private float relativePosx;
	private float relativePosy;
	private String strEyeTab[];
}
