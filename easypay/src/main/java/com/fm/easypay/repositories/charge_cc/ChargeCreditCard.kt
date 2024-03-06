package com.fm.easypay.repositories.charge_cc

import com.fm.easypay.api.responses.ChargeCreditCardResult
import com.fm.easypay.networking.NetworkResource
import com.fm.easypay.utils.secured.SecureData
import org.koin.java.KoinJavaComponent

class ChargeCreditCard {

    private val chargeCreditCardRepository: ChargeCreditCardRepository by KoinJavaComponent.inject(
        ChargeCreditCardRepository::class.java
    )

    suspend fun chargeCreditCard(
        params: ChargeCreditCardBodyParams,
        secureData: SecureData<String>,
    ): NetworkResource<ChargeCreditCardResult> {
        return chargeCreditCardRepository.chargeCreditCard(params, secureData)
    }
}