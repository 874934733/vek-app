package com.yingyangfly.baselib.utils

import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.tabs.TabLayout
import com.yingyangfly.baselib.R
import kotlinx.android.synthetic.main.tab_custom.view.*

object TabUtils {
    /**
     * @description: 设置添加Tab 带图片
     */
    fun setTabsImg(tabLayout: TabLayout, inflater: LayoutInflater, tabTitlees: List<Int>, tabImgs: List<Int>) {
        for (i in tabImgs.indices) {
            val tab = tabLayout.newTab()
            val view = inflater.inflate(R.layout.tab_custom, null)
            tab.customView = view
            val tvTitle = view.tv_tab as TextView
            tvTitle.setText(tabTitlees[i])
            val imgTab = view.img_tab as ImageView
            imgTab.setImageResource(tabImgs[i])
            tabLayout.addTab(tab)
        }
    }

    /**
     * @description: 设置添加Tab
     */
    fun setTabs(tabLayout: TabLayout, inflater: LayoutInflater, tabTitlees: List<Int>) {
//        for (i in tabTitlees.indices) {
//            val tab = tabLayout.newTab()
//            val view = inflater.inflate(R.layout.voice_tab_custom, null)
//            tab.setCustomView(view)
//
//            val tvTitle = view.tv_tab1 as TextView
//            tvTitle.setText(tabTitlees[i])
//            tabLayout.addTab(tab)
//
//        }

        for (i in tabTitlees.indices) {
            val tab = tabLayout.newTab()
            val view = inflater.inflate(R.layout.tab_tv_custom, null)
            tab.customView = view
            val tvTitle = view.tv_tab as TextView
            tvTitle.setText(tabTitlees[i])
//            var params = tab.getla
            tabLayout.addTab(tab)

        }
    }

}