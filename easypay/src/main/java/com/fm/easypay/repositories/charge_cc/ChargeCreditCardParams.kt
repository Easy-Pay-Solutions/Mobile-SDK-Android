package com.fm.easypay.repositories.charge_cc

import com.fm.easypay.api.requests.AmountsDto
import com.fm.easypay.api.requests.BillingAddressDto
import com.fm.easypay.api.requests.ChargeCreditCardBodyDto
import com.fm.easypay.api.requests.CreditCardInfoDto
import com.fm.easypay.api.requests.PersonalDataDto
import com.fm.easypay.api.requests.PurchaseItemsDto

data class ChargeCreditCardBodyParams(
    val creditCardInfo: CreditCardInfoParam,
    val accountHolder: PersonalDataParam,
    val endCustomer: PersonalDataParam,
    val amounts: AmountsParam,
    val purchaseItems: PurchaseItemsParam,
    val merchantId: Int,
) {
    internal fun toDto(encryptedAccountNumber: String): ChargeCreditCardBodyDto = ChargeCreditCardBodyDto(
        creditCardInfo = creditCardInfo.toDto(encryptedAccountNumber),
        accountHolder = accountHolder.toDto(),
        endCustomer = endCustomer.toDto(),
        amounts = amounts.toDto(),
        purchaseItems = purchaseItems.toDto(),
        merchantId = merchantId,
    )
}

data class CreditCardInfoParam(
    val expMonth: Int,
    val expYear: Int,
    val csv: String,
) {
    internal fun toDto(encryptedAccountNumber: String): CreditCardInfoDto = CreditCardInfoDto(
        accountNumber = encryptedAccountNumber,
        expMonth = expMonth,
        expYear = expYear,
        csv = csv,
    )
}

data class PersonalDataParam(
    val firstName: String,
    val lastName: String,
    val company: String? = null,
    val title: String? = null,
    val url: String? = null,
    val billingAddress: BillingAddressParam,
    val email: String? = null,
    val phone: String? = null,
) {
    internal fun toDto(): PersonalDataDto = PersonalDataDto(
        firstName = firstName,
        lastName = lastName,
        company = company,
        title = title,
        url = url,
        billingAddress = billingAddress.toDto(),
        email = email,
        phone = phone,
    )
}

data class BillingAddressParam(
    val address1: String,
    val address2: String? = null,
    val city: String? = null,
    val state: String? = null,
    val zip: String,
    val country: String? = null,
) {
    internal fun toDto(): BillingAddressDto = BillingAddressDto(
        address1 = address1,
        address2 = address2,
        city = city,
        state = state,
        zip = zip,
        country = country,
    )
}

data class AmountsParam(
    val totalAmount: Double,
    val salesTax: Double? = null,
    val surcharge: Double? = null,
    val tip: Double? = null,
    val cashback: Double? = null,
    val clinicAmount: Double? = null,
    val visionAmount: Double? = null,
    val prescriptionAmount: Double? = null,
    val dentalAmount: Double? = null,
    val totalMedicalAmount: Double? = null,
) {
    internal fun toDto(): AmountsDto = AmountsDto(
        totalAmount = totalAmount,
        salesTax = salesTax,
        surcharge = surcharge,
        tip = tip,
        cashback = cashback,
        clinicAmount = clinicAmount,
        visionAmount = visionAmount,
        prescriptionAmount = prescriptionAmount,
        dentalAmount = dentalAmount,
        totalMedicalAmount = totalMedicalAmount,
    )
}

data class PurchaseItemsParam(
    val serviceDescription: String? = null,
    val clientRefId: String? = null,
    val rpguid: String? = null,
) {
    internal fun toDto(): PurchaseItemsDto = PurchaseItemsDto(
        serviceDescription = serviceDescription,
        clientRefId = clientRefId,
        rpguid = rpguid,
    )
}