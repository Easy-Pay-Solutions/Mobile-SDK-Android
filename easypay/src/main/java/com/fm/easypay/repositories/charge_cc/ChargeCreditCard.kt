package com.fm.easypay.repositories.charge_cc

import com.fm.easypay.api.requests.ChargeCreditCardBody
import com.fm.easypay.api.responses.ChargeCreditCardResult
import com.fm.easypay.networking.NetworkResource
import org.koin.java.KoinJavaComponent

class ChargeCreditCard {

    private val chargeCreditCardRepository: ChargeCreditCardRepository by KoinJavaComponent.inject(
        ChargeCreditCardRepository::class.java
    )

    suspend fun chargeCreditCard(query: ChargeCreditCardBody): NetworkResource<ChargeCreditCardResult> {
        return chargeCreditCardRepository.chargeCreditCard(query)
    }
}