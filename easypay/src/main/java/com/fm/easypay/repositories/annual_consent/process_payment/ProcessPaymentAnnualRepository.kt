package com.fm.easypay.repositories.annual_consent.process_payment

import com.fm.easypay.api.responses.annual_consent.ProcessPaymentAnnualResult
import com.fm.easypay.networking.NetworkResource

internal interface ProcessPaymentAnnualRepository {
    suspend fun processPaymentAnnual(params: ProcessPaymentAnnualParams): NetworkResource<ProcessPaymentAnnualResult>
}