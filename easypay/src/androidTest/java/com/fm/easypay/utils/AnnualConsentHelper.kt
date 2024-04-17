package com.fm.easypay.utils

import com.fm.easypay.repositories.annual_consent.create.ConsentCreatorParam
import com.fm.easypay.repositories.annual_consent.create.CreateAnnualConsentBodyParams
import com.fm.easypay.utils.ChargeCreditCardHelper.prepareAccountHolder
import com.fm.easypay.utils.ChargeCreditCardHelper.prepareCreditCardInfo
import com.fm.easypay.utils.ChargeCreditCardHelper.prepareEndCustomer
import com.fm.easypay.utils.secured.SecureData
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar

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
            limitPerCharge = limitPerCharge,
            limitLifeTime = 100.0
        )
}