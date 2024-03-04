package com.fm.easypay.repositories.charge_cc

import com.fm.easypay.api.EasyPayApiHelper
import com.fm.easypay.api.requests.ChargeCreditCardBody
import com.fm.easypay.api.requests.ChargeCreditCardRequest
import com.fm.easypay.api.responses.ChargeCreditCardResult
import com.fm.easypay.networking.NetworkResource

internal class ChargeCreditCardRepositoryImpl(
    private val apiHelper: EasyPayApiHelper,
) : ChargeCreditCardRepository {

    override suspend fun chargeCreditCard(requestBody: ChargeCreditCardBody): NetworkResource<ChargeCreditCardResult> {
        val request = ChargeCreditCardRequest(
            userDataPresent = true,
            body = requestBody,
        )
        return apiHelper.chargeCreditCard(request)
    }
}