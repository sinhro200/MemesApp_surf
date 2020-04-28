package com.sinhro.memesapp_surf.ui.main.profile

import android.os.Bundle
import android.view.*
import com.sinhro.memesapp_surf.R
import com.sinhro.memesapp_surf.ui.main.MainActivity

class ProfileFragment : androidx.fragment.app.Fragment() {
    companion object {
        var instance: ProfileFragment? = null
        fun newInstance(): ProfileFragment {
            if (instance ==null)
                instance =
                    ProfileFragment()
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
        return inflater.inflate(R.layout.fragment_profile,container,false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.profile_toolbar,menu)
        super.onCreateOptionsMenu(menu,inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_search) {

        }
        return true
    }
}