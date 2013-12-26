package cn.evan.tools.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class MGridView1 extends GridView {

	public MGridView1(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * Integer.MAX_VALUE >> 2,如果不设置，系统默认设置是显示两条
	 */
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
