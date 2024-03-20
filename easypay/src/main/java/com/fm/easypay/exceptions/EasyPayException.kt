package com.fm.easypay.exceptions

import com.fm.easypay.api.EasyPayApiError

/**
 * A base class for EasyPay-related exceptions.
 */
abstract class EasyPayException(
    val easyPayError: EasyPayApiError? = null,
    message: String? = easyPayError?.message,
    cause: Throwable? = null,
) : Exception(message, cause)