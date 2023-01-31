package com.softsquared.template.kotlin.src.main.postupdate

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import android.widget.ImageButton
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityPostEditBinding
import com.softsquared.template.kotlin.src.main.post.PostSearchPositionActivity
import com.softsquared.template.kotlin.src.main.postupdate.models.PostUpdateResponse
import java.time.LocalDate



class PostUpdateActivity : BaseActivity<ActivityPostEditBinding>(ActivityPostEditBinding::inflate), PostUpdateActivityInterface {
    // postingId
    private var postingId: Int = 0

    // datepicker
    private val today = LocalDate.now()
    private var tvYear = today.year
    private var tvMonth = today.monthValue
    private var tvDay = today.dayOfMonth

    // 갤러리 앱 열기
    private val OPEN_GALLERY = 1
    private var uri: Uri? = null

    // registerForActivityResult API 구현
    private lateinit var getResultPosition: ActivityResultLauncher<Intent>

    // switch checked
    private var swChecked = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // api 값 받기
        val intent = intent
        postingId = intent.getIntExtra("posting-id", 3)

        // 값 전달
        PostUpdateService(this).getPostUpdateInfo(postingId)

        // 뒤로가기 버튼 누르면 뒤로 가기
        binding.editIbBack.setOnClickListener {
            finish()
        }

        // 사진 삭제
        if(uri==null){
            binding.editIbPhotoCancel.visibility = View.INVISIBLE
        }

        // 사진 삭제 imageButton 클릭시
        binding.editIbPhotoCancel.setOnClickListener {
            binding.editIbGallery.setImageResource(R.drawable.post_iv_unselected)
            uri=null
            binding.editIbPhotoCancel.visibility = View.INVISIBLE
        }

        binding.editIbCalendar.setOnClickListener {
            val bottomSheet = layoutInflater.inflate(R.layout.fragment_post_calendar, null)
            val bottomSheetDialog = BottomSheetDialog(this, R.style.calTheme_Custom)
            val btnClose = bottomSheet.findViewById<ImageButton>(R.id.calBtnQuit)
            val btnDateCheck = bottomSheet.findViewById<Button>(R.id.calBtnCheck)
            val calView = bottomSheet.findViewById<CalendarView>(R.id.calDatePicker)

            calView.maxDate = System.currentTimeMillis()

            // calendar bottomsheetdialog 설정
            bottomSheetDialog.setContentView(bottomSheet)
            bottomSheetDialog.show()

            btnClose.setOnClickListener {
                bottomSheetDialog.dismiss()
            }

            calView.setOnDateChangeListener { calView, year, month, dayOfMonth ->
                tvYear = year
                tvMonth = month + 1
                tvDay = dayOfMonth
            }

            btnDateCheck.setOnClickListener{
                dataSet(tvYear, tvMonth, tvDay)
                bottomSheetDialog.dismiss()
            }
        }
        // datePicker 설정
        dataSet(tvYear, tvMonth, tvDay)

        // 위치검색 activity 전환
        getResultPosition = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
            if(result.resultCode == RESULT_OK){
                val getPosTitle = result.data?.getStringExtra("positionTitle")
                binding.editBtnLoc.text = getPosTitle
            }
        }
        // 위치검색 activity 클릭이벤트
        binding.editBtnLoc.setOnClickListener{
            val intent = Intent(this, PostSearchPositionActivity::class.java)
            getResultPosition.launch(intent)
        }

        // 갤러리 열기
        binding.editIbGallery.setOnClickListener {
            openGallery()
        }

        // switch
        binding.editSwOpen.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                swChecked = 1
            }
            else {
                swChecked = 0
            }
        }

        // 수정하기 버튼 클릭 이벤트
        binding.editBtnPost.setOnClickListener{
            finish()
        }

        if(intent.hasExtra("positionTitle")){
            binding.editBtnLoc.text = intent.getStringExtra("positionTitle")
        }
    }

    override fun onRestart() {
        super.onRestart()
        // 장소 제목 불러오기
        if(intent.hasExtra("positionTitle")){
            binding.editBtnLoc.text = intent.getStringExtra("positionTitle")
        }
    }

    // 날짜 string 변환
    private fun dataSet(year: Int, month: Int, day: Int){
        tvYear = year
        tvMonth = month
        tvDay = day
        var date = "$tvYear / $tvMonth / $tvDay"
        binding.editTvDate.text = date
    }

    // 갤러리 열기
    private fun openGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        activityResult.launch(intent)
    }

    // 갤러리 이미지 결과 가져오기 Glide
    private val activityResult: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        // 결과 코드 Ok, 결과값 null 아니면
        if(it.resultCode == RESULT_OK && it.data != null){
            // 값 담기
            uri = it.data!!.data

            // 화면에 보여주기
            Glide.with(this)
                .load(uri)
                .into(binding.editIbGallery)

        }

        binding.editIbPhotoCancel.visibility = View.VISIBLE
    }

    override fun onGetPostUpdateInfoSuccess(response: PostUpdateResponse) {
        Log.d("postUpdateActivityResponse", response.toString())
    }


    // api 요청 실패
    override fun onGetPostUpdateInfoFailure(message: String) {
        showCustomToast("API 요청 실패, LogCat 확인")
        Log.d("Why fail?", message)
    }
}

