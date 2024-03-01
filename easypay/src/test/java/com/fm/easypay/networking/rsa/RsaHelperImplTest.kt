package com.fm.easypay.networking.rsa

import com.fm.easypay.exceptions.EasyPaySdkException
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey

@RunWith(MockitoJUnitRunner::class)
internal class RsaHelperImplTest {

    private lateinit var rsaUtils: TestRsaUtils
    private lateinit var rsaHelper: RsaHelper

    companion object {
        private const val TEST_CREDIT_CARD = "4761530001111118"
    }

    @Before
    fun setUp() {
        rsaUtils = TestRsaUtils()
        rsaHelper = RsaHelperImpl(rsaUtils)
    }

    @Test
    fun `encrypt() returns correct encrypted data`() {
        val encryptedText = rsaHelper.encrypt(TEST_CREDIT_CARD) ?: ""
        val decryptedText = RsaEncryptorDecryptor.decrypt(encryptedText, rsaUtils.getPrivateKey())
        assertEquals(TEST_CREDIT_CARD, decryptedText)
    }

    @Test
    fun `encrypt() fails on empty input`() {
        try {
            rsaHelper.encrypt("") ?: ""
        } catch (e: EasyPaySdkException) {
            assertEquals(
                EasyPaySdkException.Type.RSA_INPUT_DATA_EMPTY.message,
                e.message
            )
        }
    }
}

internal class TestRsaUtils : RsaUtils {

    companion object {
        private const val ALGORITHM = "RSA"
        private const val KEY_SIZE = 2048
    }

    private var publicKey: PublicKey
    private var privateKey: PrivateKey

    init {
        val keyGen = KeyPairGenerator.getInstance(ALGORITHM)
        keyGen.initialize(KEY_SIZE)
        val pair = keyGen.generateKeyPair()
        publicKey = pair.public
        privateKey = pair.private
    }

    override fun getPublicKey(): PublicKey = publicKey

    fun getPrivateKey(): PrivateKey = privateKey
}