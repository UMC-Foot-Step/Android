package com.softsquared.template.kotlin.src.main.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.template.kotlin.databinding.ItemGallerySectionBinding
import com.softsquared.template.kotlin.src.main.gallery.models_sample.SectionModel

class GalleryFragmentAdater(
    private val daySectionFeetStepList: List<SectionModel>,
    private val galleryFragmentInterface: GalleryFragmentInterface
) : RecyclerView.Adapter<GalleryFragmentAdater.MyViewHolder>() {

    class MyViewHolder(
        private val binding: ItemGallerySectionBinding,
        val galleryFragmentInterface: GalleryFragmentInterface
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(sectionModel: SectionModel) {
            binding.galleryTvDay.text = sectionModel.day_category

            binding.galleryItemRvSectionList.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(binding.root.context,2)
                adapter = GalleryFragmentSubAdater(sectionModel.day_post_list, galleryFragmentInterface)

                // RecyclerView Item 구분선 넣기
//                addItemDecoration(
//                    DividerItemDecoration(binding.root.context,
//                        DividerItemDecoration.VERTICAL)
//                )
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemGallerySectionBinding.inflate(LayoutInflater.from(parent.context),
            parent, false), galleryFragmentInterface)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(daySectionFeetStepList[position])
    }

    override fun getItemCount() = daySectionFeetStepList.size

}