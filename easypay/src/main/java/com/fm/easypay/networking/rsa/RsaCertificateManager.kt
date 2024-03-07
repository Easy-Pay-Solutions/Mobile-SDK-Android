package com.fm.easypay.networking.rsa

import android.content.Context
import com.fm.easypay.exceptions.EasyPaySdkException
import com.fm.easypay.utils.DownloadManager
import com.fm.easypay.utils.SdkPreferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.security.PublicKey
import java.security.cert.X509Certificate
import java.util.Date

internal interface RsaCertificateManager {
    var certificate: X509Certificate?

    @Throws(EasyPaySdkException::class)
    fun fetchCertificate()
    fun getPublicKey(): PublicKey?
}

internal class RsaCertificateManagerImpl(
    private val context: Context,
    private val downloadManager: DownloadManager,
    private val sdkPreferences: SdkPreferences,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : RsaCertificateManager {

    override var certificate: X509Certificate? = null

    companion object {
        private const val RSA_CERTIFICATE_FILE_NAME = "mobile.easypay5.com.cer"
        private const val RSA_CERTIFICATE_URL =
            "https://easypaysoftware.com/mobile.easypay5.com.cer"
        private const val REFRESH_TIME_LIMIT: Long = 7 * 24 * 60 * 60 * 1000L // 7 days
    }

    override fun fetchCertificate() {
        fetchCertificateFromLocalStorage()
        if (certificate == null || hasRefreshTimePassed()) {
            fetchCertificateFromUrl()
        }
    }

    override fun getPublicKey(): PublicKey? = certificate?.publicKey

    //region Private

    private fun fetchCertificateFromLocalStorage() {
        downloadManager.downloadFromLocalStorage(context, RSA_CERTIFICATE_FILE_NAME)?.let {
            parseBytes(it)
        }
    }

    private fun hasRefreshTimePassed(): Boolean {
        val now = Date().time
        val lastFetch = sdkPreferences.getLastRsaCertificateFetch()
        return now - lastFetch > REFRESH_TIME_LIMIT
    }

    private fun fetchCertificateFromUrl() {
        CoroutineScope(ioDispatcher).launch {
            downloadManager.downloadFrom(RSA_CERTIFICATE_URL) { bytes ->
                if (bytes == null) {
                    throw EasyPaySdkException(EasyPaySdkException.Type.RSA_CERTIFICATE_FETCH_FAILED)
                }
                downloadManager.saveLocally(bytes, context, RSA_CERTIFICATE_FILE_NAME)
                parseBytes(bytes)
                sdkPreferences.setLastRsaCertificateFetch(Date().time)
            }
        }
    }

    private fun parseBytes(bytes: ByteArray) {
        certificate = RsaUtils.convertToCertificate(bytes)
    }

    //endregion

}