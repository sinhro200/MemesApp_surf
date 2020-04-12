package com.sinhro.memesapp_surf.model

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST

class NetworkService {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val loginApi = retrofit.create(LoginApi::class.java)

    fun login(log:String,pass:String){
        val resp : Call<Any> = loginApi.login(log,pass)
        resp.enqueue(
            object : Callback<Any>{
                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Log.e("Call<POST> ",call.toString())
                    Log.e("Call<POST> request",call.request().toString())
                    Log.e("Throwable",t.toString())
                }

                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    //Log.i("resp Callback",call.toString())
                    Log.i("response body",response.body().toString())
                    Log.i("response message",response.message().toString())
                    Log.i("response code",response.code().toString())
                    Log.i("response headers",response.headers().toString())
                    Log.i("response raw",response.raw().toString())
                }

            }
        )
    }
}