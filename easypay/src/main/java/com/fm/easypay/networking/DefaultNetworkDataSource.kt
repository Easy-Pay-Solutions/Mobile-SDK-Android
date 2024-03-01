package com.fm.easypay.networking

import com.fm.easypay.api.EasyPayApiError
import com.fm.easypay.api.responses.base.ApiResponse
import com.fm.easypay.api.responses.base.ApiResult
import com.fm.easypay.api.responses.base.TransactionResult
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import retrofit2.Response

internal class DefaultNetworkDataSource : NetworkDataSource {
    override suspend fun <T : ApiResult, X : ApiResponse<T>> getResult(
        call: suspend () -> Response<X>,
    ): NetworkResource<T> {
        try {
            val response: Response<X> = call()
            if (!response.isSuccessful) {
                return error(response.code(), response.message(), response.errorBody())
            }

            val body = response.body() ?: return error(0, "empty_response", null)
            val result = body.result

            if (!result.functionOk) {
                return error(result.errorCode, result.errorMessage, null)
            }

            return NetworkResource.success(result)
        } catch (e: Exception) {
            return error(0, e.message ?: e.toString(), null)
        }
    }

    override suspend fun <T : TransactionResult, X : ApiResponse<T>> getTransactionResult(
        call: suspend () -> Response<X>,
    ): NetworkResource<T> {
        val resource = getResult(call)
        if (resource.status == NetworkResource.Status.ERROR) {
            return resource
        }

        if (resource.data?.txApproved == false) {
            return NetworkResource.declined(resource.data)
        }

        return resource
    }

    private fun <T> error(
        code: Int,
        message: String,
        errorBody: ResponseBody?,
    ): NetworkResource<T> {
        var msg = message
        errorBody?.let {
            msg = it.charStream().readText()
            try {
                val type = object : TypeToken<ResponseError?>() {}.type
                val errorResponse: ResponseError? = Gson().fromJson(msg, type)
                errorResponse?.error?.let { e -> msg = e }
            } catch (_: Exception) {
                // does nothing
            }
        }

        val e = EasyPayApiError(code, msg)
        return NetworkResource.error(e)
    }

    private data class ResponseError(val error: String?)
}