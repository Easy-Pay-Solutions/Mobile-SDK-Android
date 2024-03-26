package com.fm.easypay.repositories.consent_annual.create

import com.fm.easypay.api.responses.CreateAnnualConsentResult
import com.fm.easypay.networking.NetworkResource

internal interface CreateAnnualConsentRepository {
    suspend fun createAnnualConsent(
        params: CreateAnnualConsentBodyParams,
    ): NetworkResource<CreateAnnualConsentResult>
}