package com.fm.easypay.repositories.annual_consent.cancel

import com.fm.easypay.api.EasyPayApiHelper
import com.fm.easypay.api.requests.annual_consent.CancelAnnualConsentRequest
import com.fm.easypay.api.responses.annual_consent.CancelAnnualConsentResult
import com.fm.easypay.networking.NetworkResource

internal class CancelAnnualConsentRepositoryImpl(
    private val apiHelper: EasyPayApiHelper,
) : CancelAnnualConsentRepository {

    override suspend fun cancelAnnualConsent(
        params: CancelAnnualConsentBodyParams,
    ): NetworkResource<CancelAnnualConsentResult> {
        val request = CancelAnnualConsentRequest(
            userDataPresent = true,
            body = params.toDto(),
        )
        return apiHelper.cancelAnnualConsent(request)
    }
}