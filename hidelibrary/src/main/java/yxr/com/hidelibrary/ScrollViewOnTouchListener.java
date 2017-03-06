package yxr.com.hidelibrary;

import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ScrollView;

public class ScrollViewOnTouchListener implements View.OnTouchListener {
    private final OnScrollStateChangedListener listener;
    private final ScrollView scrollView;
    private int scrollType = AbsListView.OnScrollListener.SCROLL_STATE_IDLE;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (listener != null)
                listener.onScrollStateChanged(scrollType);
        }
    };

    public ScrollViewOnTouchListener(OnScrollStateChangedListener listener,ScrollView scrollView){
        this.listener = listener;
        this.scrollView = scrollView;
    }

    @Override
    public boolean onTouch(View v, MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                this.scrollType = AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL;
                if(listener != null)
                    listener.onScrollStateChanged(scrollType);
                //手指在上面移动的时候   取消滚动监听线程
                mHandler.removeCallbacks(scrollRunnable);
                break;
            case MotionEvent.ACTION_UP:
                //手指移动的时候
                mHandler.post(scrollRunnable);
                break;
        }
        return v.onTouchEvent(ev);
    }
    public interface OnScrollStateChangedListener{
        void onScrollStateChanged(int state);
    }
    private int scrollDealy = 100;
    /**
     * 滚动监听runnable
     */
    private Runnable scrollRunnable = new Runnable() {
        private int currentX = Integer.MIN_VALUE;
        private int currentY = Integer.MIN_VALUE;
        @Override
        public void run() {
            if(scrollView == null)
                return;
            if(scrollView.getScrollX() == currentX || scrollView.getScrollY() == currentY){
                //滚动停止  取消监听线程
                scrollType = AbsListView.OnScrollListener.SCROLL_STATE_IDLE;
                mHandler.sendEmptyMessage(0);
                currentX = scrollView.getScrollX();
                return;
            }else{
                //手指离开屏幕    view还在滚动的时候
                scrollType = AbsListView.OnScrollListener.SCROLL_STATE_FLING;
                mHandler.sendEmptyMessage(0);
                currentX = scrollView.getScrollX();
                mHandler.postDelayed(this, scrollDealy);
            }
        }
    };

}
