package com.zhouwei.androidtrainingsimples.viewpager_wrapper;

import android.content.Context;
import android.graphics.PointF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.OverScroller;

/**
 * ViewPager 的包装类，实现的功能是：当ViewPager 滑动到最后一页时，
 * 显示最后一页的一半，并且显示一个右侧的布局。
 * Created by zhouwei on 17/3/28.
 */

public class ViewPagerWrapLayout extends ViewGroup {
    public static final String TAG = "SwipeDelItemLayout";
    private int mScaleTouchSlop;//为了处理单击事件的冲突
    private int mMaxVelocity;//计算滑动速度用
    private VelocityTracker mVelocityTracker;

    private int mScrollLimit;//滑动的阀值距离
    private int mPointerId;
    private int mMenuWidths = 0;

    private int mContentWidth;

    private int mContentHeight;

    private boolean isUserSwiped;
    private boolean isUnMove;
    private boolean isTouching;

    private PointF mLastPoint = new PointF();
    private PointF mFirstPoint = new PointF();

    private OverScroller mScroller;
    private View mContentView;

    private static ViewPagerWrapLayout mViewCache;

    private boolean isInterceptFlag ;
    /**
     * 是否已经滑倒ViewPager 的最后一页
     */
    private boolean mIsLastPage = false;
    /**
     * 是否是向左滑动
     */
    private boolean mScrollToLeft = false;
    /**
     * 是否已经展开
     */
    private boolean mIsExpand = false;

    private OnRightLayoutExpandListener mExpandListener;

    public void setExpandListener(OnRightLayoutExpandListener expandListener) {
        mExpandListener = expandListener;
    }

    public void setLastPage(boolean lastPage) {
        mIsLastPage = lastPage;
    }

    public ViewPagerWrapLayout(Context context) {
        super(context);
        init();
    }

    public ViewPagerWrapLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ViewPagerWrapLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ViewPagerWrapLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        mScaleTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mMaxVelocity = ViewConfiguration.get(getContext()).getScaledMaximumFlingVelocity();
        mScroller = new OverScroller(getContext());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setClickable(true);
        mMenuWidths = 0;
        mContentWidth = 0;
        mContentHeight = 0;
        int childCount = getChildCount();

        final boolean measureMatchParentChildren = MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.EXACTLY;
        boolean isNeedMeasureChildHeight = false;

        for(int i=0;i<childCount;i++){
            View childView = getChildAt(i);
            childView.setClickable(true);
           // if(childView.getVisibility() != View.GONE){
                measureChild(childView,widthMeasureSpec,heightMeasureSpec);
                LayoutParams params =  childView.getLayoutParams();
                mContentHeight = Math.max(mContentHeight,childView.getMeasuredHeight());

                if(measureMatchParentChildren && params.height == LayoutParams.MATCH_PARENT){
                    isNeedMeasureChildHeight = true;
                }

                if(i == 0){
                    mContentWidth = childView.getMeasuredWidth();
                    mContentView = childView;
                }else{
                    mMenuWidths += childView.getMeasuredWidth();
                }

           // }
        }
        Log.e(TAG,"mMenuWidths:"+mMenuWidths);
        setMeasuredDimension(mContentWidth,mContentHeight);

        if(isNeedMeasureChildHeight){
            forceUniformHeight(childCount,widthMeasureSpec);
        }

