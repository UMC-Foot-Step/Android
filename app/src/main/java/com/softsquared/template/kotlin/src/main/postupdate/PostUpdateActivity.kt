package com.softsquared.template.kotlin.src.main.postupdate

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import android.widget.ImageButton
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityPostEditBinding
import com.softsquared.template.kotlin.src.main.post.PostSearchPositionActivity
import com.softsquared.template.kotlin.src.main.postupdate.models.PostEditResponse
import com.softsquared.template.kotlin.src.main.postupdate.models.PostUpdateResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.HashMap


class PostUpdateActivity : BaseActivity<ActivityPostEditBinding>(ActivityPostEditBinding::inflate), PostUpdateActivityInterface {
    // postingId
    private var postingId: Int = 0

    // accessToken
    private val token: String = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImZvb3RzdGVwQG5hdmVyLmNvbSIsImlhdCI6MTY3NTk2MTcyMywiZXhwIjoxNjc2MjY0MTIzfQ.9sDbAWp3N01eXTqPflKlbLF3p7YN95naQ9uhQpD70Gs"

    // datepicker
    private var today = LocalDate.now()
    private var tvYear = today.year
    private var tvMonth = today.monthValue
    private var tvDay = today.dayOfMonth

    // 갤러리 앱 열기
    private val OPEN_GALLERY = 1
    private var uri: Uri? = null

    // registerForActivityResult API 구현
    private lateinit var getResultPosition: ActivityResultLauncher<Intent>

    // switch checked
    private var swChecked = 1

    // filepath
    private var filePath: MultipartBody.Part? = null
    var postHashmap = HashMap<String, RequestBody>()

    // setData
    private var content: String? = null
    private var title: String? = null
    private var name: String? = null
    private var address: String? = null
    private var latitude: Double? = 0.0
    private var longitude: Double? = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // api 값 받기
        val intent = intent
        postingId = intent.getIntExtra("posting-id", 3)

        // api 값 요청
        PostUpdateService(this).getPostUpdateInfo(token, postingId)

        // 뒤로가기 버튼 누르면 뒤로 가기 dialog
        binding.editIbBack.setOnClickListener {
            backDialog()
        }

        // 사진 삭제
        //if(uri==null){
        //    binding.editIbPhotoCancel.visibility = View.INVISIBLE
       // }

