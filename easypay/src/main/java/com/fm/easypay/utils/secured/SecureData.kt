package com.fm.easypay.utils.secured

internal interface SecureWidget<T> {
    val data: SecureData<T>
}

data class SecureData<T> internal constructor(
    val data: T,
)