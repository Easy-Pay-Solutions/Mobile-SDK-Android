package com.fm.easypay_sample.ui.charge_card

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fm.easypay.api.responses.ChargeCreditCardResult
import com.fm.easypay.networking.NetworkResource
import com.fm.easypay.repositories.charge_cc.AmountsParam
import com.fm.easypay.repositories.charge_cc.BillingAddressParam
import com.fm.easypay.repositories.charge_cc.ChargeCreditCard
import com.fm.easypay.repositories.charge_cc.ChargeCreditCardBodyParams
import com.fm.easypay.repositories.charge_cc.CreditCardInfoParam
import com.fm.easypay.repositories.charge_cc.PersonalDataParam
import com.fm.easypay.repositories.charge_cc.PurchaseItemsParam
import com.fm.easypay.utils.secured.SecureData
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

    fun chargeCreditCard(secureData: SecureData<String>) {
        viewModelScope.launch {
            _chargeCreditCardResult.emit(ViewState.Loading())
            val params = prepareParams()
            val response = chargeCreditCard.chargeCreditCard(params, secureData)
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

    private fun prepareParams(): ChargeCreditCardBodyParams =
        ChargeCreditCardBodyParams(
            creditCardInfo = prepareCreditCardInfo(),
            accountHolder = prepareAccountHolder(),
            endCustomer = prepareEndCustomer(),
            amounts = prepareAmounts(),
            purchaseItems = preparePurchaseItems(),
            merchantId = 1
        )

    private fun prepareCreditCardInfo(): CreditCardInfoParam = CreditCardInfoParam(
        expMonth = 10,
        expYear = 2026,
        csv = "999"
    )

    private fun preparePurchaseItems(): PurchaseItemsParam = PurchaseItemsParam(
        serviceDescription = "FROM API TESTER",
        clientRefId = "12456",
        rpguid = "3d3424a6-c5f3-4c28"
    )

    private fun prepareAmounts(): AmountsParam = AmountsParam(
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

    private fun prepareAccountHolder(): PersonalDataParam = PersonalDataParam(
        firstName = "John",
        lastName = "Doe",
        company = "",
        title = "",
        url = "",
        billingAddress = prepareBillingAddress(),
        email = "robert@easypaysolutions.com",
        phone = "8775558472"
    )

    private fun prepareEndCustomer(): PersonalDataParam = PersonalDataParam(
        firstName = "John",
        lastName = "Doe",
        company = "",
        title = "",
        url = "",
        billingAddress = prepareBillingAddress(),
        email = "robert@easypaysolutions.com",
        phone = "8775558472"
    )

    private fun prepareBillingAddress(): BillingAddressParam = BillingAddressParam(
        address1 = "123 Fake St.",
        address2 = "",
        city = "PORTLAND",
        state = "ME",
        zip = "04005",
        country = "USA"
    )

    //endregion

}