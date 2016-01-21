package com.capsule.mvvm.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.capsule.mvvm.R;

/**
 * Created by 宇宙神帝 on 2014/11/21.
 */
public class LoadMoreView extends ListView implements AbsListView.OnScrollListener {

    private View moreView;

    private int lastVisibleItem;

    private boolean isLoading = false;

    private LoadMoreListener loadMoreListener;

    public LoadMoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        moreView = LayoutInflater.from(context).inflate(R.layout.load_more, null);

        addFooterView(moreView);
        setOnScrollListener(this);
        loadEnd();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (lastVisibleItem == getCount() - 1 && OnScrollListener.SCROLL_STATE_IDLE == scrollState) {
            if(loadMoreListener != null && !isLoading) {
                loadBegin();
                loadMoreListener.loadMore();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        lastVisibleItem = firstVisibleItem + visibleItemCount - 1;
    }

    public void setLoadMoreListener(LoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    public void loadBegin() {
        moreView.setVisibility(View.VISIBLE);
        isLoading = true;
    }

    public void loadEnd() {
        moreView.setVisibility(View.GONE);
        isLoading = false;
    }

    public interface LoadMoreListener {
        void loadMore();
    }
}
