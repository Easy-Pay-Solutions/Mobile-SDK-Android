package com.fm.easypay.networking.rsa

import android.util.Base64
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher

object RsaUtils {

    private const val TRANSFORMATION = "RSA/ECB/OAEPWithSHA-1AndMGF1Padding"
    private const val ALGORITHM = "RSA"

    fun stringToPublicKey(publicKeyBytes: ByteArray?): PublicKey? {
        var publicKey = publicKeyBytes?.toString(Charsets.UTF_8)
        publicKey = publicKey?.replace("-----BEGIN PUBLIC KEY-----", "")
        publicKey = publicKey?.replace("-----END PUBLIC KEY-----", "")
        publicKey = publicKey?.replace("\n", "")
        publicKey = publicKey?.replace("\r", "")
        val keyBytes = Base64.decode(publicKey, Base64.DEFAULT)
        val spec = X509EncodedKeySpec(keyBytes)
        val keyFactory = KeyFactory.getInstance(ALGORITHM)
        return keyFactory.generatePublic(spec)
    }

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