package com.fm.easypay.networking.rsa

import com.fm.easypay.exceptions.EasyPaySdkException

internal class RsaHelperImpl(
    private val rsaCertificateManager: RsaCertificateManager,
) : RsaHelper {

    //region Overridden

    override fun encrypt(data: String): String {
        if (data.isEmpty()) {
            throw EasyPaySdkException(EasyPaySdkException.Type.RSA_INPUT_DATA_EMPTY)
        }
        val publicKey = rsaCertificateManager.publicKey
        publicKey?.let {
            return RsaUtils.encrypt(data, it)
        }
        throw EasyPaySdkException(EasyPaySdkException.Type.RSA_CERTIFICATE_NOT_FETCHED)
    }

    //endregion

}

