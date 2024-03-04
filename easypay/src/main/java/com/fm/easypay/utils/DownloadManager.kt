package com.fm.easypay.utils

import java.io.ByteArrayOutputStream
import java.net.URL

class DownloadManager {

    fun downloadFile(urlString: String, byteArraySize: Int = 4096): ByteArray? {
        val url = URL(urlString)
        val output = ByteArrayOutputStream()
        url.openStream().use { stream ->
            val buffer = ByteArray(byteArraySize)
            while (true) {
                val bytesRead = stream.read(buffer)
                if (bytesRead < 0) {
                    break
                }
                output.write(buffer, 0, bytesRead)
            }
        }

        return output.toByteArray()
    }
}