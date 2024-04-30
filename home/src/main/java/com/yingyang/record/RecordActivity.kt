package com.yingyang.record

import com.alibaba.android.arouter.facade.annotation.Route
import com.yingyang.home.databinding.ActivityRecordBinding
import com.yingyangfly.baselib.base.BaseActivity
import com.yingyangfly.baselib.ext.setTitleDividerVisible
import com.yingyangfly.baselib.router.RouterUrlCommon

/**
 * 录音页面
 */
@Route(path = RouterUrlCommon.record)
class RecordActivity : BaseActivity<ActivityRecordBinding>() {

    override fun initViews() {
        title = "录音"
        setTitleDividerVisible(true)

    }

    override fun initListener() {

    }

    override fun initData() {

    }
}