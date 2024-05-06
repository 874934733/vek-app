package com.yingyang.home

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
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
class HomeActivity : BaseActivity<ActivityHomeBinding>(), TabLayout.OnTabSelectedListener {

    private var tabTitles =
        mutableListOf(R.string.tab1, R.string.tab3, R.string.tab4)
    private var tabImgs = mutableListOf(
        R.drawable.tab1_selector,
        R.drawable.tab3_selector,
        R.drawable.tab4_selector,
    )
    private val homeFragment by lazy { HomeFragment() }//首页
    private val worksFragment by lazy { RouterUtil.getFragment(RouterUrlCommon.works) }//作品
    private val mainFragment by lazy { RouterUtil.getFragment(RouterUrlCommon.main) }//我的
    private lateinit var homePagerAdapter: HomePagerAdapter

    private var tabFragments = mutableListOf<Fragment>()

    override fun initViews() {
        setFragment()
    }

    private fun setFragment() {
        tabFragments.add(homeFragment)
        tabFragments.add(worksFragment)
        tabFragments.add(mainFragment)

        homePagerAdapter = HomePagerAdapter(supportFragmentManager)
        binding.viewpager.adapter = homePagerAdapter
        TabUtils.setTabsImg(binding.tabLayout, layoutInflater, tabTitles, tabImgs)
        homePagerAdapter.notifyDataSetChanged()
    }

    override fun initListener() {
        binding.apply {
            viewpager.setScroll(false)
            viewpager.offscreenPageLimit = 4
            tabLayout.addOnTabSelectedListener(this@HomeActivity)
        }
    }

    override fun initData() {

    }

    inner class HomePagerAdapter(fm: FragmentManager) :
        FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getItem(position: Int): Fragment {
            return tabFragments[position]!!
        }

        override fun getCount(): Int {
            return tabFragments.size
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        }
    }

    override fun onTabSelected(p0: TabLayout.Tab?) {
        //Fragment切换
        tabFragments.forEachIndexed { index, fragment ->
            fragment.onHiddenChanged(binding.tabLayout.getTabAt(index)?.isSelected == true)
            binding.viewpager.currentItem = p0!!.position
        }
    }

    override fun onTabUnselected(p0: TabLayout.Tab?) {

    }

    override fun onTabReselected(p0: TabLayout.Tab?) {

    }
}