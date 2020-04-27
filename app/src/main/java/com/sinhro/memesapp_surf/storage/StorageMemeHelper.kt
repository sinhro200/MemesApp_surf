package com.sinhro.memesapp_surf.storage

import android.content.Context
import com.google.gson.Gson
import com.sinhro.memesapp_surf.domain.MemeInfo

class StorageMemeHelper(ctx : Context) {
    private var storage = Storage(ctx)
    fun saveMeme(meme : MemeInfo){
        val memeStr = Gson().toJson(meme)
        storage.save(meme.id.toString(),memeStr)
    }

    fun removeMeme(meme : MemeInfo){
        storage.remove(meme.id.toString())
    }

    fun getMeme(memeId : String) : MemeInfo?{
        val memeJson = storage.get(memeId)
        return if (!memeJson.isBlank())
            Gson().fromJson(memeJson,MemeInfo::class.java)
            else null
    }

    fun isFav(meme : MemeInfo):Boolean{
        val recMeme = getMeme(meme.id.toString())
        return  recMeme != null && recMeme.isFavorite
    }

    fun saveIfFav(meme : MemeInfo){
        if (meme.isFavorite)
            saveMeme(meme)
        else
            removeMeme(meme)
    }
}