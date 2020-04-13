package com.sinhro.memesapp_surf.model.Login

import com.sinhro.memesapp_surf.CustomDebugger.CustomDebug
import com.sinhro.memesapp_surf.domain.User
import com.sinhro.memesapp_surf.model.BASE_URL
import com.sinhro.memesapp_surf.model.RetrofitCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginService {
    var resp : Call<LoginResponse>? = null

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    private val loginApi = retrofit.create(LoginApi::class.java)

    fun login(
        log: String,
        pass: String,
        onSuccess : (User) -> Unit,
        onError :(Throwable) -> Unit
    ) {
        resp =loginApi.login(log, pass)
        resp?.enqueue(
            RetrofitCallback<LoginResponse>(
                { loginResponse -> onSuccess(
                    User(loginResponse.getAccessToken(),loginResponse.getUserInfo())
                ) },
                { err -> onError(err) }
            )
        )

        /*loginApi.login_tst(log,pass)?.enqueue( object : Callback<Any>{
            override fun onFailure(call: Call<Any>, t: Throwable) {
                CustomDebug.log(t.toString())
            }

            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                CustomDebug.log(response.body().toString())
                CustomDebug.log(response.raw().toString())
            }

        }
        )*/
    }

    fun cancel(){
        resp?.cancel()
    }
}