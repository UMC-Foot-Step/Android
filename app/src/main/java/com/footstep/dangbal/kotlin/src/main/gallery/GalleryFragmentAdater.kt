package com.footstep.dangbal.kotlin.src.main.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.footstep.dangbal.kotlin.databinding.ItemGalleryExceptionBinding
import com.footstep.dangbal.kotlin.databinding.ItemGallerySectionBinding
import com.footstep.dangbal.kotlin.src.main.gallery.models.SectionModel

class GalleryFragmentAdater(
    private val daySectionFeetStepList: List<SectionModel>,
    private val galleryFragmentInterface: GalleryFragmentInterface
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // 리사이클러 뷰의 데이터가 존재
    private val ITEM = 1

    // 리사이클러 뷰의 데이터가 존재하지 않음
    private val EMPTY = 0


    // 데이터가 존재할 때의 뷰홀더
    class MyViewHolder(
        private val binding: ItemGallerySectionBinding,
        val galleryFragmentInterface: GalleryFragmentInterface
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
                adapter = GalleryFragmentSubAdater(sectionModel.day_post_list, galleryFragmentInterface)

                // RecyclerView Item 구분선 넣기
//                addItemDecoration(
//                    DividerItemDecoration(binding.root.context,
//                        DividerItemDecoration.VERTICAL)
//                )
            }
        }

    }


    /*
        To Do 2. 데이터가 존재하지 않을 때의 뷰홀더 정의
     */
    class EmptyViewHoder(
        val binding: ItemGalleryExceptionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            ITEM ->
                MyViewHolder(ItemGallerySectionBinding.inflate(LayoutInflater.from(parent.context),
            parent, false), galleryFragmentInterface)
            EMPTY ->
                EmptyViewHoder(ItemGalleryExceptionBinding.inflate(LayoutInflater.from(parent.context),
            parent, false))
            else -> {
                throw ClassCastException("Unknown viewType $viewType")
            }
        }

//        return MyViewHolder(ItemGallerySectionBinding.inflate(LayoutInflater.from(parent.context),
//            parent, false), galleryFragmentInterface)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {

            // ITEM
            is MyViewHolder -> {
                holder.bind(daySectionFeetStepList[position])
            }

            // Empty
            is EmptyViewHoder -> {

            }
        }
    }


    /*
        To Do 1. 데이터 유무에 따른, 리사이클러 뷰 아이템 갯수 설정
     */
    override fun getItemCount(): Int {
        return if (daySectionFeetStepList.size == 0) 1 else daySectionFeetStepList.size
    }


    // 데이터 유무에 따른, 뷰 구현
    override fun getItemViewType(position: Int): Int {
//        Log.d("리사이클러 아이템 갯수 체크", getItemCount().toString())
//        Log.d("리사이클러 값 체크", "지나가?")
        return if (daySectionFeetStepList.size == 0){
            EMPTY
        } else {
            ITEM
        }
    }


}