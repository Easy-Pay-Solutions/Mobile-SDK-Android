package com.fm.easypay.api.responses.base

abstract class TransactionResult(
    functionOk: Boolean,
    errorCode: Int,
    errorMessage: String,
    responseMessage: String,
    open val txApproved: Boolean,
    open val txId: Int,
    open val txCode: String,
) : ApiResult(
    functionOk,
    errorCode,
    errorMessage,
    responseMessage
)