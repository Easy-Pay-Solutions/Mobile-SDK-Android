package com.fm.easypay.exceptions

/**
 * A base class for EasyPay-related exceptions.
 */
abstract class EasyPayException(
    message: String?,
    cause: Throwable? = null,
) : Exception(message, cause)