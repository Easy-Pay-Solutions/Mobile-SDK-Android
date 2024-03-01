package com.fm.easypay.utils

import android.content.Context
import com.fm.easypay.exceptions.EasyPaySdkException
import com.scottyab.rootbeer.RootBeer

internal object RootedDeviceValidator {

    @Throws(EasyPaySdkException::class)
    fun verifyDevice(context: Context) {
        val root = RootBeer(context)
        if (root.isRooted) {
            throw EasyPaySdkException(EasyPaySdkException.Type.ROOTED_DEVICE_DETECTED)
        }
    }
}