        mScrollLimit = mMenuWidths * 1 / 10 ;
    }

    /**
     * 给MatchParent的子View设置高度
     *
     * @param count
     * @param widthMeasureSpec
     * @see android.widget.LinearLayout# 同名方法
     */
    private void forceUniformHeight(int count, int widthMeasureSpec) {
        // Pretend that the linear layout has an exact size. This is the measured height of
        // ourselves. The measured height should be the max height of the children, changed
        // to accommodate the heightMeasureSpec from the parent
        int uniformMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredHeight(),
                MeasureSpec.EXACTLY);//以父布局高度构建一个Exactly的测量参数
        for (int i = 0; i < count; ++i) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                if (lp.height == LayoutParams.MATCH_PARENT) {
                    // Temporarily force children to reuse their old measured width
                    // FIXME: this may not be right for something like wrapping text?
                    int oldWidth = lp.width;//measureChildWithMargins 这个函数会用到宽，所以要保存一下
                    lp.width = child.getMeasuredWidth();
                    // Remeasure with new dimensions
                    measureChildWithMargins(child, widthMeasureSpec, 0, uniformMeasureSpec, 0);
                    lp.width = oldWidth;
                }
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.e(TAG,"onlayout....");
        int childCount = getChildCount();
        int left = getPaddingLeft();

        for(int i=0;i<childCount;i++){
            View childView = getChildAt(i);
            if(i==0){
                LayoutParams params = childView.getLayoutParams();
                params.width = LayoutParams.MATCH_PARENT;
                childView.layout(left,getTop(),childView.getMeasuredWidth()+left,getTop()+childView.getMeasuredHeight());
                left += childView.getWidth();
            }else{
                childView.layout(left,getTop(),childView.getMeasuredWidth()+left,getTop()+childView.getMeasuredHeight());
                //left += childView.getWidth();
            }
        }

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(mIsLastPage){
            acquireVelocityTracker(ev);
            switch (ev.getAction()){
                case MotionEvent.ACTION_DOWN:
                    Log.e(TAG,"action down");
                    isUserSwiped = false;
                    isUnMove = true;
                    isInterceptFlag = false;
                    //如果右侧布局是展开的，则直接关闭
                  /*  if(mIsExpand){
                        smoothClose();
                        return false;
                    }*/
                   /* if(mViewCache!=null && mViewCache!= this){
                        getParent().requestDisallowInterceptTouchEvent(true);
                        mViewCache.smoothClose();
                        isInterceptFlag = true;
                    }*/

                    if(isTouching){
                        return false;
                    }else{
                        isTouching = true;
                    }
                    mLastPoint.set(ev.getRawX(),ev.getRawY());
                    mFirstPoint.set(ev.getRawX(),ev.getRawY());

                    break;
                case MotionEvent.ACTION_MOVE:
                    Log.e(TAG,"action move");
                    if(isInterceptFlag){
                        break;
                    }

                    if(ev.getRawX() > mLastPoint.x && Math.abs(mLastPoint.x - ev.getRawX()) > 20){
                       mScrollToLeft = true;
                       getParent().requestDisallowInterceptTouchEvent(true);
                       break;

                    }else{
                        mScrollToLeft = false;
                    }

                    float gap = mLastPoint.x - ev.getRawX();
                    if(Math.abs(gap)> 10 || Math.abs(getScaleX())>10){
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                    if(Math.abs(gap) >mScaleTouchSlop){
                        isUnMove = false;
                    }
                    scrollBy((int) gap,0);

                    Log.e(TAG,"getScrollX:"+getScrollX()+"width:"+mMenuWidths+" 小于0："+(getScrollX()<0)+"> mMenuWidth:"+(getScrollX()>mMenuWidths));

                    //修正边界
                    if(getScrollX()<0){
                        scrollTo(0,0);
                    }
                    if(getScrollX()>mMenuWidths){
                        scrollTo(mMenuWidths,0);
                    }
                    //记录最后一次触点
                    mLastPoint.set(ev.getRawX(),ev.getRawY());

                    mPointerId = ev.getPointerId(0);
                    break;
                case MotionEvent.ACTION_UP:
                    Log.e(TAG,"action up");

                case MotionEvent.ACTION_CANCEL:
                    Log.e(TAG,"action cancel");
                    if(Math.abs(ev.getRawX() - mFirstPoint.x)>mScaleTouchSlop){
                        isUserSwiped = true;
                    }

                    //
                    if(!isInterceptFlag){
                        mVelocityTracker.computeCurrentVelocity(1000,mMaxVelocity);
                        float velocityX = mVelocityTracker.getXVelocity(mPointerId);
                        if(Math.abs(velocityX)>1000){
                            if(velocityX <-1000){
                                smoothExpand();
                            }else{
                                smoothClose();
                            }
                        }else{
                            if(Math.abs(getScrollX()) > mScrollLimit){
                                smoothExpand();
                            }else{
                                smoothClose();
                            }
                        }
                    }


                    releaseVelocityTracker();

                    isTouching = false;
                    break;
            }


            return super.dispatchTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);

    }
    private float mLastX = 0;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(mIsLastPage && mScrollToLeft){
            switch (ev.getAction()){
                case MotionEvent.ACTION_DOWN:
                    mLastX = ev.getRawX();
                    break;
                case MotionEvent.ACTION_MOVE:
                   /* if(Math.abs(ev.getRawX() - mFirstPoint.x)>mScaleTouchSlop){
                        return true;
                    }*/
                    // 判断是向左滑动还是向右滑动
                    if(ev.getRawX() > mLastX && (ev.getRawX() - mLastX) > mScaleTouchSlop){
                        if(mIsExpand){
                            smoothClose();
                        }
                        return super.onInterceptTouchEvent(ev);
                    }else{
                        return true;
                    }

                case MotionEvent.ACTION_UP:
                    if(getScrollX() > mScaleTouchSlop){
                        if(ev.getX() <getWidth() - getScrollX()){
                            if(isUnMove){
                                smoothClose();
                                Log.e(TAG,"onIntercepter up close");
                            }
                            return true;
                        }
                    }

                    if(isUserSwiped){
                        return true;
                    }
                    break;
            }
        }

        return super.onInterceptTouchEvent(ev);
    }


    /**
     * 展开动画
     */
    public void smoothExpand(){
        if(mIsExpand){
            return;
        }
        Log.e(TAG,"smoothExpand");
        mViewCache = this;
        if(mContentView!=null){
            mContentView.setLongClickable(false);
        }

        mIsExpand = true;

        mScroller.startScroll(getScrollX(),0,mMenuWidths - getScrollX(),0,300);

        invalidate();
        Log.e(TAG,"smoothExpand startX:"+getScrollX());
        // 展开回调
        if(mExpandListener!=null){
            mExpandListener.onExpand();
        }
    }

    public void smoothClose(){
        if(!mIsExpand){
            return;
        }
        Log.e(TAG,"smoothClose");
        mViewCache = null;

        if(mContentView!=null){
            mContentView.setLongClickable(true);
        }

        mIsExpand = false;

        mScroller.startScroll(getScrollX(),0,-getScrollX(),0,300);

        invalidate();
        Log.e(TAG,"smoothClose startX:"+getScrollX());
        //关闭回调
        if(mExpandListener!=null){
            mExpandListener.onClose();
        }

    }


    @Override
    public void computeScroll() {
       if(mScroller.computeScrollOffset()){
           scrollTo(mScroller.getCurrX(),0);
           invalidate();
       }

    }

    @Override
    protected void onDetachedFromWindow() {
        if(mViewCache == this){
            mViewCache.smoothClose();
            mViewCache =null;
        }
        super.onDetachedFromWindow();
    }

    private void acquireVelocityTracker(MotionEvent event){
        if(mVelocityTracker == null){
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }
    /**
     * 释放
     */
    private void releaseVelocityTracker(){
        if(mVelocityTracker!=null){
            mVelocityTracker.clear();
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    public void quickClose() {
        if (this == mViewCache) {
            mViewCache.scrollTo(0, 0);//关闭
            mViewCache = null;
        }
    }


    public interface OnRightLayoutExpandListener{
        /**
         * 右侧布局展开回调
         */
        public void onExpand();

        /**
         * 右侧布局收起回调
         */
        public void onClose();
    }
}
