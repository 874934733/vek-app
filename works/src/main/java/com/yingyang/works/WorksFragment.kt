package com.yingyang.works

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Route
import com.yingyang.works.databinding.FragmentWorksBinding
import com.yingyang.works.fragment.WorkListFragment
import com.yingyang.works.widget.MyTabLayout
import com.yingyangfly.baselib.base.BaseFragment
import com.yingyangfly.baselib.ext.initCenterTitle
import com.yingyangfly.baselib.router.RouterUrlCommon

/**
 * 作品fragment
 */
@Route(path = RouterUrlCommon.works)
class WorksFragment : BaseFragment<FragmentWorksBinding>() {

    private var tabTitles = mutableListOf(
        R.string.my_audio, R.string.my_video
    )

    //我的音频
    private val audioFragment by lazy {
        WorkListFragment().apply {
            typeStatus = "1"
        }
    }

    //我的视频
    private val videoFragment by lazy {
        WorkListFragment().apply {
            typeStatus = "2"
        }
    }

    private lateinit var adapter: WorkAdapter
    private var fragmentArray = mutableListOf<Fragment>()


    override fun initViews() {
        initCenterTitle("我的作品")
        fragmentArray.add(audioFragment)
        fragmentArray.add(videoFragment)
        adapter = WorkAdapter(childFragmentManager)
        binding.workPager.adapter = adapter
        binding.workPager.setScroll(true)
        setTabs(binding.titleBar, layoutInflater, tabTitles)
        binding.workPager.addOnPageChangeListener(
            MyTabLayout.TabLayoutOnPageChangeListener(
                binding.titleBar
            )
        )

        binding.workPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int, positionOffset: Float, positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                audioFragment.isShow = 0 == position
                videoFragment.isShow = 1 == position
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        binding.titleBar.addOnTabSelectedListener(
            MyTabLayout.ViewPagerOnTabSelectedListener(
                binding.workPager
            )
        )
    }

    override fun initListener() {

    }

    override fun initData() {

    }

    /**
     * @description: 设置添加Tab
     */
    @SuppressLint("InflateParams")
    private fun setTabs(
        tabLayout: MyTabLayout, inflater: LayoutInflater, tabTitlees: List<Int>
    ) {
        for (i in tabTitlees.indices) {
            val tab = tabLayout.newTab()
            val view = inflater.inflate(R.layout.my_tab_view, null)
            tab.customView = view
            view as TextView
            view.setText(tabTitlees[i])
            tabLayout.addTab(tab)
        }
    }

    inner class WorkAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return fragmentArray[position]
        }

        override fun getCount(): Int {
            return fragmentArray.size
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        }
    }
}