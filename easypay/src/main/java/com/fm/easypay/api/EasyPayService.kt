package com.fm.easypay.api

import com.fm.easypay.api.requests.annual_consent.CancelAnnualConsentBodyDto
import com.fm.easypay.api.requests.annual_consent.ConsentAnnualQuery
import com.fm.easypay.api.requests.annual_consent.CreateAnnualConsentBodyDto
import com.fm.easypay.api.requests.charge_cc.ChargeCreditCardBodyDto
import com.fm.easypay.api.responses.annual_consent.CancelAnnualConsentResponse
import com.fm.easypay.api.responses.annual_consent.CreateAnnualConsentResponse
import com.fm.easypay.api.responses.annual_consent.ListAnnualConsentsResponse
import com.fm.easypay.api.responses.charge_cc.ChargeCreditCardResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

internal interface EasyPayService {

    companion object {
        private const val AUTH_HEADER_NAME = "SessKey"
    }

    @POST("Query/ConsentAnnual")
    suspend fun listAnnualConsents(
        @Header(AUTH_HEADER_NAME) sessKey: String,
        @Body query: ConsentAnnualQuery,
    ): Response<ListAnnualConsentsResponse>

    @POST("CardSale/Manual")
    suspend fun cardSaleManual(
        @Header(AUTH_HEADER_NAME) sessKey: String,
        @Body body: ChargeCreditCardBodyDto,
    ): Response<ChargeCreditCardResponse>

    @POST("ListAnnualConsents/Create_MAN")
    suspend fun createAnnualConsent(
        @Header(AUTH_HEADER_NAME) sessKey: String,
        @Body body: CreateAnnualConsentBodyDto,
    ): Response<CreateAnnualConsentResponse>

    @POST("ConsentAnnual/Cancel")
    suspend fun cancelAnnualConsent(
        @Header(AUTH_HEADER_NAME) sessKey: String,
        @Body body: CancelAnnualConsentBodyDto,
    ): Response<CancelAnnualConsentResponse>
}