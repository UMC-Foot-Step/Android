package com.softsquared.template.kotlin.src.main.post

import androidx.recyclerview.widget.RecyclerView
import com.softsquared.template.kotlin.databinding.SearchPositionItemListBinding

class PositionViewHolder(private val binding: SearchPositionItemListBinding) : RecyclerView.ViewHolder(binding.root) {
    private val context = binding.root.context

    fun bind(item: PositionData){
        binding.searchPosTvItemTitle.text = item.pos_title
        binding.searchPosTvItemAddress.text = item.pos_address
    }
}

