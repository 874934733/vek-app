package com.yingyangfly.baselib.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.yingyangfly.baselib.utils.img.GlideRoundTransform;
import com.yingyangfly.baselib.utils.img.TransformationUtils;

import java.io.ByteArrayOutputStream;

import io.reactivex.annotations.Nullable;

/**
 * Author: YongChao
 * Date: 19-8-21 下午5:49
 * Description:
 */
public class GlideImgUtil {

    public static void loadImgFace(Context context, String face_url, final ImageView img) {
        RequestOptions options = new RequestOptions()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(context)
                .load(face_url)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        img.setImageDrawable(resource);
                    }
                });
    }

    public static void loadImgCircle(Context context, String imgUrl, ImageView img, int dPlaceHolder, int dError) {
        RequestOptions options = new RequestOptions()
                .placeholder(dPlaceHolder)
                .error(dError)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(context).load(imgUrl).apply(options).into(img);
    }

    public static void loadImgHeadCircle(Context context, String imgUrl, ImageView img, int dPlaceHolder, int dError) {
        RequestOptions options = new RequestOptions()
                .placeholder(dPlaceHolder)
                .error(dError)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .diskCacheStrategy(DiskCacheStrategy.NONE);

        Glide.with(context).load(imgUrl).apply(options).into(img);
    }

    /**
     * @param: [context, imgUrl, img]
     * @return: void
     * @description: 设置加载占位符及加载失败
     */
    public static void loadImgPlaceHolderError(Context context, String imgUrl, ImageView img, int dPlaceHolder, int dError) {
        RequestOptions options = new RequestOptions()
                .placeholder(dPlaceHolder)
                .error(dError)
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(context).load(imgUrl).apply(options).into(img);
    }

    public static void loadImg(Context context, String imgUrl, ImageView img) {
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(imgUrl).apply(options).into(img);
    }

    public static void loadImgNoPlaceHolder(Context context, String imgUrl, ImageView img) {
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(imgUrl).apply(options).into(img);
    }

    public static void loadGifImg(Context context, int imgUrl, ImageView img) {
        Glide.with(context).asGif().load(imgUrl).into(img);
    }

    public static void zoomImg(Context context, String imgUrl, ImageView img) {
        Glide.with(context)
                .asBitmap()
                .load(imgUrl)
                .into(new TransformationUtils(img));
    }

    /**
     * 圆角图片
     *
     * @param context
     * @param imgUrl
     * @param img
     * @param dPlaceHolder
     * @param dError
     */
    public static void loadRoundImg(Context context, String imgUrl, ImageView img, int dPlaceHolder, int dError) {
        loadRoundImg(context, imgUrl, img, dPlaceHolder, dError, 5);
    }

    public static void loadRoundImg(Context context, String imgUrl, ImageView img, int dPlaceHolder, int dError, int radius) {
        RequestOptions options = new RequestOptions()
                .placeholder(dPlaceHolder)
                .error(dError)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(imgUrl).transform(new GlideRoundTransform(context, radius)).apply(options).into(img);
    }

    public static void loadRoundImg(Context context, int imgLocoalSrc, ImageView img, int radius) {
        Glide.with(context).load(imgLocoalSrc).transform(new GlideRoundTransform(context, radius)).into(img);
    }

    public static void loadBitmap(Context context, Bitmap bitmap, ImageView imageView) {
        Glide.with(context).load(bitmap).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);
    }

    public static void loadImage(Context context, int id, ImageView imageView) {
        Glide.with(context).load(id).into(imageView);
    }

    /**
     * Bitmap转换成byte[]并且进行压缩,压缩到不大于maxkb
     *
     * @param bitmap
     * @param maxKb
     * @return
     */
    public static byte[] bmpToByteArray(Bitmap bitmap, long maxKb, boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
        int options = 100;
//        int fileSize = output.toByteArray().length;
//        options = (int) Math.max(1, maxKb * 100 / fileSize);
//        LogUtil.Companion.i("output option " + options);
        while (output.toByteArray().length > maxKb && options > 0) {
            output.reset(); //清空output
            bitmap.compress(
                    Bitmap.CompressFormat.JPEG,
                    options,
                    output
            ); //这里压缩options%，把压缩后的数据存放到output中
//            LogUtil.Companion.i("output " + output.toByteArray().length);
            options -= 10;
        }
        if (needRecycle) {
            bitmap.recycle();
        }
        return output.toByteArray();
    }


    /**
     * 加载自定义宽高圆角图片
     */
    public static void loadRoundImgWidth(Context context, String imgUrl, ImageView img, int dPlaceHolder, int dError, int radius, int width) {
        RequestOptions options = new RequestOptions()
                .placeholder(dPlaceHolder)
                .error(dError)
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        ViewGroup.LayoutParams params = img.getLayoutParams();
        params.height = width;
        params.width = width;
        img.setLayoutParams(params);

        Glide.with(context).load(imgUrl).transform(new GlideRoundTransform(context, radius)).apply(options).into(img);
    }

    /**
     * 加载自定义宽高圆角图片
     */
    public static void loadRoundImgWidthThumbnail(Context context, String imgUrl, ImageView img, int dPlaceHolder, int dError, int radius, int width) {
        RequestOptions options = new RequestOptions()
                .placeholder(dPlaceHolder)
                .error(dError)
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        ViewGroup.LayoutParams params = img.getLayoutParams();
        params.height = width;
        params.width = width;
        img.setLayoutParams(params);
        Glide.with(context).load(imgUrl).thumbnail(0.1f).transform(new GlideRoundTransform(context, radius)).apply(options).into(img);
    }

    /**
     * 加载自定义宽高圆角图片
     */
    public static void loadRoundImgWidthThumbnail(Context context, String imgUrl, ImageView img, int dPlaceHolder, int dError, int radius, int width, float sizeMultiplier) {
        RequestOptions options = new RequestOptions()
                .placeholder(dPlaceHolder)
                .error(dError)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        ViewGroup.LayoutParams params = img.getLayoutParams();
        params.height = width;
        params.width = width;
        img.setLayoutParams(params);

        Glide.with(context).load(imgUrl).thumbnail(sizeMultiplier).transform(new GlideRoundTransform(context, radius)).apply(options).into(img);
    }

    public static Bitmap convertBitmapSize(Bitmap bm, int newWidth, int newHeight) { // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = Float.parseFloat(newWidth + "") / width;
        float scaleHeight = Float.parseFloat(newHeight + "") / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
    }

}
