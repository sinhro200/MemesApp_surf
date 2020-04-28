package com.sinhro.memesapp_surf.model.memes

import android.os.Handler
import com.sinhro.memesapp_surf.model.BASE_URL
import com.sinhro.memesapp_surf.model.MEMES_TIMEOUT_MILLIS
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeoutException

class MemesService {
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
    private val memesApi = retrofit.create(MemesApi::class.java)

    fun loadMemes(
        onSuccess: (List<MemeInfo>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val observer = memesApi.getMemes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    onSuccess(it)
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
            }, MEMES_TIMEOUT_MILLIS)
    }
}