package com.yingyang.changevoice

import com.alibaba.android.arouter.facade.annotation.Route
import com.yingyang.document.adapter.DocumentAdapter
import com.yingyangfly.baselib.base.BaseFragment
import com.yingyangfly.baselib.ext.initCenterTitle
import com.yingyangfly.baselib.router.RouterUrlCommon
import com.yingyangfly.documents.databinding.FragmentDocumentBinding

/**
 * 变声fragment
 */
@Route(path = RouterUrlCommon.document)
class DocumentFragment : BaseFragment<FragmentDocumentBinding>() {

    private var documentBeans = mutableListOf<String>()
    private val adapter by lazy { DocumentAdapter() }

    override fun initViews() {
        initCenterTitle("文案")
        adapter.setData(documentBeans)
        binding.rvDocument.adapter = adapter
    }

    override fun initListener() {

    }

    override fun initData() {
        documentBeans.add("2023012312032103")
        documentBeans.add("2023012312032103")
        documentBeans.add("2023012312032103")
        documentBeans.add("2023012312032103")
        documentBeans.add("2023012312032103")
        documentBeans.add("2023012312032103")
        documentBeans.add("2023012312032103")
        documentBeans.add("2023012312032103")
        adapter.setData(documentBeans)
    }
}