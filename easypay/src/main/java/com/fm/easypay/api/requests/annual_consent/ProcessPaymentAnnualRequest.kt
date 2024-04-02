package com.fm.easypay.api.requests.annual_consent

import com.fm.easypay.api.requests.base.ApiRequest
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

internal data class ProcessPaymentAnnualRequest(
    override val userDataPresent: Boolean,
    override val body: ProcessPaymentAnnualBodyDto,
) : ApiRequest<ProcessPaymentAnnualBodyDto>(userDataPresent, body)

internal data class ProcessPaymentAnnualBodyDto(
    @SerializedName("ConsentID")
    val consentId: Int,

    @SerializedName("ProcessAmount")
    val processAmount: BigDecimal,
)