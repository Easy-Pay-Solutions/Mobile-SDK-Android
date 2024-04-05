package com.fm.easypay.networking.rsa

import com.fm.easypay.exceptions.EasyPaySdkException
import com.fm.easypay.utils.toHex
import okhttp3.CertificatePinner.Companion.sha1Hash

internal class RsaHelperImpl(
    private val rsaCertificateManager: RsaCertificateManager,
) : RsaHelper {

    //region Overridden

    override fun encrypt(data: String): String {
        if (data.isEmpty()) {
            return data
        }
        val publicKey = rsaCertificateManager.getPublicKey()
        publicKey?.let {
            val encrypted = RsaUtils.encrypt(data, it)
            return applyFingerprint(encrypted)
        }
        throw EasyPaySdkException(EasyPaySdkException.Type.RSA_CERTIFICATE_NOT_FETCHED)
    }

    //endregion

    //region Private

    private fun applyFingerprint(encrypted: String): String {
         return "$encrypted|${getFingerprint() ?: ""}"
    }

    private fun getFingerprint(): String? {
        return rsaCertificateManager.certificate?.sha1Hash()?.toByteArray()?.toHex()
    }

    //endregion

}

