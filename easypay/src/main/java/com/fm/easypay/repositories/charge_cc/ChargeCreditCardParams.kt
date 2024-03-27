package com.fm.easypay.repositories.charge_cc

import com.fm.easypay.api.requests.charge_cc.AmountsDto
import com.fm.easypay.api.requests.charge_cc.BillingAddressDto
import com.fm.easypay.api.requests.charge_cc.ChargeCreditCardBodyDto
import com.fm.easypay.api.requests.charge_cc.CreditCardInfoDto
import com.fm.easypay.api.requests.charge_cc.PersonalDataDto
import com.fm.easypay.api.requests.charge_cc.PurchaseItemsDto
import com.fm.easypay.repositories.BaseBodyParams
import com.fm.easypay.repositories.MappedField
import com.fm.easypay.utils.secured.SecureData
import com.fm.easypay.utils.validation.RegexPattern.CITY
import com.fm.easypay.utils.validation.RegexPattern.CLIENT_REF_ID_OR_RPGUID
import com.fm.easypay.utils.validation.RegexPattern.COMPANY_OR_ADDRESS
import com.fm.easypay.utils.validation.RegexPattern.EMAIL
import com.fm.easypay.utils.validation.RegexPattern.FIRST_OR_LAST_NAME
import com.fm.easypay.utils.validation.RegexPattern.ONLY_NUMBERS
import com.fm.easypay.utils.validation.RegexPattern.SERVICE_DESCRIPTION
import com.fm.easypay.utils.validation.RegexPattern.STATE
import com.fm.easypay.utils.validation.RegexPattern.ZIP_CODE
import com.fm.easypay.utils.validation.ValidateDoubleGreaterThanZero
import com.fm.easypay.utils.validation.ValidateLength
import com.fm.easypay.utils.validation.ValidateRegex

data class ChargeCreditCardBodyParams(
    val encryptedCardNumber: SecureData<String>,
    val creditCardInfo: CreditCardInfoParam,
    val accountHolder: PersonalDataParam,
    val endCustomer: PersonalDataParam?,
    val amounts: AmountsParam,
    val purchaseItems: PurchaseItemsParam,
    val merchantId: Int,
) : BaseBodyParams() {
    internal fun toDto(): ChargeCreditCardBodyDto =
        ChargeCreditCardBodyDto(
            creditCardInfo = creditCardInfo.toDto(encryptedCardNumber.data),
            accountHolder = accountHolder.toDto(),
            endCustomer = endCustomer?.toDto() ?: Any(),
            amounts = amounts.toDto(),
            purchaseItems = purchaseItems.toDto(),
            merchantId = merchantId,
        )

    override fun toMappedFields(): List<MappedField> {
        val list = this.javaClass.declaredFields.toList().map { MappedField(it, it.get(this)) } +
                creditCardInfo.toMappedFields() +
                accountHolder.toMappedFields() +
                amounts.toMappedFields() +
                purchaseItems.toMappedFields()
        endCustomer?.let { list.plus(it.toMappedFields()) }
        return list
    }
}

data class CreditCardInfoParam(
    @ValidateLength(maxLength = 2)
    val expMonth: Int,

    @ValidateLength(maxLength = 4)
    val expYear: Int,

    @ValidateLength(maxLength = 4)
    val csv: String,
) : BaseBodyParams() {
    internal fun toDto(encryptedAccountNumber: String): CreditCardInfoDto = CreditCardInfoDto(
        accountNumber = encryptedAccountNumber,
        expMonth = expMonth,
        expYear = expYear,
        csv = csv,
    )

    override fun toMappedFields(): List<MappedField> {
        return this.javaClass.declaredFields.toList().map { MappedField(it, it.get(this)) }
    }
}

