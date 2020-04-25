package com.sinhro.memesapp_surf.ui.main.memesList

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.sinhro.memesapp_surf.R
import com.sinhro.memesapp_surf.model.memes.Meme

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder>() {

    private var data: List<Meme> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.meme_cell,
            parent,
            false
        )
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(itemViewHolder: ItemViewHolder, position: Int) {
        val meme = data[position]
        itemViewHolder.memeTitle_tv.text = meme.title
        Glide.with(itemViewHolder.memeImage_iv.context)
            .load(meme.photoUrl)
            .into(itemViewHolder.memeImage_iv)
    }

     fun setMemes(memes: List<Meme>) {
        this.data = memes
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val memeTitle_tv: TextView = v.findViewById(R.id.mcell_title_tv)
        val memeImage_iv: ImageView = v.findViewById(R.id.mcell_mainImage_iv)
    }
}