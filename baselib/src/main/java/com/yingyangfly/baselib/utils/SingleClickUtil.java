package com.yingyangfly.baselib.utils;

import android.view.View;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author 王鹏鹏
 */
public class SingleClickUtil {

    public interface SingleClickListener {
        void onClick(View view);
    }

    public static void proxyOnClickListener(final View view, final SingleClickListener SingleClickListener) {
        proxyOnClickListener(1, view, SingleClickListener);
    }

    public static void proxyOnClickListener(int seconds, final View view, final SingleClickListener SingleClickListener) {

        ObservableOnSubscribe<View> subscribe = emitter -> view.setOnClickListener(v -> emitter.onNext(view));

        Observer<View> observer = new Observer<View>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(View view) {
                SingleClickListener.onClick(view);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        Observable.create(subscribe)
                .throttleFirst(seconds, TimeUnit.SECONDS)
                .subscribe(observer);
    }
}
