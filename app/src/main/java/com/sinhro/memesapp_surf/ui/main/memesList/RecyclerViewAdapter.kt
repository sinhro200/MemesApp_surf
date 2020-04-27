package com.sinhro.memesapp_surf.ui.main.memesList

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.sinhro.memesapp_surf.R
import com.sinhro.memesapp_surf.domain.MemeInfo
import com.sinhro.memesapp_surf.storage.StorageMemeHelper
import com.sinhro.memesapp_surf.ui.ShareUtil
import com.sinhro.memesapp_surf.ui.main.MemeInfoActivity

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder>() {

    private var data: List<MemeInfo> = listOf()
    private lateinit var storageMemeHelper: StorageMemeHelper

    private var recMeme: MemeInfo? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.meme_cell,
            parent,
            false
        )
        storageMemeHelper = StorageMemeHelper(parent.context)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(itemViewHolder: ItemViewHolder, position: Int) {
        val meme = data[position]
        itemViewHolder.memeTitle_tv.text = meme.title

        itemViewHolder.memeFavorite_iv.isActivated = storageMemeHelper.isFav(meme)

        Glide.with(itemViewHolder.memeImage_iv.context)
            .load(meme.photoUrl)
            .into(itemViewHolder.memeImage_iv)

        itemViewHolder.memeFavorite_iv.setOnClickListener { v: View? ->
            v as ImageView
            v.isActivated = !v.isActivated
            meme.isFavorite = v.isActivated
            storageMemeHelper.saveIfFav(meme)
        }

        itemViewHolder.memeShare_iv.setOnClickListener { v: View? ->
            v as ImageView
            shareMeme(meme)
        }

        itemViewHolder.memeBody_Ll.setOnClickListener {
            openMemeInfoActivity(it.context, meme)
        }
    }

    fun setMemes(memeInfos: List<MemeInfo>) {
        this.data = memeInfos
        notifyDataSetChanged()
    }


    private fun shareMeme(meme: MemeInfo) {
        ShareUtil.share(meme)
    }

    private fun openMemeInfoActivity(ctx: Context, meme: MemeInfo) {
        recMeme = meme
        val intent = Intent(ctx, MemeInfoActivity::class.java)
        intent.putExtra("meme", Gson().toJson(meme))
        ctx.startActivity(intent)
    }

    fun checkMemeAfterResumed(){
        recMeme?.let {
            val i = data.indexOf(it)
            if (i >=0){
                notifyItemChanged(i)
            }
        }
    }

    inner class ItemViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val memeBody_Ll: LinearLayout = v.findViewById(R.id.mcell_body_ll)
        val memeTitle_tv: TextView = v.findViewById(R.id.mcell_title_tv)
        val memeImage_iv: ImageView = v.findViewById(R.id.mcell_mainImage_iv)
        val memeShare_iv: ImageView = v.findViewById(R.id.mcell_share_iv)
        val memeFavorite_iv: ImageView = v.findViewById(R.id.mcell_favorite_iv)
    }
}