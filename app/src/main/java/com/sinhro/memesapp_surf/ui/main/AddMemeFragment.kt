package com.sinhro.memesapp_surf.ui.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
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
        setHasOptionsMenu(true)
        (activity as MainActivity).supportActionBar?.title = ""
        return inflater.inflate(R.layout.fragment_add_meme,container,false)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.add_meme_toolbar,menu)
        super.onCreateOptionsMenu(menu,inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_search) {

        }
        return true
    }
}