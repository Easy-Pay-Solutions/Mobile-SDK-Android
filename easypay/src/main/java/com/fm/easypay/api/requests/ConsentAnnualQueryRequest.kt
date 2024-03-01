package com.fm.easypay.api.requests

import com.fm.easypay.api.requests.base.EasyPayQuery
import com.fm.easypay.api.requests.base.ApiRequest

data class ConsentAnnualQueryRequest(
    override val userDataPresent: Boolean,
    override val body: EasyPayQuery,
) : ApiRequest<EasyPayQuery>(userDataPresent, body)