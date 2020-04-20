package com.sinhro.memesapp_surf.ui.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sinhro.memesapp_surf.R

class MemesListFragment : Fragment() {
    companion object{
        fun newInstance():MemesListFragment{
            return MemesListFragment()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_meme_list,container,false)
    }
}