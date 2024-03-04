package com.fm.easypay.utils

import com.fm.easypay.BuildConfig
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DownloadManagerTest {

    private val downloadManager = DownloadManager()

    companion object {
        private const val TEST_DOWNLOAD_URL = BuildConfig.RSA_CERTIFICATE_URL
        private const val LOCAL_CERT_FILE_NAME = "mobile.easypay5.com.pem"
    }

    @Test
    fun `downloadFile() returns proper file content`() {
        val result = downloadManager.downloadFile(TEST_DOWNLOAD_URL)
        val localCert = getLocalCert()
        assert(localCert.contentEquals(result))
    }

    //region Helpers

    private fun getLocalCert(): ByteArray? {
        val inputStream = object {}.javaClass.classLoader?.getResourceAsStream(LOCAL_CERT_FILE_NAME)
        return inputStream?.readBytes()
    }

    //endregion

}