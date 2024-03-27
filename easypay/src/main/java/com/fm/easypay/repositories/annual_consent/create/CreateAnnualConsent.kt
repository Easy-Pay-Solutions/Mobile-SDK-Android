package com.fm.easypay.repositories.annual_consent.create

import com.fm.easypay.api.responses.annual_consent.CreateAnnualConsentResult
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