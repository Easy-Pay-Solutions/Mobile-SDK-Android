package com.fm.easypay.repositories.charge_cc

import com.fm.easypay.api.responses.charge_cc.ChargeCreditCardResult
import com.fm.easypay.networking.NetworkResource
import org.koin.java.KoinJavaComponent

class ChargeCreditCard {

    private val chargeCreditCardRepository: ChargeCreditCardRepository by KoinJavaComponent.inject(
        ChargeCreditCardRepository::class.java
    )

    suspend fun chargeCreditCard(
        params: ChargeCreditCardBodyParams,
    ): NetworkResource<ChargeCreditCardResult> {
        return chargeCreditCardRepository.chargeCreditCard(params)
    }
}