package com.afflyas.pushover.api

import com.google.gson.annotations.SerializedName

data class PushResponse(
        @SerializedName("status") val status: Int,
        @SerializedName("request") val request: String
)