package com.example.customizehistogramview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.customizehistogramview.R;

public class HistogramView extends View {

    private static final String TAG = HistogramView.class.getSimpleName();
    /**
     * 坐标轴原点 X 的默认值
     */
    private final static float DEFAULT_CENTER_X = 100;
    /**
     * 坐标轴原点 Y 的默认值
     */
    private final static float DEFAULT_CENTER_Y = 400;
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 坐标轴原点 X 坐标
     */
    private float centerX;
    /**
     * 坐标轴原点 Y 坐标
     */
    private float centerY;
    /**
     * X 轴长度
     */
    private float xLength = 300;
    /**
     * Y 轴长度
     */
    private float yLength = 300;
    /**
     * View宽度
     */
    private int mViewWidth;
    /**
     * View高度
     */
    private int mViewHeight;
    /**
     * Y轴文字宽度
     */
    private float mYDistance;
    /**
     * X轴底部高度
     */
    private float mXDistance;

    /**
     * XY轴画笔
     */
    private Paint mPaintTextXY;
    /**
     * XY 轴画笔颜色
     */
    private int mLineXYColor = Color.BLACK;
    /**
     * XY 轴画笔宽度
     */
    private float mLineXYSize = 2;
    /**
     * X轴底部文字
     */
    private String[] mDataTextX = {"1", "6", "11", "16"};
    /**
     * X轴底部文字之间的间隔
     */
    private float intervalDataX = 10;


    public HistogramView(Context context) {
        super(context);
        initView();
    }

    public HistogramView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.HistogramView);
        //柱状图的宽度
        mXDistance = array.getDimension(R.styleable.HistogramView_x_distance, dip2px(mContext, 30));
        mYDistance = array.getDimension(R.styleable.HistogramView_y_distance, dip2px(mContext, 30));
        centerX = array.getDimension(R.styleable.HistogramView_center_X, dip2px(mContext, DEFAULT_CENTER_X));
        centerY = array.getDimension(R.styleable.HistogramView_center_y, dip2px(mContext, DEFAULT_CENTER_Y));
        array.recycle();
        initView();
    }

    /**
     * 初始画笔
     */
    private void initView() {

        //XY轴
        mPaintTextXY = new Paint();
        mPaintTextXY.setStrokeWidth(3);
        mPaintTextXY.setColor(mLineXYColor);
        mPaintTextXY.setTextSize(mLineXYSize);
        mPaintTextXY.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * 绘制柱状图
         */
//        drawLineData(canvas);
        /**
         * 绘制坐标轴
         */
        drawLineXY(canvas);
        /**
         * 绘制X坐标值
         */
        drawDataLineX(canvas);
        /**
         * 绘制Y坐标值
         */
//        drawLineY(canvas);
    }

    /**
     * 绘制坐标轴
     * @param canvas
     */
    private void drawLineXY(Canvas canvas) {

        canvas.drawLine(centerX, centerY, centerX + xLength, centerY, mPaintTextXY);
        canvas.drawLine(centerX, centerY, centerX, centerY - yLength, mPaintTextXY);

//        canvas.drawLine(mYDistance, mViewHeight - mXDistance, mYDistance, 15, mPaintTextXY);
//        canvas.drawLine(mYDistance, mViewHeight - mXDistance, mViewWidth - 15, mViewHeight - mXDistance, mPaintTextXY);
//
//        if (mIsShowArrow) {
//            //Y轴箭头
//            canvas.drawLine(mYDistance, 15, mYDistance - 10, 25, mPaintTextXY);
//            canvas.drawLine(mYDistance, 15, mYDistance + 10, 25, mPaintTextXY);
//            //X轴箭头
//            canvas.drawLine(mViewWidth - 15, mViewHeight - mXDistance, mViewWidth - 25, mViewHeight - mXDistance - 10, mPaintTextXY);
//            canvas.drawLine(mViewWidth - 15, mViewHeight - mXDistance, mViewWidth - 25, mViewHeight - mXDistance + 10, mPaintTextXY);
//
//        }
    }

    /**
     * 绘制X坐标值
     * @param canvas
     */
    private void drawDataLineX(Canvas canvas) {
        float startTextX = centerX + intervalDataX;
        float startTextY = centerY + mXDistance / 2;
        for (int i = 0; i < mDataTextX.length; i++) {
            //绘制进度数字
            String text = mDataTextX[i];
            //获取文字宽度
            float textWidth = mPaintTextXY.measureText(text, 0, text.length());
            mPaintTextXY.setTextSize(12);
            mPaintTextXY.setColor(Color.RED);
            canvas.drawText(text, startTextX, startTextY, mPaintTextXY);
            startTextX += startTextY + textWidth + intervalDataX;
            Log.d(TAG, "text: " + text);
            Log.d(TAG, "textWidth: " + textWidth);

//            float dx = (mYDistance + mBigDistance + mSmallDistance / 2 + mLineWidth + (i * (mBigDistance + mSmallDistance + 2 * mLineWidth))) - textWidth / 2;
//            Paint.FontMetricsInt fontMetricsInt = mPaintTextXY.getFontMetricsInt();
//            float dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
//            float baseLine = mViewHeight - mXDistance / 2 + dy;
//            canvas.drawText(text, dx, baseLine, mPaintTextXY);
        }
    }

    public void setData(String[] dataTextX) {
        this.mDataTextX = dataTextX;
    }


    /**
     * sp转px
     * @param context
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * dp转px
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


}
