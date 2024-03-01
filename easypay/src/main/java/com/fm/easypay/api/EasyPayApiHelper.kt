package com.fm.easypay.api

import com.fm.easypay.api.requests.ConsentAnnualQueryRequest
import com.fm.easypay.api.responses.ConsentAnnualQueryResult
import com.fm.easypay.networking.NetworkResource

internal interface EasyPayApiHelper {

    suspend fun getConsentAnnuals(request: ConsentAnnualQueryRequest): NetworkResource<ConsentAnnualQueryResult>
}