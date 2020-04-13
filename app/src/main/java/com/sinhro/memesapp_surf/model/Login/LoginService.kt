package com.sinhro.memesapp_surf.model.Login

import com.sinhro.memesapp_surf.model.BASE_URL
import com.sinhro.memesapp_surf.model.RetrofitCallback
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginService {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val loginApi = retrofit.create(LoginApi::class.java)

    fun login(
        log: String,
        pass: String,
        onSuccess : (String) -> Unit,
        onError :(Throwable) -> Unit
    ) {
        val resp: Call<LoginResponse> = loginApi.login(log, pass)
        resp.enqueue(
            RetrofitCallback<LoginResponse>(
                { loginResponse -> onSuccess(loginResponse.getAccessToken()) },
                { err -> onError(err) }
            )
        )
    }
}