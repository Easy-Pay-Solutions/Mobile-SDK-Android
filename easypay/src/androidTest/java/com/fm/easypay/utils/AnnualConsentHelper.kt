package com.fm.easypay.utils

import com.fm.easypay.repositories.annual_consent.create.ConsentCreatorParam
import com.fm.easypay.repositories.annual_consent.create.CreateAnnualConsentBodyParams
import com.fm.easypay.repositories.charge_cc.BillingAddressParam
import com.fm.easypay.repositories.charge_cc.CreditCardInfoParam
import com.fm.easypay.repositories.charge_cc.PersonalDataParam
import com.fm.easypay.utils.secured.SecureData
import java.util.Date

internal object AnnualConsentHelper {
    fun prepareParams(
        secureData: SecureData<String>,
        zip: String = "04005",
        limitPerCharge: Double = 10.0,
        csv: String = "999",
    ): CreateAnnualConsentBodyParams =
        CreateAnnualConsentBodyParams(
            encryptedCardNumber = secureData,
            creditCardInfo = prepareCreditCardInfo(csv),
            accountHolder = prepareAccountHolder(zip),
            endCustomer = prepareEndCustomer(),
            consentCreator = prepareConsentCreator(limitPerCharge),
        )

    private fun prepareConsentCreator(limitPerCharge: Double): ConsentCreatorParam =
        ConsentCreatorParam(
            merchantId = 1,
            serviceDescription = "Service Description",
            customerReferenceId = "Client Ref Id",
            rpguid = "RPGUID",
            startDate = Date(),
            numDays = 365,
            limitPerCharge = limitPerCharge,
            limitLifeTime = 100.0
        )

    private fun prepareCreditCardInfo(csv: String): CreditCardInfoParam = CreditCardInfoParam(
        expMonth = 10,
        expYear = 2026,
        csv = csv
    )

    private fun prepareAccountHolder(zip: String): PersonalDataParam =
        PersonalDataParam(
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

    private fun prepareBillingAddress(zip: String = "04005"): BillingAddressParam =
        BillingAddressParam(
            address1 = "123 Fake St.",
            address2 = "",
            city = "PORTLAND",
            state = "ME",
            zip = zip,
            country = "USA"
        )

}