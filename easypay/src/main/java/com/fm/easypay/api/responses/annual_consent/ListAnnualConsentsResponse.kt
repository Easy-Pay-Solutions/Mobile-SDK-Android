package com.fm.easypay.api.responses.annual_consent

import com.fm.easypay.api.responses.base.ApiResponse
import com.fm.easypay.api.responses.base.ApiResult
import com.google.gson.annotations.SerializedName

internal data class ListAnnualConsentsResponse(
    @SerializedName("ConsentAnnual_QueryResult")
    override val result: ListAnnualConsentsResult,
) : ApiResponse<ListAnnualConsentsResult>(result)

data class ListAnnualConsentsResult(
    @SerializedName("FunctionOk")
    override val functionOk: Boolean,

    @SerializedName("ErrCode")
    override val errorCode: Int,

    @SerializedName("ErrMsg")
    override val errorMessage: String,

    @SerializedName("RespMsg")
    override val responseMessage: String,

    @SerializedName("NumRecords")
    val numRecords: Int,

    @SerializedName("Consents")
    val consents: List<AnnualConsent>,
) : ApiResult(
    functionOk,
    errorCode,
    errorMessage,
    responseMessage
)

data class AnnualConsent(
    @SerializedName("AcctHolderFirstName")
    val accountHolderFirstName: String,

    @SerializedName("AcctHolderID")
    val accountHolderId: Int,

    @SerializedName("AcctHolderLastName")
    val accountHolderLastName: String,

    @SerializedName("AcctNo")
    val accountNumber: String,

    @SerializedName("AuthTxID")
    val authTxId: Int,

    @SerializedName("CreatedBy")
    val createdBy: String,

    // TODO: Parse to date
    @SerializedName("CreatedOn")
    val createdOn: String,

    @SerializedName("CustID")
    val customerId: Int,

    @SerializedName("CustomerRefID")
    val customerReferenceId: String,

    // TODO: Parse to date
    @SerializedName("EndDate")
    val endDate: String,

    @SerializedName("ID")
    val id: Int,

    @SerializedName("IsEnabled")
    val isEnabled: Boolean,

    @SerializedName("LimitLifeTime")
    val limitLifeTime: Double,

    @SerializedName("LimitPerCharge")
    val limitPerCharge: Double,

    @SerializedName("MerchID")
    val merchId: Int,

    @SerializedName("NumDays")
    val numDays: Int,

    @SerializedName("RPGUID")
    val rpguid: String,

    @SerializedName("ServiceDescrip")
    val serviceDescription: String,

    // TODO: Parse to date
    @SerializedName("StartDate")
    val startDate: String,
)
