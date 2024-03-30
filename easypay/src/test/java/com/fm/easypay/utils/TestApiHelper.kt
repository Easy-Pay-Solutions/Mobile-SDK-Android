package com.fm.easypay.utils

import com.fm.easypay.api.EasyPayApiHelper
import com.fm.easypay.api.requests.annual_consent.CancelAnnualConsentRequest
import com.fm.easypay.api.requests.annual_consent.CreateAnnualConsentRequest
import com.fm.easypay.api.requests.annual_consent.ListAnnualConsentsRequest
import com.fm.easypay.api.requests.charge_cc.ChargeCreditCardRequest
import com.fm.easypay.api.responses.annual_consent.CancelAnnualConsentResult
import com.fm.easypay.api.responses.annual_consent.CreateAnnualConsentResult
import com.fm.easypay.api.responses.annual_consent.ListAnnualConsentsResult
import com.fm.easypay.api.responses.charge_cc.ChargeCreditCardResult
import com.fm.easypay.networking.NetworkResource
import org.mockito.Mockito

internal class TestApiHelper : EasyPayApiHelper {
    override suspend fun listAnnualConsents(request: ListAnnualConsentsRequest): NetworkResource<ListAnnualConsentsResult> {
        return NetworkResource.success(Mockito.mock())
    }

    override suspend fun chargeCreditCard(request: ChargeCreditCardRequest): NetworkResource<ChargeCreditCardResult> {
        return NetworkResource.success(Mockito.mock())
    }

    override suspend fun createAnnualConsent(request: CreateAnnualConsentRequest): NetworkResource<CreateAnnualConsentResult> {
        return NetworkResource.success(Mockito.mock())
    }

    override suspend fun cancelAnnualConsent(request: CancelAnnualConsentRequest): NetworkResource<CancelAnnualConsentResult> {
        return NetworkResource.success(Mockito.mock())
    }
}