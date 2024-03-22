package com.fm.easypay.repositories.charge_cc

import com.fm.easypay.api.responses.ChargeCreditCardResult
import com.fm.easypay.networking.NetworkResource

internal interface ChargeCreditCardRepository {
    suspend fun chargeCreditCard(
        params: ChargeCreditCardBodyParams,
    ): NetworkResource<ChargeCreditCardResult>
}