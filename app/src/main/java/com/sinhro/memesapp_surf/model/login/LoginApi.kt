package com.sinhro.memesapp_surf.model.Login

import com.sinhro.memesapp_surf.domain.UserAuthInfo
import com.sinhro.memesapp_surf.model.login.LoginResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginApi {
    @POST("/auth/login")
    fun login(@Body userAuthInfo: UserAuthInfo): Observable<LoginResponse>
}