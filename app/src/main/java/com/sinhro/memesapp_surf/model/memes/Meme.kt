package com.sinhro.memesapp_surf.model.memes

import com.google.gson.annotations.SerializedName

data class Meme(
    @SerializedName("id") val id : Long,
    @SerializedName("title") val title:String,
    @SerializedName("description") val description: String,
    @SerializedName("isFavorite") val isFavorite:Boolean,
    @SerializedName("createdDate") val createdDate:Long,
    @SerializedName("photoUrl") val photoUrl:String
)