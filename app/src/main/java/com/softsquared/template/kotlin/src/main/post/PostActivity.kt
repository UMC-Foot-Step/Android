package com.softsquared.template.kotlin.src.main.post

import android.content.Intent
import android.net.Uri
import android.os.Bundle
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
import com.softsquared.template.kotlin.databinding.ActivityMainPostBinding
import java.time.LocalDate

class PostActivity : BaseActivity<ActivityMainPostBinding>(ActivityMainPostBinding::inflate) {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 사진 삭제 imageButton
        if (uri==null) {
            binding.postIbPhotoCancel.visibility = View.INVISIBLE
        }

        // 사진 삭제 imageButton 클릭시
        binding.postIbPhotoCancel.setOnClickListener {
            binding.postIbGallery.setImageResource(R.drawable.post_iv_unselected)
            uri = null
            binding.postIbPhotoCancel.visibility = View.INVISIBLE
        }


        // 뒤로가기 버튼 누르면 뒤로가기..!
        binding.postIbBack.setOnClickListener {
            finish()
        }

        binding.postIbCalendar.setOnClickListener {
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

        // 위치 검색 activity 전환
        getResultPosition = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
                if(result.resultCode == RESULT_OK){
                    val getPosTitle = result.data?.getStringExtra("positionTitle")
                    binding.postBtnLoc.text = getPosTitle }
        }

        binding.postBtnLoc.setOnClickListener{
            val intent = Intent(this, PostSearchPositionActivity::class.java)
            getResultPosition.launch(intent)
        }

        // 갤러리 열기
        binding.postIbGallery.setOnClickListener {
            openGallery()
        }
        
        // 작성하기 버튼 눌렀을 때 값 저장
        binding.postBtnPost.setOnClickListener {
            finish()
        }

        if(intent.hasExtra("positionTitle")){
            binding.postBtnLoc.text = intent.getStringExtra("positionTitle")
        }
    }
    override fun onRestart() {
        super.onRestart()
        // 장소 제목 불러오기
        if(intent.hasExtra("positionTitle")){
            binding.postBtnLoc.text = intent.getStringExtra("positionTitle")
        }
    }
    private fun dataSet(year: Int, month: Int, day: Int){
        tvYear = year
        tvMonth = month
        tvDay = day
        var date = "$tvYear / $tvMonth / $tvDay"
        binding.postTvDate.text = date
    }

    // 갤러리 열기
    private fun openGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        activityResult.launch(intent)
    }

    // 갤러리 이미지 결과 가져오기 - Glide
    private val activityResult: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        // 결과 코드 Ok, 결과값 null 아니면
        if(it.resultCode == RESULT_OK && it.data != null){
            // 값 담기
            uri = it.data!!.data

            // 화면에 보여주기
            Glide.with(this)
                .load(uri)
                .into(binding.postIbGallery)
        }

        binding.postIbPhotoCancel.visibility = View.VISIBLE
    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK){
            if(requestCode == OPEN_GALLERY){
                var currentImageUrl : Uri? = data?.data

                try{
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, currentImageUrl)
                    binding.postIbGallery.setImageBitmap(bitmap)
                } catch(e:Exception){
                    e.printStackTrace()
                }
            }
        }
        else{
            Log.d("ActivityResult", "something wrong")
        }
    }*/

}
