package com.fm.easypay.api.responses.base

abstract class ApiResponse<T : ApiResult>(
    open val result: T,
)