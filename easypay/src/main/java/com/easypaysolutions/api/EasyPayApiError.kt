package com.easypaysolutions.api

/**
 * A model representing an EasyPay API errors object.
 */
data class EasyPayApiError(
    val code: Int,
    val message: String,
)