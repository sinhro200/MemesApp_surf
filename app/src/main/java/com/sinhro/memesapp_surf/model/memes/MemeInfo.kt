package com.sinhro.memesapp_surf.model.memes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.sinhro.memesapp_surf.database.DATABASE_TABLE_NAME

@Entity(tableName = DATABASE_TABLE_NAME)
data class MemeInfo(
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id : Long,

    @ColumnInfo(name = "title")
    @SerializedName("title")
    val title:String,

    @ColumnInfo(name = "description")
    @SerializedName("description")
    val description: String,

    @ColumnInfo(name = "isFavorite")
    @SerializedName("isFavorite")
    var isFavorite:Boolean,

    @ColumnInfo(name = "createdDate")
    @SerializedName("createdDate")
    val createdDate:Long,

    @ColumnInfo(name = "photoUrl")
    @SerializedName("photoUrl")
    val photoUrl:String
)