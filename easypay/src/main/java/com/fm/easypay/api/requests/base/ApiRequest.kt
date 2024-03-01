package com.fm.easypay.api.requests.base

abstract class ApiRequest<T>(
    open val userDataPresent: Boolean,
    open val body: T
)