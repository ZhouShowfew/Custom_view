package com.steven.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.steven.view.util.Utils;

/**
 * Created by Steven on 2016/8/26.
 *
 */
public class Line extends View {

    //progress color
    int progressColor = Color.parseColor("#3F51B5");//蓝色
    //success color
    int successColor = Color.parseColor("#66aaaaaa");//灰色
    //fail color
    int failColor = Color.parseColor("#ff0000");//红色
    //line length
    int lineH = Utils.dpToPx(1);
    int lineW = Utils.dpToPx(80);

    int radius=10;
    int height=200;

    Paint mPaint;

    public enum LineType{
        LINE_SUCCESS,LINE_FAIL,LINE_PROGRESS
    }

    private LineType mLineType;

    public Line(Context context) {
        super(context);
    }

    public Line(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Line(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.Line, 0, 0);
        progressColor=t.getColor(R.styleable.Line_line_color_progress,progressColor);
        successColor=t.getColor(R.styleable.Line_line_color_success,successColor);
        failColor=t.getColor(R.styleable.Line_line_color_fail,failColor);
        lineH=t.getDimensionPixelOffset(R.styleable.Line_line_height,lineH);
        lineW=t.getDimensionPixelOffset(R.styleable.Line_line_width,lineW);
        t.recycle();   // we should always recycle after used
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(lineH);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height ;
        if (widthMode == MeasureSpec.EXACTLY)
        {
            width = widthSize;
        } else
        {
            width = 2*radius;
        }

        if (heightMode == MeasureSpec.EXACTLY)
        {
            height = heightSize;
        } else
        {
            if (haveLine)
                height = this.height;
            else
                height =2*radius;
        }
        setMeasuredDimension(width, height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //上色
        if (mLineType==LineType.LINE_SUCCESS){
            mPaint.setColor(successColor);
        }else if (mLineType==LineType.LINE_FAIL){
            mPaint.setColor(failColor);
        }else if (mLineType==LineType.LINE_PROGRESS){
            mPaint.setColor(progressColor);
        }
        //画圆和线
        canvas.drawCircle(10,10,radius,mPaint);
        if (haveLine)
        canvas.drawLine(10,10,10,height ,mPaint);
        invalidate();
    }

    public void setType(LineType type){
        mLineType=type;
    }

    boolean haveLine=true;
    public void setLineGone(){
        haveLine = false;
    }

}
