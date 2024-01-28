package com.loyalty.player.api

import com.loyalty.player.ui.splash.response.SplashResponse
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface UserAPI {
    @POST("api/content/splashtxt")
    suspend fun splash(
        @Query("city") city: String,
        @Query("isSpi") isSpi: String,
        @Query("av") version: String,
        @Query("pt") platform: String
    ): Response<SplashResponse>
}