package com.sinhro.memesapp_surf.ui.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.FrameLayout
import com.sinhro.memesapp_surf.R
import com.sinhro.memesapp_surf.ui.main.memesList.MemesListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var frameLayout: FrameLayout

    private var fragments: MutableList<Fragment> = mutableListOf()
    private var prevFrag: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavView = findViewById(R.id.main_bottomNavView)
        frameLayout = findViewById(R.id.main_frameLayout)

        showFragment(MemesListFragment.newInstance())
        bottomNavView.setOnNavigationItemSelectedListener(
            createOnNavItemSelectedListener()
        )
    }

    private fun createOnNavItemSelectedListener(): BottomNavigationView.OnNavigationItemSelectedListener {
        return object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(botNavViewItem: MenuItem): Boolean {
                val curFragment = when (botNavViewItem.itemId) {
                    R.id.menu_list -> MemesListFragment.newInstance()
                    R.id.menu_add -> AddMemeFragment.newInstance()
                    R.id.menu_profile -> ProfileFragment.newInstance()
                    else -> return false
                }
                showFragment(curFragment)
                return true
            }
        }
    }

    private fun showFragment(fr: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        prevFrag?.let {
            transaction.hide(it)
        }
        if (fragments.contains(fr)) {
            transaction.show(fr)
        } else {
            transaction.add(R.id.main_frameLayout, fr)
        }
        transaction.commit()
        prevFrag = fr
        fragments.add(fr)
    }


}