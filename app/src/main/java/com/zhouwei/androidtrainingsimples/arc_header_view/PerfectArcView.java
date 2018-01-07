package com.zhouwei.androidtrainingsimples.arc_header_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.zhouwei.androidtrainingsimples.R;

/**
 * Created by zhouwei on 2017/12/5.
 */

public class PerfectArcView extends View implements Target {
    private Paint mPaint;
    private Bitmap mBitmap;
    private int mHeight;
    private int mWidth;
    private RectF mRect = new RectF();
    private Point mCircleCenter;
    private float mRadius;
    private int mStartColor;
    private int mEndColor;
    private LinearGradient mLinearGradient;
    /**
     * 显示图片还是显示色值
     */
    private boolean mIsShowImage = true;

    public PerfectArcView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        readAttr(attrs);
        init();
    }

    private void init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        //  mBitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.splash);
        mCircleCenter = new Point();
    }

    private void readAttr(AttributeSet set) {
        TypedArray typedArray = getContext().obtainStyledAttributes(set, R.styleable.PerfectArcView);
        mStartColor = typedArray.getColor(R.styleable.PerfectArcView_p_arc_startColor, Color.parseColor("#FF3A80"));
        mEndColor = typedArray.getColor(R.styleable.PerfectArcView_p_arc_endColor, Color.parseColor("#FF3745"));
        mIsShowImage = typedArray.getBoolean(R.styleable.PerfectArcView_p_arc_showImage, false);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = getHeight();
        int width = getWidth();
        mWidth = width;
        // 半径
        mRadius = width * 2;
        // 矩形
        mRect.left = 0;
        mRect.top = 0;
        mRect.right = width;
        mRect.bottom = mHeight;
        // 圆心坐标
        mCircleCenter.x = width / 2;
        mCircleCenter.y = mHeight - width * 2;

        mLinearGradient = new LinearGradient(width / 2, 0, width / 2, mHeight, mStartColor, mEndColor, Shader.TileMode.MIRROR);
    }

    /**
     * 加载网络图片
     *
     * @param url
     */
    public void setImageUrl(String url) {
        Picasso.with(getContext()).load(url).into(this);
    }

    /**
     * @param startColor
     * @param endColor
     */
    public void setColor(@ColorInt int startColor, @ColorInt int endColor) {
        mStartColor = startColor;
        mEndColor = endColor;
        mIsShowImage = false;
        mLinearGradient = new LinearGradient(mWidth / 2, 0, mWidth / 2, mHeight, mStartColor, mEndColor, Shader.TileMode.MIRROR);
        invalidate();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        int layerId = canvas.saveLayer(0, 0, canvasWidth, canvasHeight, null, Canvas.ALL_SAVE_FLAG);
        canvas.drawCircle(mCircleCenter.x, mCircleCenter.y, mRadius, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        if (mIsShowImage) {
            if (mBitmap != null) {
                canvas.drawBitmap(mBitmap, null, mRect, mPaint);
            }

        } else {
            mPaint.setShader(mLinearGradient);//绘制渐变色
            canvas.drawRect(mRect, mPaint);
        }

        mPaint.setXfermode(null);
        canvas.restoreToCount(layerId);
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        Log.e("zhouwei", "onBitmapLoaded....");
        mBitmap = bitmap;
        invalidate();
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        Log.e("zhouwei", "onBitmapFailed....");
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
        Log.e("zhouwei", "onPrepareLoad....");
    }
}
