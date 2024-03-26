package com.fm.easypay.api

import com.fm.easypay.api.requests.ChargeCreditCardBodyDto
import com.fm.easypay.api.requests.CreateAnnualConsentBodyDto
import com.fm.easypay.api.requests.base.EasyPayQuery
import com.fm.easypay.api.responses.ChargeCreditCardResponse
import com.fm.easypay.api.responses.ConsentAnnualQueryResponse
import com.fm.easypay.api.responses.CreateAnnualConsentResponse
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
        @Body body: ChargeCreditCardBodyDto,
    ): Response<ChargeCreditCardResponse>

    @POST("ConsentAnnual/Create_MAN")
    suspend fun createAnnualConsent(
        @Header(AUTH_HEADER_NAME) sessKey: String,
        @Body body: CreateAnnualConsentBodyDto,
    ): Response<CreateAnnualConsentResponse>
}