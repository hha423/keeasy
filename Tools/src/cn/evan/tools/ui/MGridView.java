package cn.evan.tools.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.GridView;
import android.widget.Toast;
import cn.evan.tools.base.IListView;

public class MGridView extends GridView implements OnScrollListener {

	int first;
	int visb;
	int total;
	IListView ilist;

	public MGridView(Context context) {
		super(context);
		setOnScrollListener(this);
	}

	public MGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOnScrollListener(this);
	}

	public void mListen(IListView ilist) {
		this.ilist = ilist;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (scrollState == 0 && total - visb <= first) {
			if (ilist != null)
				ilist.mListViewDataChange();
			else
				Toast.makeText(getContext(), "mListen()没有初始化!",
						Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		first = firstVisibleItem;
		visb = visibleItemCount;
		total = totalItemCount;
	}

}
