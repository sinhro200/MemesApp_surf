package com.sinhro.memesapp_surf.ui.main.memesList

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.sinhro.memesapp_surf.R
import com.sinhro.memesapp_surf.customDebugger.CustomDebug
import com.sinhro.memesapp_surf.model.memes.MemesService
import com.sinhro.memesapp_surf.ui.SnackbarHelper
import com.sinhro.memesapp_surf.ui.main.MainActivity


class MemesListFragment : androidx.fragment.app.Fragment() {
    companion object {
        private var instance: MemesListFragment? = null
        fun newInstance(): MemesListFragment {
            if (instance == null)
                instance = MemesListFragment()
            return instance!!
        }
    }

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

        setHasOptionsMenu(true)
        (activity as MainActivity).supportActionBar?.title = getString(R.string.title_memes_list)

        return body
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK)
            adapter.checkMemeAfterResumed()
        super.onActivityResult(requestCode, resultCode, data)
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.memes_list_toolbar,menu)
        super.onCreateOptionsMenu(menu,inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_search) {

        }
        return true
    }

    override fun onResume() {
        super.onResume()
    }


    private fun findViews(body: View) {
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