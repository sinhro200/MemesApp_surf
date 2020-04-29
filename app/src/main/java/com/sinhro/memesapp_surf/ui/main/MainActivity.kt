package com.sinhro.memesapp_surf.ui.main

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.sinhro.memesapp_surf.R
import com.sinhro.memesapp_surf.ui.main.addMeme.AddMemeActivity
import com.sinhro.memesapp_surf.ui.main.addMeme.AddMemeFragment
import com.sinhro.memesapp_surf.ui.main.memesList.MemesListFragment
import com.sinhro.memesapp_surf.ui.main.profile.ProfileFragment

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var frameLayout: FrameLayout
    private lateinit var toolbar: Toolbar

    private var fragments: MutableList<Fragment> = mutableListOf()
    private var prevFrag: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.main_toolbar)
        setSupportActionBar(toolbar)

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
                val curFragment: Fragment
                when (botNavViewItem.itemId) {
                    R.id.menu_list -> showFragment(MemesListFragment.newInstance())
                    R.id.menu_add -> openAddMemeActivity()//AddMemeFragment.newInstance()
                    R.id.menu_profile -> showFragment(ProfileFragment.newInstance())
                    else -> return false
                }
                return true
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        for (fragment in this.supportFragmentManager.fragments)
            fragment.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
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

    private fun openAddMemeActivity() {
        val intent = Intent(this, AddMemeActivity::class.java)
        startActivity(intent)
    }


}