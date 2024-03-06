package com.fm.easypay.repositories.charge_cc

import com.fm.easypay.api.responses.ChargeCreditCardResult
import com.fm.easypay.networking.NetworkResource
import com.fm.easypay.utils.secured.SecureData

internal interface ChargeCreditCardRepository {
    suspend fun chargeCreditCard(
        params: ChargeCreditCardBodyParams,
        secureData: SecureData<String>,
    ): NetworkResource<ChargeCreditCardResult>
}