package com.sinhro.memesapp_surf.model.Login

import com.sinhro.memesapp_surf.model.Login.LoginResponse
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginApi {
    @POST("/auth/login")
    fun login(
        @Query("login") login: String,
        @Query("password") password: String
    ): Call<LoginResponse>
}