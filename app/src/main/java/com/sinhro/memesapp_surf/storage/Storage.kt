package com.sinhro.memesapp_surf.storage

import android.content.Context
import android.content.SharedPreferences
import com.sinhro.memesapp_surf.customDebugger.CustomDebug

class Storage(context : Context) {

    var sharedPreferences: SharedPreferences =
        context.getSharedPreferences(APP_PREFERENCES_NAME,Context.MODE_PRIVATE)

    fun save(name : String, value : String){
        sharedPreferences.edit()
            .putString(name,value)
            .apply()
        CustomDebug.log("Shared prefs saved : [$name] = [$value]")
    }

    fun get(name : String):String = sharedPreferences.getString(name,"")?:""

    fun remove(name : String){
        sharedPreferences.edit()
            .remove(name)
            .apply()
        CustomDebug.log("Shared prefs removed : [$name]")
    }

    fun saveInt(name : String, value : Int){
        sharedPreferences.edit()
            .putInt(name,value)
            .apply()
        CustomDebug.log("Shared prefs saved : [$name] = [$value]")
    }

    fun getInt(name : String) = sharedPreferences.getInt(name,0)

}