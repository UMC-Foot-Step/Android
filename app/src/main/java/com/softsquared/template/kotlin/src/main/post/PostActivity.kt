package com.softsquared.template.kotlin.src.main.post

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
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
import com.softsquared.template.kotlin.databinding.ActivityMainPostBinding
import com.softsquared.template.kotlin.src.main.MainActivity
import com.softsquared.template.kotlin.src.main.post.models.PostResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.time.LocalDate

class PostActivity
    : BaseActivity<ActivityMainPostBinding>(ActivityMainPostBinding::inflate), PostActivityInterface {
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
    var postHashmap = HashMap<String, RequestBody>()

    // switch checked
    private var swChecked = 1

    // accessToken
    private val token: String = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImZvb3RzdGVwQG5hdmVyLmNvbSIsImlhdCI6MTY3NTk2MTcyMywiZXhwIjoxNjc2MjY0MTIzfQ.9sDbAWp3N01eXTqPflKlbLF3p7YN95naQ9uhQpD70Gs"

    // setData
    private var content: String? = null
    private var title: String? = null
    private var name: String? = null
    private var address: String? = null
    private var latitude: Double? = 0.0
    private var longitude: Double? = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 뒤로가기 버튼 누르면 뒤로 가기 dialog
        binding.postIbBack.setOnClickListener {
            backDialog()
        }

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
                    name = result.data?.getStringExtra("positionTitle")
                    binding.postBtnLoc.text = name
                    address = result.data?.getStringExtra("positionAddress")
                    longitude = result.data?.getDoubleExtra("positionLongitude", 0.0)
                    latitude = result.data?.getDoubleExtra("positionLatitude", 0.0)
                }
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
                swChecked = 0
            }
            else {
                swChecked = 1
            }
        }
        
        // 작성하기 버튼 눌렀을 때 값 저장
        binding.postBtnPost.setOnClickListener {
            content = binding.postEtContent.text.toString()
            title = binding.postEtTitle.text.toString()

            Log.d("post image", filePath.toString())
            Log.d("post content", content.toString())
            Log.d("post title", title.toString())
            Log.d("post address", address.toString())
            Log.d("post latitude", latitude.toString())
            Log.d("post longitude", longitude.toString())
            Log.d("post name", name.toString())
            Log.d("post tvYear", tvYear.toString())
            Log.d("post tvMonth", tvMonth.toString())
            Log.d("post tvDay", tvDay.toString())
            Log.d("post swChecked", swChecked.toString())


            // 모든 값이 존재하는지 확인
            // filePath, content, title, address, latitude, longitude, name만 확인 필요
            // 모든 값이 존재한다면 setData

            // 나머지는 서버 success return code 에 따라 구분하기
            if(filePath != null){
                showCustomToast("업로드 중 입니다")
                setData(content!!, title!!, address!!, latitude!!, longitude!!, name!!, tvYear, tvMonth, tvDay, swChecked)
            }

            else{
                // alertDialog 작성
                btnPostDialog()
            }


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

    // 뒤로 가기(물리키)
    override fun onBackPressed() {
        // 기존 뒤로가기 막기 위해 주석 처리 필요
        // super.onBackPressed()

        // dialog 실행
        backDialog()
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

            binding.postIbPhotoCancel.visibility = View.VISIBLE
        }
    }

    // 데이터 지정하기
    private fun setData(setContent: String, setTitle: String,
    setAddress: String, setLatitude: Double, setLongitude: Double, setName: String,
    setYear: Int, setMonth: Int, setDate: Int, setOpen: Int) {
        val contentRequestBody: RequestBody = setContent.toPlainRequestBody()
        val titleRequestBody: RequestBody = setTitle.toPlainRequestBody()
        val addressRequestBody: RequestBody = setAddress.toPlainRequestBody()
        val latitudeRequestBody: RequestBody = setLatitude.toString().toPlainRequestBody()
        val longitudeRequestBody: RequestBody = setLongitude.toString().toPlainRequestBody()
        val nameRequestBody: RequestBody = setName.toPlainRequestBody()
        val dateRequestBody: RequestBody = changeDate(setYear, setMonth, setDate).toPlainRequestBody()
        val openRequestBody: RequestBody = setOpen.toString().toPlainRequestBody()
        postHashmap["content"] = contentRequestBody
        postHashmap["title"] = titleRequestBody
        postHashmap["recordDate"] = dateRequestBody
        postHashmap["visibilityStatusCode"] = openRequestBody
        postHashmap["createPlaceDto.address"] = addressRequestBody
        postHashmap["createPlaceDto.latitude"] = latitudeRequestBody
        postHashmap["createPlaceDto.longitude"] = longitudeRequestBody
        postHashmap["createPlaceDto.name"] = nameRequestBody

        CoroutineScope(Dispatchers.IO).launch {
            postInfo()
        }
    }

    private suspend fun postInfo(){
        PostService(this).postInfo(token, filePath, postHashmap)

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

    // file -> MultipartBody.Part
    private fun changeMultipart(filePath: String): MultipartBody.Part {
        val file = File(filePath)
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("image", file.name, requestFile)
    }

    // string을 plain text requestbody로 바꿔주는 확장함수
    private fun String?.toPlainRequestBody() = requireNotNull(this).toRequestBody("text/plain".toMediaTypeOrNull())

    // date string type -> date(string) type
    private fun changeDate(year: Int, month: Int, day: Int): String {
        return "$year-$month-$day"
    }


    override suspend fun onPostPostInfoSuccess(response: PostResponse) {
        Log.d("Post Success", "$response")
        Log.d("post date", "$tvYear-$tvMonth-$tvDay")

        when(response.code){
            // 성공
            200 -> {
              CoroutineScope(Dispatchers.Main).launch {

                Log.d("데이터로드", "PostActivity 작성하기 완료")
                showCustomToast("작성하기 완료")
              }
              withContext(Dispatchers.Default) {

                // 완료한 경우 fragment 종료
                finish()}
                }

            // 제목, 내용, 장소
            2030, 2031, 2040 -> {
                // alertDialog 출력
                btnPostDialog()
            }
        }

    }

    override suspend fun onPostPostInfoFailure(message: String) {
        withContext(Dispatchers.Main) {
            showCustomToast("API 요청 실패, LogCat 확인")
        }
        Log.d("Whyfail", message)
    }

    // 작성하기 계속 or 취소 dialog 작성
    private fun backDialog() {
        val myDialog = layoutInflater.inflate(R.layout.dialog_post_back, null)
        val build = AlertDialog.Builder(this).setView(myDialog)
        val dialog = build.create()
        dialog.show()

        // 확인 - 작성하기 중단
        val okButton = myDialog.findViewById<Button>(R.id.btn_post_back_ok)
        okButton.setOnClickListener {
            showCustomToast("작성하기 취소 완료")
            dialog.dismiss()
            // activity 종료하기
            finish()
        }
        // 취소 - 계속 작성하기
        val cancelButton = myDialog.findViewById<Button>(R.id.btn_post_back_cancel)
        cancelButton.setOnClickListener {
            showCustomToast("작성하기 진행 중")
            dialog.dismiss()
        }
    }

    // 모든 내용의 값이 없는 경우 alertDialog
    private fun btnPostDialog() {
        val builder = AlertDialog.Builder(this)
            .setTitle("발자취 기록하기")
            .setMessage("모든 내용이 입력되지 않았습니다. 모든 내용을 입력해주십시오. \n모든 내용이 입력되어야 기록할 수 있습니다.")
            .setPositiveButton("확인",
                DialogInterface.OnClickListener { dialog, which ->
                    showCustomToast("빠짐없이 입력해주세요.")
                })
        builder.show()
    }

    /*
    private fun dialog(view: View){
        // 다이얼로그
        val myDialog = layoutInflater.inflate(R.layout.dialog_post_back, null)
        val build = AlertDialog.Builder(view.context).setView(myDialog)
        val dialog = build.create()
        dialog.show()

        // ok
        val okButton = myDialog.findViewById<Button>(R.id.btn_post_back_ok)
        okButton.setOnClickListener {
            showCustomToast("작성하기 취소 완료")
            dialog.dismiss()
            finish()
        }

        // cancel
        val cancelButton = myDialog.findViewById<Button>(R.id.btn_post_back_cancel)
        cancelButton.setOnClickListener {
            showCustomToast("작성하기 진행")
            dialog.dismiss()
        }
    }
    */



}


