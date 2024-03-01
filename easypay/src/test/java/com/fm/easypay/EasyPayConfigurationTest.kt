package com.fm.easypay

import com.fm.easypay.exceptions.EasyPayException
import com.fm.easypay.exceptions.EasyPaySdkException
import com.fm.easypay.utils.VersionManager
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class EasyPayConfigurationTest {

    companion object {
        private const val TEST_SESSION_KEY = "9B9175EF556E4DDA93303132323141303035383339"
        private const val TEST_HMAC_SECRET = "7D55DBB3D691C9E0FDF341E4AB38C3C9"
    }

    @Test
    fun `calling getInstance() before init() throws IllegalStateException`() {
        try {
            EasyPayConfiguration.getInstance()
        } catch (e: EasyPayException) {
            assertEquals(
                EasyPaySdkException.Type.EASY_PAY_CONFIGURATION_NOT_INITIALIZED.message,
                e.message
            )
        }
    }

    @Test
    fun `init() sets session key and hmac secret`() {
        initConfiguration()
        val configuration = EasyPayConfiguration.getInstance()
        assertEquals(TEST_SESSION_KEY, configuration.getSessionKey())
        assertEquals(TEST_HMAC_SECRET, configuration.getHmacSecret())
    }

    @Test
    fun `getLibraryVersion() returns current sdk version`() {
        initConfiguration()
        val sdkVersion = EasyPayConfiguration.getInstance().getSdkVersion()
        val targetSdkVersion = VersionManager().getCurrentSdkVersion()
        assertEquals(targetSdkVersion, sdkVersion)
    }

    @After
    fun tearDown() {
        EasyPayConfiguration.reset()
    }

    //region Private

    private fun initConfiguration() {
        EasyPayConfiguration.init(TEST_SESSION_KEY, TEST_HMAC_SECRET)
    }

    //endregion

}