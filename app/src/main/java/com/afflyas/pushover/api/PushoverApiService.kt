package com.afflyas.pushover.api

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface PushoverApiService {

    companion object {
        const val MY_USER_KEY = "ujnifpj177v4of2ppm51imzr5imgg7"
        const val MY_DEVICE = "oneplus5t"

        const val FINSOFT_USER_KEY = "u3ba37zs37ftt72cab43qwp2xofidn"
        const val FINSOFT_DEVICE = "galaxys9"

        const val APP_API_TOKEN = "aoa6mhehd4ke45d6fg8j2k2s3i4iwq"

        const val BASE_URL = "https://api.pushover.net/1/"
    }


    /**
     * Push message
     */
    @POST("messages.json")
    fun pushMessage(
            @Query("token") token: String,
            @Query("user") user: String,
            @Query("device") device: String,
            @Query("message") message: String,
            @Query("timestamp") timestamp: Long
    ): Call<PushResponse>

}