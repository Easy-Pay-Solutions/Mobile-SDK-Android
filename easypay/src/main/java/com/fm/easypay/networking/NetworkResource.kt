package com.fm.easypay.networking

import com.fm.easypay.api.EasyPayApiError

data class NetworkResource<out T>(val status: Status, val data: T?, val error: EasyPayApiError?) {

    enum class Status {
        SUCCESS,
        ERROR,
        DECLINED
    }

    companion object {
        fun <T> success(data: T): NetworkResource<T> {
            return NetworkResource(Status.SUCCESS, data, null)
        }

        fun <T> error(error: EasyPayApiError, data: T? = null): NetworkResource<T> {
            return NetworkResource(Status.ERROR, data, error)
        }

        fun <T> declined(data: T? = null): NetworkResource<T> {
            return NetworkResource(Status.DECLINED, data, null)
        }
    }
}