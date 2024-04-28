package com.yingyangfly.baselib.widget;

import android.app.Activity;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 设置View 的一些属性 on 2017/11/11.
 * 封装一些枚举与接口
 * 枚举主要是用于指定相关属性和提供回调。
 */

public class HighLight {

    private static final int DEFAULT_WIDTH_BLUR = 15;   //默认模糊边界的大小
    private static final int DEFAULT_RADIUS = 6;        //默认圆角度数

    //虚线的排列方式，需要setIsNeedBorder(true)并且边框类型为HighLight.MyType.DASH_LINE，该样式才能生效
    private float[] intervals = new float[]{4, 4};
    private float borderWidth = 3;                      //边框宽度，单位：dp，默认3dp

    private boolean shadow = false;                     //是否需要模糊化边界，默认不需要
    private boolean intercept = true;                   //是否拦截点击事件
    private boolean isNeedBorder = true;                //设置是否需要边框，默认需要
    private int maskColor = 0x99000000;                 // 默认背景颜色
    private int borderColor = maskColor;                //设置边框颜色，默认和背景颜色一样
    private int blurSize = DEFAULT_WIDTH_BLUR;          //模糊边界大小，默认15
    private int radius = DEFAULT_RADIUS;                //圆角大小，默认6

    private List<ViewPosInfo> mViewRects;               //保存高亮View的信息的集合

    private Activity mContext;                          // Activity对象
    private View mAnchor;                               //需要增加高亮区域的根布局
    private LightGuideView mLightGuideView;             //  表示高亮视图的对象
    private ViewUtils viewUtils;                        //ViewUtils对象

    private OnClickCallback clickCallback;              //点击事件的回调，要想点击有效果，必须设置intercept为TRUE

    private MyType myType = MyType.DASH_LINE;//边框类型,默认虚线 HighLight.MyType.DASH_LINE


    // 标识需要高亮的形状：圆形、矩形
    public enum MyShape {
        CIRCULAR, RECTANGULAR,
    }

    // 边框的样式，实线、虚线
    public enum MyType {
        FULL_LINE, DASH_LINE,//虚线
    }

    /**
     * 封装了  需要高亮view的信息
     * (静态类)
     */
    public static class ViewPosInfo {
        public RectF rectF;
        public View view;

        public int layoutId = -1;
        public MyShape myShape;

        public MarginInfo marginInfo;
        public OnPosCallback onPosCallback;
    }


    /**
     * 封装上下左右的边距
     */
    public static class MarginInfo {
        public float topMargin;
        public float bottomMargin;
        public float leftMargin;
        public float rightMargin;
    }

    //构造方法
    public HighLight(Activity activity) {
        this.mContext = activity;
        viewUtils = ViewUtils.getInsance(activity);
        mViewRects = new ArrayList<>();                         //保存高亮View的信息的集合
        mAnchor = activity.findViewById(android.R.id.content);  //需要增加高亮区域的根布局
    }

    /**
     * 增加高亮的布局 1
     *
     * @param viewId        需要高亮的控件id
     * @param decorLayoutId 布局文件资源id
     * @param onPosCallback 回调，用于设置位置
     */
    public HighLight addHighLight(int viewId, int decorLayoutId, OnPosCallback onPosCallback) {
        ViewGroup parent = (ViewGroup) mAnchor;
        View view = parent.findViewById(viewId);
        addHighLight(view, decorLayoutId, onPosCallback);
        return this;
    }

    /**
     * 增加高亮的布局 1-1
     *
     * @param view          高亮布局的视图
     * @param decorLayoutId 布局文件资源id
     * @param onPosCallback 回调，用于设置位置
     */
    private HighLight addHighLight(View view, int decorLayoutId, OnPosCallback onPosCallback) {
        ViewGroup parent = (ViewGroup) mAnchor;
        RectF rect = new RectF(viewUtils.getLocationInView(parent, view));

        ViewPosInfo viewPosInfo = new ViewPosInfo();
        viewPosInfo.layoutId = decorLayoutId;
        viewPosInfo.rectF = rect;
        viewPosInfo.view = view;

        if (onPosCallback == null && decorLayoutId != -1) {
            throw new IllegalArgumentException("参数错误：OnPosCallback == null && decorLayoutId != -1");
        }
        MarginInfo marginInfo = new MarginInfo();
        onPosCallback.getPos(parent.getWidth() - rect.right, parent.getHeight() - rect.bottom,
                rect, marginInfo);

        viewPosInfo.marginInfo = marginInfo;
        viewPosInfo.onPosCallback = onPosCallback;
        mViewRects.add(viewPosInfo);
        return this;
    }


