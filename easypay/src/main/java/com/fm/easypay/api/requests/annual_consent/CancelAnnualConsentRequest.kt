package com.fm.easypay.api.requests.annual_consent

import com.fm.easypay.api.requests.base.ApiRequest
import com.google.gson.annotations.SerializedName

internal data class CancelAnnualConsentRequest(
    override val userDataPresent: Boolean,
    override val body: CancelAnnualConsentBodyDto,
) : ApiRequest<CancelAnnualConsentBodyDto>(userDataPresent, body)

internal data class CancelAnnualConsentBodyDto(
    @SerializedName("ConsentID")
    val consentId: Int,
)