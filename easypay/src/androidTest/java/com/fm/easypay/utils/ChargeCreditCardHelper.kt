package com.fm.easypay.utils

import com.fm.easypay.repositories.charge_cc.AmountsParam
import com.fm.easypay.repositories.charge_cc.BillingAddressParam
import com.fm.easypay.repositories.charge_cc.ChargeCreditCardBodyParams
import com.fm.easypay.repositories.charge_cc.CreditCardInfoParam
import com.fm.easypay.repositories.charge_cc.PersonalDataParam
import com.fm.easypay.repositories.charge_cc.PurchaseItemsParam
import com.fm.easypay.utils.secured.SecureData

internal object ChargeCreditCardHelper {
    fun prepareParams(
        secureData: SecureData<String>,
        zip: String = "04005",
        totalAmount: Double = 10.0,
        csv: String = "999",
    ): ChargeCreditCardBodyParams = ChargeCreditCardBodyParams(
        encryptedCardNumber = secureData,
        creditCardInfo = prepareCreditCardInfo(csv),
        accountHolder = prepareAccountHolder(zip),
        endCustomer = prepareEndCustomer(),
        amounts = prepareAmounts(totalAmount),
        purchaseItems = preparePurchaseItems(),
        merchantId = 1
    )

    private fun prepareCreditCardInfo(csv: String): CreditCardInfoParam = CreditCardInfoParam(
        expMonth = 10,
        expYear = 2026,
        csv = csv
    )

    private fun preparePurchaseItems(): PurchaseItemsParam = PurchaseItemsParam(
        serviceDescription = "FROM API TESTER",
        clientRefId = "12456",
        rpguid = "3d3424a6-c5f3-4c28"
    )

    private fun prepareAmounts(totalAmount: Double): AmountsParam = AmountsParam(
        totalAmount = totalAmount,
        salesTax = 0.0,
        surcharge = 0.0,
    )

    private fun prepareAccountHolder(zip: String): PersonalDataParam = PersonalDataParam(
        firstName = "John",
        lastName = "Doe",
        company = "",
        billingAddress = prepareBillingAddress(zip),
        email = "robert@easypaysolutions.com",
        phone = "8775558472"
    )

    private fun prepareEndCustomer(): PersonalDataParam = PersonalDataParam(
        firstName = "John",
        lastName = "Doe",
        company = "",
        billingAddress = prepareBillingAddress(),
        email = "robert@easypaysolutions.com",
        phone = "8775558472"
    )

    private fun prepareBillingAddress(
        zip: String = "04005",
    ): BillingAddressParam = BillingAddressParam(
        address1 = "123 Fake St.",
        address2 = "",
        city = "PORTLAND",
        state = "ME",
        zip = zip,
        country = "USA"
    )
}