package com.fm.easypay.repositories.annual_consent.list

import com.fm.easypay.repositories.BaseBodyParams
import com.fm.easypay.repositories.MappedField

data class ListAnnualConsentsBodyParams(
    val merchantId: Int? = null,
    val customerReferenceId: String? = null,
): BaseBodyParams() {
    override fun toMappedFields(): List<MappedField> {
        return this.javaClass.declaredFields.toList().map { MappedField(it, it.get(this)) }
    }
}