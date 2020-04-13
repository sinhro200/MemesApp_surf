package com.sinhro.memesapp_surf.model.Storage

import android.content.Context
import android.content.SharedPreferences
import com.sinhro.memesapp_surf.CustomDebugger.CustomDebug

class Storage(context : Context) {

    var sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(APP_PREFERENCES_NAME,Context.MODE_PRIVATE)
    }

    fun save(name : String, value : String){
        sharedPreferences.edit().putString(name,value).apply()
        CustomDebug.log("Shared prefs saved : [$name] = [$value]")
    }

    fun get(name : String) : String{
        return sharedPreferences.getString(name,"")
    }


    fun saveIntByName(name : String , value : Int){
        sharedPreferences.edit().putInt(name,value).apply()
        CustomDebug.log("Shared prefs saved : [$name] = [$value]")
    }

    fun getIntByName(name : String) = sharedPreferences.getInt(name,0)

}