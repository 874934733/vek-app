package com.yingyang.home

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.tabs.TabLayout
import com.yingyang.fragment.HomeFragment
import com.yingyang.home.databinding.ActivityHomeBinding
import com.yingyangfly.baselib.base.BaseActivity
import com.yingyangfly.baselib.router.RouterUrlCommon
import com.yingyangfly.baselib.utils.RouterUtil
import com.yingyangfly.baselib.utils.TabUtils

/**
 * app首页
 */
@Route(path = RouterUrlCommon.home)
class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    private var tabTitles =
        mutableListOf(R.string.tab1, R.string.tab2, R.string.tab3, R.string.tab4)
    private var tabImgs = mutableListOf(
        R.drawable.tab1_selector,
        R.drawable.tab2_selector,
        R.drawable.tab4_selector,
        R.drawable.tab3_selector,
    )
    private val homeFragment by lazy { HomeFragment() }//首页
    private val changeVoiceFragment by lazy { RouterUtil.getFragment(RouterUrlCommon.changeVoice) }//变声
    private val worksFragment by lazy { RouterUtil.getFragment(RouterUrlCommon.works) }//作品
    private val mainFragment by lazy { RouterUtil.getFragment(RouterUrlCommon.main) }//我的

    private var tabFragments = mutableListOf<Fragment>()

    override fun initViews() {
        setFragment()
    }

    private fun setFragment() {
        tabFragments.add(homeFragment)
        tabFragments.add(changeVoiceFragment)
        tabFragments.add(worksFragment)
        tabFragments.add(mainFragment)

        TabUtils.setTabsImg(binding.tablayoutHome, layoutInflater, tabTitles, tabImgs)

        showFragment(R.id.home_content, tabFragments[0])
        binding.tablayoutHome.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val posi = tab?.position!!
                showFragment(R.id.home_content, tabFragments[posi])
                tabFragments.forEachIndexed { index, fragment ->
                    fragment.onHiddenChanged(posi != index)
                }
            }
        })
    }

    override fun initListener() {

    }

    override fun initData() {

    }
}