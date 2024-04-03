package com.fm.easypay_sample

import android.app.Application
import com.fm.easypay.EasyPay
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initEasyPay()
        initAppCenter()
    }

    private fun initAppCenter() {
        AppCenter.start(
            this,
            BuildConfig.APP_CENTER_SECRET,
            Analytics::class.java,
            Crashes::class.java
        )
    }

    private fun initEasyPay() {
        val sessionKey = BuildConfig.SESSION_KEY
        val hmacSecret = BuildConfig.HMAC_SECRET
        EasyPay.init(this.applicationContext, sessionKey, hmacSecret)
    }
}