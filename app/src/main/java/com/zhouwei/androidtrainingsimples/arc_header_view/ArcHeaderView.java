package com.zhouwei.androidtrainingsimples.arc_header_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhouwei on 2017/9/14.
 */

public class ArcHeaderView extends View {
    private Paint mPaint;
    private PointF mStartPoint, mEndPoint, mControlPoint;
    private int mWidth;
    private int mHeight;
    private Path mPath = new Path();
    private int mArcHeight = 100;// 圆弧高度
    private int mStartColor;
    private int mEndColor;
    private LinearGradient mLinearGradient;

    public ArcHeaderView(Context context) {
        super(context);
        init();
    }

    public ArcHeaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ArcHeaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ArcHeaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mPaint = new Paint();
        // mPaint.setColor(Color.parseColor("#37B99F"));
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.FILL);

        mStartPoint = new PointF(0, 0);
        mEndPoint = new PointF(0, 0);
        mControlPoint = new PointF(0, 0);

        mStartColor = Color.parseColor("#FF3A80");
        mEndColor = Color.parseColor("#FF3745");

    }


    /**
     *
     * @param startColor
     * @param endColor
     */
    public void setColor(@ColorInt int startColor, @ColorInt int endColor) {
        mStartColor = startColor;
        mEndColor = endColor;
        mLinearGradient = new LinearGradient(mWidth / 2, 0, mWidth / 2, mHeight, mStartColor, mEndColor, Shader.TileMode.MIRROR);
        invalidate();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = w;
        mHeight = h;

        mPath.reset();

        mPath.moveTo(0, 0);
        mPath.addRect(0, 0, mWidth, mHeight - mArcHeight, Path.Direction.CCW);

        mStartPoint.x = 0;
        mStartPoint.y = mHeight - mArcHeight;

        mEndPoint.x = mWidth;
        mEndPoint.y = mHeight - mArcHeight;

        mControlPoint.x = mWidth / 2 - 50;
        mControlPoint.y = mHeight + 100;

        // 初始化shader
        mLinearGradient = new LinearGradient(mWidth / 2, 0, mWidth / 2, mHeight, mStartColor, mEndColor, Shader.TileMode.MIRROR);

        ///SweepGradient sweepGradient = new SweepGradient(mEndPoint.x / 2,mEndPoint.y / 2,mStartColor,mEndColor);
        //mPaint.setShader(sweepGradient);


        invalidate();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setShader(mLinearGradient);

        mPath.moveTo(mStartPoint.x, mStartPoint.y);
        mPath.quadTo(mControlPoint.x, mControlPoint.y, mEndPoint.x, mEndPoint.y);

        canvas.drawPath(mPath, mPaint);
    }
}
