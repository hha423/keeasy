package com.evan.eyesight;

public class DrawObject {

	public DrawObject(float f, float f1, int i, float f2, int j) {
		rst = -1;
		x = f;
		y = f1;
		nDir = i;
		dm = f2;
		strIndex = j;
	}

	float dm;
	int nDir;
	int rst;
	int strIndex;
	float x;
	float y;
}