data class PersonalDataParam(
    @ValidateLength(maxLength = 75)
    @ValidateRegex(regex = FIRST_OR_LAST_NAME)
    val firstName: String,

    @ValidateLength(maxLength = 75)
    @ValidateRegex(regex = FIRST_OR_LAST_NAME)
    val lastName: String,

    @ValidateLength(maxLength = 100)
    @ValidateRegex(regex = COMPANY_OR_ADDRESS)
    val company: String? = null,

    val billingAddress: BillingAddressParam,

    @ValidateLength(maxLength = 150)
    @ValidateRegex(regex = EMAIL)
    val email: String? = null,

    @ValidateLength(maxLength = 16)
    @ValidateRegex(regex = ONLY_NUMBERS)
    val phone: String? = null,
) : BaseBodyParams() {
    internal fun toDto(): PersonalDataDto = PersonalDataDto(
        firstName = firstName,
        lastName = lastName,
        company = company,
        title = "",
        url = "",
        billingAddress = billingAddress.toDto(),
        email = email,
        phone = phone,
    )

    override fun toMappedFields(): List<MappedField> {
        return this.javaClass.declaredFields.toList().map { MappedField(it, it.get(this)) } +
                billingAddress.toMappedFields()
    }
}

data class BillingAddressParam(
    @ValidateLength(maxLength = 100)
    @ValidateRegex(regex = COMPANY_OR_ADDRESS)
    val address1: String,

    @ValidateLength(maxLength = 100)
    @ValidateRegex(regex = COMPANY_OR_ADDRESS)
    val address2: String? = null,

    @ValidateLength(maxLength = 75)
    @ValidateRegex(regex = CITY)
    val city: String? = null,

    @ValidateLength(maxLength = 75)
    @ValidateRegex(regex = STATE)
    val state: String? = null,

    @ValidateLength(maxLength = 20)
    @ValidateRegex(regex = ZIP_CODE)
    val zip: String,

    @ValidateLength(maxLength = 75)
    val country: String? = null,
) : BaseBodyParams() {
    internal fun toDto(): BillingAddressDto = BillingAddressDto(
        address1 = address1,
        address2 = address2,
        city = city,
        state = state,
        zip = zip,
        country = country,
    )

    override fun toMappedFields(): List<MappedField> {
        return this.javaClass.declaredFields.toList().map { MappedField(it, it.get(this)) }
    }
}

data class AmountsParam(
    @ValidateDoubleGreaterThanZero
    val totalAmount: Double,
    val salesTax: Double? = null,
    val surcharge: Double? = null,
) : BaseBodyParams() {
    internal fun toDto(): AmountsDto = AmountsDto(
        totalAmount = totalAmount,
        salesTax = salesTax,
        surcharge = surcharge,
        tip = 0.0,
        cashback = 0.0,
        clinicAmount = 0.0,
        visionAmount = 0.0,
        prescriptionAmount = 0.0,
        dentalAmount = 0.0,
        totalMedicalAmount = 0.0,
    )

    override fun toMappedFields(): List<MappedField> {
        return this.javaClass.declaredFields.toList().map { MappedField(it, it.get(this)) }
    }
}

data class PurchaseItemsParam(
    @ValidateLength(maxLength = 200)
    @ValidateRegex(regex = SERVICE_DESCRIPTION)
    val serviceDescription: String? = null,

    @ValidateLength(maxLength = 75)
    @ValidateRegex(regex = CLIENT_REF_ID_OR_RPGUID)
    val clientRefId: String? = null,

    @ValidateLength(maxLength = 75)
    @ValidateRegex(regex = CLIENT_REF_ID_OR_RPGUID)
    val rpguid: String? = null,
) : BaseBodyParams() {
    internal fun toDto(): PurchaseItemsDto = PurchaseItemsDto(
        serviceDescription = serviceDescription,
        clientRefId = clientRefId,
        rpguid = rpguid,
    )

    override fun toMappedFields(): List<MappedField> {
        return this.javaClass.declaredFields.toList().map { MappedField(it, it.get(this)) }
    }
}