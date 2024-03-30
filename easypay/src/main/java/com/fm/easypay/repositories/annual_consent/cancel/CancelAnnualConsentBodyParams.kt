package com.fm.easypay.repositories.annual_consent.cancel

import com.fm.easypay.api.requests.annual_consent.CancelAnnualConsentBodyDto
import com.fm.easypay.repositories.BaseBodyParams
import com.fm.easypay.repositories.MappedField

data class CancelAnnualConsentBodyParams(val consentId: Int) : BaseBodyParams() {

    internal fun toDto(): CancelAnnualConsentBodyDto = CancelAnnualConsentBodyDto(consentId)

    override fun toMappedFields(): List<MappedField> {
        return this.javaClass.declaredFields.toList().map { MappedField(it, it.get(this)) }
    }
}