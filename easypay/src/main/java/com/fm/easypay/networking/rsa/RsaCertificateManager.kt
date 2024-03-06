package com.fm.easypay.networking.rsa

import com.fm.easypay.utils.DownloadManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.security.PublicKey
import java.security.cert.X509Certificate

internal interface RsaCertificateManager {
    var certificate: X509Certificate?
    var publicKey: PublicKey?

    fun fetchCertificateIfNeeded()
}

internal class RsaCertificateManagerImpl(
    private val downloadManager: DownloadManager,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : RsaCertificateManager {

    override var certificate: X509Certificate? = null
    override var publicKey: PublicKey? = null

    companion object {
        private const val RSA_CERTIFICATE_URL =
            "https://easypaysoftware.com/mobile.easypay5.com.pem"
    }

    override fun fetchCertificateIfNeeded() {
        if (publicKey == null || certificate == null) {
            fetchCertificate()
        }

        // TODO: Implement checks for time intervals and certificate expiration
    }

    //region Private

    private fun fetchCertificate() {
        CoroutineScope(ioDispatcher).launch {
            downloadManager.downloadFrom(RSA_CERTIFICATE_URL) { bytes ->
                publicKey = RsaUtils.stringToPublicKey(bytes)
                // TODO: Implement Certificate parsing (some issues with KeyStore right now)
            }
        }
    }

    //endregion

}