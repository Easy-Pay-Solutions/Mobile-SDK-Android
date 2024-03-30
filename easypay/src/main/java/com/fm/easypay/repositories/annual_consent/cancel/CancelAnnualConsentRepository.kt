package com.fm.easypay.repositories.annual_consent.cancel

import com.fm.easypay.api.responses.annual_consent.CancelAnnualConsentResult
import com.fm.easypay.networking.NetworkResource

internal interface CancelAnnualConsentRepository {
    suspend fun cancelAnnualConsent(
        params: CancelAnnualConsentBodyParams,
    ): NetworkResource<CancelAnnualConsentResult>
}