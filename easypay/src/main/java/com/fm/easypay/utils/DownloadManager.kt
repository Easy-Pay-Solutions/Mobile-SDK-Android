package com.fm.easypay.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.net.URL

internal interface DownloadManager {
    fun downloadFrom(
        urlString: String,
        byteArraySize: Int = 4096,
        callback: (ByteArray?) -> Unit,
    )
}

internal class DownloadManagerImpl(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : DownloadManager {
    override fun downloadFrom(
        urlString: String,
        byteArraySize: Int,
        callback: (ByteArray?) -> Unit,
    ) {
        CoroutineScope(ioDispatcher).launch {
            val url = URL(urlString)
            val output = ByteArrayOutputStream()
            withContext(ioDispatcher) {
                url.openStream()
            }.use { stream ->
                val buffer = ByteArray(byteArraySize)
                while (true) {
                    val bytesRead = stream.read(buffer)
                    if (bytesRead < 0) {
                        break
                    }
                    output.write(buffer, 0, bytesRead)
                }
            }
            callback(output.toByteArray())
        }
    }
}