package com.fm.easypay_sample.ui.charge_card

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fm.easypay.api.requests.Amounts
import com.fm.easypay.api.requests.BillingAddress
import com.fm.easypay.api.requests.ChargeCreditCardBody
import com.fm.easypay.api.requests.CreditCardInfo
import com.fm.easypay.api.requests.PersonalData
import com.fm.easypay.api.requests.PurchaseItems
import com.fm.easypay.api.responses.ChargeCreditCardResult
import com.fm.easypay.networking.NetworkResource
import com.fm.easypay.repositories.charge_cc.ChargeCreditCard
import com.fm.easypay_sample.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias ChargeCreditCardState = ViewState<ChargeCreditCardResult>

@HiltViewModel
class ChargeCardViewModel @Inject constructor(
    private val chargeCreditCard: ChargeCreditCard,
) : ViewModel() {

    private val _chargeCreditCardResult = MutableSharedFlow<ChargeCreditCardState>()
    val chargeCreditCardResult: SharedFlow<ChargeCreditCardState> = _chargeCreditCardResult

    //region Public methods

    fun chargeCreditCard() {
        viewModelScope.launch {
            _chargeCreditCardResult.emit(ViewState.Loading())
            val request = prepareRequest()
            val response = chargeCreditCard.chargeCreditCard(request)
            when (response.status) {
                NetworkResource.Status.SUCCESS -> {
                    response.data?.let {
                        _chargeCreditCardResult.emit(ViewState.Success(data = it))
                    } ?: _chargeCreditCardResult.emit(ViewState.Error())
                }

                NetworkResource.Status.ERROR -> emitError(response.error?.message)
                NetworkResource.Status.DECLINED -> emitError(response.error?.message)
            }
        }
    }

    //endregion

    //region Private methods

    private suspend fun emitError(message: String?) {
        _chargeCreditCardResult.emit(
            ViewState.Error(message)
        )
    }

    private fun prepareRequest(): ChargeCreditCardBody =
        ChargeCreditCardBody(
            creditCardInfo = prepareCreditCardInfo(),
            accountHolder = preparePersonalData(),
            endCustomer = preparePersonalData(),
            amounts = prepareAmounts(),
            purchaseItems = preparePurchaseItems(),
            merchantId = 1
        )

    private fun prepareCreditCardInfo(): CreditCardInfo = CreditCardInfo(
        accountNumber = "QWOY6TAb5qjPFQdGrxTFThJ7EPbc295nw4JN2L/GLGjqMV1dFJQGBNL9SblCuJId15CjCjrFAVilc1zNM6l5jNnDRR9s2/+co0QLvQLTapTTgveP/5dn6PMfhxwKnSb/a0Qd9+qLb5FeHMgmMbhBMJo3PHlf/JNwDX/PWZ9I2LAEHPPf+5cgteLgoOVNw5U/mf1i5mtovNYI6Nsfsp3JGe2HOQTFCjcBx2OlzRQooYawZVByE1BV+FDvkWirU3v4DO1BxrhDODgK/TMrneg8ne75ig8LlCJhpcuO5oXp9ES6JsdQ/Q9mIqqJBA2Zo86qFLr1yCGh7taQIJ9V6j91VPybJJ9MLv5xJlUv5puxrvSfnECaR0nwLBBHrF/sHq+SmAiGGwC1qQB7dLO5PJWWd7Qy0uhxWarQSVaPKJRW7uNFKXxFdGWNBcINVR/mkNECJ4E+AdbhbbGj3Vx4ExGB2aIFpfp9izXx75T4JGvK/uBv1XABLgqzXInA1WLU50arOLnQH58FBaZiZRATukP60KPLqWSC2NPuHDQw/3eFQ/KvViLR3BSh2WLTvS7qVcZ7f43iNiHt8CMLuaaEWL8GfvU1FU2rB/DsOp9ab+N5DmTs9voRLEfKARCgUggAaC57eDzT8rY/vA7/+2iTGZqv9OuMSlopoGmZF5eiH0ysXUI=",
        expMonth = 10,
        expYear = 2026,
        csv = "999"
    )

    private fun preparePurchaseItems(): PurchaseItems = PurchaseItems(
        serviceDescription = "FROM API TESTER",
        clientRefId = "12456",
        rpguid = "3d3424a6-c5f3-4c28"
    )

    private fun prepareAmounts(): Amounts = Amounts(
        totalAmount = 10.0,
        salesTax = 0.0,
        surcharge = 0.0,
        tip = 0.0,
        cashback = 0.0,
        clinicAmount = 0.0,
        visionAmount = 0.0,
        prescriptionAmount = 0.0,
        dentalAmount = 0.0,
        totalMedicalAmount = 0.0
    )

    private fun preparePersonalData(): PersonalData = PersonalData(
        firstName = "John",
        lastName = "Doe",
        company = "",
        title = "",
        url = "",
        billingAddress = prepareBillingAddress(),
        email = "robert@easypaysolutions.com",
        phone = "8775558472"
    )

    private fun prepareBillingAddress(): BillingAddress = BillingAddress(
        address1 = "123 Fake St.",
        address2 = "",
        city = "PORTLAND",
        state = "ME",
        zip = "04005",
        country = "USA"
    )

    //endregion

}