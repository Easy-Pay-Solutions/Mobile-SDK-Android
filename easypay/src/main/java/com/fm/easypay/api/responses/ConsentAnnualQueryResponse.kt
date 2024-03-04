package com.fm.easypay.api.responses

import com.fm.easypay.api.responses.base.ApiResponse
import com.fm.easypay.api.responses.base.ApiResult
import com.google.gson.annotations.SerializedName

internal data class ConsentAnnualQueryResponse(
    @SerializedName("ConsentAnnual_QueryResult")
    override val result: ConsentAnnualQueryResult,
) : ApiResponse<ConsentAnnualQueryResult>(result)

data class ConsentAnnualQueryResult(
    @SerializedName("FunctionOk")
    override val functionOk: Boolean,

    @SerializedName("ErrCode")
    override val errorCode: Int,

    @SerializedName("ErrMsg")
    override val errorMessage: String,

    @SerializedName("RespMsg")
    override val responseMessage: String,
) : ApiResult(
    functionOk,
    errorCode,
    errorMessage,
    responseMessage
)