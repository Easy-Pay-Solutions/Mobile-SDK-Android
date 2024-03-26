package com.fm.easypay.api

import com.fm.easypay.api.requests.ChargeCreditCardRequest
import com.fm.easypay.api.requests.ConsentAnnualQueryRequest
import com.fm.easypay.api.requests.CreateAnnualConsentRequest
import com.fm.easypay.api.responses.ChargeCreditCardResponse
import com.fm.easypay.api.responses.ChargeCreditCardResult
import com.fm.easypay.api.responses.ConsentAnnualQueryResult
import com.fm.easypay.api.responses.CreateAnnualConsentResult
import com.fm.easypay.networking.NetworkResource

internal interface EasyPayApiHelper {

    suspend fun getConsentAnnuals(request: ConsentAnnualQueryRequest): NetworkResource<ConsentAnnualQueryResult>

    suspend fun chargeCreditCard(request: ChargeCreditCardRequest): NetworkResource<ChargeCreditCardResult>

    suspend fun createAnnualConsent(request: CreateAnnualConsentRequest): NetworkResource<CreateAnnualConsentResult>
}