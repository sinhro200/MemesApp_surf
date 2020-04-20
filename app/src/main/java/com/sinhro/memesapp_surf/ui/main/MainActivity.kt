package com.sinhro.memesapp_surf.ui.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.sinhro.memesapp_surf.R

class MainActivity:AppCompatActivity(){

    lateinit var bottomNavView: BottomNavigationView
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(MemesListFragment.newInstance())

        toolbar = findViewById(R.id.main_toolbar)
        bottomNavView = findViewById(R.id.main_bottomNavView)



        bottomNavView.setOnNavigationItemSelectedListener(
            createOnNavItemSelectedListener()
        )
    }

    private fun createOnNavItemSelectedListener(): BottomNavigationView.OnNavigationItemSelectedListener {
        return object : BottomNavigationView.OnNavigationItemSelectedListener{
            override fun onNavigationItemSelected(p0: MenuItem): Boolean {
                when (p0.itemId) {
                    R.id.menu_list -> replaceFragment(MemesListFragment.newInstance())
                    R.id.menu_add -> replaceFragment(AddMemeFragment.newInstance())
                    R.id.menu_profile -> replaceFragment(ProfileFragment.newInstance())
                    else -> return false
                }
                return true
            }

        }
    }

    private fun replaceFragment(fr : Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frameLayout,fr)
            .commit()
    }



}