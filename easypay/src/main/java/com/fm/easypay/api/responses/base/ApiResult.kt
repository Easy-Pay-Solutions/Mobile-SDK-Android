package com.fm.easypay.api.responses.base

abstract class ApiResult(
    open val functionOk: Boolean,
    open val errorCode: Int,
    open val errorMessage: String,
    open val responseMessage: String,
) {
    internal open fun parseIfNeeded() {}
}