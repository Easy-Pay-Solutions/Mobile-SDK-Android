package com.fm.easypay.utils

import com.fm.easypay.rules.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DownloadManagerTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val downloadManager: DownloadManager =
        DownloadManagerImpl(mainDispatcherRule.testDispatcher)

    companion object {
        private const val TEST_DOWNLOAD_URL = "https://easypaysoftware.com/mobile.easypay5.com.pem"
        private const val LOCAL_CERT_FILE_NAME = "mobile.easypay5.com.pem"
    }

    @Test
    fun `downloadFile() returns proper file content`() = runTest {
        downloadManager.downloadFrom(TEST_DOWNLOAD_URL) { result ->
            val localCert = getLocalCert()
            assert(localCert?.equals(result) == true)
        }
    }

    //region Helpers

    private fun getLocalCert(): ByteArray? {
        val inputStream = object {}.javaClass.classLoader?.getResourceAsStream(LOCAL_CERT_FILE_NAME)
        return inputStream?.readBytes()
    }

    //endregion

}