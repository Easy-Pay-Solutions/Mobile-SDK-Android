package com.fm.easypay_sample.ui.charge_card

import com.fm.easypay.repositories.charge_cc.AmountsParam
import com.fm.easypay.repositories.charge_cc.BillingAddressParam
import com.fm.easypay.repositories.charge_cc.ChargeCreditCardBodyParams
import com.fm.easypay.repositories.charge_cc.CreditCardInfoParam
import com.fm.easypay.repositories.charge_cc.PersonalDataParam
import com.fm.easypay.repositories.charge_cc.PurchaseItemsParam

data class ChargeCardViewData(
    var merchantId: String? = null,
    var expMonth: String? = null,
    var expYear: String? = null,
    var cvv: String? = null,
    var holderFirstName: String? = null,
    var holderLastName: String? = null,
    var holderCompany: String? = null,
    var holderTitle: String? = null,
    var holderUrl: String? = null,
    var holderPhone: String? = null,
    var holderEmail: String? = null,
    var holderAddress1: String? = null,
    var holderAddress2: String? = null,
    var holderCity: String? = null,
    var holderState: String? = null,
    var holderZip: String? = null,
    var holderCountry: String? = null,
    var customerFirstName: String? = null,
    var customerLastName: String? = null,
    var customerCompany: String? = null,
    var customerTitle: String? = null,
    var customerUrl: String? = null,
    var customerPhone: String? = null,
    var customerEmail: String? = null,
    var customerAddress1: String? = null,
    var customerAddress2: String? = null,
    var customerCity: String? = null,
    var customerState: String? = null,
    var customerZip: String? = null,
    var customerCountry: String? = null,
    var totalAmount: String? = null,
    var salesAmount: String? = null,
    var surcharge: String? = null,
    var tip: String? = null,
    var cashBack: String? = null,
    var clinicAmount: String? = null,
    var visionAmount: String? = null,
    var prescriptionAmount: String? = null,
    var dentalAmount: String? = null,
    var totalMedicalAmount: String? = null,
    var serviceDescription: String? = null,
    var clientRefId: String? = null,
    var rpguid: String? = null,
) {

    //region Public

    fun toChargeCreditCardBodyParams(): ChargeCreditCardBodyParams {
        return ChargeCreditCardBodyParams(
            creditCardInfo = prepareCreditCardInfo(),
            accountHolder = prepareAccountHolder(),
            endCustomer = prepareEndCustomer(),
            amounts = prepareAmounts(),
            purchaseItems = preparePurchaseItems(),
            merchantId = merchantId?.toInt() ?: 0
        )
    }

    //endregion

    //region Private

    private fun preparePurchaseItems(): PurchaseItemsParam {
        return PurchaseItemsParam(
            serviceDescription = serviceDescription,
            clientRefId = clientRefId,
            rpguid = rpguid
        )
    }

    private fun prepareAmounts(): AmountsParam {
        return AmountsParam(
            totalAmount = totalAmount?.toDouble() ?: 0.0,
            salesTax = salesAmount?.toDouble(),
            surcharge = surcharge?.toDouble(),
            tip = tip?.toDouble(),
            cashback = cashBack?.toDouble(),
            clinicAmount = clinicAmount?.toDouble(),
            visionAmount = visionAmount?.toDouble(),
            prescriptionAmount = prescriptionAmount?.toDouble(),
            dentalAmount = dentalAmount?.toDouble(),
            totalMedicalAmount = totalMedicalAmount?.toDouble()
        )
    }

    private fun prepareEndCustomer(): PersonalDataParam {
        return PersonalDataParam(
            firstName = customerFirstName ?: "",
            lastName = customerLastName ?: "",
            company = customerCompany,
            title = customerTitle,
            url = customerUrl,
            billingAddress = prepareBillingAddress(
                customerAddress1,
                customerAddress2,
                customerCity,
                customerState,
                customerZip,
                customerCountry
            ),
            email = customerEmail,
            phone = customerPhone
        )
    }

    private fun prepareAccountHolder(): PersonalDataParam {
        return PersonalDataParam(
            firstName = holderFirstName ?: "",
            lastName = holderLastName ?: "",
            company = holderCompany,
            title = holderTitle,
            url = holderUrl,
            billingAddress = prepareBillingAddress(
                holderAddress1,
                holderAddress2,
                holderCity,
                holderState,
                holderZip,
                holderCountry
            ),
            email = holderEmail,
            phone = holderPhone
        )
    }

    private fun prepareBillingAddress(
        address1: String?,
        address2: String?,
        city: String?,
        state: String?,
        zip: String?,
        country: String?,
    ): BillingAddressParam {
        return BillingAddressParam(
            address1 = address1 ?: "",
            address2 = address2,
            city = city,
            state = state,
            zip = zip ?: "",
            country = country
        )
    }

    private fun prepareCreditCardInfo(): CreditCardInfoParam {
        return CreditCardInfoParam(
            expMonth = expMonth?.toInt() ?: 0,
            expYear = expYear?.toInt() ?: 0,
            csv = cvv ?: ""
        )
    }

    //endregion

}