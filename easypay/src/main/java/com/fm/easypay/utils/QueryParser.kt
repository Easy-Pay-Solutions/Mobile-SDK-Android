package com.fm.easypay.utils

import com.fm.easypay.api.requests.annual_consent.ConsentAnnualQuery
import com.fm.easypay.repositories.annual_consent.list.ListAnnualConsentsBodyParams

internal object QueryParser {

    fun parseToQuery(params: ListAnnualConsentsBodyParams): ConsentAnnualQuery {
        // G=1 - Consent type: annual
        // H=1 - Enabled: true
        var query = "(${ConsentAnnualQuery.Variable.CONSENT_TYPE.value}=1)" +
                "&&(${ConsentAnnualQuery.Variable.ENABLED.value}=1)"
        params.apply {
            merchantId?.let {
                query += "&&(${ConsentAnnualQuery.Variable.MERCHANT_ID.value}=$it)"
            }
            customerReferenceId?.let {
                query += "&&(${ConsentAnnualQuery.Variable.CUSTOMER_REF_ID.value}='$it')"
            }
        }
        return ConsentAnnualQuery(query)
    }
}