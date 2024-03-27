package com.fm.easypay.repositories.annual_consent.list

import com.fm.easypay.api.responses.annual_consent.ListAnnualConsentsResult
import com.fm.easypay.networking.NetworkResource

internal interface ListAnnualConsentsRepository {
    suspend fun listAnnualConsents(params: ListAnnualConsentsBodyParams): NetworkResource<ListAnnualConsentsResult>
}