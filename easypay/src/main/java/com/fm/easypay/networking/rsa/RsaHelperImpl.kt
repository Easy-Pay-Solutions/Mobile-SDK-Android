package com.fm.easypay.networking.rsa

import com.fm.easypay.exceptions.EasyPaySdkException
import okhttp3.CertificatePinner.Companion.sha256Hash

internal class RsaHelperImpl(
    private val rsaCertificateManager: RsaCertificateManager,
) : RsaHelper {

    //region Overridden

    override fun encrypt(data: String): String {
        if (data.isEmpty()) {
            throw EasyPaySdkException(EasyPaySdkException.Type.RSA_INPUT_DATA_EMPTY)
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
        return "$encrypted|${getFingerprint()}"
    }

    private fun getFingerprint(): String {
        // TODO: Clarify how to get the fingerprint
        return rsaCertificateManager.certificate?.sha256Hash()?.base64() ?: ""
    }

    //endregion

}

