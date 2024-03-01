package com.fm.easypay.api.requests.base

import com.google.gson.annotations.SerializedName

data class EasyPayQuery(
    @SerializedName("Query")
    val query: String,
)
