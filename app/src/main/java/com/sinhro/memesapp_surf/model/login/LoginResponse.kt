package com.sinhro.memesapp_surf.model.login

import com.google.gson.annotations.SerializedName
import com.sinhro.memesapp_surf.domain.UserInfo

class LoginResponse (
    @SerializedName("accessToken") private val accTok: String,
    @SerializedName("userInfo") private val userInfo: UserInfo
){
    fun getAccessToken() = accTok
    fun getUserInfo() = userInfo
}