package com.yingyang.works.webview

import android.os.Bundle
import android.text.TextUtils
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.alibaba.android.arouter.facade.annotation.Route
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ktx.immersionBar
import com.yingyang.works.R
import com.yingyangfly.baselib.ext.setOnSingleClickListener
import com.yingyangfly.baselib.router.RouterUrlCommon
import com.yingyangfly.baselib.utils.DownloadUtils
import com.yingyangfly.baselib.webview.base.RobustWebView
import com.yingyangfly.baselib.webview.base.WebViewCacheHolder
import com.yingyangfly.baselib.webview.base.WebViewListener

@Route(path = RouterUrlCommon.WEB_VIEW_TENCENT_WEBVIEW)
class TencentWebviewActivity : AppCompatActivity() {

    private var url: String = ""

    private val rootLayout by lazy {
        findViewById<ViewGroup>(R.id.rootLayout)
    }

    private val btnSave by lazy {
        findViewById<AppCompatButton>(R.id.btnSave)
    }

    private lateinit var webView: RobustWebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        immersionBar {
            hideBar(BarHide.FLAG_HIDE_BAR)
            navigationBarColor(R.color.transparent)
        }
        setContentView(R.layout.activity_tencent_webview)
        url = intent.getStringExtra("url") ?: ""
        webView = WebViewCacheHolder.acquireWebViewInternal(this)
        webView.webViewListener = webViewListener
        rootLayout.addView(webView)
        if (TextUtils.isEmpty(url).not()) {
            webView.loadUrl(url)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        WebViewCacheHolder.prepareWebView()
    }

    private val webViewListener = object : WebViewListener {
        override fun onProgressChanged(webView: RobustWebView, progress: Int) {

        }

        override fun onReceivedTitle(webView: RobustWebView, title: String) {

        }

        override fun onPageFinished(webView: RobustWebView, url: String) {

        }
    }
}