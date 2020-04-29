package com.sinhro.memesapp_surf.ui.main.profile

import android.graphics.Bitmap
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.sinhro.memesapp_surf.R
import com.sinhro.memesapp_surf.storage.PREF_NAME_USERDESCRIPTION
import com.sinhro.memesapp_surf.storage.PREF_NAME_USERNAME
import com.sinhro.memesapp_surf.storage.Storage
import com.sinhro.memesapp_surf.ui.main.MainActivity


class ProfileFragment : androidx.fragment.app.Fragment() {

    private lateinit var profileDescriptionTv: TextView
    private lateinit var profileNameTv: TextView
    private lateinit var profileImageIv: ImageView

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
        (activity as MainActivity).supportActionBar?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(v.context,R.color.colorPrimaryDark)
            )
        )

        findViews(v)
        storage = Storage(v.context)
        val username = storage.get(PREF_NAME_USERNAME)
        val userDescr = storage.get(PREF_NAME_USERDESCRIPTION)

        profileNameTv.text = username
        profileDescriptionTv.text = userDescr

        Glide.with(v.context)
            .load(R.drawable.unnamed)
            .apply(RequestOptions().circleCrop())
            .into(profileImageIv)

        return v
    }

    private fun findViews(v: View) {
        profileDescriptionTv = v.findViewById(R.id.profile_description_tv)
        profileNameTv = v.findViewById(R.id.profile_name_tv)
        profileImageIv = v.findViewById(R.id.profile_image_iv)
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