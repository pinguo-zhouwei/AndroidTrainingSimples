package com.zhouwei.androidtrainingsimples.viewpager_transform;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;

import com.zhouwei.indicatorview.DisplayUtils;

/**
 * Created by zhouwei on 17/5/24.
 */

public class PolygonView extends AppCompatImageView {
    private int mWidth = 0;
    private int mHeight = 0;
    private Paint mPaint;
    private Paint mBorderPaint;
    private PorterDuffXfermode mXfermode;
    private Bitmap mBitmap;
    private int mBorderWidth;
    private Bitmap mMaskBitmap;
    public PolygonView(Context context) {
        super(context);
        init();
    }

    public PolygonView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PolygonView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mBorderWidth = DisplayUtils.dpToPx(4);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);// 关闭硬件加速加速
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setDither(true);

        mBorderPaint = new Paint();
        mBorderPaint.setColor(Color.WHITE);
        mBorderPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mBorderPaint.setAntiAlias(true);//抗锯齿
        mBorderPaint.setDither(true);//防抖动

        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        mMaskBitmap = getMaskBitmap();

    }

    @Override
    public void setImageResource(@DrawableRes int resId) {
        super.setImageResource(resId);
        mBitmap = BitmapFactory.decodeResource(getResources(),resId);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.save();

        canvas.drawBitmap(mMaskBitmap,0,0,mBorderPaint);
        mPaint.setXfermode(mXfermode);

        Bitmap bitmap = getCenterCropBitmap(mBitmap,mWidth,mHeight);
        canvas.drawBitmap(bitmap,0,0,mPaint);
        mPaint.setXfermode(null);
        canvas.restore();


    }

    private Bitmap getMaskBitmap(){
        Bitmap bm = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);

        Point point1 = new Point(0,30);
        Point point2 = new Point(mWidth,0);
        Point point3 = new Point(mWidth,mHeight);
        Point point4 = new Point(0,mHeight - 30);

        Path path = new Path();
        path.moveTo(point1.x,point1.y);
        path.lineTo(point2.x,point2.y);
        path.lineTo(point3.x,point3.y);
        path.lineTo(point4.x,point4.y);
        path.close();

        c.drawPath(path,mBorderPaint);

        return bm;
    }

    private Bitmap getCenterCropBitmap(Bitmap src, float rectWidth, float rectHeight) {

        float srcRatio = ((float) src.getWidth()) / src.getHeight();
        float rectRadio = rectWidth / rectHeight;
        if (srcRatio < rectRadio) {
            return Bitmap.createScaledBitmap(src, (int)rectWidth, (int)((rectWidth / src.getWidth()) * src.getHeight()), false);
        } else {
            return Bitmap.createScaledBitmap(src, (int)((rectHeight / src.getHeight()) * src.getWidth()), (int)rectHeight, false);
        }
    }

    /**
     * 对原图进行等比裁剪
     */
    private Bitmap scaleImage(Bitmap bitmap){

        if(bitmap!=null){

            int widht=bitmap.getWidth();
            int height=bitmap.getHeight();

            int new_width=0;
            int new_height=0;

            if(widht!=height){
                if(widht>height){
                    new_height=mHeight;
                    new_width=widht*new_height/height;
                }else{
                    new_width=mWidth;
                    new_height=height*new_width/widht;
                }
            }else{
                new_width=mWidth;
                new_height=mHeight;
            }
            return Bitmap.createScaledBitmap(bitmap, new_width, new_height, true);
        }
        return null;
    }


}
