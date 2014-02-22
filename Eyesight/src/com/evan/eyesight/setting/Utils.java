package com.evan.eyesight.setting;

import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Utils {

	public static boolean bMustDrawMid = false;

	public static void drawEye_E(Canvas canvas, Paint paint, int i, float f,
			float f1, float f2, boolean flag) {
		if (canvas != null && paint != null) {
			boolean flag1;
			if (bMustDrawMid)
				flag1 = true;
			else if ((double) f2 > 0.59999999999999998D)
				flag1 = true;
			else
				flag1 = false;
			System.out.println("进来了");
			if (flag) {
				switch (i) {
				default:
					canvas.drawRect(f, f1, f + f2, f1 + 5F * f2, paint);
					canvas.drawRect(f + f2, f1, f + 5F * f2, f1 + f2, paint);
					if (flag1)
						canvas.drawRect(f + f2, f1 + 2.0F * f2, f + 5F * f2, f1
								+ 3F * f2, paint);
					canvas.drawRect(f + f2, f1 + 4F * f2, f + 5F * f2, f1 + 5F
							* f2, paint);
					break;

				case 0: // '\0'
					canvas.drawRect(f, f1, f + f2, f1 + 4F * f2, paint);
					if (flag1)
						canvas.drawRect(f + 2.0F * f2, f1, f + 3F * f2, f1 + 4F
								* f2, paint);
					canvas.drawRect(f + 4F * f2, f1, f + 5F * f2, f1 + 4F * f2,
							paint);
					canvas.drawRect(f, f1 + 4F * f2, f + 5F * f2, f1 + 5F * f2,
							paint);
					break;

				case 2: // '\002'
					canvas.drawRect(f, f1, f + 5F * f2, f1 + f2, paint);
					canvas.drawRect(f, f1 + f2, f + f2, f1 + 5F * f2, paint);
					if (flag1)
						canvas.drawRect(f + 2.0F * f2, f1 + f2, f + 3F * f2, f1
								+ 5F * f2, paint);
					canvas.drawRect(f + 4F * f2, f1 + f2, f + 5F * f2, f1 + 5F
							* f2, paint);
					break;

				case 1: // '\001'
					canvas.drawRect(f, f1, f + 4F * f2, f1 + f2, paint);
					if (flag1)
						canvas.drawRect(f, f1 + 2.0F * f2, f + 4F * f2, f1 + 3F
								* f2, paint);
					canvas.drawRect(f, f1 + 4F * f2, f + 4F * f2, f1 + 5F * f2,
							paint);
					canvas.drawRect(f + 4F * f2, f1, f + 5F * f2, f1 + 5F * f2,
							paint);
					break;
				}
			} else {
				switch (i) {
				default:
					canvas.drawRect(f, f1 - 5F * f2, f + f2, f1, paint);
					canvas.drawRect(f + f2, f1 - f2, f + 5F * f2, f1, paint);
					if (flag1)
						canvas.drawRect(f + f2, f1 - 3F * f2, f + 5F * f2, f1
								- 2.0F * f2, paint);
					canvas.drawRect(f + f2, f1 - 5F * f2, f + 5F * f2, f1 - 4F
							* f2, paint);
					break;

				case 0: // '\0'
					canvas.drawRect(f, f1 - f2, f + 5F * f2, f1, paint);
					canvas.drawRect(f, f1 - 5F * f2, f + f2, f1 - f2, paint);
					if (flag1)
						canvas.drawRect(f + 2.0F * f2, f1 - 5F * f2, f + 3F
								* f2, f1 - f2, paint);
					canvas.drawRect(f + 4F * f2, f1 - 5F * f2, f + 5F * f2, f1
							- f2, paint);
					break;

				case 2: // '\002'
					canvas.drawRect(f, f1 - 4F * f2, f + f2, f1, paint);
					if (flag1)
						canvas.drawRect(f + 2.0F * f2, f1 - 4F * f2, f + 3F
								* f2, f1, paint);
					canvas.drawRect(f + 4F * f2, f1 - 4F * f2, f + 5F * f2, f1,
							paint);
					canvas.drawRect(f, f1 - 5F * f2, f + 5F * f2, f1 - 4F * f2,
							paint);
					break;

				case 1: // '\001'
					canvas.drawRect(f, f1 - f2, f + 4F * f2, f1, paint);
					if (flag1)
						canvas.drawRect(f, f1 - 3F * f2, f + 4F * f2, f1 - 2.0F
								* f2, paint);
					canvas.drawRect(f, f1 - 5F * f2, f + 4F * f2, f1 - 4F * f2,
							paint);
					canvas.drawRect(f + 4F * f2, f1 - 5F * f2, f + 5F * f2, f1,
							paint);
					break;
				}
			}
		}
	}

	public static float formatDm(float f) {
		return (float) Math.round(100F * ((f * 1000F) / 5F / 1000F)) / 100F;
	}

	public static float formatValidDm(float f) {
		return (float) Math.round(f * 100F) / 100F;
	}

	public static final int[] getRandom_serial(int i) {
		int ai[] = new int[i];
		ai[0] = (int) (100D * Math.random()) % 4;
		// 0 69
		// 1 92
		// 2 117;
		int l1 = 0;
		// if(l1 < i)
		Random random;
		int k;
		random = new Random();
		k = i - 1;
		if (k <= 0)
			return ai;
		int k1 = 0;
		while (k1 < i) {
			ai[k1] = k1 % 4;
			k1++;
		}
		int j1 = 0;
		while (j1 < i) {
			ai[j1] = (j1 + 1) % 4;
			j1++;
		}
		int j = 0;
		while (j < i) {
			ai[j] = (j + 2) % 4;
			j++;
		}
		ai[l1] = (l1 + 3) % 4;
		l1++;
		int l = random.nextInt(k);
		int i1 = ai[k];
		ai[k] = ai[l];
		ai[l] = i1;
		k--;
		return ai;
	}

}
