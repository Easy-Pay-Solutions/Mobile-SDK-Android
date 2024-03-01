package com.fm.easypay.utils

import java.util.Locale

fun ByteArray.toHex(): String =
    joinToString(separator = "") { eachByte -> "%02x".format(eachByte) }.uppercase(
        Locale.getDefault()
    )