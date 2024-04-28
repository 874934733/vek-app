package com.yingyangfly.baselib.net;

import android.content.Context;

import com.yingyangfly.baselib.BaseApplication;
import com.yingyangfly.baselib.dialog.LoadingDialog;
import com.yingyangfly.baselib.utils.LogUtil;
import com.yingyangfly.baselib.utils.NetWorkUtils;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Author: Austin
 * Date: 2018/10/9
 * Description:
 */
public class MyRxScheduler {
    public static <T> ObservableTransformer<T,T> applySchedulers() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> ObservableTransformer<T,T> ioMain() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    if (!NetWorkUtils.Companion.isConnectedByState(BaseApplication.appContext)) {
                        disposable.dispose();
                        LogUtil.Companion.d("doOnSubscribe -- There's no network link.");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> ObservableTransformer<T,T> ioMain(Context context,Boolean isShowDialog) {

        final LoadingDialog loadingDialog = new LoadingDialog(context);
        return observable -> observable.subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    if (!NetWorkUtils.Companion.isConnectedByState(BaseApplication.appContext)) {
                        disposable.dispose();
                        LogUtil.Companion.d("doOnSubscribe -- There's no network link.");
                    } else {
//                        LogUtil.Companion.d("doOnSubscribe -- There's a network link.");
                        if (!loadingDialog.isShowing() && isShowDialog)
                            loadingDialog.show();
                    }
                })
                .doFinally(() -> {
                    if (loadingDialog != null && loadingDialog.isShowing())
                        loadingDialog.dismiss();

                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> ObservableTransformer<T,T> ioMain(Context context) {
        return ioMain(context,true);
    }
}
