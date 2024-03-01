package com.fm.easypay.repositories

import com.fm.easypay.api.requests.base.EasyPayQuery
import com.fm.easypay.api.responses.ConsentAnnualQueryResult
import com.fm.easypay.networking.NetworkResource
import org.koin.java.KoinJavaComponent.inject

class ConsentAnnual {

    private val consentAnnualRepository: ConsentAnnualRepository by inject(ConsentAnnualRepository::class.java)

    suspend fun getConsentAnnuals(query: EasyPayQuery): NetworkResource<ConsentAnnualQueryResult> {
        return consentAnnualRepository.getConsentAnnuals(query)
    }
}