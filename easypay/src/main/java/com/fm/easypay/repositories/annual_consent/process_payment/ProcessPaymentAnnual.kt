package com.fm.easypay.repositories.annual_consent.process_payment

import com.fm.easypay.api.responses.annual_consent.ProcessPaymentAnnualResult
import com.fm.easypay.networking.NetworkResource
import org.koin.java.KoinJavaComponent

class ProcessPaymentAnnual {

    private val processPaymentAnnualRepository: ProcessPaymentAnnualRepository by KoinJavaComponent.inject(
        ProcessPaymentAnnualRepository::class.java
    )

    suspend fun processPaymentAnnual(
        params: ProcessPaymentAnnualParams,
    ): NetworkResource<ProcessPaymentAnnualResult> {
        return processPaymentAnnualRepository.processPaymentAnnual(params)
    }
}