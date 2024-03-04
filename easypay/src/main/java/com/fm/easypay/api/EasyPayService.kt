package com.fm.easypay.api

import com.fm.easypay.api.requests.ChargeCreditCardBody
import com.fm.easypay.api.requests.base.EasyPayQuery
import com.fm.easypay.api.responses.ChargeCreditCardResponse
import com.fm.easypay.api.responses.ConsentAnnualQueryResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

internal interface EasyPayService {

    companion object {
        private const val AUTH_HEADER_NAME = "SessKey"
    }

    @POST("Query/ConsentAnnual")
    suspend fun getConsentAnnuals(
        @Header(AUTH_HEADER_NAME) sessKey: String,
        @Body query: EasyPayQuery,
    ): Response<ConsentAnnualQueryResponse>

    @POST("CardSale/Manual")
    suspend fun cardSaleManual(
        @Header(AUTH_HEADER_NAME) sessKey: String,
        @Body body: ChargeCreditCardBody,
    ): Response<ChargeCreditCardResponse>
}