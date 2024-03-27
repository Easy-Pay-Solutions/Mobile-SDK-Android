package com.fm.easypay.repositories.annual_consent.create

import com.fm.easypay.api.responses.annual_consent.CreateAnnualConsentResult
import com.fm.easypay.networking.NetworkResource

internal interface CreateAnnualConsentRepository {
    suspend fun createAnnualConsent(
        params: CreateAnnualConsentBodyParams,
    ): NetworkResource<CreateAnnualConsentResult>
}