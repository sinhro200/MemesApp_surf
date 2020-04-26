package com.sinhro.memesapp_surf.ui.main.memesList

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import com.sinhro.memesapp_surf.R
import com.sinhro.memesapp_surf.customDebugger.CustomDebug
import com.sinhro.memesapp_surf.model.memes.MemesService
import com.sinhro.memesapp_surf.ui.SnackbarHelper


class MemesListFragment : Fragment() {
    companion object {
        private var instance: MemesListFragment? = null
        fun newInstance(): MemesListFragment {
            if (instance == null)
                instance = MemesListFragment()
            return instance!!
        }
    }

    private lateinit var searchIv: ImageView
    private lateinit var errorTv: TextView
    private lateinit var memesListRecycleView: RecyclerView
    private lateinit var swipeRefreshLayout : SwipeRefreshLayout

    private val adapter = RecyclerViewAdapter()
    private val memesService = MemesService()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val body = inflater.inflate(R.layout.fragment_meme_list, container, false)
        findViews(body)
        memesListRecycleView.adapter = adapter
        swipeRefreshLayout.setOnRefreshListener { refreshMemes() }
        refreshMemes()

        return body
    }

    private fun findViews(body: View) {
        searchIv = body.findViewById(R.id.memes_list_search_iv)
        memesListRecycleView = body.findViewById(R.id.memes_list_recView)
        errorTv = body.findViewById(R.id.error_tv)
        swipeRefreshLayout = body.findViewById(R.id.swipe_refresh_layout)
    }


    private fun refreshMemes() {
        setLoadingState(true)
        memesService.loadMemes(
            {
                adapter.setMemes(it)
                setLoadingState(false)
                setErrorState(false)
            },
            {
                setLoadingState(false)
                CustomDebug.log(it.message.toString())
                setErrorState(true)
            }
        )
    }

    private fun setLoadingState(isLoading: Boolean) {
        swipeRefreshLayout.isRefreshing = isLoading
    }

    private fun setErrorState(isError: Boolean) {
        if (isError){
            errorTv.visibility =  View.VISIBLE
            SnackbarHelper.showErrorMessage(
                memesListRecycleView,
                getString(R.string.missingInternetConnection)
            )
        }else{
            errorTv.visibility =  View.INVISIBLE
        }

    }
}