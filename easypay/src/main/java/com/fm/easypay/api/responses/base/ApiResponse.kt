package com.fm.easypay.api.responses.base

internal abstract class ApiResponse<T : ApiResult> internal constructor(
    open val result: T,
)