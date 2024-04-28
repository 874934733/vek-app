package com.yingyangfly.baselib.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * 操作View的工具类 on 2017/11/11.
 * http://blog.csdn.net/itrenj/article/details/53890118
 */

public class ViewUtils {
    private static final String FRAGMENT_CON = "NoSaveStateFrameLayout";

    private static ViewUtils viewUtils;
    private static Activity mActivity;
    private OnViewClickListener clickListener;

    public static ViewUtils getInsance(Activity activity) {
        mActivity = activity;
        if (viewUtils == null) {
            viewUtils = new ViewUtils();
        }
        return viewUtils;
    }


    /**
     * 在整个窗体上面增加一层布局
     * @param layoutId 布局id
     */
    public void addView(int layoutId) {
        final View view = View.inflate(mActivity, layoutId, null);
        FrameLayout frameLayout = (FrameLayout) getRootView();
        frameLayout.addView(view);

        //设置整个布局的单击监听
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(view);
                if (clickListener != null) {
                    clickListener.onClick(view);
                }
            }
        });
    }


    /**
     * 移除View
     * @param view 需要移除的视图
     */
    public void removeView(View view) {
        FrameLayout frameLayout = (FrameLayout) getRootView();
        frameLayout.removeView(view);
    }

    //@return 返回最顶层视图
    public ViewGroup getDeCorView() {
        return (ViewGroup) mActivity.getWindow().getDecorView();
    }

    // @return 返回内容区域根视图
    private ViewGroup getRootView() {
        return (ViewGroup) mActivity.findViewById(android.R.id.content);
    }

    /**
     * 获取子View 在 父View中的位置
     */
    public Rect getLocationInView(View parent, View child) {
        if (child == null || parent == null) {
            throw new IllegalArgumentException("parent and child can not be null");
        }
        View decorView = null;
        Context context = child.getContext();
        if (context instanceof Activity) {
            decorView = ((Activity) context).getWindow().getDecorView();
        }
        Rect result = new Rect();
        Rect tmpRect = new Rect();

        View tmp = child;
        if (child == parent) {
            child.getHitRect(result);
            return result;
        }

        while (tmp != decorView && tmp != parent) {
            tmp.getHitRect(tmpRect);
            if (!tmp.getClass().equals(FRAGMENT_CON)) {
                result.left += tmpRect.left;
                result.top += tmpRect.top;
            }
            tmp = (View) tmp.getParent();
        }

        result.right = result.left + child.getMeasuredWidth();
        result.bottom = result.top + child.getMeasuredHeight();
        return result;
    }

    public void setOnViewClickListener(OnViewClickListener clickListener) {
        this.clickListener = clickListener;
    }

    /**
     * 单击视图监听，用于多个引导页面时连续调用
     */
    public interface OnViewClickListener{
        //单击监听回调
        void onClick(View view);
    }

}
