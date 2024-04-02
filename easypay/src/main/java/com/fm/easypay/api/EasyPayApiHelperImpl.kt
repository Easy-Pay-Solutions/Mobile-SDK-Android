package com.fm.easypay.api

import com.fm.easypay.api.requests.annual_consent.CancelAnnualConsentRequest
import com.fm.easypay.api.requests.annual_consent.CreateAnnualConsentRequest
import com.fm.easypay.api.requests.annual_consent.ListAnnualConsentsRequest
import com.fm.easypay.api.requests.annual_consent.ProcessPaymentAnnualRequest
import com.fm.easypay.api.requests.base.ApiRequest
import com.fm.easypay.api.requests.charge_cc.ChargeCreditCardRequest
import com.fm.easypay.api.responses.annual_consent.CancelAnnualConsentResult
import com.fm.easypay.api.responses.annual_consent.CreateAnnualConsentResult
import com.fm.easypay.api.responses.annual_consent.ListAnnualConsentsResult
import com.fm.easypay.api.responses.annual_consent.ProcessPaymentAnnualResult
import com.fm.easypay.api.responses.charge_cc.ChargeCreditCardResult
import com.fm.easypay.networking.NetworkDataSource
import com.fm.easypay.networking.NetworkResource
import com.fm.easypay.networking.authentication.AuthHelper

internal class EasyPayApiHelperImpl(
    private val easyPayService: EasyPayService,
    private val networkDataSource: NetworkDataSource,
    private val authHelper: AuthHelper,
) : EasyPayApiHelper {

    //region Overridden

    override suspend fun listAnnualConsents(
        request: ListAnnualConsentsRequest,
    ): NetworkResource<ListAnnualConsentsResult> = networkDataSource.getResult {
        val header = getHeader(request)
        easyPayService.listAnnualConsents(header, request.body)
    }

    override suspend fun chargeCreditCard(
        request: ChargeCreditCardRequest,
    ): NetworkResource<ChargeCreditCardResult> = networkDataSource.getTransactionResult {
        val header = getHeader(request)
        easyPayService.cardSaleManual(header, request.body)
    }

    override suspend fun createAnnualConsent(
        request: CreateAnnualConsentRequest,
    ): NetworkResource<CreateAnnualConsentResult> = networkDataSource.getResult {
        val header = getHeader(request)
        easyPayService.createAnnualConsent(header, request.body)
    }

    override suspend fun cancelAnnualConsent(
        request: CancelAnnualConsentRequest,
    ): NetworkResource<CancelAnnualConsentResult> = networkDataSource.getResult {
        val header = getHeader(request)
        easyPayService.cancelAnnualConsent(header, request.body)
    }

    override suspend fun processPaymentAnnual(
        request: ProcessPaymentAnnualRequest,
    ): NetworkResource<ProcessPaymentAnnualResult> = networkDataSource.getTransactionResult {
        val header = getHeader(request)
        easyPayService.processPaymentAnnual(header, request.body)
    }

    //endregion

    //region Private

    private fun getHeader(request: ApiRequest<*>): String {
        return authHelper.getSessKey(request.userDataPresent)
    }

    //endregion

}