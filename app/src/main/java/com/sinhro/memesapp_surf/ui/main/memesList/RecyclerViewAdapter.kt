package com.sinhro.memesapp_surf.ui.main.memesList

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.sinhro.memesapp_surf.R
import com.sinhro.memesapp_surf.domain.MemeInfo
import com.sinhro.memesapp_surf.storage.StorageHelper

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder>() {

    private var data: List<MemeInfo> = listOf()
    private lateinit var storageHelper: StorageHelper

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.meme_cell,
            parent,
            false
        )
        storageHelper = StorageHelper(parent.context)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(itemViewHolder: ItemViewHolder, position: Int) {
        val meme = data[position]
        itemViewHolder.memeTitle_tv.text = meme.title

        val recMeme = storageHelper.getMeme(meme.id.toString())
        itemViewHolder.memeFavorite_iv.isActivated = recMeme != null && recMeme.isFavorite

        Glide.with(itemViewHolder.memeImage_iv.context)
            .load(meme.photoUrl)
            .into(itemViewHolder.memeImage_iv)

        itemViewHolder.memeFavorite_iv.setOnClickListener { v: View? ->
            v as ImageView
            v.isActivated = !v.isActivated
            meme.isFavorite = v.isActivated
            setMemeIfFavorite(meme)
        }

        itemViewHolder.memeShare_iv.setOnClickListener { v: View? ->
            v as ImageView
            shareMeme(meme)
        }
    }

    fun setMemes(memeInfos: List<MemeInfo>) {
        this.data = memeInfos
        notifyDataSetChanged()
    }

    private fun setMemeIfFavorite(meme: MemeInfo) {
        if (meme.isFavorite)
            storageHelper.saveMeme(meme)
    }

    private fun shareMeme(meme: MemeInfo) {

    }

    inner class ItemViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val memeTitle_tv: TextView = v.findViewById(R.id.mcell_title_tv)
        val memeImage_iv: ImageView = v.findViewById(R.id.mcell_mainImage_iv)
        val memeShare_iv: ImageView = v.findViewById(R.id.mcell_share_iv)
        val memeFavorite_iv: ImageView = v.findViewById(R.id.mcell_favorite_iv)
    }
}