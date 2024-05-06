package com.yingyangfly.baselib.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.yingyangfly.baselib.R

/**
 * @author gold
 * @date 2022/9/5 上午10:09
 * @description 加入了DataBinding的RecyclerView.Adapter基类，在RecyclerView中实现了DataBinding，适合简单列表。
 * 只支持绑定一种数据类型，重写[layoutId]设置布局，重写[onBindViewHolder]抽象函数实现数据绑定。
 * 其中[T]是数据类型，在xml中使用；[B]是对应布局的数据绑定类。
 * 实现类只需要重写布局id[layoutId]和[onBindViewHolder]一个抽象函数即可。
 * https://juejin.cn/post/6932402972915662861
 * 空布局思路： RecyclerView支持多种ViewType的item，我们可以把空布局作为一个ViewType,。当无数据时，getItemCount返回1，让RecyclerView显示一个item，
 * 这个item就是我们要显示的空布局。
 */
abstract class BaseDataBindingAdapter<T, B : ViewDataBinding> :
    RecyclerView.Adapter<BaseDataBindingAdapter.ViewHolder>() {

    /**
     * 普通的item ViewType
     */
    private val TYPE_ITEM = 1

    /**
     * 空布局的 ViewType
     */
    private val TYPE_EMPYT = 2

    /**
     * 是否显示空布局，默认不显示
     */
    var showEmptyView = true

    /**
     * 数据列表，只支持一种类型的数据
     */
    val data = mutableListOf<T>()

    open var emtpyView = R.layout.rv_empty

    /**
     * 判断数据是否为空，如果没有数据，并且需要空布局，就返回1
     */
    override fun getItemCount(): Int = if (isEmptyView()) 1 else {
        data.size
    }

    /**
     * 布局文件的id，在子类中实现（可以在构造函数中重写）
     */
    abstract val layoutId: Int

    /**
     * ViewHolder，因为实现数据绑定，所以实际操作由[binding]实现。
     */
    open class ViewHolder(open val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)

    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (isEmptyView()) {
            val binding = DataBindingUtil.inflate<B>(
                LayoutInflater.from(parent.context), emtpyView, parent, false
            )
            ViewHolder(binding)
        } else {
            val binding = DataBindingUtil.inflate<B>(
                LayoutInflater.from(parent.context), layoutId, parent, false
            )
            ViewHolder(binding)
        }
    }

    override fun getItemViewType(position: Int) = if (isEmptyView()) {
        TYPE_EMPYT
    } else {
        TYPE_ITEM
    }

    /**
     * 全量更新数据
     */
    @SuppressLint("NotifyDataSetChanged")
    fun setData(_data: List<T>?) {
        data.clear()
        if (_data.isNullOrEmpty().not()) data.addAll(_data!!)
        notifyDataSetChanged()
    }

    /**
     * 清除数据
     */
    @SuppressLint("NotifyDataSetChanged")
    fun clearData() {
        data.clear()
        notifyDataSetChanged()
    }

    /**
     * 增量更新数据
     */
    @SuppressLint("NotifyDataSetChanged")
    fun addData(_data: List<T>?) {
        if (_data.isNullOrEmpty().not()) data.addAll(_data!!)
        notifyDataSetChanged()
    }

    final override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        @Suppress("UNCHECKED_CAST")
        if (data.size == 0 && showEmptyView) {
            onBindEmptyViewHolder(holder.binding)
        } else {
            onBindViewHolder(holder.binding as B, data[position], position)
            holder.binding.executePendingBindings()
        }
    }

    /**
     * 显示数据时使用。通过[binding]设置布局中对应的变量[item]更新数据。
     */
    abstract fun onBindViewHolder(binding: B, item: T, position: Int)

    /**
     * 显示空数据时使用。通过[binding]设置布局中对应的变量[item]更新数据。
     */
    open fun onBindEmptyViewHolder(binding: ViewDataBinding) {}

    /**
     * 判断是否空布局
     */
    fun isEmptyView(): Boolean {
        return data.size == 0 && showEmptyView
    }
}
