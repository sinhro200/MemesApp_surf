package com.sinhro.memesapp_surf.model.login

import com.google.gson.annotations.SerializedName

data class LoginRequest (
    @SerializedName("login")
    var login : String,
    @SerializedName("password")
    var password: String
)