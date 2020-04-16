package com.sinhro.memesapp_surf.model.login

import android.os.Handler
import com.sinhro.memesapp_surf.domain.User
import com.sinhro.memesapp_surf.model.BASE_URL
import com.sinhro.memesapp_surf.model.LOGIN_TIMEOUT_MILLIS
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeoutException

class LoginService {

    companion object {
        fun getInstance(): Retrofit {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
        }
    }

    private val retrofit = getInstance()
    private val loginApi = retrofit.create(LoginApi::class.java)

    fun login(
        loginRequest: LoginRequest,
        onSuccess: (User) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val observer = loginApi.login(loginRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    onSuccess(User(it.getAccessToken(), it.getUserInfo()))
                },
                {
                    onError(it)
                }
            )
        applyTimeoutHandler(observer, onError)
    }

    private fun applyTimeoutHandler(obs: Disposable, onError: (Throwable) -> Unit) {
        Handler()
            .postDelayed({
                if (!obs.isDisposed) {
                    obs.dispose()
                    onError(TimeoutException())
                }
            }, LOGIN_TIMEOUT_MILLIS)
    }
}