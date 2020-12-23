package com.example.customizehistogramview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.customizehistogramview.R;
import com.example.customizehistogramview.bean.DataPoint;

import java.util.ArrayList;
import java.util.List;

public class HistogramView extends View {

    private static final String TAG = HistogramView.class.getSimpleName();
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 坐标轴原点 X 的默认值
     */
    private final static float DEFAULT_CENTER_X = 100;
    /**
     * 坐标轴原点 Y 的默认值
     */
    private final static float DEFAULT_CENTER_Y = 300;
    /**
     * 柱状图默认颜色
     */
    private final static int DEFAULT_LINE_COLOR = Color.GREEN;
    /**
     * 柱状图默认宽度
     */
    private final static int DEFAULT_LINE_WIDTH = 30;
    /**
     * X 轴两刻度之间距离的默认值
     */
    private final static float DEFAULT_INTERVAL_DATA_X = DEFAULT_LINE_WIDTH + 10;
    /**
     * Y 轴两刻度之间距离的默认值
     */
    private final static float DEFAULT_INTERVAL_DATA_Y = 40;
    /**
     * XY轴画笔的默认颜色
     */
    private final static int DEFAULT_LINE_XY_COLOR = Color.BLACK;
    /**
     * XY轴画笔宽度默认值
     */
    private final static float DEFAULT_LINE_XY_SIZE = 2;
    /**
     * 坐标轴原点 X 坐标
     */
    private float mCenterX;
    /**
     * 坐标轴原点 Y 坐标
     */
    private float mCenterY;
    /**
     * X 轴长度
     */
    private float mLengthX = 300;
    /**
     * Y 轴长度
     */
    private float mLengthY = 300;
    /**
     * X轴底部文字
     */
    private List<String> mDataTextX;
    /**
     * Y轴左侧文字
     */
    private List<String> mDataTextY;
    /**
     * X轴底部文字与X轴的间距
     */
    private float marginDataX = 10;
    /**
     * X轴底部文字的高度（只考虑单行文字情况）
     */
    private float textHeightX = 0;
    /**
     * Y轴底部文字与Y轴的间距
     */
    private float marginDataY = 25;
    /**
     * XY轴画笔
     */
    private Paint mPaintTextXY;
    /**
     * XY 轴画笔颜色
     */
    private int mLineXYColor;
    /**
     * XY 轴画笔宽度
     */
    private float mLineXYSize;
    /**
     * 柱状图画笔
     */
    private Paint mPaintLineData;
    /**
     * 柱状图宽度
     */
    private float mLineWidth;
    /**
     * 顶部柱状图的颜色
     */
    private int mBottomLineDataColor;
    /**
     * 底部柱状图的颜色
     */
    private int mTopLineDataColor;
    /**
     * 坐标原点处的值
     */
    private String mCenterValue = "0";
    /**
     * X轴每个刻度之间的距离
     */
    private float mIntervalDataX = 40;
    /**
     * Y轴每个刻度之间的距离
     */
    private float mIntervalDataY = 40;
    /**
     * X 轴的单位长度
     */
    private float mScaleX;
    /**
     * Y 轴的单位长度
     */
    private float mScaleY;
    /**
     * X 轴相邻两刻度之间的差值
     */
    private int mValueMinusX = 1;
    /**
     * Y 轴相邻两刻度之间的差值
     */
    private int mValueMinusY = 10;
    /**
     * 顶部柱状图数据
     */
    private List<DataPoint> mTopLineData;
    /**
     * 底部柱状图数据
     */
    private List<DataPoint> mBottomLineData;
    /**
     * Y轴大刻度线宽度
     */
    private float mBigTickMarkWidth = 10;
    /**
     * Y轴小刻度线宽度
     */
    private float mSmallTickMarkWidth = 5;
    /**
     * Y轴每个一个大刻度均分为几个小刻度的数量
     */
    private int mBigPartNum = 5;
    /**
     * 判断是否根据坐标轴数据量自动设置宽和高
     */
    private boolean mIsAutoSetAxis = true;
    /**
     * X轴底部文字的颜色
     */
    private int mTextColorX = Color.BLACK;
    /**
     * X轴底部文字的大小
     */
    private float mTextSizeX = 20;
    /**
     * Y轴左侧文字的颜色
     */
    private int mTextColorY = Color.BLACK;
    /**
     * Y轴左侧文字的大小
     */
    private float mTextSizeY = 20;
    /**
     * 数值平均线的颜色
     */
    private int mAverageValueColor = Color.RED;
    /**
     * 数值平均线的宽度
     */
    private float mAverageValueSize = 2;
    /**
     * 虚线中小实线的长度
     */
    private float mDottedLineWidth = 5;
    /**
     * 设置平均线的显示方式：1、实线；2、虚线。
     */
    private boolean isShowDottedLine = true;
    /**
     * X轴刻度数量
     */
    private int mValueNumX = 6;
    /**
     * Y轴刻度数量
     */
    private int mValueNumY = 6;
    /**
     * 是否重新绘制坐标轴刻度及文字
     */
    private boolean isReDrawText = true;
    /**
     * 是否重新绘制坐标轴
     */
    private boolean isReDrawLineXY = true;
    /**
     * 是否重新绘制顶部柱状图
     */
    private boolean isReDrawTopLineData = true;
    /**
     * 是否重新绘制底部柱状图
     */
    private boolean isReDrawBottomLineData = true;
    /**
     * 使用.9.png图片作为顶部柱状图的背景
     */
    private int topLineBitmap = R.drawable.ic_launcher;
    /**
     * 判断是否使用.9.png图片替换顶部柱状图的背景颜色
     */
    private boolean isReplaceTopLineByBitmap = false;
    /**
     * 使用.9.png图片作为底部柱状图的背景
     */
    private int bottomLineBitmap = R.drawable.ic_launcher;
    /**
     * 判断是否使用.9.png图片替换底部柱状图的背景颜色
     */
    private boolean isReplaceBottomLineByBitmap = false;

