package com.fm.easypay_sample.ui.consent_annual.add

import com.fm.easypay.repositories.annual_consent.create.ConsentCreatorParam
import com.fm.easypay.repositories.annual_consent.create.CreateAnnualConsentBodyParams
import com.fm.easypay.repositories.charge_cc.BillingAddressParam
import com.fm.easypay.repositories.charge_cc.CreditCardInfoParam
import com.fm.easypay.repositories.charge_cc.PersonalDataParam
import com.fm.easypay.utils.secured.SecureData
import com.fm.easypay_sample.utils.DateUtils
import com.fm.easypay_sample.utils.toNullIfBlank
import java.util.Date

data class AddAnnualConsentViewData(
    var merchantId: String? = null,
    var expMonth: String? = null,
    var expYear: String? = null,
    var cvv: String? = null,
    var holderFirstName: String? = null,
    var holderLastName: String? = null,
    var holderCompany: String? = null,
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
    var customerPhone: String? = null,
    var customerEmail: String? = null,
    var customerAddress1: String? = null,
    var customerAddress2: String? = null,
    var customerCity: String? = null,
    var customerState: String? = null,
    var customerZip: String? = null,
    var customerCountry: String? = null,
    var serviceDescription: String? = null,
    var customerReferenceId: String? = null,
    var rpguid: String? = null,
    var numDays: String? = null,
    var limitPerCharge: String? = null,
    var limitLifeTime: String? = null,
    var startDate: String? = null,
) {

    //region Public

    fun toCreateAnnualConsentParams(secureData: SecureData<String>): CreateAnnualConsentBodyParams {
        return CreateAnnualConsentBodyParams(
            encryptedCardNumber = secureData,
            creditCardInfo = prepareCreditCardInfo(),
            accountHolder = prepareAccountHolder(),
            endCustomer = prepareEndCustomer(),
            consentCreator = prepareConsentCreator(),
        )
    }

    //endregion

    //region Private


    private fun prepareConsentCreator(): ConsentCreatorParam {
        return ConsentCreatorParam(
            merchantId = merchantId?.toIntOrNull() ?: 0,
            serviceDescription = serviceDescription?.toNullIfBlank(),
            customerReferenceId = customerReferenceId?.toNullIfBlank(),
            rpguid = rpguid?.toNullIfBlank(),
            startDate = startDate?.let { DateUtils.parseDate(it) } ?: Date(),
            numDays = numDays?.toIntOrNull() ?: 0,
            limitPerCharge = limitPerCharge?.toDoubleOrNull() ?: 0.0,
            limitLifeTime = limitLifeTime?.toDoubleOrNull() ?: 0.0
        )
    }

    private fun prepareEndCustomer(): PersonalDataParam? {
        if (customerFirstName.isNullOrEmpty() &&
            customerLastName.isNullOrEmpty() &&
            customerCompany.isNullOrEmpty() &&
            customerEmail.isNullOrEmpty() &&
            customerPhone.isNullOrEmpty() &&
            customerAddress1.isNullOrEmpty() &&
            customerAddress2.isNullOrEmpty() &&
            customerZip.isNullOrEmpty() &&
            customerCountry.isNullOrEmpty() &&
            customerCity.isNullOrEmpty() &&
            customerState.isNullOrEmpty()
        ) {
            return null
        }
        return PersonalDataParam(
            firstName = customerFirstName ?: "",
            lastName = customerLastName ?: "",
            company = customerCompany?.toNullIfBlank(),
            billingAddress = prepareBillingAddress(
                customerAddress1,
                customerAddress2,
                customerCity,
                customerState,
                customerZip,
                customerCountry
            ),
            email = customerEmail?.toNullIfBlank(),
            phone = customerPhone?.toNullIfBlank()
        )
    }

    private fun prepareAccountHolder(): PersonalDataParam {
        return PersonalDataParam(
            firstName = holderFirstName ?: "",
            lastName = holderLastName ?: "",
            company = holderCompany?.toNullIfBlank(),
            billingAddress = prepareBillingAddress(
                holderAddress1,
                holderAddress2,
                holderCity,
                holderState,
                holderZip,
                holderCountry
            ),
            email = holderEmail?.toNullIfBlank(),
            phone = holderPhone?.toNullIfBlank()
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
            address2 = address2?.toNullIfBlank(),
            city = city?.toNullIfBlank(),
            state = state?.toNullIfBlank(),
            zip = zip ?: "",
            country = country?.toNullIfBlank()
        )
    }

    private fun prepareCreditCardInfo(): CreditCardInfoParam {
        return CreditCardInfoParam(
            expMonth = expMonth?.toIntOrNull() ?: 0,
            expYear = expYear?.toIntOrNull() ?: 0,
            csv = cvv ?: ""
        )
    }

    //endregion

}