package com.yingyangfly.baselib.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.lang.reflect.Field;

public class ViewTool {

    public static int heightPixels;
    public static int widthPixels;

    @SuppressLint("NewApi")
    public static void initPixels(View views, int width, int height) {
        if (views instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) views;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View view = viewGroup.getChildAt(i);
                ViewGroup.LayoutParams lp = view.getLayoutParams();
                if (lp.height == lp.width) {
                    if (lp.height > 0) {
                        lp.height = (int) (((float) lp.width) / width * widthPixels);
                    }
                    if (lp.width > 0) {
                        lp.width = (int) (((float) lp.width) / width * widthPixels);
                    }
                } else {
                    if (lp.height > 5) {
                        lp.height = (int) (((float) lp.height) / height * heightPixels);
                    }
                    if (lp.width > 5) {
                        lp.width = (int) (((float) lp.width) / width * widthPixels);
                    }
                }
                int bottom = view.getPaddingBottom();
                int left = view.getPaddingLeft();
                int right = view.getPaddingRight();
                int top = view.getPaddingTop();
                if (bottom > 0) {
                    bottom = (int) (((float) bottom) / height * heightPixels);
                }
                if (top > 0) {
                    top = (int) (((float) top) / height * heightPixels);
                }
                if (left > 0) {
                    left = (int) (((float) left) / width * widthPixels);
                }
                if (right > 0) {
                    right = (int) (((float) right) / width * widthPixels);
                }
                view.setPadding(left, top, right, bottom);
                if (views instanceof LinearLayout) {
                    LinearLayout.LayoutParams lllp = (LinearLayout.LayoutParams) lp;
                    lllp.leftMargin = (int) (((float) lllp.leftMargin) / width * widthPixels);
                    lllp.rightMargin = (int) (((float) lllp.rightMargin)
                            / width * widthPixels);
                    lllp.topMargin = (int) (((float) lllp.topMargin) / height * heightPixels);
                    lllp.bottomMargin = (int) (((float) lllp.bottomMargin)
                            / height * heightPixels);
                } else if (views instanceof FrameLayout) {
                    FrameLayout.LayoutParams fllp = (FrameLayout.LayoutParams) lp;
                    fllp.leftMargin = (int) (((float) fllp.leftMargin) / width * widthPixels);
                    fllp.rightMargin = (int) (((float) fllp.rightMargin)
                            / width * widthPixels);
                    fllp.topMargin = (int) (((float) fllp.topMargin) / height * heightPixels);
                    fllp.bottomMargin = (int) (((float) fllp.bottomMargin)
                            / height * heightPixels);
                } else if (views instanceof ConstraintLayout) {
                    ConstraintLayout.LayoutParams fllp = (ConstraintLayout.LayoutParams) lp;
                    fllp.leftMargin = (int) (((float) fllp.leftMargin) / width * widthPixels);
                    fllp.rightMargin = (int) (((float) fllp.rightMargin)
                            / width * widthPixels);
                    fllp.topMargin = (int) (((float) fllp.topMargin) / height * heightPixels);
                    fllp.bottomMargin = (int) (((float) fllp.bottomMargin)
                            / height * heightPixels);
                } else if (views instanceof RelativeLayout) {
                    RelativeLayout.LayoutParams fllp = (RelativeLayout.LayoutParams) lp;
                    fllp.leftMargin = (int) (((float) fllp.leftMargin) / width * widthPixels);
                    fllp.rightMargin = (int) (((float) fllp.rightMargin)
                            / width * widthPixels);
                    fllp.topMargin = (int) (((float) fllp.topMargin) / height * heightPixels);
                    fllp.bottomMargin = (int) (((float) fllp.bottomMargin)
                            / height * heightPixels);
                } else if (views instanceof CardView) {
                    RelativeLayout.LayoutParams fllp = (RelativeLayout.LayoutParams) lp;
                    fllp.leftMargin = (int) (((float) fllp.leftMargin) / width * widthPixels);
                    fllp.rightMargin = (int) (((float) fllp.rightMargin)
                            / width * widthPixels);
                    fllp.topMargin = (int) (((float) fllp.topMargin) / height * heightPixels);
                    fllp.bottomMargin = (int) (((float) fllp.bottomMargin)
                            / height * heightPixels);
                }
                if (view instanceof TextView) {
                    TextView textView = (TextView) view;
                    float sp = (float) (height) / (float) (heightPixels);
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textView.getTextSize() / sp);
                } else if (view instanceof Button) {
                    Button button = (Button) view;
                    float sp = (float) (height) / (float) (heightPixels);
                    button.setTextSize(TypedValue.COMPLEX_UNIT_PX, button.getTextSize() / sp);
                } else if (view instanceof ViewGroup) {
                    initPixels(view, width, height);
                }
            }
        }
    }

    public static View inflateLayoutPixels(Context context, View views, int width, int height) {
        if (views == null) {
            return null;
        }
        DisplayMetrics dm2 = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm2);
        if (heightPixels <= 0) {
            heightPixels = dm2.heightPixels;
            widthPixels = dm2.widthPixels;
            Class<?> c = null;
            Object obj = null;
            Field field = null;
            int x = 0, sbar = 0;
            try {
                c = Class.forName("com.android.internal.R$dimen");
                obj = c.newInstance();
                field = c.getField("status_bar_height");
                x = Integer.parseInt(field.get(obj).toString());
                sbar = context.getResources().getDimensionPixelSize(x);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            heightPixels = heightPixels - sbar;
        }
        Resources resources = context.getResources();
        float heightPixelsN, widthPixelsN;
        heightPixelsN = heightPixels;
        widthPixelsN = widthPixels;
        int rid = resources.getIdentifier("config_showNavigationBar", "bool",
                "android");
        if (resources.getBoolean(rid)) {
            // 获取导航栏是否显示true or false
            int resourceId = resources.getIdentifier("navigation_bar_height",
                    "dimen", "android");
            if (resourceId > 0) {
                if (isScreenChange(context)) {
                    widthPixelsN += resources.getDimensionPixelSize(resourceId);
                } else {
                    heightPixelsN += resources
                            .getDimensionPixelSize(resourceId);
                }
            }
        }
        if (widthPixelsN / heightPixelsN == 9.0 / 16.0) {
            width = (int) ((9.0 / 16.0) * height);

        } else if (widthPixelsN / heightPixelsN == 10.0 / 16.0) {
            width = (int) ((10.0 / 16.0) * height);
        }
        initPixels(views, width, height);
        ViewGroup.LayoutParams lp = views.getLayoutParams();
        if (lp != null) {

            if (lp.height > 0) {
                lp.height = (int) (((float) lp.height) / height * heightPixels);
            }
            if (lp.width > 0) {
                lp.width = (int) (((float) lp.width) / width * widthPixels);
            }
            views.setLayoutParams(lp);
        }
        return views;
    }

    public static View inflateLayoutPixelsById(Context context, int layoutId,
                                               int width, int height) {
        View views = LayoutInflater.from(context).inflate(layoutId, null);
        if (views == null) {
            return null;
        }
        DisplayMetrics dm2 = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(dm2);
        if (heightPixels <= 0) {
            heightPixels = dm2.heightPixels;
            widthPixels = dm2.widthPixels;
            Class<?> c = null;
            Object obj = null;
            Field field = null;
            int x = 0, sbar = 0;
            try {
                c = Class.forName("com.android.internal.R$dimen");
                obj = c.newInstance();
                field = c.getField("status_bar_height");
                x = Integer.parseInt(field.get(obj).toString());
                sbar = context.getResources().getDimensionPixelSize(x);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            heightPixels = heightPixels - sbar;
        }

        Resources resources = context.getResources();
        float heightPixelsN, widthPixelsN;
        heightPixelsN = heightPixels;
        widthPixelsN = widthPixels;
        int rid = resources.getIdentifier("config_showNavigationBar", "bool",
                "android");
        if (resources.getBoolean(rid)) {
            // 获取导航栏是否显示true or false
            int resourceId = resources.getIdentifier("navigation_bar_height",
                    "dimen", "android");
            if (resourceId > 0) {
                if (isScreenChange(context)) {
                    widthPixelsN += resources.getDimensionPixelSize(resourceId);
                } else {
                    heightPixelsN += resources
                            .getDimensionPixelSize(resourceId);
                }
            }
        }
        if (widthPixelsN / heightPixelsN == 9.0 / 16.0) {
            width = (int) ((9.0 / 16.0) * height);

        } else if (widthPixelsN / heightPixelsN == 10.0 / 16.0) {
            width = (int) ((10.0 / 16.0) * height);
        }
        initPixels(views, width, height);
        ViewGroup.LayoutParams lp = views.getLayoutParams();
        if (lp != null) {
            if (lp.height > 0) {
                lp.height = (int) (((float) lp.height) / height * heightPixels);
            }
            if (lp.width > 0) {
                lp.width = (int) (((float) lp.width) / width * widthPixels);
            }
            views.setLayoutParams(lp);
        }
        return views;
    }

    public static View inflateFragmentPixels(Context context, int layoutId, ViewGroup container,
                                             int width, int height) {
        View views = LayoutInflater.from(context).inflate(layoutId, container, false);
        if (views == null) {
            return null;
        }
        DisplayMetrics dm2 = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(dm2);
        if (heightPixels <= 0) {
            heightPixels = dm2.heightPixels;
            widthPixels = dm2.widthPixels;
            Class<?> c = null;
            Object obj = null;
            Field field = null;
            int x = 0, sbar = 0;
            try {
                c = Class.forName("com.android.internal.R$dimen");
                obj = c.newInstance();
                field = c.getField("status_bar_height");
                x = Integer.parseInt(field.get(obj).toString());
                sbar = context.getResources().getDimensionPixelSize(x);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            heightPixels = heightPixels - sbar;
        }

        Resources resources = context.getResources();
        float heightPixelsN, widthPixelsN;
        heightPixelsN = heightPixels;
        widthPixelsN = widthPixels;
        int rid = resources.getIdentifier("config_showNavigationBar", "bool",
                "android");
        if (resources.getBoolean(rid)) {
            // 获取导航栏是否显示true or false
            int resourceId = resources.getIdentifier("navigation_bar_height",
                    "dimen", "android");
            if (resourceId > 0) {
                if (isScreenChange(context)) {
                    widthPixelsN += resources.getDimensionPixelSize(resourceId);
                } else {
                    heightPixelsN += resources
                            .getDimensionPixelSize(resourceId);
                }
            }
        }

        if (widthPixelsN / heightPixelsN == 9.0 / 16.0) {
            width = (int) ((9.0 / 16.0) * height);
        } else if (widthPixelsN / heightPixelsN == 10.0 / 16.0) {
            width = (int) ((10.0 / 16.0) * height);
        }
        initPixels(views, width, height);
        ViewGroup.LayoutParams lp = views.getLayoutParams();
        if (lp != null) {
            if (lp.height > 0) {
                lp.height = (int) (((float) lp.height) / height * heightPixels);
            }
            if (lp.width > 0) {
                lp.width = (int) (((float) lp.width) / width * widthPixels);
            }
            views.setLayoutParams(lp);
        }
        return views;
    }

    public static View inflateFragmentPixels(Context context, View views, int width, int height) {
        if (views == null) {
            return null;
        }
        DisplayMetrics dm2 = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(dm2);
        if (heightPixels <= 0) {
            heightPixels = dm2.heightPixels;
            widthPixels = dm2.widthPixels;
            Class<?> c = null;
            Object obj = null;
            Field field = null;
            int x = 0, sbar = 0;
            try {
                c = Class.forName("com.android.internal.R$dimen");
                obj = c.newInstance();
                field = c.getField("status_bar_height");
                x = Integer.parseInt(field.get(obj).toString());
                sbar = context.getResources().getDimensionPixelSize(x);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            heightPixels = heightPixels - sbar;
        }
        Resources resources = context.getResources();
        float heightPixelsN, widthPixelsN;
        heightPixelsN = heightPixels;
        widthPixelsN = widthPixels;
        int rid = resources.getIdentifier("config_showNavigationBar", "bool",
                "android");
        if (resources.getBoolean(rid)) {
            // 获取导航栏是否显示true or false
            int resourceId = resources.getIdentifier("navigation_bar_height",
                    "dimen", "android");
            if (resourceId > 0) {
                if (isScreenChange(context)) {
                    widthPixelsN += resources.getDimensionPixelSize(resourceId);
                } else {
                    heightPixelsN += resources
                            .getDimensionPixelSize(resourceId);
                }
            }
        }

        if (widthPixelsN / heightPixelsN == 9.0 / 16.0) {
            width = (int) ((9.0 / 16.0) * height);
        } else if (widthPixelsN / heightPixelsN == 10.0 / 16.0) {
            width = (int) ((10.0 / 16.0) * height);
        }
        initPixels(views, width, height);
        ViewGroup.LayoutParams lp = views.getLayoutParams();
        if (lp != null) {
            if (lp.height > 0) {
                lp.height = (int) (((float) lp.height) / height * heightPixels);
            }
            if (lp.width > 0) {
                lp.width = (int) (((float) lp.width) / width * widthPixels);
            }
            views.setLayoutParams(lp);
        }
        return views;
    }

    public static boolean isScreenChange(Context context) {
        Configuration mConfiguration = context.getResources()
                .getConfiguration();  // 获取设置的配置信息
        int ori = mConfiguration.orientation; // 获取屏幕方向
        if (ori == mConfiguration.ORIENTATION_LANDSCAPE) {
            // 横屏
            return true;
        } else if (ori == mConfiguration.ORIENTATION_PORTRAIT) {
            // 竖屏
            return false;
        }
        return false;
    }

    public static View inflateFragmentPixelsById(Context context, int layoutId, ViewGroup container,
                                                 int width, int height) {
        View views = LayoutInflater.from(context).inflate(layoutId, container);
        if (views == null) {
            return null;
        }
        DisplayMetrics dm2 = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(dm2);
        if (heightPixels <= 0) {
            heightPixels = dm2.heightPixels;
            widthPixels = dm2.widthPixels;
            Class<?> c = null;
            Object obj = null;
            Field field = null;
            int x = 0, sbar = 0;
            try {
                c = Class.forName("com.android.internal.R$dimen");
                obj = c.newInstance();
                field = c.getField("status_bar_height");
                x = Integer.parseInt(field.get(obj).toString());
                sbar = context.getResources().getDimensionPixelSize(x);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            heightPixels = heightPixels - sbar;
        }
        Resources resources = context.getResources();
        float heightPixelsN, widthPixelsN;
        heightPixelsN = heightPixels;
        widthPixelsN = widthPixels;
        int rid = resources.getIdentifier("config_showNavigationBar", "bool",
                "android");
        if (resources.getBoolean(rid)) {
            // 获取导航栏是否显示true or false
            int resourceId = resources.getIdentifier("navigation_bar_height",
                    "dimen", "android");
            if (resourceId > 0) {
                if (isScreenChange(context)) {
                    widthPixelsN += resources.getDimensionPixelSize(resourceId);
                } else {
                    heightPixelsN += resources
                            .getDimensionPixelSize(resourceId);
                }
            }
        }

        if (widthPixelsN / heightPixelsN == 9.0 / 16.0) {
            width = (int) ((9.0 / 16.0) * height);
        } else if (widthPixelsN / heightPixelsN == 10.0 / 16.0) {
            width = (int) ((10.0 / 16.0) * height);
        }
        initPixels(views, width, height);
        ViewGroup.LayoutParams lp = views.getLayoutParams();
        if (lp != null) {
            if (lp.height > 0) {
                lp.height = (int) (((float) lp.height) / height * heightPixels);
            }
            if (lp.width > 0) {
                lp.width = (int) (((float) lp.width) / width * widthPixels);
            }
            views.setLayoutParams(lp);
        }
        return views;
    }
}
