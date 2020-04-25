package com.sinhro.memesapp_surf.ui.main.memesList

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.*
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.TextView
import com.sinhro.memesapp_surf.R
import com.sinhro.memesapp_surf.customDebugger.CustomDebug
import com.sinhro.memesapp_surf.model.memes.MemesService
import com.sinhro.memesapp_surf.ui.SnackbarHelper
import com.sinhro.memesapp_surf.ui.SwipeListener


class MemesListFragment : Fragment() {
    companion object {
        var instance: MemesListFragment? = null
        fun newInstance(): MemesListFragment {
            if (instance == null)
                instance = MemesListFragment()
            return instance!!
        }
    }

    private lateinit var searchIv: ImageView
    private lateinit var refreshIv: ImageView
    private lateinit var errorTv: TextView
    private lateinit var memesListRecycleView: RecyclerView

    private val adapter = RecyclerViewAdapter()
    private val memesService = MemesService()

    private lateinit var rotateAnimation: Animation

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val body = inflater.inflate(R.layout.fragment_meme_list, container, false)
        searchIv = body.findViewById(R.id.memes_list_search_iv)
        memesListRecycleView = body.findViewById(R.id.memes_list_recView)
        refreshIv = body.findViewById(R.id.refreshIv)
        errorTv = body.findViewById(R.id.error_tv)

        memesListRecycleView.adapter = adapter

        rotateAnimation =
            AnimationUtils.loadAnimation(context, R.anim.rotate_center)
        rotateAnimation.interpolator = LinearInterpolator()

        loadMemes()

        val listener = SwipeListener()
        listener.OnSwipeTouchListener(context, { loadMemes() }, {}, {}, {})
        memesListRecycleView.setOnTouchListener(listener)
        return body
    }


    private fun loadMemes() {
        setLoadState(true)
        memesService.loadMemes(
            {
                adapter.setMemes(it)
                setLoadState(false)
            },
            {
                setLoadState(false)
                CustomDebug.log(it.message.toString())
                setErrorState(true)
            }
        )
    }

    private fun setLoadState(isLoading: Boolean) {
        refreshIv.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
        if (isLoading)
            refreshIv.startAnimation(rotateAnimation)
        else
            refreshIv.clearAnimation()
    }

    private fun setErrorState(isError: Boolean) {
        errorTv.visibility = if (isError) View.VISIBLE else View.INVISIBLE
        SnackbarHelper.showErrorMessage(
            memesListRecycleView,
            getString(R.string.missingInternetConnection)
        )
    }
}