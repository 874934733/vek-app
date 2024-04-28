package com.yingyangfly.baselib.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yingyangfly.baselib.R
import com.yingyangfly.baselib.utils.ViewTool
import kotlinx.android.synthetic.main.rv_empty.view.empty_view_message

/**
 * Author: YongChao
 * Date: 19-8-30 下午4:29
 * Description: RecyclerView.Adapter 基础类封装
 */
open class XBaseAdapter<T>(
    val layoutResourceId: Int,
    var items: List<T>,
    var bindView: (View, T, Int) -> Unit
) :
    RecyclerView.Adapter<XBaseAdapter.ViewHolder<T>>() {
    /**
     * 0:空布局  1： 非空布局
     */
    val VIEW_TYPE_ITEM = 1
    val VIEW_TYPE_EMPTY = 0
    var isShowEmptyView = true
    var emptyLayout = R.layout.rv_empty
    var emptyText = ""
    var recyclable = true  //是否服用Item  true: 是

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<T> {
        if (isShowEmptyView && viewType == VIEW_TYPE_EMPTY) {
            val viewEmpty = LayoutInflater.from(parent.context).inflate(emptyLayout, parent, false)
            val view = ViewTool.inflateLayoutPixels(parent.context, viewEmpty, 1194, 834)
            return ViewHolder(view, bindView)
        }
        val viewLayout = LayoutInflater.from(parent.context).inflate(layoutResourceId, parent, false)
        val view = ViewTool.inflateLayoutPixels(parent.context, viewLayout, 1194, 834)
        return ViewHolder(view, bindView)
    }

    override fun onBindViewHolder(holder: ViewHolder<T>, position: Int) {
        if (items.isNotEmpty()) {
            holder.bindForecast(items[position], position)
            holder.setIsRecyclable(recyclable)
        } else if (isShowEmptyView) {
            holder.itemView.empty_view_message?.visibility =
                if (emptyText.isEmpty()) View.GONE else View.VISIBLE
            holder.itemView.empty_view_message?.text = emptyText
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isShowEmptyView && items.isEmpty()) VIEW_TYPE_EMPTY else VIEW_TYPE_ITEM
    }

    override fun getItemCount() = if (isShowEmptyView && items.isEmpty()) 1 else items.size

    class ViewHolder<in T>(view: View, val bindView: (View, T, Int) -> Unit) :
        RecyclerView.ViewHolder(view) {
        fun bindForecast(item: T, position: Int) {
            bindView(itemView, item, position)
        }
    }

    open fun setEmpytView(eLayout: Int) {
        this.emptyLayout = eLayout
    }

    open fun setEmpytText(text: String) {
        this.emptyText = text
    }

    open fun setIsShowEmptyView(isShow: Boolean) {
        isShowEmptyView = isShow
    }
}