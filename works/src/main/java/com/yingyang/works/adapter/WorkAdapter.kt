package com.yingyang.works.adapter

import android.annotation.SuppressLint
import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.yingyang.works.R
import com.yingyang.works.databinding.ItemWorkBinding
import com.yingyangfly.baselib.adapter.BaseDataBindingAdapter
import com.yingyangfly.baselib.ext.setOnSingleClickListener
import com.yingyangfly.baselib.room.VideoBean

/**
 * 我的作品adapter
 */
class WorkAdapter(override val layoutId: Int = R.layout.item_work) :
    BaseDataBindingAdapter<VideoBean, ItemWorkBinding>() {

    var content: Context? = null

    var onDeleteClickListener: ((bean: VideoBean) -> Unit)? = null

    var onClickListener: ((bean: VideoBean) -> Unit)? = null

    fun setAdapterContent(content: Context) {
        this.content = content
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(binding: ItemWorkBinding, item: VideoBean, position: Int) {
        binding.data = item
        Glide.with(content!!).setDefaultRequestOptions(
            RequestOptions().frame(0).centerCrop()
        ).load(item.url).into(binding.videoImage)
        binding.tvName.text = if (item.name.isNullOrEmpty()) {
            item.date
        } else {
            item.name
        }
        binding.tvDelete.setOnSingleClickListener {
            onDeleteClickListener?.invoke(item)
        }

        binding.workLayout.setOnSingleClickListener {
            onClickListener?.invoke(item)
        }
    }
}