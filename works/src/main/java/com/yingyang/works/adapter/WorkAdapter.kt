package com.yingyang.works.adapter

import com.yingyang.works.R
import com.yingyang.works.databinding.ItemWorkBinding
import com.yingyangfly.baselib.adapter.BaseDataBindingAdapter
import com.yingyangfly.baselib.room.VideoBean

/**
 * 我的作品adapter
 */
class WorkAdapter(override val layoutId: Int = R.layout.item_work) :
    BaseDataBindingAdapter<VideoBean, ItemWorkBinding>() {
    override fun onBindViewHolder(binding: ItemWorkBinding, item: VideoBean, position: Int) {
        binding.data = item
        binding.tvName.text = if (item.name.isNullOrEmpty()) {
            item.date
        } else {
            item.name
        }
    }
}