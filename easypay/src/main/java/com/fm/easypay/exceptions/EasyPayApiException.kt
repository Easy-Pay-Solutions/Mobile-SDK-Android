package com.fm.easypay.exceptions

import com.fm.easypay.api.EasyPayApiError

class EasyPayApiException(
    easyPayError: EasyPayApiError? = null,
    message: String? = easyPayError?.message,
    cause: Throwable? = null,
) : EasyPayException(easyPayError, message, cause)
