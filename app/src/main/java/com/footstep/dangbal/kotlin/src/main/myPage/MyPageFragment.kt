package com.footstep.dangbal.kotlin.src.main.myPage


import android.app.Activity.*
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.footstep.dangbal.kotlin.R
import com.footstep.dangbal.kotlin.config.BaseFragment
import com.footstep.dangbal.kotlin.databinding.FragmentMyPageBinding
import com.footstep.dangbal.kotlin.src.login.LoginProcessActivity
import com.footstep.dangbal.kotlin.src.main.myPage.mypageResponseFile.changeNicknameInfo
import com.footstep.dangbal.kotlin.src.main.myPage.mypageResultFile.Resultmypage
import com.footstep.dangbal.kotlin.util.getJwt
import com.footstep.dangbal.kotlin.util.getRefresh
import com.footstep.dangbal.kotlin.util.removeJwt
import com.footstep.dangbal.kotlin.util.removeRefresh
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


// 메인 - 마이페이지
class MyPageFragment :
    BaseFragment<FragmentMyPageBinding>(FragmentMyPageBinding::bind, R.layout.fragment_my_page){


    private val communityRule = "https://mud-century-1f3.notion.site/29485b0e5055495db2041d8693128696"


    //호출 시점은 accessToken호출 때
    private val accessToken : String by lazy{
        val jwt = getJwt()

        if(jwt == null){
            startActivity(Intent(requireContext(),LoginProcessActivity::class.java))
            requireActivity().finish()

            //flag clear -> 로그인 화면 넘어가면 이전 activity삭제
            return@lazy ""
        }

        jwt
    }

    private val refreshToken : String by lazy{
        val refresh = getRefresh()

        if(refresh == null){
            showCustomToast("재 로그인 후 시도 필요")
            startActivity(Intent(requireContext(),LoginProcessActivity::class.java))
            requireActivity().finish()

            return@lazy ""
        }

        refresh
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getmypage()

        //닉네임 초기설정 = 마이페이지 누를때 서버에서 api로 가져옴.
        //갤러리 부분 수정할때 Glide가 많이 바뀌어서 그냥 하기보다는 물어보라고 하심.


        //수정버튼
        binding.btnModify.setOnClickListener {

            //텍스트 변경
            binding.txtNickname.visibility = View.INVISIBLE
            binding.edtNickname.visibility = View.VISIBLE
            binding.btnModify.visibility = View.INVISIBLE
            binding.btnModifyOk.visibility = View.VISIBLE

            //나머지 버튼 비활성화
            binding.imgBtnProfilePlus.isClickable = false
            binding.btnLogout.isClickable = false
            binding.btnWithdraw.isClickable = false
            binding.btnMyFootprint.isClickable = false

            binding.edtNickname.setText(binding.txtNickname.text)
        }

        binding.edtNickname.addTextChangedListener (object : TextWatcher{

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(editable: Editable) {
                if(editable.isNotEmpty()){
                    binding.btnModifyOk.isClickable =true
                    binding.btnModifyOk.setBackgroundColor(Color.TRANSPARENT)
                    binding.btnModifyOk.visibility = View.VISIBLE
                }else
                {
                    binding.btnModifyOk.isClickable = false
                    binding.btnModifyOk.visibility = View.INVISIBLE
                    showCustomToast("공백 불가")
                }
            }

        })

        val imm = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        //확인버튼
        binding.btnModifyOk.setOnClickListener {
            with(binding.edtNickname){
                if(isFocused){
                    clearFocus()
                }
            }

            val aftername = binding.edtNickname.text.toString()

            if(binding.edtNickname.text.toString() == ""){
                binding.txtNickname.text = "닉네임"
            }else{
                binding.txtNickname.text = aftername
            }

            imm.hideSoftInputFromWindow(binding.edtNickname.windowToken, 0)

            nicknamechange()


        }
        //프로필 설정 버튼
        binding.imgBtnProfilePlus.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            activityResult.launch(intent)


        }

//        binding.btnWithdraw.setOnClickListener {
//            val word = binding.txtNickname.text.toString()
//            showLoadingDialog(requireContext())
//            MypageService(this).tryGetUserNickname(word)
//
//        }

        //로그아웃
        binding.btnLogout.setOnClickListener {

            val mDialogView = LayoutInflater.from(context).inflate(R.layout.fragment_dialog,null)

            val mBuilder = AlertDialog.Builder(context)
                .setView(mDialogView)

            val mAlertDialog = mBuilder.show()

            val okButton = mDialogView.findViewById<Button>(R.id.btn_ok_log)
            okButton.setOnClickListener {
                showCustomToast("로그아웃 완료!")

                logout()

                //flag

                startActivity(Intent(activity,LoginProcessActivity::class.java))
                requireActivity().finish()
            }
            val noButton = mDialogView.findViewById<Button>(R.id.btn_no_log)
            noButton.setOnClickListener {
                mAlertDialog.dismiss()
            }
        }


        //회원탈퇴
        binding.btnWithdraw.setOnClickListener {

            val mDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_withdraw,null)

            val mBuilder = AlertDialog.Builder(context)
                .setView(mDialogView)

            val mAlertDialog = mBuilder.show()

            val okButton = mDialogView.findViewById<Button>(R.id.btn_ok_with)
            okButton.setOnClickListener {
                showCustomToast("탈퇴 완료")

                withdraw()

                startActivity(Intent(activity,LoginProcessActivity::class.java))
                requireActivity().finish()
            }
            val noButton = mDialogView.findViewById<Button>(R.id.btn_no_with)
            noButton.setOnClickListener {
                mAlertDialog.dismiss()
            }
        }

        //비밀번호 변경
        binding.btnPasswordChange.setOnClickListener {
            Log.d("Tester", "onViewCreated: ㅁㄴㅇㄻㄴㄹㅇㅁㄹㄹ")
            val intent = Intent(activity,ChangePasswordActivity::class.java)
            startActivity(intent)
        }


        //커뮤니티 이용규칙
        binding.btnCommunityRule.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(communityRule)))
        }




    }//onCreate


    var filepath: MultipartBody.Part? = null


    private fun changeProfile(){
        ProfileService().tryChangeProfile(accessToken, filepath!!, object : ProfileView{
            override fun onProfileSuccess(code: Int, result: String) {
                when(code){
                    200->{
                        showCustomToast("이미지 변경 성공!")
                    }
                }
            }

            override fun onProfileFailure(message: String) {
                showCustomToast(message)

            }

        })
    }


    private fun getmypage(){


//        binding.txtNickname.text = mypageinformation.nickname
//        binding.txtFootprintNum.text = mypageinformation.postingcount.toString()
        MyPageService().tryGetMyPage(accessToken, object : MyPageView{

            override fun onMyPageSuccess(code: Int, result: Resultmypage) {

                when(code){
                    200->{
                        binding.txtNickname.text = result.nickname
                        binding.txtFootprintNum.text = result.postingCount.toString()
                        Glide.with(this@MyPageFragment)
                            .load(result.url)
                            .into(binding.imgMyProfile)


                    }
                }
            }
            override fun onMyPageFailure(message: String) {
                showCustomToast(message)
            }

        } )


    }

    private fun nicknamechange(){

        NicknameService().trychangeNickname(accessToken , getNickname() , object : NicknameView{
            override fun onNicknameSuccess(code: Int, result: String) {
                when(code){
                    200->{
                        changeNicknameInfo(getNickname().toString())
                        nicknameChangeSuccess()
                        showCustomToast(result)
                    }
                }
            }

            override fun onNicknameFailure(message: String) {
                showCustomToast(message)
                Log.d("Tester", "onNicknameFailure: 실행됨")
            }

        })

    }

    private fun nicknameChangeSuccess(){

        binding.txtNickname.visibility = View.VISIBLE

        binding.edtNickname.visibility = View.INVISIBLE

        binding.btnModifyOk.visibility = View.INVISIBLE

        binding.btnModify.visibility = View.VISIBLE

        binding.imgBtnProfilePlus.isClickable = true
        binding.btnLogout.isClickable = true
        binding.btnWithdraw.isClickable = true
        binding.btnMyFootprint.isClickable = true

    }

    private fun getNickname():changeNicknameInfo{
        val aftername = binding.edtNickname.text.toString()

        return changeNicknameInfo(aftername)
    }


    private val activityResult: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){

            if(it.resultCode == RESULT_OK && it.data != null){
                val uri = it.data!!.data

                Glide.with(this)
                    .load(uri)
                    .into(binding.imgMyProfile)
                filepath = changeMultipart(getRealPathFormURI(uri!!))
                changeProfile()
            }
        //사진을 직접 보내기 retrofit multipart
        }
    private fun changeMultipart(filePath:String):MultipartBody.Part{
        val file = File(filePath)
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("profile",file.name,requestFile)
    }
    private fun getRealPathFormURI(uri: Uri):String{
        val buildName = Build.MANUFACTURER
        if(buildName.equals("Xiaomi")){
            return uri.path.toString()
        }
        var columIndex = 0
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = activity?.contentResolver?.query(uri,proj,null,null,null)
        if(cursor!!.moveToFirst()){
            columIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        }
        return cursor.getString(columIndex)
    }




    private fun logout() {
        removeRefresh()
        removeJwt()
    }

    private fun withdraw() {
        secceion()
        removeRefresh()
        removeJwt()
    }

    private fun secceion(){
        SecessionService().trySecession(accessToken, refreshToken, object : SecessionView{
            override fun onSecessionSuccess(code: Int, result: String) {
                when(code){
                    200->{
                        showCustomToast(result)
                    }
                }
            }

            override fun onSecessionFailure(message: String) {
                showCustomToast(message)
            }

        })
    }




    //비밀번호 변경 api보고 비밀번호 담아서 요청


}

//사용자 구별=토큰
//어디에든 똑같은 키 code=키값을 관리하는곳 jwt나 auth는 문자열(key값)
//
//페이지에 들어 왔을때 api요청
//
//


