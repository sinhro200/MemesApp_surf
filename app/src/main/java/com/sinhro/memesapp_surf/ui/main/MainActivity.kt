package com.sinhro.memesapp_surf.ui.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.sinhro.memesapp_surf.R
import com.sinhro.memesapp_surf.model.Storage.*

class MainActivity:AppCompatActivity(){
    lateinit var storage: Storage
    lateinit var test_tv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        test_tv = findViewById(R.id.test_tv)

        storage = Storage(applicationContext)

        showUserFromSharedPrefs()
    }

    private fun showUserFromSharedPrefs() {
        val tok = storage.get(PREF_NAME_TOKEN)
        val id = storage.get(PREF_NAME_ID)
        val usrname = storage.get(PREF_NAME_USERNAME)
        val fname = storage.get(PREF_NAME_FIRSTNAME)
        val lname = storage.get(PREF_NAME_LASTNAME)
        val usrdescr = storage.get(PREF_NAME_USERDESCRIPTION)
        test_tv.text = "{user info from shared prefs} : \n $tok \n $id \n $usrname \n $fname \n $lname \n $usrdescr  "
    }

}