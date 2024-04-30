package com.easypaysolutions.repositories.annual_consent.create

import com.easypaysolutions.api.EasyPayApiHelper
import com.easypaysolutions.api.requests.annual_consent.CreateAnnualConsentRequest
import com.easypaysolutions.api.responses.annual_consent.CreateAnnualConsentResult
import com.easypaysolutions.networking.NetworkResource
import com.easypaysolutions.utils.validation.ValidatorUtils

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