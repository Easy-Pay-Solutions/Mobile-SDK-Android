package com.fm.easypay.repositories.annual_consent.list

import com.fm.easypay.api.EasyPayApiHelper
import com.fm.easypay.api.requests.annual_consent.ListAnnualConsentsRequest
import com.fm.easypay.api.responses.annual_consent.ListAnnualConsentsResult
import com.fm.easypay.networking.NetworkResource
import com.fm.easypay.utils.QueryParser

internal class ListAnnualConsentsRepositoryImpl(
    private val apiHelper: EasyPayApiHelper,
) : ListAnnualConsentsRepository {

    override suspend fun listAnnualConsents(
        params: ListAnnualConsentsBodyParams,
    ): NetworkResource<ListAnnualConsentsResult> {
        val query = QueryParser.parseToQuery(params)
        val request = ListAnnualConsentsRequest(
            userDataPresent = true,
            body = query,
        )
        return apiHelper.listAnnualConsents(request)
    }
}