    /**
     * 增加高亮布局 2
     *
     * @param viewId        需要高亮的控件id
     * @param decorLayoutId 布局文件资源id
     * @param onPosCallback 回调，用于设置位置
     * @param shape         指定高亮的形状，枚举类型
     * @return
     */
    public HighLight addHighLight(int viewId, int decorLayoutId, OnPosCallback onPosCallback, MyShape shape) {
        ViewGroup parent = (ViewGroup) mAnchor;
        View view = parent.findViewById(viewId);
        addHighLight(view, decorLayoutId, onPosCallback, shape);
        return this;
    }

    /**
     * 增加高亮布局 2-1
     *
     * @param view          高亮布局的视图
     * @param decorLayoutId 布局文件资源id
     * @param onPosCallback 回调，用于设置位置
     * @param shape         指定高亮的形状，枚举类型
     */
    private HighLight addHighLight(View view, int decorLayoutId, OnPosCallback onPosCallback, MyShape shape) {
        ViewGroup parent = (ViewGroup) mAnchor;
        RectF rect = new RectF(viewUtils.getLocationInView(parent, view));

        ViewPosInfo viewPosInfo = new ViewPosInfo();
        viewPosInfo.layoutId = decorLayoutId;
        viewPosInfo.rectF = rect;
        viewPosInfo.view = view;

        if (onPosCallback == null && decorLayoutId != -1) {
            throw new IllegalArgumentException("参数错误：OnPosCallback == null && decorLayoutId != -1");
        }

        MarginInfo marginInfo = new MarginInfo();
        onPosCallback.getPos(parent.getWidth() - rect.right, parent.getHeight() - rect.bottom,
                rect, marginInfo);
        viewPosInfo.marginInfo = marginInfo;
        viewPosInfo.myShape = shape;
        viewPosInfo.onPosCallback = onPosCallback;

        mViewRects.add(viewPosInfo);
        return this;
    }

    /**
     * 增加高亮布局 3
     *
     * @param rect          高亮布局的位置
     * @param decorLayoutId 布局文件资源id
     * @param onPosCallback 回调，用于设置位置
     */
    public HighLight addHighLight(RectF rect, int decorLayoutId, OnPosCallback onPosCallback) {
        ViewGroup parent = (ViewGroup) mAnchor;

        ViewPosInfo viewPosInfo = new ViewPosInfo();
        viewPosInfo.layoutId = decorLayoutId;
        viewPosInfo.rectF = rect;

        if (onPosCallback == null && decorLayoutId != -1) {
            throw new IllegalArgumentException("参数错误：OnPosCallback == null && decorLayoutId != -1");
        }

        MarginInfo marginInfo = new MarginInfo();
        onPosCallback.getPos(parent.getWidth() - rect.right, parent.getHeight() - rect.bottom, rect, marginInfo);
        viewPosInfo.marginInfo = marginInfo;
        viewPosInfo.onPosCallback = onPosCallback;
        mViewRects.add(viewPosInfo);

        return this;
    }


    /**
     * 将一个布局文件加到根布局上，默认点击移除视图
     */
    public HighLight addLayout(int layoutId) {
        viewUtils.addView(layoutId);
        viewUtils.setOnViewClickListener(new ViewUtils.OnViewClickListener() {
            @Override
            public void onClick(View view) {
                if (intercept && (HighLight.this.clickCallback != null)) {
                    HighLight.this.clickCallback.onClick();
                }
            }
        });
        return this;
    }


    /**
     * 更新位置信息
     */
    public void updateInfo() {
        ViewGroup parent = (ViewGroup) mAnchor;
        for (ViewPosInfo viewPosInfo : mViewRects) {
            viewPosInfo.onPosCallback.getPos(parent.getWidth() - viewPosInfo.rectF.right,
                    parent.getHeight() - viewPosInfo.rectF.bottom,
                    viewPosInfo.rectF, viewPosInfo.marginInfo);
        }
    }


    /**
     * 显示含有高亮区域的页面
     */
    public void show() {
        if (mLightGuideView != null)
            return;

        LightGuideView lightGuideView = new LightGuideView(mContext, this, maskColor, mViewRects);
        // 设置是否需要模糊边界和模糊边界的大小
        lightGuideView.setIsBlur(this.shadow);
        if (this.shadow) {
            lightGuideView.setBlurWidth(this.blurSize);
        }
        // 设置边框的相关配置
        lightGuideView.setIsNeedBorder(this.isNeedBorder);
        if (this.isNeedBorder) {
            lightGuideView.setBoderColor(this.borderColor);
            lightGuideView.setBoderWidth(this.borderWidth);
            lightGuideView.setMyType(this.myType);
            // 是虚线才需要设置虚线样式
            if (this.myType == MyType.DASH_LINE)
                lightGuideView.setIntervals(this.intervals);
        }
        lightGuideView.setRadius(this.radius);
        lightGuideView.setMaskColor(maskColor);
        if ("FrameLayout".equals(mAnchor.getClass().getSimpleName())) {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ((ViewGroup) mAnchor).addView(lightGuideView, ((ViewGroup) mAnchor).getChildCount(), params);
        } else {
            FrameLayout frameLayout = new FrameLayout(mContext);
            ViewGroup parent = (ViewGroup) mAnchor.getParent();
            parent.removeView(mAnchor);
            parent.addView(frameLayout, mAnchor.getLayoutParams());
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            frameLayout.addView(mAnchor, params);
            frameLayout.addView(lightGuideView);
        }

        //是否拦截点击事件
        if (intercept) {
            lightGuideView.setOnClickListener(v -> {
                remove();
                if (clickCallback != null) {
                    clickCallback.onClick();
                }
            });
        }

        mLightGuideView = lightGuideView;
    }


