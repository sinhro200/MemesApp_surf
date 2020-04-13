package com.sinhro.memesapp_surf.domain

import com.google.gson.annotations.SerializedName

class UserInfo(
    @SerializedName("id") val id: Int,
    @SerializedName("username") val username: String,
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("userDescription") val userDescription: String
)