package com.yingyangfly.baselib.utils;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yingyangfly.baselib.R;

/**
 * @author edz
 */
public class ToastUtils {

    private Toast mToast;
    private TextView mTextView;
    private TimeCount timeCount;
    private String message;
    private Handler mHandler = new Handler();
    private boolean canceled = true;

    public ToastUtils(Context context, int layoutId, String msg) {
        message = msg;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //自定义布局
        View view = inflater.inflate(layoutId, null);
        //自定义toast文本
        mTextView = view.findViewById(R.id.tvToast);
        if(mTextView!=null) {
            mTextView.setText(msg);
        }
//        if (mToast == null) {
            mToast = new Toast(context);
//        }
        //设置toast居中显示
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setView(view);
    }

    /**
     * 自定义居中显示toast
     */
    public void show() {
        mToast.show();
    }

    /**
     * 自定义时长、居中显示toast
     *
     * @param duration
     */
    public void show(int duration) {
        timeCount = new TimeCount(duration, 1000);
        if (canceled) {
            timeCount.start();
            canceled = false;
            showUntilCancel();
        }
    }

    /**
     * 隐藏toast
     */
    private void hide() {
        if (mToast != null) {
            mToast.cancel();
        }
        canceled = true;
    }

    private void showUntilCancel() {
        if (canceled) {
            //如果已经取消显示，就直接return
            return;
        }
        mToast.show();
        mHandler.postDelayed(this::showUntilCancel, Toast.LENGTH_LONG);
    }

    /**
     * 自定义计时器
     */
    private class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            //millisInFuture总计时长，countDownInterval时间间隔(一般为1000ms)
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mTextView.setText(message);
        }

        @Override
        public void onFinish() {
            hide();
        }
    }
}
