package com.fm.easypay.repositories.annual_consent.cancel

import com.fm.easypay.api.responses.annual_consent.CancelAnnualConsentResult
import com.fm.easypay.networking.NetworkResource
import org.koin.java.KoinJavaComponent

class CancelAnnualConsent {

    private val cancelAnnualConsentRepository: CancelAnnualConsentRepository by KoinJavaComponent.inject(
        CancelAnnualConsentRepository::class.java
    )

    suspend fun cancelAnnualConsent(
        params: CancelAnnualConsentBodyParams,
    ): NetworkResource<CancelAnnualConsentResult> {
        return cancelAnnualConsentRepository.cancelAnnualConsent(params)
    }
}