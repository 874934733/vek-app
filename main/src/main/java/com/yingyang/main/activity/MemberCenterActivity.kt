package com.yingyang.main.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.yingyang.main.databinding.ActivityMemberCenterBinding
import com.yingyangfly.baselib.base.BaseActivity
import com.yingyangfly.baselib.router.RouterUrlCommon

/**
 * 会员中心
 */
@Route(path = RouterUrlCommon.memberCenter)
class MemberCenterActivity : BaseActivity<ActivityMemberCenterBinding>() {
    override fun initViews() {
        title = "会员中心"
    }

    override fun initListener() {

    }

    override fun initData() {

    }
}