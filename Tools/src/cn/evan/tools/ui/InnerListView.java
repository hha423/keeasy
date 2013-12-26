package cn.evan.tools.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;
import android.widget.ScrollView;

public class InnerListView extends ListView {

	private ScrollView parentScrollView;

	public InnerListView(Context context) {
		super(context);
	}

	public InnerListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public InnerListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public ScrollView getParentScrollView() {
		return parentScrollView;
	}

	public void setParentScrollView(ScrollView parentScrollView) {
		this.parentScrollView = parentScrollView;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {

		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (!isStackFromBottom()) {
				setParentScrollAble(false);// 当手指触到listview的时候，让父ScrollView交出ontouch权限，也就是让父scrollview
				// 停住不能滚动
			}
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_UP:

		case MotionEvent.ACTION_CANCEL:
			setParentScrollAble(true); // 当手指松开时，让父ScrollView重新拿到onTouch权限
			break;
		default:
			break;
		}
		return super.onInterceptTouchEvent(ev);
	}

	/**
	 * 是否把滚动事件交给父scrollview
	 * 
	 * @param flag
	 */
	private void setParentScrollAble(boolean flag) {
		parentScrollView.requestDisallowInterceptTouchEvent(!flag);// 这里的parentScrollView就是listview外面的那个scrollview
	}

}
