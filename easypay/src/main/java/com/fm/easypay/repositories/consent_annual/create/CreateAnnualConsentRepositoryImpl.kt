package com.fm.easypay.repositories.consent_annual.create

import com.fm.easypay.api.EasyPayApiHelper
import com.fm.easypay.api.requests.CreateAnnualConsentRequest
import com.fm.easypay.api.responses.CreateAnnualConsentResult
import com.fm.easypay.networking.NetworkResource
import com.fm.easypay.utils.validation.ValidatorUtils

internal class CreateAnnualConsentRepositoryImpl(
    private val apiHelper: EasyPayApiHelper,
) : CreateAnnualConsentRepository {
    override suspend fun createAnnualConsent(
        params: CreateAnnualConsentBodyParams,
    ): NetworkResource<CreateAnnualConsentResult> = ValidatorUtils.validate(params) {
        val request = CreateAnnualConsentRequest(
            userDataPresent = true,
            body = params.toDto(),
        )
        apiHelper.createAnnualConsent(request)
    }
}