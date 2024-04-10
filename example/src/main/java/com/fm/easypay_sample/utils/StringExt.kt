package com.fm.easypay_sample.utils

fun String.toNullIfBlank(): String? {
    return ifBlank { null }
}