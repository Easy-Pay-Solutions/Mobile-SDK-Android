package com.fm.easypay.repositories.charge_cc

import com.fm.easypay.api.EasyPayApiHelper
import com.fm.easypay.api.requests.ChargeCreditCardRequest
import com.fm.easypay.api.responses.ChargeCreditCardResult
import com.fm.easypay.networking.NetworkResource
import com.fm.easypay.utils.secured.SecureData
import com.fm.easypay.utils.validation.ValidatorUtils

internal class ChargeCreditCardRepositoryImpl(
    private val apiHelper: EasyPayApiHelper,
) : ChargeCreditCardRepository {

    override suspend fun chargeCreditCard(
        params: ChargeCreditCardBodyParams,
        secureData: SecureData<String>,
    ): NetworkResource<ChargeCreditCardResult> = ValidatorUtils.validate(params) {
        val request = ChargeCreditCardRequest(
            userDataPresent = true,
            body = params.toDto(secureData.data),
        )
        apiHelper.chargeCreditCard(request)
    }
}