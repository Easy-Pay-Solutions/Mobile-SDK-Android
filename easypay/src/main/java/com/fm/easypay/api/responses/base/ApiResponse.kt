package com.fm.easypay.api.responses.base

internal abstract class ApiResponse<T : ApiResult>(
    open val result: T,
)