package com.fm.easypay.repositories.consent_annual.create

import com.fm.easypay.api.responses.CreateAnnualConsentResult
import com.fm.easypay.networking.NetworkResource
import org.koin.java.KoinJavaComponent

class CreateAnnualConsent {

    private val createAnnualConsentRepository: CreateAnnualConsentRepository by KoinJavaComponent.inject(
        CreateAnnualConsentRepository::class.java
    )

    suspend fun createAnnualConsent(
        params: CreateAnnualConsentBodyParams,
    ): NetworkResource<CreateAnnualConsentResult> {
        return createAnnualConsentRepository.createAnnualConsent(params)
    }
}