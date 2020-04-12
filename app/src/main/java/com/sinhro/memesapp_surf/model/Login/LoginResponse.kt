package com.sinhro.memesapp_surf.model.Login

import com.google.gson.annotations.SerializedName

class LoginResponse (
    @SerializedName("accessToken") private val accTok: String
){
    fun getAccessToken() = accTok
}