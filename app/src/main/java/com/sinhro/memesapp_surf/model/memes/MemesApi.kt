package com.sinhro.memesapp_surf.model.memes

import com.sinhro.memesapp_surf.domain.MemeInfo
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface MemesApi {
    @GET("/memes")
    fun getMemes(): Observable<List<MemeInfo>>
}