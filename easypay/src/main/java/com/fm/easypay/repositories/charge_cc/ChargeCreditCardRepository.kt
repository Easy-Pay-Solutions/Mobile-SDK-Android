package com.fm.easypay.repositories.charge_cc

import com.fm.easypay.api.requests.ChargeCreditCardBody
import com.fm.easypay.api.responses.ChargeCreditCardResult
import com.fm.easypay.networking.NetworkResource

internal interface ChargeCreditCardRepository {
    suspend fun chargeCreditCard(requestBody: ChargeCreditCardBody): NetworkResource<ChargeCreditCardResult>
}