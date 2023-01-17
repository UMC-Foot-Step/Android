package com.softsquared.template.kotlin.src.main.post

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.template.kotlin.databinding.SearchPositionItemListBinding

class PositionListAdapter(itemList: List<PositionData>) : RecyclerView.Adapter<PositionViewHolder>() {
    var itemList = mutableListOf<PositionData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PositionViewHolder {
        val binding = SearchPositionItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PositionViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: PositionViewHolder, position: Int) {
        val item = itemList[position]

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
        holder.apply{
            bind(item)
        }
    }

    // ClickListener
    interface OnItemClickListener{
        fun onClick(v: View, position: Int)
    }

    private lateinit var itemClickListener : OnItemClickListener

    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

}