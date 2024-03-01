package com.fm.easypay.api.responses.base

import com.google.gson.annotations.SerializedName

abstract class TransactionResult(
    @SerializedName("FunctionOk")
    override val functionOk: Boolean,

    @SerializedName("ErrCode")
    override val errorCode: Int,

    @SerializedName("ErrMsg")
    override val errorMessage: String,

    @SerializedName("RespMsg")
    override val responseMessage: String,

    @SerializedName("TxApproved")
    val txApproved: Boolean,

    @SerializedName("TxID")
    val txId: Int,

    @SerializedName("TxnCode")
    val txCode: String,
) : ApiResult(
    functionOk,
    errorCode,
    errorMessage,
    responseMessage
)