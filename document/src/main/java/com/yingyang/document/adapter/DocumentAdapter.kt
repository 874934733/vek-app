package com.yingyang.document.adapter

import com.yingyangfly.baselib.adapter.BaseDataBindingAdapter
import com.yingyangfly.documents.R
import com.yingyangfly.documents.databinding.ItemDocumentBinding

/**
 * 我的文案adapter
 */
class DocumentAdapter(override val layoutId: Int = R.layout.item_document) :
    BaseDataBindingAdapter<String, ItemDocumentBinding>() {
    override fun onBindViewHolder(binding: ItemDocumentBinding, item: String, position: Int) {
        binding.data = item
    }
}