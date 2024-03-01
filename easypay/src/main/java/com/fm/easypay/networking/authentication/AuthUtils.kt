package com.fm.easypay.networking.authentication

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import java.util.Date

internal class AuthUtilsImpl : AuthUtils {

    override fun getEpoch(): String {
        return (Date().time / 1000L).toString()
    }

    @SuppressLint("HardwareIds")
    override fun getDeviceId(context: Context): String {
        // TODO("Fix this after clarification with the client")
//        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        return "EP8392831"
    }
}

internal interface AuthUtils {
    fun getEpoch(): String
    fun getDeviceId(context: Context): String
}