        // 사진 삭제 imageButton 클릭시
        //binding.editIbPhotoCancel.setOnClickListener {
            //binding.editIbGallery.setImageResource(R.drawable.post_iv_unselected)
            //uri=null
            //inding.editIbPhotoCancel.visibility = View.INVISIBLE
       // }

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
                name = result.data?.getStringExtra("positionTitle")
                binding.editBtnLoc.text = name
                address = result.data?.getStringExtra("positionAddress")
                longitude = result.data?.getDoubleExtra("positionLongitude", 0.0)
                latitude = result.data?.getDoubleExtra("positionLatitude", 0.0)
            }
        }
        // 위치검색 activity 클릭이벤트
        binding.editBtnLoc.setOnClickListener{
            val intent = Intent(this, PostSearchPositionActivity::class.java)
            getResultPosition.launch(intent)
        }

        // 갤러리 열기
        //binding.editIbGallery.setOnClickListener {
        //    openGallery()
        //}

        // switch
        binding.editSwOpen.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                swChecked = 0
            }
            else {
                swChecked = 1
            }
        }

        // 수정하기 버튼 클릭 이벤트
        binding.editBtnPost.setOnClickListener{
            content = binding.editEtContent.text.toString()
            title = binding.editEtTitle.text.toString()

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
            // content, title, address, latitude, longitude, name만 확인 필요
            // 모든 값이 존재한다면 setData
            // 이미지 경로만 지금 확인
            // 나머지는 서버 success return code 에 따라 구분하기
            //if(filePath!=null){
            if(content!=null&&title!=null&&address!=null&&latitude!=null&&longitude!=null&&name!=null){
                setData(content!!, title!!, address!!, latitude!!, longitude!!, name!!, tvYear, tvMonth, tvDay, swChecked)
            }

            else{
                // alertDialog 작성
                btnPostDialog()
            }

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

    override fun onBackPressed() {
        // 뒤로가기 물리키 막기
        // super.onBackPressed()

        // 뒤로가기 dialog 실행
        backDialog()
    }

    // 뒤로가기 dialog
    private fun backDialog() {
        val myDialog = layoutInflater.inflate(R.layout.dialog_post_edit_back, null)
        val build = AlertDialog.Builder(this).setView(myDialog)
        val dialog = build.create()
        dialog.show()

        // 확인 - 수정하기 중단
        val okButton = myDialog.findViewById<Button>(R.id.btn_post_edit_back_ok)
        okButton.setOnClickListener {
            showCustomToast("수정하기 취소 완료")
            dialog.dismiss()
            // activity 종료하기
            finish()
        }

        // 취소 - 수정하기 계속 진행
        val cancelButton = myDialog.findViewById<Button>(R.id.btn_post_edit_back_cancel)
        cancelButton.setOnClickListener {
            showCustomToast("수정하기 진행 중")
            dialog.dismiss()
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

            filePath = changeMultipart(getRealPathFromURI(uri!!))

            //binding.editIbPhotoCancel.visibility = View.VISIBLE
        }
    }

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

        PostUpdateService(this).postPostUpdateInfo(token, filePath, postHashmap, postingId)
    }

    // string을 plain text requestbody로 바꿔주는 확장함수
    private fun String?.toPlainRequestBody() = requireNotNull(this).toRequestBody("text/plain".toMediaTypeOrNull())

    // date string type -> date(string) type
    private fun changeDate(year: Int, month: Int, day: Int): String {
        return "$year-$month-$day"
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

    // server image -> filePath
    //private fun setImagePath(filePath: String){}

    // api 요청 성공
    override fun onGetPostUpdateInfoSuccess(response: PostUpdateResponse) {
        // log
        Log.d("Success get", "$response")
        // title
        title = binding.editEtTitle.setText(response.result.postingTitle).toString()
        // content
        content = binding.editEtContent.setText(response.result.postingContent).toString()
        // img
        val serverImg = response.result.postingImageUrl
        showServerImg(serverImg)
        // place name
        name = response.result.createPlaceDto.name
        binding.editBtnLoc.text = name
        // place address
        address = response.result.createPlaceDto.address
        // place latitude
        latitude = response.result.createPlaceDto.latitude
        // place longitude
        longitude = response.result.createPlaceDto.longitude
        // calendar
        showCalendar(response.result.postingDate)
        Log.d("post Update date", "$tvYear-$tvMonth-$tvDay")
        // swChecked
        swChecked = response.result.postingOpen
        when(swChecked){
            0 -> binding.editSwOpen.isChecked = false
            1 -> binding.editSwOpen.isChecked = true
        }

    }

    // api 요청 실패
    override fun onGetPostUpdateInfoFailure(message: String) {
        showCustomToast("API 요청 실패, LogCat 확인")
        Log.d("Why fail?", message)
    }


    // api post 성공
    override fun onPostPostUpdateInfoSuccess(response: PostEditResponse) {
        Log.d("Success post", "$response")

        when(response.code){
            // 성공
            200 -> {
                Log.d("데이터로드", "PostActivity 수정하기 완료")
                showCustomToast("발자취 수정하기 완료")
                // 완료한 경우 fragment 종료
                finish()
            }

            // 제목, 내용, 장소
            2030, 2031, 2040 -> {
                // alertDialog 출력
                btnPostDialog()
            }
        }
    }
    // api post 실패
    override fun onPostPostUpdateInfoFailure(message: String) {
        showCustomToast("API post 실패, LogCat 확인")
        Log.d("Why fail?", message)
    }

    // 이미지 보여주기
    private fun showServerImg(serverUrl: String) {
        Glide.with(this)
            .load(serverUrl)
            .into(binding.editIbGallery)

        //binding.editIbPhotoCancel.visibility = View.VISIBLE
    }

    private fun imageUrlToCacheFileAsync(context: Context, url: String) {
        // 이미지 다운받기
        Glide.with(context)
            .asBitmap()
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    val newFile = File(
                        context.cacheDir.path,
                        Random(SystemClock.currentThreadTimeMillis()).nextLong().toString()
                    ).apply {
                        createNewFile()
                    }
                    FileOutputStream(newFile).use{
                        resource.compress(Bitmap.CompressFormat.JPEG, 100, it)
                    }
                    filePath = loadFilechangeMultipart(newFile)
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                    Log.d("imageLoad", "clear")
                }
            })


    }

    private fun loadFilechangeMultipart(file: File): MultipartBody.Part{
        // file -> MultipartBody.Part
        val requestFile = file?.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("image", file?.name, requestFile)
    }


    // uri
    /*
    fun getImageUri(inContext: Context, inImage: Bitmap): Uri{
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            inContext.contentResolver,
            inImage,
            title,
            null
        )
        return Uri.parse(path)
    }
    */



    // 캘린더 날짜 보여주기
    private fun showCalendar(serverDate: String){
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        today = LocalDate.parse(serverDate, formatter)
        tvYear = today.year
        tvMonth = today.monthValue
        tvDay = today.dayOfMonth
        dataSet(tvYear, tvMonth, tvDay)
    }

    // 모든 내용의 값이 없는 경우 alertDialog
    private fun btnPostDialog() {
        val builder = AlertDialog.Builder(this)
            .setTitle("발자취 수정하기")
            .setMessage("모든 내용이 입력되지 않았습니다. 모든 내용을 입력해주십시오. \n모든 내용이 입력되어야 기록할 수 있습니다.")
            .setPositiveButton("확인",
                DialogInterface.OnClickListener { dialog, which ->
                    showCustomToast("빠짐없이 입력해주세요.")
                })
        builder.show()
    }


}

