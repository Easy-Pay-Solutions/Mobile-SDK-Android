package com.fm.easypay

import com.fm.easypay.exceptions.EasyPaySdkException
import com.fm.easypay.utils.VersionManager

data class EasyPayConfiguration internal constructor(
    private val sessionKey: String,
    private val hmacSecret: String,
    private val versionManager: VersionManager = VersionManager(),
) {

    private val sdkVersion: String = versionManager.getCurrentSdkVersion()

    companion object {

        internal var API_VERSION = BuildConfig.API_VERSION

        private var instance: EasyPayConfiguration? = null

        @Throws(EasyPaySdkException::class)
        fun getInstance(): EasyPayConfiguration {
            return instance ?: throw EasyPaySdkException(
                EasyPaySdkException.Type.EASY_PAY_CONFIGURATION_NOT_INITIALIZED
            )
        }

        internal fun init(
            sessionKey: String,
            hmacSecret: String,
        ) {
            instance = EasyPayConfiguration(
                sessionKey = sessionKey,
                hmacSecret = hmacSecret
            )
        }

        internal fun reset() {
            instance = null
        }
    }

    fun getSdkVersion(): String = sdkVersion

    fun getSessionKey(): String = sessionKey

    fun getHmacSecret(): String = hmacSecret
}