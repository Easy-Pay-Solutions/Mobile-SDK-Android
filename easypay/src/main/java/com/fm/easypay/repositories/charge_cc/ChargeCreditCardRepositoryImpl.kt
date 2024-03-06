package com.fm.easypay.repositories.charge_cc

import com.fm.easypay.api.EasyPayApiHelper
import com.fm.easypay.api.requests.ChargeCreditCardRequest
import com.fm.easypay.api.responses.ChargeCreditCardResult
import com.fm.easypay.networking.NetworkResource
import com.fm.easypay.utils.secured.SecureData

internal class ChargeCreditCardRepositoryImpl(
    private val apiHelper: EasyPayApiHelper,
) : ChargeCreditCardRepository {

    override suspend fun chargeCreditCard(
        params: ChargeCreditCardBodyParams,
        secureData: SecureData<String>,
    ): NetworkResource<ChargeCreditCardResult> {
        val request = ChargeCreditCardRequest(
            userDataPresent = true,
            body = params.toDto(secureData.data),
        )
        return apiHelper.chargeCreditCard(request)
    }
}