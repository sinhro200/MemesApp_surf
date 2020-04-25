package com.sinhro.memesapp_surf.ui.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sinhro.memesapp_surf.R

class AddMemeFragment : Fragment() {
    companion object {
        var instance:AddMemeFragment? = null
        fun newInstance(): AddMemeFragment {
            if (instance==null)
                instance = AddMemeFragment()
            return instance!!
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_meme,container,false)
    }
}