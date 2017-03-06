package yxr.com.hidelibrary;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

public class AnimationHelper {
    private int mViewType = View.VISIBLE;
    private View mView;

    /**
     * 设置缩小动画
     */
    public void setScaleAnimationOut(View mView) {
        if(mViewType == View.GONE)
            return;
        this.mView = mView;
        mViewType = View.GONE;
        AnimatorSet set = new AnimatorSet();
        set.addListener(new AnimationListener(mViewType));
        set.playTogether(
                ObjectAnimator.ofFloat(mView, "scaleX", 1, 0),
                ObjectAnimator.ofFloat(mView, "scaleY", 1, 0));
        set.setDuration(300).start();

    }
    /**
     * 设置放大动画
     */
    public void setScaleAnimationIn(View mView) {
        if(mViewType == View.VISIBLE)
            return;
        this.mView = mView;
        mViewType = View.VISIBLE;
        mView.setVisibility(mViewType);

        AnimatorSet set = new AnimatorSet();
        set.addListener(new AnimationListener(mViewType));
        set.playTogether(
                ObjectAnimator.ofFloat(mView, "scaleX", mView.getScaleX(), 1),
                ObjectAnimator.ofFloat(mView, "scaleY", mView.getScaleY(), 1));
        set.setDuration(300).start();
    }


    public class AnimationListener implements Animator.AnimatorListener{
        private final int visibility;
        public AnimationListener(int visibility){
            this.visibility = visibility;
        }
        @Override
        public void onAnimationStart(Animator animation) {
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            AnimationHelper.this.mView.setVisibility(visibility);
        }

        @Override
        public void onAnimationCancel(Animator animation) {
        }
        @Override
        public void onAnimationRepeat(Animator animation) {
        }
    }
}
