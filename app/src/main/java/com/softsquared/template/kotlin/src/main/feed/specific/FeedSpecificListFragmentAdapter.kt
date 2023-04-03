package com.softsquared.template.kotlin.src.main.feed.specific

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.template.kotlin.databinding.ItemGallerySectionBinding
import com.softsquared.template.kotlin.src.main.gallery.GalleryFragmentAdater
import com.softsquared.template.kotlin.src.main.gallery.GalleryFragmentInterface
import com.softsquared.template.kotlin.src.main.gallery.GalleryFragmentSubAdater
import com.softsquared.template.kotlin.src.main.gallery.models.SectionModel

class FeedSpecificListFragmentAdapter(
    private val daySectionFeetStepList: List<SectionModel>,
    private val feedSpecificListFragmentInterface: FeedSpecificListFragmentInterface,
    private val userIdFromFragment:Int
) : RecyclerView.Adapter<FeedSpecificListFragmentAdapter.MyViewHolder>() {


    class MyViewHolder(
        private val binding: ItemGallerySectionBinding,
        val feedSpecificListFragmentInterface: FeedSpecificListFragmentInterface,
        val userId:Int
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(sectionModel: SectionModel) {

            /*
                To Do 1. 날짜 데이터 ~년 ~월 ~일 뷰 형태로 뿌리기
                (년, 월, 일)
             */
            binding.galleryTvYearNum.text = sectionModel.day_category.substring(0 until 4)

            // 월 데이터 한자릿수 판별
            if(sectionModel.day_category[5].toString() == "0"){
                binding.galleryTvMonthNum.text = sectionModel.day_category[6].toString()
            }
            else{
                binding.galleryTvMonthNum.text = sectionModel.day_category.substring(5 until 7)
            }

            // 일 데이터 한자릿수 판별
            if(sectionModel.day_category[8].toString() == "0"){
                binding.galleryTvDayNum.text = sectionModel.day_category[9].toString()
            }
            else{
                binding.galleryTvDayNum.text = sectionModel.day_category.substring(8 until 10)
            }


            binding.galleryItemRvSectionList.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(binding.root.context,2)
                adapter = FeedSpecificListFragmentSubAdapter(sectionModel.day_post_list, feedSpecificListFragmentInterface,userId)

                // RecyclerView Item 구분선 넣기
//                addItemDecoration(
//                    DividerItemDecoration(binding.root.context,
//                        DividerItemDecoration.VERTICAL)
//                )
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemGallerySectionBinding.inflate(
                LayoutInflater.from(parent.context),
            parent, false), feedSpecificListFragmentInterface,userIdFromFragment)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(daySectionFeetStepList[position])
    }

    override fun getItemCount() = daySectionFeetStepList.size

}