package com.sinhro.memesapp_surf.ui.main.memesList

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.sinhro.memesapp_surf.R
import com.sinhro.memesapp_surf.customDebugger.CustomDebug
import com.sinhro.memesapp_surf.model.memes.Meme
import com.sinhro.memesapp_surf.model.memes.MemesService

class MemesListFragment : Fragment() {
    companion object {
        var instance:MemesListFragment? = null
        fun newInstance(): MemesListFragment {
            if (instance==null)
                instance = MemesListFragment()
            return instance!!
        }
    }

    private lateinit var searchIv: ImageView
    private lateinit var memesListRecycleView: RecyclerView
    private val memesService = MemesService()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val body = inflater.inflate(R.layout.fragment_meme_list, container, false)
        searchIv = body.findViewById(R.id.memes_list_search_iv)
        memesListRecycleView = body.findViewById(R.id.memes_list_recView)

        val adapter = RecyclerViewAdapter()
        memesListRecycleView.adapter = adapter
        var list: List<Meme>
        memesService.loadMemes(
            {
                list = it
                adapter.setMemes(list)
            },
            {
                CustomDebug.log(it.message.toString())
            }
        )
        return body
    }

}