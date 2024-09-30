package com.mehdik.freemotus.ui.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mehdik.freemotus.databinding.ItemWordBinding
import com.mehdik.freemotus.ui.main.holders.WordViewHolder
import com.mehdik.freemotus.ui.main.models.WordData

class WordAdapter(
    var items: List<WordData>
) : RecyclerView.Adapter<WordViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        WordViewHolder(
            ItemWordBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = items.size

    private fun getItem(position: Int) = items[position]

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) =
        holder.bind(getItem(position))
}
