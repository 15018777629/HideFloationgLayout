package yxr.com.hidelibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class HideLayout extends RelativeLayout implements AbsListView.OnScrollListener, ScrollViewOnTouchListener.OnScrollStateChangedListener {
    private int mPosition;
    private View mView;
    private AnimationHelper animationHelper;
    private boolean mIsFirst = true;
    private int leftMargin;
    private int topMargin;
    private int rightMargin;
    private int bottomMargin;
    private int width;
    private int height;
    private OnClickListener listener;

    public HideLayout(Context context) {
        this(context,null);
    }

    public HideLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HideLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if(attrs != null){
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.HideLayout);
            mPosition = typedArray.getInt(R.styleable.HideLayout_position,0);
        }else{
            mPosition = 0;
        }
        animationHelper = new AnimationHelper();
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(R.drawable.add);
        mView = imageView;
    }

    /**
     * 初始化在AbsListView 或 ScrollView 滚动时可以隐藏的控件
     */
    public void initView() {
        if(mView == null)
            return;
        removeView(mView);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                width == 0 ? LayoutParams.WRAP_CONTENT : width,
                height == 0 ? LayoutParams.WRAP_CONTENT : height);
        switch (mPosition){
            case 0:                 // left
                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                params.addRule(RelativeLayout.CENTER_VERTICAL);
                break;
            case 1:                 // left_top
                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                break;
            case 2:                 // top
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                break;
            case 3:                 // right_top
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                break;
            case 4:                 // right
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                params.addRule(RelativeLayout.CENTER_VERTICAL);
                break;
            case 5:                 // right_bottom
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                break;
            case 6:                 // bottom
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                break;
            case 7:                 // left_bottom
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                break;
        }
        params.leftMargin = leftMargin;
        params.topMargin = topMargin;
        params.rightMargin = rightMargin;
        params.bottomMargin = bottomMargin;
        mView.setLayoutParams(params);
        addView(mView);
        setFloatingClickListener(listener);
    }

    /**
     * 遍历所有的ViewGroup,获取第一个AbsListView或ScrollView
     * 然后给他们设置滑动状态监听事件
     * 根据滑动状态显示或隐藏这个控件
     */
    private void checkChilsView(ViewGroup viewGroup){
        if(viewGroup instanceof AbsListView){
            AbsListView absListView = (AbsListView) viewGroup;
            absListView.setOnScrollListener(this);
            return;
        }
        if(viewGroup instanceof ScrollView){
            ScrollView scrollView = (ScrollView) viewGroup;
            scrollView.setOnTouchListener(new ScrollViewOnTouchListener(this,scrollView));
            return;
        }
        int count = viewGroup.getChildCount();
        for(int i = 0 ; i < count ; i++){
            View childAt = viewGroup.getChildAt(i);
            if(childAt == null || !(childAt instanceof ViewGroup))
                continue;
            checkChilsView((ViewGroup) childAt);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(mIsFirst){
            mIsFirst = false;
            initView();
            checkChilsView(this);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        onScrollStateChanged(scrollState);
    }
    @Override
    public void onScrollStateChanged(int state) {
        switch (state){
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                showView();
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                hideView();
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                hideView();
                break;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    }

    /**
     * 隐藏控件
     */
    private void hideView() {
        if(animationHelper != null)
            animationHelper.setScaleAnimationOut(mView);
    }

    /**
     * 显示控件
     */
    private void showView() {
        if(animationHelper != null)
            animationHelper.setScaleAnimationIn(mView);
    }

    /**
     * 设置Margin
     * @param left ： 左边距
     * @param top ： 上边距
     * @param right ： 右边距
     * @param bottom : 下边距
     */
    public void setFloatingMargin(int left, int top, int right, int bottom){
        this.leftMargin = left;
        this.topMargin = top;
        this.rightMargin = right;
        this.bottomMargin = bottom;
        initView();
    }

    /**
     * 设置该控件的宽高
     * @param width ： 控件宽
     * @param height ： 控件高
     */
    public void setFloatingWidthHeight(int width,int height){
        this.width = width;
        this.height = height;
        initView();
    }

    /**
     * 设置在AbsListView 或 ScrollView 滚动时可以隐藏的控件
     * @param mView : 控件
     */
    public void setFloatingView(View mView){
        this.mView = mView;
        initView();
    }

    /**
     * 设置图片
     * 如果该控件为ImageView时，该方法才会起作用
     * @param res : 图片资源
     */
    public void setFloatingRes(int res){
        if(mView == null || !(mView instanceof ImageView))
            return;
        ImageView imageView = (ImageView) mView;
        imageView.setImageResource(res);
    }

    /**
     * 设置控件点击事件
     * @param listener
     */
    public void setFloatingClickListener(OnClickListener listener) {
        this.listener = listener;
        if(mView != null)
            mView.setOnClickListener(listener);
    }
}
