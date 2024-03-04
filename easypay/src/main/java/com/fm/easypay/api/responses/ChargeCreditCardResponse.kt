package com.fm.easypay.api.responses

import com.fm.easypay.api.responses.base.ApiResponse
import com.fm.easypay.api.responses.base.TransactionResult
import com.google.gson.annotations.SerializedName

internal data class ChargeCreditCardResponse(
    @SerializedName("CreditCardSale_ManualResult")
    override val result: ChargeCreditCardResult,
) : ApiResponse<ChargeCreditCardResult>(result)

data class ChargeCreditCardResult(
    @SerializedName("FunctionOk")
    override val functionOk: Boolean,

    @SerializedName("ErrCode")
    override val errorCode: Int,

    @SerializedName("ErrMsg")
    override val errorMessage: String,

    @SerializedName("RespMsg")
    override val responseMessage: String,

    @SerializedName("TxApproved")
    override val txApproved: Boolean,

    @SerializedName("TxID")
    override val txId: Int,

    @SerializedName("TxnCode")
    override val txCode: String,

    @SerializedName("AVSresult")
    val avsResult: String,

    // TODO: Clarify type
    @SerializedName("AcquirerResponseEMV")
    val acquirerResponseEmv: String?,

    @SerializedName("CVVresult")
    val cvvResult: String,

    @SerializedName("IsPartialApproval")
    val isPartialApproval: Boolean,

    @SerializedName("RequiresVoiceAuth")
    val requiresVoiceAuth: Boolean,

    @SerializedName("ResponseApprovedAmount")
    val responseApprovedAmount: Int,

    @SerializedName("ResponseAuthorizedAmount")
    val responseAuthorizedAmount: Int,

    @SerializedName("ResponseBalanceAmount")
    val responseBalanceAmount: Int,
) : TransactionResult(
    functionOk,
    errorCode,
    errorMessage,
    responseMessage,
    txApproved,
    txId,
    txCode
)