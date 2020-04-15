package com.sinhro.memesapp_surf.model

import com.sinhro.memesapp_surf.customDebugger.CustomDebug
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.IllegalStateException

class RetrofitCallback<T>(
    val onSuccess: (T) -> Unit,
    val onError: (Throwable) -> Unit
):
    Callback<T> {
    override fun onFailure(call: Call<T>, t: Throwable) {
        onError(t)
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        val body = response.body()

        CustomDebug.log(response.raw().toString())
        CustomDebug.log(body.toString())
        body.toString()
        if (response.isSuccessful && body != null){
            onSuccess(body)
        }else{
            onError(IllegalStateException("Response body is null"))
        }
    }
}