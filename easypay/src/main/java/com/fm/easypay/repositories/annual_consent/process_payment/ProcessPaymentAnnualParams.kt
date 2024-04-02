package com.fm.easypay.repositories.annual_consent.process_payment

import com.fm.easypay.api.requests.annual_consent.ProcessPaymentAnnualBodyDto
import com.fm.easypay.repositories.BaseBodyParams
import com.fm.easypay.repositories.MappedField

data class ProcessPaymentAnnualParams(
    val consentId: Int,
    val processAmount: Double,
) : BaseBodyParams() {
    override fun toMappedFields(): List<MappedField> {
        return this.javaClass.declaredFields.toList().map { MappedField(it, it.get(this)) }
    }

    internal fun toDto(): ProcessPaymentAnnualBodyDto = ProcessPaymentAnnualBodyDto(
        consentId = consentId,
        processAmount = processAmount.toBigDecimal().toPlainString(),
    )
}