package com.easypaysolutions.repositories.annual_consent.list

import com.easypaysolutions.api.EasyPayApiHelper
import com.easypaysolutions.api.requests.annual_consent.ListAnnualConsentsRequest
import com.easypaysolutions.api.responses.annual_consent.ListAnnualConsentsResult
import com.easypaysolutions.networking.NetworkResource
import com.easypaysolutions.utils.QueryParser

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