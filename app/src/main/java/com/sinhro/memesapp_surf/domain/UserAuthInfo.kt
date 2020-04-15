package com.sinhro.memesapp_surf.domain

import com.google.gson.annotations.SerializedName

data class UserAuthInfo (
    @SerializedName("login")
    var login : String,
    @SerializedName("password")
    var password: String
)