    /**
     * 移除含有高亮区域的页面
     */
    public void remove() {
        if (mLightGuideView == null)
            return;
        ViewGroup parent = (ViewGroup) mLightGuideView.getParent();
        if (parent instanceof RelativeLayout || parent instanceof FrameLayout) {
            parent.removeView(mLightGuideView);
        } else {
            parent.removeView(mLightGuideView);
            View origin = parent.getChildAt(0);
            ViewGroup graParent = (ViewGroup) parent.getParent();
            graParent.removeView(parent);
            graParent.addView(origin, parent.getLayoutParams());
        }
        mLightGuideView = null;
    }


    /*****************-------Set设置-------********************/
    /**
     * 1. 绑定根布局，需要高亮显示部分区域时需要第一个调用的方法
     * (如果是在Activity中调用，可以不调用)
     */
    public HighLight anchor(View anchor) {
        mAnchor = anchor;   //需要增加高亮区域的根布局
        return this;
    }

    /**
     * 2. 设置是否需要拦截点击事件
     */
    public HighLight setIntercept(boolean intercept) {
        this.intercept = intercept;
        return this;
    }

    /**
     * 3. 设置是否需要模糊化边框，默认不需要
     */
    public HighLight setShadow(boolean shadow) {
        this.shadow = shadow;
        return this;
    }

    /**
     * 4. 设置背景颜色，默认 99000000
     */
    public HighLight setMaskColor(int maskColor) {
        this.maskColor = maskColor;
        return this;
    }


    /**
     * 5. 设置边框类型，需要setIsNeedBorder(true)，该方法才能生效
     */
    public HighLight setMyBroderType(MyType myType) {
        this.myType = myType;
        return this;
    }

    /**
     * 6. 设置边框宽度，需要setIsNeedBorder(true)，该方法才能生效；不需要转换单位，默认dp
     */
    public HighLight setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
        return this;
    }

    /**
     * 7. 设置虚线边框的样式，需要setIsNeedBorder(true)并且边框类型为HighLight.MyType.DASH_LINE，该方法才能生效；不需要转换单位，默认dp
     * <p>
     * 必须是偶数长度,且>=2,指定了多少长度的实线之后再画多少长度的空白.
     * 如在 new float[] { 1, 2, 4, 8}中,表示先绘制长度1的实线,再绘制长度2的空白,
     * 再绘制长度4的实线,再绘制长度8的空白,依次重复
     */
    public HighLight setIntervals(float[] intervals) {
        int length = intervals.length;
        if ((length >= 2) && (length % 2 == 0)) {
            this.intervals = intervals;
        } else {
            throw new IllegalArgumentException("元素的个数必须大于2并且是偶数");
        }
        return this;
    }

    /**
     * 8. 设置是否需要边框
     */
    public HighLight setIsNeedBorder(boolean isNeedBorder) {
        this.isNeedBorder = isNeedBorder;
        return this;
    }

    /**
     * 9. 设置模糊边界的宽度，需要setIsBlur(true)，该方法才能生效
     */
    public HighLight setBlurSize(int blurSize) {
        this.blurSize = blurSize;
        return this;
    }

    /**
     * 10. 设置圆角度数
     */
    public HighLight setRadius(int radius) {
        this.radius = radius;
        return this;
    }

    /**
     * 11. 设置边框颜色，需要setIsNeedBorder(true)，该方法才能生效
     */
    public HighLight setBorderColor(int borderColor) {
        this.borderColor = borderColor;
        return this;
    }

    /**
     * 12. 一个场景可能有多个步骤的高亮。一个步骤完成之后再进行下一个步骤的高亮,
     * 添加点击事件，将每次点击传给应用逻辑
     *
     * @param clickCallback 设置整个引导的点击事件
     */
    public HighLight setOnClickCallback(OnClickCallback clickCallback) {
        this.clickCallback = clickCallback;
        return this;
    }


    // 增加高亮View的回调
    public interface OnPosCallback {
        //封装了高亮View的位置信息
        void getPos(float rightMargin, float bottomMargin, RectF rectF, MarginInfo marginInfo);
    }

    // 点击回调接口
    public interface OnClickCallback {
        //点击回调方法，要想点击有效果，必须设置intercept为TRUE
        void onClick();
    }

}
