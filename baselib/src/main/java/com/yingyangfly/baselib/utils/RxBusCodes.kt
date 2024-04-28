package com.yingyangfly.baselib.utils

object RxBusCodes {

    //关闭webview页面
    const val FINISHWEBVIEW = "finishWebview"

    //播放语音
    const val PLAYVOICE = "playVoice"

    //获取数据监控
    const val GetDataMonitor = 9001

    //获取训练内容
    const val GetTrainContent = 9002

    //结束答题
    const val EndMMSEQuestion = 9003

    //结束答题
    const val EndMOCAQuestion = 9004

    //脑能力值
    const val BrainAbility = 9008

    //获取理疗报告
    const val GetTrainReport = 9005

    //购买成功
    const val SuccessfulPurchase = "SuccessfulPurchase"

    //评价医生
    const val EVALUATE_ORDER = 9007

    //退出游戏
    const val CALLQUITGAME = "callQuitGame"

    //退出游戏
    const val QUITGAME = "quitGame"

    /**
     * 游戏计时
     */
    const val SETTIME = "setTime"

    /**
     * 游戏分数
     */
    const val SETSCORE = "setScore"

    /**
     * 游戏关卡
     */
    const val SETLEVEL = "setLevel"

    /**
     * 保存游戏进度
     */
    const val SAVEDATA = "saveData"

    /**
     * 游戏加载完成
     */
    const val LOADINGOVER = "loading:over"

    /**
     * 游戏超时退出
     */
    const val GAMEOVERTIME = "gameOvertime"

    /**
     * 结束游戏
     */
    const val FINISHGAME = "finishGame"

    /**
     * 游戏倒计时弹框消失
     */
    const val COUNTDOWNSUCCESS = "countdownSuccess"

    /**
     * 语音合成
     */
    const val SPEECHSYNTHESIS = "speechSynthesis"

    /**
     * 结束语音播放
     */
    const val STOPVOICE = "stopVoice"

    /**
     * 开始播放音乐
     */
    const val STARTMUSIC = "startMusic"

    /**
     * 展示首页弹窗
     */
    const val SHOWTASKDIALOG = "showTaskDialog"

    /**
     * 退出登录
     */
    const val LOGINOUT = "LoginOut"

    /**
     * 医生开始接诊
     */
    const val doctorReception = 9006

    /**
     * 已预约直播
     */
    const val APPOINTMENTLIVE = "appointmentLive"

    /**
     * 购买问诊服务成功
     */
    const val purchaseConsultationEvent = 9009

    /**
     * IM被踢下线
     */
    const val ONKICKEDOFFLINE = "onKickedOffline"

    /**
     * 网络异常
     */
    const val ONCONNECTFAILED = "onConnectFailed"

    /**
     * IM登录成功
     */
    const val IMLoginSuccess = "IMLoginSuccess"

    /**
     * IM解除消息接收监听
     */
    const val IMLoginOut = "IMLoginOut"

    /**
     * 结束游戏介绍页面
     */
    const val FINISHGAMEINTRODUCTION = "finishGameIntroduction"

    /**
     * 结束游戏页面
     */
    const val FINISHPLAYGAME = "finishPlayGame"
}