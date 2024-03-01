package com.fm.easypay.networking.rsa

import com.fm.easypay.exceptions.EasyPaySdkException

internal interface RsaHelper {
    @Throws(EasyPaySdkException::class)
    fun encrypt(data: String): String?
}