    public HistogramView(Context context) {
        super(context);
        init();
    }

    public HistogramView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public HistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.HistogramView);
        //柱状图的宽度
        mCenterX = array.getDimension(R.styleable.HistogramView_center_x, dip2px(mContext, DEFAULT_CENTER_X));
        mCenterY = array.getDimension(R.styleable.HistogramView_center_y, dip2px(mContext, DEFAULT_CENTER_Y));
        mTopLineDataColor = array.getInteger(R.styleable.HistogramView_topLineDataColor, DEFAULT_LINE_COLOR);
        mBottomLineDataColor = array.getInteger(R.styleable.HistogramView_bottomLineDataColor, DEFAULT_LINE_COLOR);
        mIntervalDataX = array.getFloat(R.styleable.HistogramView_intervalDataX, DEFAULT_INTERVAL_DATA_X);
        mIntervalDataY = array.getFloat(R.styleable.HistogramView_intervalDataY, DEFAULT_INTERVAL_DATA_Y);
        mLineWidth = array.getFloat(R.styleable.HistogramView_lineWidth, DEFAULT_LINE_WIDTH);
        mLineXYColor = array.getInteger(R.styleable.HistogramView_lineXYColor, DEFAULT_LINE_XY_COLOR);
        mLineXYSize = array.getFloat(R.styleable.HistogramView_lineXYSize,DEFAULT_LINE_XY_SIZE);
        array.recycle();
        init();
    }

    /**
     * 初始画笔和数值
     */
    private void init() {

        this.mDataTextX = new ArrayList<>();
        this.mDataTextY = new ArrayList<>();
        this.mTopLineData = new ArrayList<>();
        this.mBottomLineData = new ArrayList<>();

        //XY轴
        mPaintTextXY = new Paint();
        mPaintTextXY.setAntiAlias(true);

        //柱状图
        mPaintLineData = new Paint();
        mPaintLineData.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mDataTextX != null && mDataTextY != null) {
            //绘制X坐标值
            drawDataLineX(canvas);
            //绘制Y坐标值
            drawDataLineY(canvas);
        }
        //判断是否根据录入的数据设置坐标轴长度
        if (mIsAutoSetAxis) {
            mLengthX = mIntervalDataX * mDataTextX.size() + mLineWidth * 2;
            mLengthY = mIntervalDataY * mDataTextY.size();
        }
        Log.d(TAG, "Length of X----" + mLengthX);
        Log.d(TAG, "Length of Y----" + mLengthY);
        //绘制坐标轴
        drawLineXY(canvas);

        mScaleX = mIntervalDataX / mValueMinusX;
        mScaleY = mIntervalDataY / mValueMinusY;

        if (!mTopLineData.isEmpty())
            drawTopLineData(canvas);//绘制顶部柱状图
        if (!mBottomLineData.isEmpty())
            drawBottomLineData(canvas);//绘制底部柱状图

        drawAverageValueLine(canvas);

    }


    /**
     * 绘制坐标轴
     * @param canvas
     */
    private void drawLineXY(Canvas canvas) {
        mPaintTextXY.setStrokeWidth(mLineXYSize);
        mPaintTextXY.setColor(mLineXYColor);
        canvas.drawLine(mCenterX, mCenterY, mCenterX + mLengthX, mCenterY, mPaintTextXY);
        canvas.drawLine(mCenterX, mCenterY + textHeightX + 2 * marginDataX,
                mCenterX + mLengthX, mCenterY + textHeightX + 2 * marginDataX, mPaintTextXY);
        canvas.drawLine(mCenterX, mCenterY, mCenterX, mCenterY - mLengthY, mPaintTextXY);
        canvas.drawLine(mCenterX, mCenterY, mCenterX, mCenterY + mLengthY + textHeightX + 2 * marginDataX, mPaintTextXY);
    }

    /**
     * 绘制柱状图数据平均线
     * @param canvas
     */
    private void drawAverageValueLine(Canvas canvas) {
        mPaintTextXY.setStrokeWidth(mAverageValueSize);
        mPaintTextXY.setColor(mAverageValueColor);
        float stopLineX, startLineX, startTopLineY, stopTopLineY, startBottomLineY, stopBottomLineY;
        startLineX = mCenterX;
        stopLineX = startLineX + mLengthX;
        startTopLineY = stopTopLineY = mCenterY - getTopAverageValue() * mScaleY;
        startBottomLineY = stopBottomLineY = mCenterY + textHeightX + 2 * marginDataX + getBottomAverageValue() * mScaleY;
        if (isShowDottedLine)
            mPaintTextXY.setPathEffect(new DashPathEffect(new float[]{mDottedLineWidth, mDottedLineWidth}, 0));
        canvas.drawLine(startLineX, startTopLineY, stopLineX, stopTopLineY, mPaintTextXY);
        canvas.drawLine(startLineX, startBottomLineY, stopLineX, stopBottomLineY, mPaintTextXY);
    }

    /**
     * 绘制顶部柱状图
     * @param canvas
     */
    private void drawTopLineData(Canvas canvas) {
        mPaintLineData.setColor(mTopLineDataColor);
        mPaintLineData.setStrokeWidth(mLineWidth);
        float left, top, right, bottom;
        float startLineX, stopLineX, stopLineY;
        float startLineY = mCenterY;
        //从右往左绘制
        for (int i = mValueNumX, j = 1; (i >= 1) && ((mTopLineData.size() - j) >= 0); i--, j++) {
            startLineX = mCenterX + i * mIntervalDataX;
            stopLineX = startLineX;
            stopLineY = startLineY - mTopLineData.get(mTopLineData.size() - j).getDataY() * mScaleY;
            Log.d("stopLineY", i + "--" + mTopLineData.get(mTopLineData.size() - j).getDataY());

            if (isReplaceTopLineByBitmap) {
                left = startLineX - mLineWidth / 2;
                top = stopLineY;
                right = startLineX + mLineWidth / 2;
                bottom = startLineY;
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), topLineBitmap);
                RectF rectF = new RectF(left, top, right, bottom);
                canvas.drawBitmap(bitmap, null, rectF, mPaintTextXY);//限定图片显示范围
            } else
                canvas.drawLine(startLineX, startLineY, stopLineX, stopLineY, mPaintLineData);
        }
    }

    /**
     * 绘制底部柱状图
     * @param canvas
     */
    private void drawBottomLineData(Canvas canvas) {
        mPaintLineData.setColor(mBottomLineDataColor);
        mPaintLineData.setStrokeWidth(mLineWidth);
        float left, top, right, bottom;
        float startLineX, stopLineX, stopLineY;
        float startLineY = mCenterY + textHeightX + 2 * marginDataX;
        //从右往左绘制
        for (int i = mValueNumX, j = 1; (i >= 1) && ((mBottomLineData.size() - j) >= 0); i--, j++) {
            startLineX = mCenterX + i * mIntervalDataX;
            stopLineX = startLineX;
            stopLineY = startLineY + mBottomLineData.get(mBottomLineData.size() - j).getDataY() * mScaleY;
            Log.d("stopLineY", i + "--" + mBottomLineData.get(mBottomLineData.size() - j).getDataY());

            if (isReplaceBottomLineByBitmap) {
                left = startLineX - mLineWidth / 2;
                top = startLineY;
                right = startLineX + mLineWidth / 2;
                bottom = stopLineY;
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), bottomLineBitmap);
                RectF rectF = new RectF(left, top, right, bottom);
                canvas.drawBitmap(bitmap, null, rectF, mPaintTextXY);//限定图片显示范围
            } else
                canvas.drawLine(startLineX, startLineY, stopLineX, stopLineY, mPaintLineData);
        }
    }

    /**
     * 绘制X轴的刻度值
     * @param canvas
     */
    private void drawDataLineX(Canvas canvas) {
        mPaintTextXY.setColor(mTextColorX);
        mPaintTextXY.setTextSize(mTextSizeX);
        mPaintTextXY.setTextAlign(Paint.Align.CENTER);
        float startTextX;
        float centerTextWidth = mPaintTextXY.measureText(mCenterValue, 0, mCenterValue.length());
        textHeightX = measureTextHeight(mPaintTextXY);
        float startTextY = mCenterY + textHeightX + marginDataX;
        startTextX = mCenterX - centerTextWidth;
        canvas.drawText(mCenterValue, startTextX, startTextY, mPaintTextXY);
        for (int i = 1; i <= mDataTextX.size(); i++) {
            //绘制进度数字
            String text = mDataTextX.get(i-1);
            startTextX = mCenterX + i * mIntervalDataX;
            canvas.drawText(text, startTextX, startTextY, mPaintTextXY);
        }
    }

    /**
     * 绘制Y轴的刻度值
     * @param canvas
     */
    private void drawDataLineY(Canvas canvas) {
        mPaintTextXY.setColor(mTextColorY);
        mPaintTextXY.setTextSize(mTextSizeY);
        mPaintTextXY.setTextAlign(Paint.Align.CENTER);
        float startTopTextY, startBottomTextY, startTopTickY, startBottomTickY;
        float startTopTextX = mCenterX - marginDataY;
        float startBottomTextX = mCenterX - marginDataY;
        for (int i = 1; i <= mDataTextY.size(); i++) {
            //绘制进度数字
            String text = mDataTextY.get(i-1);
            startTopTextY = mCenterY - i * mIntervalDataY;
            startBottomTextY = mCenterY + i * mIntervalDataY + textHeightX + 2 * marginDataX;
            canvas.drawText(text, startTopTextX, startTopTextY + textHeightX / 2, mPaintTextXY);
            canvas.drawText(text, startBottomTextX, startBottomTextY, mPaintTextXY);
            //绘制大刻度线
            canvas.drawLine(mCenterX, startTopTextY, mCenterX - mBigTickMarkWidth, startTopTextY, mPaintTextXY);
            canvas.drawLine(mCenterX, startBottomTextY, mCenterX - mBigTickMarkWidth, startBottomTextY, mPaintTextXY);
            for (int j = 1; j < mBigPartNum; j++) {
                startTopTickY = mCenterY - (i-1) * mIntervalDataY - j * (mIntervalDataY / mBigPartNum);
                startBottomTickY = mCenterY + (i-1) * mIntervalDataY + textHeightX + 2 * marginDataX + j * (mIntervalDataY / mBigPartNum);
                //绘制小刻度线
                canvas.drawLine(mCenterX, startTopTickY, mCenterX - mSmallTickMarkWidth, startTopTickY, mPaintTextXY);
                canvas.drawLine(mCenterX, startBottomTickY, mCenterX - mSmallTickMarkWidth, startBottomTickY, mPaintTextXY);
            }
        }
    }

    /**
     * 方式一：手动输入坐标轴数值
     * @param dataTextX
     * @param dataTextY
     */
    public void setOrdinaryAxisData(List<String> dataTextX, List<String> dataTextY) {
        if (dataTextX != null) {
            this.mDataTextX.clear();
            this.mDataTextX = dataTextX;
        }
        if (dataTextY != null) {
            this.mDataTextY.clear();
            this.mDataTextY = dataTextY;
        }
    }

    /**
     * 方式二：自动录入数字刻度值
     * @param startValueX
     * @param valueNumX
     * @param startValueY
     * @param valueNumY
     */
    public void setDigitalAxisData(int startValueX, int valueNumX, int startValueY, int valueNumY) {
        List<String> digitalValueX = new ArrayList<>();
        List<String> digitalValueY = new ArrayList<>();
        mValueNumX = valueNumX;
        mValueNumY = valueNumY;
        for (int i = 0; i < mValueNumX; i++)
            digitalValueX.add(String.valueOf(startValueX + i * mValueMinusX));
        for (int j= 0; j < mValueNumY; j++)
            digitalValueY.add(String.valueOf(startValueY + j * mValueMinusY));
        this.mDataTextX.clear();
        this.mDataTextY.clear();
        this.mDataTextX = digitalValueX;
        this.mDataTextY = digitalValueY;
        invalidate();
    }

    /**
     * 设置顶部柱状图的数据
     * @param topLineData
     */
    public void setTopLineData(List<DataPoint> topLineData) {
        if (topLineData == null)
            return;
        this.mTopLineData.clear();
        this.mTopLineData = topLineData;
        invalidate(); //请求重新draw
    }

    /**
     * 添加顶部柱状图的数据
     * @param addTopLineData
     */
    public void addTopLineData(List<DataPoint> addTopLineData) {
        if (addTopLineData == null)
            return;
        //合并新添加的数据
        List<DataPoint> tempLineData = new ArrayList<>();
        tempLineData.addAll(mTopLineData);
        tempLineData.addAll(addTopLineData);
        this.mTopLineData.clear();
        this.mTopLineData = tempLineData;
        invalidate();
    }

    /**
     * 获取顶部柱状图数据的平均值
     * @return
     */
    public float getTopAverageValue() {
        float averageValue = 0, sum = 0;
        if (mTopLineData != null) {
            for (int i = 0; i < mTopLineData.size(); i++)
                sum += mTopLineData.get(i).getDataY();
            Log.d(TAG, "Top Data Number----" + mTopLineData.size());
            Log.d(TAG, "Top Data Sum----" + sum);
            averageValue = sum / mTopLineData.size();
            Log.d(TAG, "Top Data averageValue----" + averageValue);
            Log.d(TAG, "Top Data -----------------------------------");
        }
        return averageValue;
    }

    /**
     * 设置底部柱状图的数据
     * @param topBottomData
     */
    public void setBottomLineData(List<DataPoint> topBottomData) {
        if (topBottomData == null)
            return;
        this.mBottomLineData.clear();
        this.mBottomLineData = topBottomData;
        invalidate(); //请求重新draw
    }

    /**
     * 添加顶部柱状图的数据
     * @param addBottomLineData
     */
    public void addBottomLineData(List<DataPoint> addBottomLineData) {
        if (addBottomLineData == null)
            return;
        List<DataPoint> tempLineData = new ArrayList<>();
        tempLineData.addAll(mBottomLineData);
        tempLineData.addAll(addBottomLineData);
        Log.d(TAG, "mBottomLineData is " + mBottomLineData);
        Log.d(TAG, "addBottomLineData is " + addBottomLineData);
        Log.d(TAG, "tempLineData is " + tempLineData);
        this.mBottomLineData.clear();
        this.mBottomLineData = tempLineData;
        invalidate();
    }

    /**
     * 获取底部柱状图数据的平均值
     * @return
     */
    public float getBottomAverageValue() {
        float averageValue = 0, sum = 0;
        if (mBottomLineData != null) {
            for (int i = 0; i < mBottomLineData.size(); i++)
                sum += mBottomLineData.get(i).getDataY();
            Log.d(TAG, "Bottom Data Number----" + mBottomLineData.size());
            Log.d(TAG, "Bottom Data Sum----" + sum);
            averageValue = sum / mBottomLineData.size();
            Log.d(TAG, "Bottom Data averageValue----" + averageValue);
            Log.d(TAG, "Bottom Data -----------------------------------");
        }
        return averageValue;
    }

    /**
     * 测量文字的高度
     * --经测试后发现，采用另一种带Rect的方式，获得的数据并不准确。
     * 特别是在一些对文字有一些倾斜处理的时候
     * @param paint
     * @return
     */
    public static float measureTextHeight(Paint paint){
        float height = 0f;
        if(null == paint){
            return height;
        }
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        height = fontMetrics.descent - fontMetrics.ascent;
//        height = fontMetrics.bottom - fontMetrics.top;
        return height;
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

    /**
     * 判断是否自动设置坐标轴宽和高
     * @param isAutoSetAxis
     */
    public void setAutoSetAxis(boolean isAutoSetAxis) {
        mIsAutoSetAxis = isAutoSetAxis;
    }

    /**
     * 设置坐标轴的X轴和Y轴长度（正方向）
     * @param lengthX
     * @param lengthY
     */
    public void setAxisValue(float lengthX, float lengthY) {
        mLengthX = lengthX;
        mLengthY = lengthY;
    }

    /**
     * 设置底部柱状图的颜色
     * @param mBottomLineDataColor
     */
    public void setBottomLineDataColor(int mBottomLineDataColor) {
        this.mBottomLineDataColor = mBottomLineDataColor;
    }

    /**
     * 设置顶部柱状图的颜色
     * @param mTopLineDataColor
     */
    public void setTopLineDataColor(int mTopLineDataColor) {
        this.mTopLineDataColor = mTopLineDataColor;
    }

    /**
     * 设置柱状图的宽度（顶部和底部宽度一致）
     * @param mLineWidth
     */
    public void setLineWidth(int mLineWidth) {
        this.mLineWidth = mLineWidth;
    }

    /**
     * 设置X轴两刻度之间的距离
     * @param mIntervalDataX
     */
    public void setIntervalDataX(int mIntervalDataX) {
        this.mIntervalDataX = mIntervalDataX;
    }

    /**
     * 设置Y轴两刻度之间的距离
     * @param mIntervalDataY
     */
    public void setIntervalDataY(int mIntervalDataY) {
        this.mIntervalDataY = mIntervalDataY;
    }

    /**
     * 设置坐标轴原点
     * @param mCenterX
     * @param mCenterY
     */
    public void setOriginAxis(float mCenterX, float mCenterY) {
        this.mCenterX = mCenterX;
        this.mCenterY = mCenterY;
    }

    /**
     * 设置XY轴画笔的颜色
     * @param mLineXYColor
     */
    public void setLineXYColor(int mLineXYColor) {
        this.mLineXYColor = mLineXYColor;
    }

    /**
     * 设置XY轴画笔的宽度
     * @param mLineXYSize
     */
    public void setLineXYSize(float mLineXYSize) {
        this.mLineXYSize = mLineXYSize;
    }

    /**
     * 设置X轴底部文字的颜色和大小
     * @param colorX
     * @param textSizeX
     */
    public void setTextConfigAxisX(int colorX, float textSizeX) {
        this.mTextColorX = colorX;
        this.mTextSizeX = textSizeX;
    }

    /**
     * 设置Y轴左侧文字的颜色和大小
     * @param colorY
     * @param textSizeY
     */
    public void setTextConfigAxisY(int colorY, float textSizeY) {
        this.mTextColorY = colorY;
        this.mTextSizeY = textSizeY;
    }

    /**
     * 设置X轴底部文字与X轴之间的距离
     * @param marginDataX
     */
    public void setMarginDataX(float marginDataX) {
        this.marginDataX = marginDataX;
    }

    /**
     * 设置Y轴底部文字与Y轴之间的距离
     * @param marginDataY
     */
    public void setMarginDataY(float marginDataY) {
        this.marginDataY = marginDataY;
    }

    /**
     * 设置坐标原点处的文字
     * @param mCenterValue
     */
    public void setCenterValue(String mCenterValue) {
        this.mCenterValue = mCenterValue;
    }

    /**
     * 设置XY坐标轴相邻两刻度之间的差值
     * @param mValueMinusX
     * @param mValueMinusY
     */
    public void setValueMinus(int mValueMinusX, int mValueMinusY) {
        this.mValueMinusX = mValueMinusX;
        this.mValueMinusY = mValueMinusY;
    }

    /**
     * 设置Y轴大小刻度线长度
     * @param mBigTickMarkWidth
     * @param mSmallTickMarkWidth
     */
    public void setTickMarkWidth(float mBigTickMarkWidth, float mSmallTickMarkWidth) {
        this.mBigTickMarkWidth = mBigTickMarkWidth;
        this.mSmallTickMarkWidth = mSmallTickMarkWidth;
    }

    /**
     * 设置Y轴每个大刻度包含的小刻度数量
     * @param mBigPartNum
     */
    public void setBigPartNum(int mBigPartNum) {
        this.mBigPartNum = mBigPartNum;
    }

    /**
     * 设置数据平均线的颜色
     * @param mAverageValueColor
     */
    public void setAverageValueColor(int mAverageValueColor) {
        this.mAverageValueColor = mAverageValueColor;
    }

    /**
     * 设置数据平均线的宽度
     * @param mAverageValueSize
     */
    public void setAverageValueSize(float mAverageValueSize) {
        this.mAverageValueSize = mAverageValueSize;
    }

    /**
     * 设置虚线中每个小实线的长度
     * @param mDottedLineWidth
     */
    public void setDottedLineWidth(float mDottedLineWidth) {
        this.mDottedLineWidth = mDottedLineWidth;
    }

    /**
     * 设置数据平均线的显示方式
     * @param showDottedLine
     */
    public void setShowDottedLine(boolean showDottedLine) {
        isShowDottedLine = showDottedLine;
    }

    /**
     * 设置顶部柱状图的背景
     * @param topLineBitmap
     */
    public void setTopLineBitmap(int topLineBitmap) {
        this.topLineBitmap = topLineBitmap;
    }

    /**
     * 判断是否使用位图作为顶部柱状图的背景
     * @param replaceTopLineByBitmap
     */
    public void setReplaceTopLineByBitmap(boolean replaceTopLineByBitmap) {
        isReplaceTopLineByBitmap = replaceTopLineByBitmap;
    }

    /**
     * 设置底部柱状图的背景
     * @param bottomLineBitmap
     */
    public void setBottomLineBitmap(int bottomLineBitmap) {
        this.bottomLineBitmap = bottomLineBitmap;
    }

    /**
     * 判断是否使用位图作为底部柱状图的背景
     * @param replaceBottomLineByBitmap
     */
    public void setReplaceBottomLineByBitmap(boolean replaceBottomLineByBitmap) {
        isReplaceBottomLineByBitmap = replaceBottomLineByBitmap;
    }
}
