package com.fm.easypay.repositories

import com.fm.easypay.api.EasyPayApiHelper
import com.fm.easypay.api.requests.ConsentAnnualQueryRequest
import com.fm.easypay.api.requests.base.EasyPayQuery
import com.fm.easypay.api.responses.ConsentAnnualQueryResult
import com.fm.easypay.networking.NetworkResource

internal class ConsentAnnualRepositoryImpl(
    private val apiHelper: EasyPayApiHelper,
) : ConsentAnnualRepository {

    override suspend fun getConsentAnnuals(query: EasyPayQuery): NetworkResource<ConsentAnnualQueryResult> {
        val request = ConsentAnnualQueryRequest(
            userDataPresent = true,
            body = query,
        )
        return apiHelper.getConsentAnnuals(request)
    }
}