package com.dalong.imageview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

/**
 * 进度条imageview
 * Created by zhouweilong on 16/5/10.
 */
public class ProgressImageView extends ImageView {

    private  int mType;//开始的进度的方向类型

    private  int mEndColor;//开始的颜色

    private  int mStartColor;//结束的颜色

    private  int mTextColor;//文字颜色

    private  int mTextSize;// 文字大小

    private  Paint mPaint;// 画笔

    private Context mContext;//上下文

    private int progress = 0;//进度值

    public ProgressImageView(Context context) {
        this(context,null);
    }

    public ProgressImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.ProgressImageView);
        mTextSize= typedArray.getDimensionPixelSize(R.styleable.ProgressImageView_progressTextSize, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
        mTextColor=typedArray.getColor(R.styleable.ProgressImageView_progressTextColor,Color.GREEN);
        mStartColor=typedArray.getColor(R.styleable.ProgressImageView_progressStartColor,Color.parseColor("#70000000"));
        mEndColor=typedArray.getColor(R.styleable.ProgressImageView_progressEndColor,Color.parseColor("#00000000"));
        mType=typedArray.getInt(R.styleable.ProgressImageView_progressType,3);
        typedArray.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true); // 消除锯齿
        mPaint.setStyle(Paint.Style.FILL);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);//之后画区域透明度

        mPaint.setColor(mStartColor);// 设置开始颜色

        //默认我设置了是底部的设置
        int startLeft=0;//开始的颜色左面一直是固定0位置
        int startTop=0;//开始的颜色上面一直是固定0位置
        int startRight=getWidth();//右面一直是整个宽度
        int startBootom=getHeight()- getHeight() * progress/ 100;//颜色变化主要是竖直方向

        switch (mType){
            case 0: //left 从左面开始   上下位置不变   左面变化
                startLeft=getWidth()*progress/100;
                startTop=0;
                startRight=getWidth();
                startBootom=getHeight();
                break;
            case 1://top 从上面开始    左右不变    顶部变化
                startLeft=0;
                startTop=getHeight()*progress/100;
                startRight=getWidth();
                startBootom=getHeight();
                break;
            case 2://right  从右面开始   上下不变   右面变化
                startLeft=0;
                startTop=0;
                startRight=getWidth()-getWidth()* progress/ 100;
                startBootom=getHeight();
                break;
            case 3:// bottom  从下面开始   左右不变   底部变化
                startLeft=0;
                startTop=0;
                startRight=getWidth();
                startBootom=getHeight()- getHeight() * progress/ 100;
                break;
        }
        canvas.drawRect(startLeft, startTop,startRight, startBootom, mPaint);




        mPaint.setColor(mEndColor);// 设置结束颜色
        //默认我设置了是底部的设置
        int endLeft=0;//开始的颜色左面一直是固定0位置
        int endTop=getHeight() - getHeight() * progress / 100;//开始的颜色上面一直是固定0位置
        int endRight=getWidth();//右面一直是整个宽度
        int endBootom=getHeight();//颜色变化主要是竖直方向
        switch (mType){
            case 0: //left   左面上传进度 右面不断向右 其他没变
                endLeft=0;
                endTop=0;
                endRight=getWidth()*progress/100;
                endBootom=getHeight();
                break;
            case 1://top   上面上传进度    下面不断向下  其他没变
                endLeft=0;
                endTop=0;
                endRight=getWidth();
                endBootom=getHeight()*progress/100;
                break;
            case 2://right   右面上传进度     左面不断向左
                endLeft=getWidth()-getWidth()*progress/100;
                endTop=0;
                endRight=getWidth();
                endBootom=getHeight();
                break;
            case 3:// bottom  下面上传进度   上面不断向上
                endLeft=0;
                endTop=getHeight() - getHeight() * progress / 100;
                endRight=getWidth();
                endBootom=getHeight();
                break;
        }

        canvas.drawRect(endLeft, endTop,endRight, endBootom, mPaint);

        mPaint.setTextSize(mTextSize);//设置文字大小
        mPaint.setColor(mTextColor);// 设置文字颜色
        mPaint.setStrokeWidth(2);//画笔宽
        Rect rect = new Rect();//创建区域
        mPaint.getTextBounds("100%", 0, "100%".length(), rect);// 测量最大文字的宽度
        // 设置文字在最中间
        canvas.drawText(progress + "%", getWidth() / 2 - rect.width() / 2,getHeight() / 2, mPaint);

    }

    /**
     * 设置进度
     * @param progress
     */
    public void setProgress(int progress) {
        this.progress = progress;
        postInvalidate();
    }

    /**
     * 设置类型
     * @param type
     */
    public void setType(int type){
        this.mType=type;
    }
}
