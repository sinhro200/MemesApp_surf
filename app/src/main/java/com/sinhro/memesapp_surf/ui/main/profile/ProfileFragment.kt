package com.sinhro.memesapp_surf.ui.main.profile

import android.app.Application
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sinhro.memesapp_surf.R
import com.sinhro.memesapp_surf.customDebugger.CustomDebug
import com.sinhro.memesapp_surf.database.MemeViewModel
import com.sinhro.memesapp_surf.model.memes.MemeInfo
import com.sinhro.memesapp_surf.storage.PREF_NAME_USERDESCRIPTION
import com.sinhro.memesapp_surf.storage.PREF_NAME_USERNAME
import com.sinhro.memesapp_surf.storage.Storage
import com.sinhro.memesapp_surf.ui.main.MainActivity
import com.sinhro.memesapp_surf.ui.main.memesList.RecyclerViewAdapter
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class ProfileFragment : androidx.fragment.app.Fragment() {

    private lateinit var profileDescriptionTv: TextView
    private lateinit var profileNameTv: TextView
    private lateinit var profileImageIv: ImageView
    private lateinit var profileRecyclerView: RecyclerView
    private val adapter = RecyclerViewAdapter()

    private lateinit var storage:Storage
    //private lateinit var profile_description_tv: TextView

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
        val v = inflater.inflate(R.layout.fragment_profile,container,false)
        setHasOptionsMenu(true)
        (activity as MainActivity).supportActionBar?.title = ""
        /**
         * TODO
         * после ухода с фрагмента цвет Toolbar таким и остаёться
         *
         * (Вероятно не правильно так ставить цвет Toolbar)
         */
        (activity as MainActivity).supportActionBar?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(v.context,R.color.colorPrimaryDark)
            )
        )

        findViews(v)
        storage = Storage(v.context)

        setProfileInfo()
        setProfilePic(v.context)

        profileRecyclerView.adapter = this.adapter
        val mvm = MemeViewModel(v.context.applicationContext as Application)
        mvm.getObservableMemesList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                adapter.setMemes(it)
                it.forEach {
                    CustomDebug.log(it.toString())
                }
            }



        return v
    }

    override fun onPause() {
        super.onPause()
    }

    private fun setProfileInfo(){
        val username = storage.get(PREF_NAME_USERNAME)
        val userDescr = storage.get(PREF_NAME_USERDESCRIPTION)

        profileNameTv.text = username
        profileDescriptionTv.text = userDescr
    }



    private fun setProfilePic(ctx : Context){
        Glide.with(ctx)
            .load(R.drawable.unnamed)
            .apply(RequestOptions().circleCrop())
            .into(profileImageIv)
    }

    private fun findViews(v: View) {
        profileDescriptionTv = v.findViewById(R.id.profile_description_tv)
        profileNameTv = v.findViewById(R.id.profile_name_tv)
        profileImageIv = v.findViewById(R.id.profile_image_iv)
        profileRecyclerView = v.findViewById(R.id.profile_recyclerView)
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