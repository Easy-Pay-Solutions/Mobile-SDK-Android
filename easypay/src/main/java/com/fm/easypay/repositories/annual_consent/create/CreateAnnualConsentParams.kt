package com.fm.easypay.repositories.annual_consent.create

import com.fm.easypay.api.requests.annual_consent.ConsentCreatorDto
import com.fm.easypay.api.requests.annual_consent.CreateAnnualConsentBodyDto
import com.fm.easypay.repositories.BaseBodyParams
import com.fm.easypay.repositories.MappedField
import com.fm.easypay.repositories.charge_cc.CreditCardInfoParam
import com.fm.easypay.repositories.charge_cc.PersonalDataParam
import com.fm.easypay.utils.DateUtils
import com.fm.easypay.utils.secured.SecureData
import com.fm.easypay.utils.validation.RegexPattern
import com.fm.easypay.utils.validation.ValidateDoubleGreaterThanZero
import com.fm.easypay.utils.validation.ValidateLength
import com.fm.easypay.utils.validation.ValidateRegex
import java.util.Date

data class CreateAnnualConsentBodyParams(
    val encryptedCardNumber: SecureData<String>,
    val creditCardInfo: CreditCardInfoParam,
    val accountHolder: PersonalDataParam,
    val endCustomer: PersonalDataParam?,
    val consentCreator: ConsentCreatorParam,
) : BaseBodyParams() {
    internal fun toDto(): CreateAnnualConsentBodyDto = CreateAnnualConsentBodyDto(
        creditCardInfo = creditCardInfo.toDto(encryptedCardNumber.data),
        accountHolder = accountHolder.toDto(),
        endCustomer = endCustomer?.toDto() ?: Any(),
        consentCreator = consentCreator.toDto(),
    )

    override fun toMappedFields(): List<MappedField> {
        val list = this.javaClass.declaredFields.toList().map { MappedField(it, it.get(this)) } +
                creditCardInfo.toMappedFields() +
                accountHolder.toMappedFields() +
                consentCreator.toMappedFields()
        endCustomer?.let { list.plus(it.toMappedFields()) }
        return list
    }
}

data class ConsentCreatorParam(
    val merchantId: Int,

    @ValidateLength(maxLength = 200)
    @ValidateRegex(regex = RegexPattern.SERVICE_DESCRIPTION)
    val serviceDescription: String? = null,

    @ValidateLength(maxLength = 75)
    @ValidateRegex(regex = RegexPattern.CLIENT_REF_ID_OR_RPGUID)
    val customerReferenceId: String? = null,

    @ValidateLength(maxLength = 75)
    @ValidateRegex(regex = RegexPattern.CLIENT_REF_ID_OR_RPGUID)
    val rpguid: String? = null,
    val startDate: Date,
    val numDays: Int,

    @ValidateDoubleGreaterThanZero
    val limitPerCharge: Double,

    @ValidateDoubleGreaterThanZero
    val limitLifeTime: Double,
) : BaseBodyParams() {
    internal fun toDto(): ConsentCreatorDto = ConsentCreatorDto(
        merchantId = merchantId,
        serviceDescription = serviceDescription,
        customerReferenceId = customerReferenceId,
        rpguid = rpguid,
        startDate = DateUtils.parseDateForApi(startDate),
        numDays = numDays,
        limitPerCharge = limitPerCharge.toBigDecimal().toPlainString(),
        limitLifeTime = limitLifeTime.toBigDecimal().toPlainString(),
    )

    override fun toMappedFields(): List<MappedField> {
        return this.javaClass.declaredFields.toList().map { MappedField(it, it.get(this)) }
    }
}