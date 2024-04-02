package com.fm.easypay.repositories.annual_consent.process_payment

import com.fm.easypay.api.EasyPayApiHelper
import com.fm.easypay.api.requests.annual_consent.ProcessPaymentAnnualRequest
import com.fm.easypay.api.responses.annual_consent.ProcessPaymentAnnualResult
import com.fm.easypay.networking.NetworkResource

internal class ProcessPaymentAnnualRepositoryImpl(
    private val apiHelper: EasyPayApiHelper,
) : ProcessPaymentAnnualRepository {

    override suspend fun processPaymentAnnual(
        params: ProcessPaymentAnnualParams,
    ): NetworkResource<ProcessPaymentAnnualResult> {
        val request = ProcessPaymentAnnualRequest(
            userDataPresent = true,
            body = params.toDto(),
        )
        return apiHelper.processPaymentAnnual(request)
    }
}