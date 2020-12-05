package com.example.uiexample.music.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.uiexample.R
import com.example.uiexample.music.MusicItemData
import com.example.uiexample.music.MusicItemViewHolder

class MusicAdapter(
    private var source: List<MusicItemData>,
    private val action: (MusicItemData) -> Unit
) : RecyclerView.Adapter<MusicItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_music, parent, false)
        return MusicItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MusicItemViewHolder, position: Int) {
        holder.bindView(source[position], action)
    }

    override fun getItemCount(): Int {
        return source.size
    }

    public fun updateContent(list: List<MusicItemData>) {
        source = list
        notifyDataSetChanged()
    }
}