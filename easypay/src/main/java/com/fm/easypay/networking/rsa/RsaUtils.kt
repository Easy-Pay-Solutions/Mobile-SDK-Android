package com.fm.easypay.networking.rsa

import android.util.Base64
import java.security.PrivateKey
import java.security.PublicKey
import javax.crypto.Cipher

internal interface RsaUtils {
    fun getPublicKey(): PublicKey?
}

internal class RsaUtilsImpl : RsaUtils {

    // TODO: To be adjusted during the implementation of Charge a Credit Card feature
    override fun getPublicKey(): PublicKey? {
        TODO("Implement this method")
    }
}

object RsaEncryptorDecryptor {

    private const val TRANSFORMATION = "RSA/ECB/OAEPWithSHA-1AndMGF1Padding"

    fun encrypt(
        data: String,
        publicKey: PublicKey,
        transformation: String = TRANSFORMATION,
    ): String {
        val cipher = Cipher.getInstance(transformation)
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)
        val bytes = cipher.doFinal(data.toByteArray())
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }

    fun decrypt(
        data: String,
        privateKey: PrivateKey,
        transformation: String = TRANSFORMATION,
    ): String {
        val cipher = Cipher.getInstance(transformation)
        cipher.init(Cipher.DECRYPT_MODE, privateKey)
        val decode = Base64.decode(data, Base64.DEFAULT)
        return String(cipher.doFinal(decode))
    }
}
