package com.softsquared.template.kotlin.src.main.gallery.map

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.template.kotlin.databinding.ItemGallerySectionBinding
import com.softsquared.template.kotlin.databinding.ItemMapgallerySectionBinding
import com.softsquared.template.kotlin.src.main.gallery.GalleryFragmentInterface
import com.softsquared.template.kotlin.src.main.gallery.GalleryFragmentSubAdater
import com.softsquared.template.kotlin.src.main.gallery.models.SectionModel

class MapGalleryActivityAdapter(
    private val daySectionFeetStepList: List<SectionModel>,
    private val mapGalleryActivityInterface: MapGalleryActivityInterface
) : RecyclerView.Adapter<MapGalleryActivityAdapter.MyViewHolder>() {

    class MyViewHolder(
        private val binding: ItemMapgallerySectionBinding,
        val mapGalleryActivityInterface: MapGalleryActivityInterface
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(sectionModel: SectionModel) {
            binding.galleryTvDay.text = sectionModel.day_category

            binding.galleryItemRvSectionList.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(binding.root.context,2)
                adapter = MapGalleryActivitySubAdapter(sectionModel.day_post_list, mapGalleryActivityInterface)

                // RecyclerView Item 구분선 넣기
//                addItemDecoration(
//                    DividerItemDecoration(binding.root.context,
//                        DividerItemDecoration.VERTICAL)
//                )
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemMapgallerySectionBinding.inflate(LayoutInflater.from(parent.context),
            parent, false), mapGalleryActivityInterface)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(daySectionFeetStepList[position])
    }

    override fun getItemCount() = daySectionFeetStepList.size

}