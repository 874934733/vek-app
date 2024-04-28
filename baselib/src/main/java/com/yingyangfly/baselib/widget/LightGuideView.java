package com.yingyangfly.baselib.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import java.util.List;

/**
 * 核心View on 2017/11/11.
 * <p>
 * 1. 把自定义的xml引导说明布局文件 通过 inflate()变为View对象，然后添加到FrameLayout上；
 * 2. 重写onDraw()方法，绘制半透明背景，然后根据设置的属性在需要高亮的控件周围绘制全透明的
 * 指定形状达到高亮效果。
 */

public class LightGuideView extends FrameLayout {

    //用于实现新绘制的像素与Canvas上对应位置已有的像素按照混合规则进行颜色混合
    private static final PorterDuffXfermode MODE_DST_OUT = new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT);
    private static final int DEFAULT_WIDTH_BLUR = 15;   //默认模糊边界的大小
    private static final int DEFAULT_RADIUS = 6;        //默认圆角度数

    private Context context;
    private Paint mPaint;                               //绘制高亮区域的画笔
    private Bitmap mMaskBitmap;                         //用于标识高亮区的图片

    //虚线的排列方式，需要setIsNeedBorder(true)并且边框类型为HighLight.MyType.DASH_LINE，该样式才能生效
    private float[] intervals;
    private boolean isNeedBorder = true;                //是否需要边框，默认需要
    private boolean isBlur = false;                     //是否需要模糊边界，默认不需要
    private int maskColor = 0x99000000;                 //背景颜色
    private int boderColor = maskColor;                 //边框颜色，默认和背景颜色一样
    private float boderWidth = 3;                       //边框宽度，单位：dp，默认3dp
    private int blurSize = DEFAULT_WIDTH_BLUR;          //模糊边界大小，默认15
    private int radius = DEFAULT_RADIUS;                //圆角大小，默认6
    private int phase = 1;                              //偏移量，直接使用1即可

    private List<HighLight.ViewPosInfo> mViewRects;                 //用于保存高亮View的集合
    private HighLight mHighLight;                                   //HighLight对象
    private HighLight.MyType myType = HighLight.MyType.DASH_LINE;   //边框类型,默认虚线
    private LayoutInflater mInflater;                               //视图填充器（打气筒）

    public LightGuideView(Context context, HighLight highLight, int maskColor, List<HighLight.ViewPosInfo> viewRects) {
        super(context);
        this.context = context;
        this.maskColor = maskColor;
        mHighLight = highLight;
        mInflater = LayoutInflater.from(context);
        mViewRects = viewRects;

        setWillNotDraw(false);
        init();
    }

    /**
     * 初始化一些配置参数
     */
    private void init() {
        mPaint = new Paint();
        mPaint.setDither(true);             //设置防抖动
        mPaint.setAntiAlias(true);          //抗锯齿
        mPaint.setStyle(Paint.Style.FILL);  //只绘制图形内容 (填充),STROKE 只绘制图形轮廓（描边）
        mPaint.setStrokeWidth(5);

        addViewForTip();
        //初始化虚线的样式
        intervals = new float[]{dip2px(4), dip2px(4)};
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        //1.精确模式（MeasureSpec.EXACTLY）:
        //      尺寸的值是多少，那么这个组件的长或宽就是多少;
        //      使用measureSpec中size的值作为宽高的精确值。
        //2.最大模式（MeasureSpec.AT_MOST）:
        //      这个也就是父组件，能够给出的最大的空间，当前组件的长或宽最大只能为这么大，当然也可以比这个小。
        //      使用measureSpec中size的值作为最大值，采用不超过这个值的最大允许值。
        //3.未指定模式（MeasureSpec.UNSPECIFIED）:当前组件，可以随便用空间，不受限制。
        measureChildren(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mMaskBitmap, 0, 0, null);

        super.onDraw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            buildMask();       // 绘制高亮区域
            updateTipPos();     //更新高亮位置
        }
    }


    /**
     * 将需要高亮的view增加到帧布局上方
     */
    private void addViewForTip() {
        for (HighLight.ViewPosInfo viewPosInfo : mViewRects) {
            View view = mInflater.inflate(viewPosInfo.layoutId, this, false);

            LayoutParams params = buildTipLayoutParams(view, viewPosInfo);
            if (params == null)
                continue;
            params.leftMargin = (int) viewPosInfo.marginInfo.leftMargin;
            params.topMargin = (int) viewPosInfo.marginInfo.topMargin;
            params.rightMargin = (int) viewPosInfo.marginInfo.rightMargin;
            params.bottomMargin = (int) viewPosInfo.marginInfo.bottomMargin;
            Log.e("Tag-LightView:", String.valueOf(viewPosInfo.marginInfo.leftMargin));

            if (params.rightMargin != 0) {
                params.gravity = Gravity.RIGHT;
            } else {
                params.gravity = Gravity.LEFT;
            }
            if (params.bottomMargin != 0) {
                params.gravity |= Gravity.BOTTOM;   //|=符号，a|=b的意思就是把a和b按位或然后赋值给a
            } else {
                params.gravity |= Gravity.TOP;
            }

            addView(view, params);
        }
    }


    /**
     * 更新高亮位置
     */
    private void updateTipPos() {
        for (int i = 0, n = getChildCount(); i < n; i++) {
            View view = getChildAt(i);
            HighLight.ViewPosInfo viewPosInfo = mViewRects.get(i);

            LayoutParams params = buildTipLayoutParams(view, viewPosInfo);
            if (params == null)
                continue;
            view.setLayoutParams(params);
        }
    }

    //设置高亮区域参数
    private LayoutParams buildTipLayoutParams(View view, HighLight.ViewPosInfo viewPosInfo) {
        LayoutParams params = (LayoutParams) view.getLayoutParams();
        if (params.leftMargin == (int) viewPosInfo.marginInfo.leftMargin &&
                params.topMargin == (int) viewPosInfo.marginInfo.topMargin &&
                params.rightMargin == (int) viewPosInfo.marginInfo.rightMargin &&
                params.bottomMargin == (int) viewPosInfo.marginInfo.bottomMargin) {
            return null;
        }
        params.leftMargin = (int) viewPosInfo.marginInfo.leftMargin;
        params.topMargin = (int) viewPosInfo.marginInfo.topMargin;
        params.rightMargin = (int) viewPosInfo.marginInfo.rightMargin;
        params.bottomMargin = (int) viewPosInfo.marginInfo.bottomMargin;

        if (params.rightMargin != 0) {
            params.gravity = Gravity.RIGHT;
        } else {
            params.gravity = Gravity.LEFT;
        }
        if (params.bottomMargin != 0) {
            params.gravity |= Gravity.BOTTOM;   //|=符号，a|=b的意思就是把a和b按位或然后赋值给a
        } else {
            params.gravity |= Gravity.TOP;
        }
        return params;
    }

    /**
     * 绘制高亮区域
     */
    private void buildMask() {
        mMaskBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mMaskBitmap);
        canvas.drawColor(maskColor);

        mPaint.setXfermode(MODE_DST_OUT);//图像混合模式
        mPaint.setColor(Color.parseColor("#00000000"));//透明

        if (isBlur) {
            mPaint.setMaskFilter(new BlurMaskFilter(this.blurSize, BlurMaskFilter.Blur.SOLID));
        }

        mHighLight.updateInfo();
        for (HighLight.ViewPosInfo viewPosInfo : mViewRects) {
            if (viewPosInfo.myShape != null) {
                switch (viewPosInfo.myShape) {
                    case CIRCULAR:
                        float width = viewPosInfo.rectF.width();
                        float height = viewPosInfo.rectF.height();
                        float circle_center1;
                        float circle_center2;
                        double radius = Math.sqrt(Math.pow(width / 2, 2) + Math.pow(height / 2, 2));//勾股定理
                        circle_center1 = width / 2;
                        circle_center2 = height / 2;
                        canvas.drawCircle(viewPosInfo.rectF.right - circle_center1, viewPosInfo.rectF.bottom - circle_center2,
                                (int) radius, mPaint);
                        if (isNeedBorder) {
                            drawCircleBorder(canvas, viewPosInfo, circle_center1, circle_center2, (int) radius);
                        }
                        break;
                    case RECTANGULAR:
                        canvas.drawRoundRect(viewPosInfo.rectF, this.radius, this.radius, mPaint);
                        if (isNeedBorder) {
                            drawRectBorder(canvas, viewPosInfo);
                        }
                        break;
                }
            } else {
                canvas.drawRoundRect(viewPosInfo.rectF, this.radius, this.radius, mPaint);
                if (isNeedBorder) {
                    drawRectBorder(canvas, viewPosInfo);
                }
            }
        }
    }


    /**
     * 绘制圆形边框
     */
    private void drawCircleBorder(Canvas canvas, HighLight.ViewPosInfo viewPosInfo, float circle_center1, float circle_center2, int radius) {
        Paint paint = new Paint();
        paint.reset();

        if (this.myType == HighLight.MyType.DASH_LINE) {
//            DashPathEffect是PathEffect类的一个子类,可以使paint画出类似虚线的样子,并且可以任意指定虚实的排列方式。
//            float数组,必须是偶数长度,且>=2,指定了多少长度的实线之后再画多少长度的空白。
            DashPathEffect pathEffect = new DashPathEffect(intervals, this.phase);
            paint.setPathEffect(pathEffect);
        }
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(dip2px(boderWidth));
        paint.setAntiAlias(true);
        paint.setColor(boderColor);
        Path path = new Path();
        path.addCircle(viewPosInfo.rectF.right - circle_center1, viewPosInfo.rectF.bottom - circle_center2,
                radius, Path.Direction.CW);//Path.Direction.CW--顺时针,Path.Direction.CCW--逆时针
        canvas.drawPath(path, paint);
    }


    /**
     * 绘制矩形边框
     */
    private void drawRectBorder(Canvas canvas, HighLight.ViewPosInfo viewPosInfo) {
        Paint paint = new Paint();
        paint.reset();
        if (this.myType == HighLight.MyType.DASH_LINE) {
            DashPathEffect pathEffect = new DashPathEffect(intervals, this.phase);
            paint.setPathEffect(pathEffect);
        }
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(dip2px(boderWidth));
        paint.setAntiAlias(true);
        paint.setColor(boderColor);

        Path path = new Path();
        path.addRect(viewPosInfo.rectF, Path.Direction.CW);
        canvas.drawPath(path, paint);
    }

    /*******************-------Set设置------**********************/
    /**
     * 设置是否需要边框
     */
    public void setIsNeedBorder(boolean isNeedBorder) {
        this.isNeedBorder = isNeedBorder;
    }

    /**
     * 设置边框颜色，需要setIsNeedBorder(true)，该方法才能生效
     */
    public void setBoderColor(int boderColor) {
        this.boderColor = boderColor;
    }

    /**
     * 设置边框宽度，需要setIsNeedBorder(true)，该方法才能生效；不需要转换单位，默认dp
     */
    public void setBoderWidth(float boderWidth) {
        this.boderWidth = boderWidth;
    }

    /**
     * 是否需要模糊边界
     */
    public void setIsBlur(boolean isBlur) {
        this.isBlur = isBlur;
    }

    /**
     * 设置模糊边界的宽度，需要setIsBlur(true)，该方法才能生效
     */
    public void setBlurWidth(int blurSize) {
        this.blurSize = blurSize;
        if (isBlur) {
            mPaint.setMaskFilter(new BlurMaskFilter(this.blurSize, BlurMaskFilter.Blur.SOLID));
        }
    }

    /**
     * 设置边框类型，需要setIsNeedBorder(true)，该方法才能生效
     */
    public void setMyType(HighLight.MyType myType) {
        this.myType = myType;
    }

    /**
     * 设置虚线边框的样式，需要setIsNeedBorder(true)并且边框类型为HighLight.MyType.DASH_LINE，该方法才能生效；
     * 不需要转换单位，默认dp
     * <p>
     * 必须是偶数长度,且>=2,指定了多少长度的实线之后再画多少长度的空白.
     * 如在 new float[] { 1, 2, 4, 8}中,表示先绘制长度1的实线,再绘制长度2的空白,再绘制长度4的实线,再绘制长度8的空白,依次重复
     */
    public void setIntervals(float[] intervals) {
        int length = intervals.length;
        if (length >= 2 && (length % 2 == 0)) {
            this.intervals = new float[length];
            for (int i = 0; i < length; i++) {
                this.intervals[i] = dip2px(intervals[i]);
            }
        } else {
            throw new IllegalArgumentException("元素的个数必须大于2并且是偶数");
        }
    }

    /**
     * 设置圆角度数
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }

    /**
     * 设置背景颜色
     */
    public void setMaskColor(int maskColor) {
        this.maskColor = maskColor;
    }

    private int dip2px(float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


}
