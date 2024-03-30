package com.fm.easypay.api

import com.fm.easypay.api.requests.annual_consent.CancelAnnualConsentRequest
import com.fm.easypay.api.requests.annual_consent.CreateAnnualConsentRequest
import com.fm.easypay.api.requests.annual_consent.ListAnnualConsentsRequest
import com.fm.easypay.api.requests.charge_cc.ChargeCreditCardRequest
import com.fm.easypay.api.responses.annual_consent.CancelAnnualConsentResult
import com.fm.easypay.api.responses.annual_consent.CreateAnnualConsentResult
import com.fm.easypay.api.responses.annual_consent.ListAnnualConsentsResult
import com.fm.easypay.api.responses.charge_cc.ChargeCreditCardResult
import com.fm.easypay.networking.NetworkResource

internal interface EasyPayApiHelper {

    suspend fun listAnnualConsents(request: ListAnnualConsentsRequest): NetworkResource<ListAnnualConsentsResult>

    suspend fun chargeCreditCard(request: ChargeCreditCardRequest): NetworkResource<ChargeCreditCardResult>

    suspend fun createAnnualConsent(request: CreateAnnualConsentRequest): NetworkResource<CreateAnnualConsentResult>

    suspend fun cancelAnnualConsent(request: CancelAnnualConsentRequest): NetworkResource<CancelAnnualConsentResult>
}