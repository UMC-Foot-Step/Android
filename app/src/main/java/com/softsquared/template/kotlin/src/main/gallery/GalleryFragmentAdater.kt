package com.softsquared.template.kotlin.src.main.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.template.kotlin.databinding.ItemGallerySectionBinding
import com.softsquared.template.kotlin.src.main.gallery.models.SectionModel

class GalleryFragmentAdater(
    private val daySectionFeetStepList: List<SectionModel>
) : RecyclerView.Adapter<GalleryFragmentAdater.MyViewHolder>() {

    class MyViewHolder(
        private val binding: ItemGallerySectionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(sectionModel: SectionModel) {
            binding.galleryTvDay.text = sectionModel.day_category

            binding.galleryItemRvSectionList.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(binding.root.context)
                adapter = GalleryFragmentSubAdater(sectionModel.day_post_list)
                addItemDecoration(
                    DividerItemDecoration(binding.root.context,
                        DividerItemDecoration.VERTICAL)
                )
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemGallerySectionBinding.inflate(LayoutInflater.from(parent.context),
            parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(daySectionFeetStepList[position])
    }

    override fun getItemCount() = daySectionFeetStepList.size

}