package com.fm.easypay.networking

import com.fm.easypay.api.responses.base.ApiResponse
import com.fm.easypay.api.responses.base.ApiResult
import com.fm.easypay.api.responses.base.TransactionResult
import retrofit2.Response

internal interface NetworkDataSource {
    suspend fun <T : ApiResult, X : ApiResponse<T>> getResult(call: suspend () -> Response<X>): NetworkResource<T>
    suspend fun <T : TransactionResult, X : ApiResponse<T>> getTransactionResult(call: suspend () -> Response<X>): NetworkResource<T>
}