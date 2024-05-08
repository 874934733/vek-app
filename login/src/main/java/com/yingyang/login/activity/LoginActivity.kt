package com.yingyang.login.activity

import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import com.alibaba.android.arouter.facade.annotation.Route
import com.yingyang.login.R
import com.yingyang.login.databinding.ActivityLoginBinding
import com.yingyangfly.baselib.ext.setOnSingleClickListener
import com.yingyangfly.baselib.ext.toast
import com.yingyangfly.baselib.mvvm.BaseMVVMActivity
import com.yingyangfly.baselib.router.RouterUrlCommon
import com.yingyangfly.baselib.utils.RxTimer
import com.yingyangfly.baselib.utils.User
import java.util.regex.Pattern

/**
 * 登录activity
 */
@Route(path = RouterUrlCommon.login)
class LoginActivity : BaseMVVMActivity<ActivityLoginBinding, LoginViewModel>() {

    private lateinit var rxTimer: RxTimer

    override fun initViews() {
        rxTimer = RxTimer()

    }

    override fun initListener() {
        binding {
            //获取验证码
            tvCode.setOnSingleClickListener {
                if (judgeFormCode()) {
                    sendCode()
                }
            }
            //登录
            btnLogin.setOnSingleClickListener {
                if (judgeByCode()) {
                    loginMsg()
                }
            }
        }
    }

    override fun initData() {

    }

    override fun onDestroy() {
        rxTimer.cancel()
        super.onDestroy()
    }

    /**
     * 非空验证
     */
    private fun judgeByCode(): Boolean {
        if (binding.username.text.toString().trim().isEmpty()) {
            "请输入手机号".toast()
            return false
        }
        if (binding.username.text.toString().trim().isEmpty()) {
            "请输入验证码".toast()
            return false
        }

        if (Pattern.matches("^\\d{11}$", binding.username.text.toString().trim()).not()) {
            "请输入正确的手机号".toast()
            return false
        }

        return true
    }

    /**
     * 非空验证
     */
    private fun judgeFormCode(): Boolean {
        if (binding.username.text.toString().trim().isEmpty()) {
            "请输入用户手机号".toast()
            return false
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        if (User.getPhone().isEmpty().not()) {
            binding.username.setText(User.getPhone())
        }
    }

    /**
     * 获取验证码
     */
    private fun sendCode() {
        viewModel.sendCode(binding.username.text.toString().trim(), fail = {
            it.toast()
        }, success = {
            timer()
        })
    }

    /**
     * 开始倒计时
     */
    private fun timer() {
        binding.tvCode.isEnabled = false
        val time = 60.toLong()
        val m: Long = 1000
        binding.tvCode.text = setTvSendColor("重新发送(${time}s)")
        rxTimer.interval(m) {
            if (it == time - 1) {
                rxTimer.cancel()
                binding.tvCode.text = "重新发送"
                binding.tvCode.isEnabled = true
            } else {
                binding.tvCode.text = setTvSendColor("重新发送(${time - 1 - it}s)")
            }
        }
    }

    private fun setTvSendColor(str: String): SpannableString {
        val startIndex = "重新发送(".length - 1
        val spannableString = SpannableString(str)
        spannableString.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.color_FF92ADFF)),
            0,
            startIndex,
            Spanned.SPAN_INCLUSIVE_EXCLUSIVE
        )
        return spannableString
    }

    /**
     * 用户登录
     */
    private fun loginMsg() {
        viewModel.loginMsg(binding.username.text.toString().trim(),
            binding.password.text.toString().trim(),
            fail = {
                it.toast()
            },
            success = {
                runOnUiThread {
                    if (TextUtils.isEmpty(it).not()) {
                        User.savePhone(binding.username.text.toString().trim())
                        User.saveToken(it!!)
                        getUserInfo()
                    }
                }
            })
    }

    /**
     * 获取用户信息
     */
    private fun getUserInfo() {
        viewModel.getUserInfo(fail = {
            it.toast()
        }, success = {
            runOnUiThread {
                if (it != null) {


                }
            }
        })
    }
}