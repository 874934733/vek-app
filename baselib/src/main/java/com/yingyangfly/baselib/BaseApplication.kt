package com.yingyangfly.baselib

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.constant.SpinnerStyle
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.tencent.bugly.crashreport.CrashReport
import com.yingyangfly.baselib.config.AccountConfig
import com.yingyangfly.baselib.utils.LogUtil
import java.lang.reflect.Field
import java.lang.reflect.Method

/**
 * @author: gold
 * @time: 2022/6/1 下午2:26
 * @description: application基础类
 */
open class BaseApplication : Application() {

    var moduleApps = ArrayList<Application>()

    companion object {
        // 单例模式： 双重校验锁式
        val instance: BaseApplication by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            BaseApplication()
        }
        lateinit var appContext: Context

        //        @Volatile
        var isCreate = false

        //        @Volatile
        var isAttach = false

        //配置： SmartRefreshLayout
        init {//static 代码段可以防止内存泄露
            //设置全局的Header构建器
            SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
                layout.setPrimaryColorsId(R.color.bgDefault, R.color.colorTxtDefault)//全局设置主题颜色
                ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Translate)//指定为经典Header，默认是 贝塞尔雷达Header
            }

            //设置全局的Footer构建器
            SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
                //指定为经典Footer，默认是 BallPulseFooter
                ClassicsFooter(context).setSpinnerStyle(SpinnerStyle.Translate)
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        if (!isCreate) {
            isCreate = true
            appContext = applicationContext
            // 初始化Log
            LogUtil.initLogger(AccountConfig.DEBUG)
            // 依次调用组件的Application onCreate
            moduleApps.forEach {
                it.onCreate()
            }
            // 初始化路由
            initRouter()
            //初始化bugly
            formalInitThirdParty()
            closeAndroidPDialog()
            initViews()
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        if (!isAttach) {
            isAttach = true
            MultiDex.install(this)
            initModuleApplication()
        }

    }

    open fun initViews() {
    }

    private fun initRouter() {
        // ARouter
        if (AccountConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }

    /**
     * 初始化bugly
     */
    private fun formalInitThirdParty() {
        // Bugly
        CrashReport.initCrashReport(
            appContext,
            AccountConfig.BUGLY_APPID,
            AccountConfig.DEBUG
        )
    }

    /**
     * 适配P
     */
    @SuppressLint("SoonBlockedPrivateApi", "PrivateApi", "DiscouragedPrivateApi")
    private fun closeAndroidPDialog() {
        try {
            val aClass =
                Class.forName("android.content.pm.PackageParser\$Package")
            val declaredConstructor =
                aClass.getDeclaredConstructor(String::class.java)
            declaredConstructor.isAccessible = true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            val cls = Class.forName("android.app.ActivityThread")
            val declaredMethod: Method = cls.getDeclaredMethod("currentActivityThread")
            declaredMethod.isAccessible = true
            val activityThread: Any = declaredMethod.invoke(null)
            val mHiddenApiWarningShown: Field = cls.getDeclaredField("mHiddenApiWarningShown")
            mHiddenApiWarningShown.isAccessible = true
            mHiddenApiWarningShown.setBoolean(activityThread, true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /************************************************** 通过反射初始化module  *********************************************************/
    /**
     * 反射创建组件Application
     */
    private fun initModuleApplication() {
        val info = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
        if (info.metaData == null)
            return
        val apps = info.metaData.keySet()
        apps.forEach {
            try {
                // 完整类名
                val cla = Class.forName(it.toString())
                // 获得实例
                val app = cla.newInstance()
                if (app is Application && cla.name != this::class.java.name) {
                    initModuleAppAttach(app)
                }

            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 反射对组件Application进行初始化调用
     */
    private fun initModuleAppAttach(app: Application) {
        val method: Method? =
            Application::class.java.getDeclaredMethod("attach", Context::class.java)
        if (method != null) {
            method.isAccessible = true
            method.invoke(app, baseContext)
            moduleApps.add(app)
        }
    }
}
