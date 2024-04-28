package com.yingyangfly.baselib.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

/**
 * @author wangsai
 * @date 2022/9/15 11:58
 * @description 图片处理工具类
 */
object ImageUtil {

    /**
     * 加载网络图片
     * @param url 图片地址
     * @param isCircle 是否为圆形
     * @param roundRadius 圆角
     */
    fun ImageView.loadUrl(
        url: String?,
        @DrawableRes resId: Int = 0,
        isCircle: Boolean? = false,
        roundRadius: Float = 0F
    ) {
        val load = Glide.with(AppUtil.getContext())
            .load(url)
            .placeholder(resId)
            .format(DecodeFormat.PREFER_RGB_565)
            .diskCacheStrategy(DiskCacheStrategy.ALL)

        if (isCircle == true) {
            load.transform(CircleCrop()).into(this)
        } else if (roundRadius > 0) {
            load.transform(
                CenterCrop(),
                RoundedCorners(ScreenUtil.dp2px(roundRadius))
            ).into(this)
        } else {
            load.into(this)
        }
    }

    /**
     * 加载网络图片
     * @param url 图片地址
     * @param isCircle 是否为圆形
     * @param roundRadius 圆角
     */
    fun ImageView.loadUrl(
        url: String?,
        roundRadius: Float = 0F
    ) {
        val load = Glide.with(AppUtil.getContext())
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
        if (roundRadius > 0) {
            load.transform(
                CenterCrop(),
                RoundedCorners(ScreenUtil.dp2px(roundRadius))
            ).into(this)
        } else {
            load.into(this)
        }
    }

    /**
     * 加载网络图片
     * @param url 图片地址
     * @param isCircle 是否为圆形
     * @param roundRadius 圆角
     */
    fun ImageView.loadUrlExtra(
        url: String?, @DrawableRes resId: Int = 0, topLeft: Float = 0F,
        topRight: Float = 0F, bottomLeft: Float = 0F, bottomRight: Float = 0F,
    ) {
        val load = Glide.with(AppUtil.getContext())
            .load(url)
            .placeholder(resId)
            .format(DecodeFormat.PREFER_RGB_565)
            .diskCacheStrategy(DiskCacheStrategy.ALL)

        load.transform(
            CenterCrop(),
            GranularRoundedCorners(
                ScreenUtil.dp2px(topLeft).toFloat(),
                ScreenUtil.dp2px(topRight).toFloat(),
                ScreenUtil.dp2px(bottomRight).toFloat(),
                ScreenUtil.dp2px(bottomLeft).toFloat(),
            )
        ).into(this)
    }


    /**
     * @author dd
     * 加载网络图片  附带 加载 成功失败 监听
     * @param url 图片地址
     * @param isCircle 是否为圆形
     * @param roundRadius 圆角
     */
    fun ImageView.loadUrlWithListener(
        url: String?,
        @DrawableRes resId: Int = 0,
        isCircle: Boolean? = false,
        roundRadius: Float = 0F,
        fail: (() -> Unit)
    ) {
        val load = Glide.with(AppUtil.getContext())
            .load(url)
            .placeholder(resId)
            .format(DecodeFormat.PREFER_RGB_565)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .addListener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    fail
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            })

        if (isCircle == true) {
            load.transform(CircleCrop()).into(this)
        } else if (roundRadius > 0) {
            load.transform(RoundedCorners(ScreenUtil.dp2px(roundRadius)))
                .into(this)
        } else {
            load.into(this)
        }
    }

    /**
     * 加载res资源图片
     * @param resId 图片id
     * @param isCircle 是否为圆形
     * @param roundRadius 圆角
     */
    fun ImageView.loadResId(
        @DrawableRes resId: Int?,
        isCircle: Boolean? = false,
        roundRadius: Float = 0F
    ) {
        if (resId == null) return
        val load = Glide.with(AppUtil.getContext())
            .load(resId)
        if (isCircle == true) {
            load.transform(CircleCrop()).into(this)
        } else if (roundRadius > 0) {
            load.transform(RoundedCorners(ScreenUtil.dp2px(roundRadius)))
                .into(this)
        } else {
            load.into(this)
        }
    }

    /**
     * 加载网络图片
     */
    fun loadUrl(
        content: Context,
        bitmap: Drawable,
        imageView: ImageView
    ) {
        Glide.with(content)
            .load(bitmap)
            .into(imageView)
    }
}