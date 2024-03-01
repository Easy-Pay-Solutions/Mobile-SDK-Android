package com.fm.easypay.api

import com.fm.easypay.api.requests.ConsentAnnualQueryRequest
import com.fm.easypay.api.requests.base.ApiRequest
import com.fm.easypay.api.responses.ConsentAnnualQueryResult
import com.fm.easypay.networking.NetworkDataSource
import com.fm.easypay.networking.NetworkResource
import com.fm.easypay.networking.authentication.AuthHelper

internal class EasyPayApiHelperImpl(
    private val easyPayService: EasyPayService,
    private val networkDataSource: NetworkDataSource,
    private val authHelper: AuthHelper,
) : EasyPayApiHelper {

    //region Overridden

    override suspend fun getConsentAnnuals(
        request: ConsentAnnualQueryRequest,
    ): NetworkResource<ConsentAnnualQueryResult> = networkDataSource.getResult {
        val header = getHeader(request)
        easyPayService.getConsentAnnuals(header, request.body)
    }

    //endregion

    //region Private

    private fun getHeader(request: ApiRequest<*>): String {
        return authHelper.getSessKey(request.userDataPresent)
    }

    //endregion

}