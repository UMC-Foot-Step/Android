package com.softsquared.template.kotlin.src.main.post

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
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
import com.softsquared.template.kotlin.src.main.post.models.PostPlaceList
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
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

    // filepath
    private var filePath: MultipartBody.Part? = null

    // switch checked
    private var swChecked = 0

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

        // switch
        binding.postSwOpen.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                swChecked = 1
            }
            else {
                swChecked = 0
            }
        }
        
        // 작성하기 버튼 눌렀을 때 값 저장
        /*
            @SerializedName("image") val image: MultipartBody.Part,
    @SerializedName("content") val content: String,
    @SerializedName("postingPlaceDto") val placeList: ArrayList<PostPlaceList>,
    @SerializedName("recordDate") val date: String,
    @SerializedName("title") val title: String,
    @SerializedName("visibilityStatusCode") val open: Int
         */
        binding.postBtnPost.setOnClickListener {
            val textHashMap = hashMapOf<String, RequestBody>()
            val contentRequestBody: RequestBody = binding.postEtContent.text.toString().toPlainRequestBody()
            val dateRequestBody: RequestBody = changeDate(tvYear, tvMonth, tvDay).toPlainRequestBody()
            val openRequestBody: RequestBody = swChecked.toString().toPlainRequestBody()
            val titleRequestBody: RequestBody = binding.postEtTitle.toString().toPlainRequestBody()
            textHashMap["content"] = contentRequestBody
            textHashMap["title"] = titleRequestBody
            textHashMap["recordDate"] = dateRequestBody
            textHashMap["visibilityStatusCode"] = openRequestBody
            PostService().postPost(filePath, textHashMap)
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
            PostPlaceList(
                intent.getStringExtra("positionAddress").toString(),
                intent.getDoubleExtra("positionLatitude", 0.0),
                intent.getDoubleExtra("positionLongitude", 0.0),
                intent.getStringExtra("positionTitle").toString()
            )
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

            filePath = changeMultipart(getRealPathFromURI(uri!!))
        }

        binding.postIbPhotoCancel.visibility = View.VISIBLE
    }

    // uri -> file 형식의 데이터
    private fun getRealPathFromURI(uri: Uri): String{
        val buildName = Build.MANUFACTURER
        if(buildName.equals("Xiaomi")){
            return uri.path.toString()
        }
        var columnIndex = 0
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        var cursor = contentResolver.query(uri, proj, null, null, null)

        if(cursor!!.moveToFirst()){
            columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        }

        return cursor.getString(columnIndex)
    }

    private fun changeMultipart(filePath: String): MultipartBody.Part {
        val file = File(filePath)
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("image", file.name, requestFile)
    }

    // string을 plain text requestbody로 바꿔주는 확장함수
    private fun String?.toPlainRequestBody() = requireNotNull(this).toRequestBody("test/plain".toMediaTypeOrNull())

    // date string type -> date type
    private fun changeDate(year: Int, month: Int, day: Int): String {
        return "$year-$month-$day"
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
