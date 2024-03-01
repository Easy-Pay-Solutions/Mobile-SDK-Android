package com.fm.easypay.networking.rsa

import com.fm.easypay.exceptions.EasyPaySdkException

internal class RsaHelperImpl(
    private val rsaUtils: RsaUtils,
) : RsaHelper {

    //region Overridden

    // TODO: Replace String with SecureTextField
    override fun encrypt(data: String): String? {
        if (data.isEmpty()) {
            throw EasyPaySdkException(EasyPaySdkException.Type.RSA_INPUT_DATA_EMPTY)
        }
        val publicKey = rsaUtils.getPublicKey()
        publicKey?.let {
            return RsaEncryptorDecryptor.encrypt(data, it)
        }
        return null
    }

    //endregion

}

