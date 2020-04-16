package com.sinhro.memesapp_surf.model.login

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("/auth/login")
    fun login(@Body loginRequest: LoginRequest): Observable<LoginResponse>
}