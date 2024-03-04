package com.fm.easypay.repositories.consent_annual

import com.fm.easypay.api.requests.base.EasyPayQuery
import com.fm.easypay.api.responses.ConsentAnnualQueryResult
import com.fm.easypay.networking.NetworkResource

internal interface ConsentAnnualRepository {
    suspend fun getConsentAnnuals(query: EasyPayQuery): NetworkResource<ConsentAnnualQueryResult>
}