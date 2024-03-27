package com.fm.easypay.repositories.annual_consent.create

import com.fm.easypay.api.EasyPayApiHelper
import com.fm.easypay.api.requests.annual_consent.CreateAnnualConsentRequest
import com.fm.easypay.api.responses.annual_consent.CreateAnnualConsentResult
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