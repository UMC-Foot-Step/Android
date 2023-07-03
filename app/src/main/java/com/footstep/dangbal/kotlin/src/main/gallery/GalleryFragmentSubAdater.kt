package com.footstep.dangbal.kotlin.src.main.gallery

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.footstep.dangbal.kotlin.R
import com.footstep.dangbal.kotlin.databinding.ItemGallerySubSectionBinding
import com.footstep.dangbal.kotlin.src.main.gallery.models.PostList


/*
    날짜별 카테고리로 게시글 리스트 갯수만큼 조회
 */
class GalleryFragmentSubAdater(
    private val resultFeetStepList: ArrayList<PostList>,
    val galleryFragmentInterface: GalleryFragmentInterface
) : RecyclerView.Adapter<GalleryFragmentSubAdater.MyViewHolder>() {

    class MyViewHolder(
        private val binding: ItemGallerySubSectionBinding,
        private val galleryFragmentInterface: GalleryFragmentInterface
    ) : RecyclerView.ViewHolder(binding.root) {


        fun bind(resultFeetStep: PostList) {
            with(binding) {
                Glide.with(binding.root.context).load(resultFeetStep.imageUrl).into(galleryIvPostimg)
                galleryTvPostTitle.text = resultFeetStep.title
                galleryTvPostlikeCnt.text = resultFeetStep.likes_cnt.toString()
                galleryTvPostPosition.text = resultFeetStep.placeName

                if(resultFeetStep.isLike==1)
                    galleryBtnPostLike.setBackgroundResource(R.drawable.ic_postlike_btn_selected)
                else if(resultFeetStep.isLike==0)
                    galleryBtnPostLike.setBackgroundResource(R.drawable.ic_postlike_btn_unselected)

            }

            binding.root.setOnClickListener{
                val pos: Int = getAdapterPosition()
                Log.d("리사이클러 뷰 포지션 값 체크", "position = "+pos)
                Log.d("리사이클러 Item 체크", "data = "+ resultFeetStep)

                // 게시글 인덱스
                val post_idx: Int = resultFeetStep.postingId
                galleryFragmentInterface.changeGalleryInfoActivity(post_idx)



                /*
                    To Do. 발자취 상세 정보 API 더미데이터 구성

                    FeetStepInfoResponse 객체 만들어서
                    GalleryInfoActivity로 전달
                    (API 엮을 땐, resultFeetStep 객체에 저장된 게시글 Idx를 넘겨주기만 하면 됨)
                    (지금은, 프론트 리소스 이용한 개발 과정)
                 */

                // 게시글 상세 정보 조회 데이터객체의 내부 데이터객체인,
                // 댓글 데이터객체 생성
//                val resultCommentList = ArrayList<ResultCommentList>()
//
//                resultCommentList.apply {
//                    add(ResultCommentList(
//                        "김다미",
//                    "역시, 1950 갬성 카페가 최고임 ㅋㅋ"))
//
//                    add(
//                        ResultCommentList(
//                        "김다미",
//                        "여기에서, 그해 우리는 촬영해도 좋았겠다.."
//                    )
//                    )
//
//                    add(
//                        ResultCommentList(
//                        "김다미",
//                        "좋은 사람들과 좋은 시간"
//                    )
//                    )
//
//                    add(
//                        ResultCommentList(
//                            "김다미",
//                            "오늘은 날씨가 상쾌했다."
//                        )
//                    )
//
//                    add(
//                        ResultCommentList(
//                            "김다미",
//                            "오랜만에 즐기는 일상에 여유"
//                        )
//                    )
//                }
//
//                // 댓글 데이터객체 게시글 사엣 정보 조회 데이터객체에 삽입
//                // 게시글 상세 정보 조회 데이터객체 생성 후 삽입 진행
//                val feetStepInfoResponse = FeetStepInfoResponse(
//                    resultFeetStep.day,
//                    resultFeetStep.img,
//                    resultFeetStep.title,
//                    "테스팅 설명 데이터입니다. 곧있으면 API 엮을 거에용",
//                    resultFeetStep.like_cnt,
//                    resultFeetStep.position,
//                    "김다미",
//                    3,
//                    resultCommentList
//                )


                // GalleryFragment -> GalleryInfoActivity 전환
                // 더미데이터 객체 함께 넘겨주기

                /*
                    RecyclerView의 특정 발자취 게시글 클릭시,
                    동일 장소별 발자취 게시글 리스트 조회 액티비티로 전환 (태스팅 용)
                 */
//                galleryFragmentInterface.changeGalleryInfoActivity(feetStepInfoResponse)
//                galleryFragmentInterface.testChangeMapGalleryActivity()


                /*
                    게시글 상세 조회 API 연결
                 */

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemGallerySubSectionBinding.inflate(LayoutInflater.from(parent.context),
            parent, false), galleryFragmentInterface)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.d("포지션 값 체크", "Postion = $position")
        holder.bind(resultFeetStepList[position])
    }

    override fun getItemCount() = resultFeetStepList.size

}