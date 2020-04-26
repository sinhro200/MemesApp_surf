package com.sinhro.memesapp_surf.storage

import android.content.Context
import com.google.gson.Gson
import com.sinhro.memesapp_surf.domain.MemeInfo

class StorageHelper(ctx : Context) {
    private var storage = Storage(ctx)
    public fun saveMeme(meme : MemeInfo){
        val memeStr = Gson().toJson(meme)
        storage.save(meme.id.toString(),memeStr)
    }

    public fun getMeme(memeId : String) : MemeInfo?{
        val memeJson = storage.get(memeId)
        return if (!memeJson.isBlank())
            Gson().fromJson(memeJson,MemeInfo::class.java)
            else null
    }
}