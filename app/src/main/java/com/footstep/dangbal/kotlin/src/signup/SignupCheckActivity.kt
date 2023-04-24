package com.footstep.dangbal.kotlin.src.signup

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import com.footstep.dangbal.kotlin.R
import com.footstep.dangbal.kotlin.config.BaseActivity
import com.footstep.dangbal.kotlin.databinding.ActivitySignupCheckBinding
import com.footstep.dangbal.kotlin.src.signup.DataFile.SignUpForm
import com.footstep.dangbal.kotlin.src.signup.DataSource.SignUpService
import com.footstep.dangbal.kotlin.util.*

class SignupCheckActivity : BaseActivity<ActivitySignupCheckBinding>(ActivitySignupCheckBinding::inflate) {

    var nicknamecheck = getSignInNicknameCheck()
    var checkbox1check = getCheck1()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var signup_btn = findViewById<Button>(R.id.signUp_btn)
        var agree_check = findViewById<CheckBox>(R.id.agree_check)

        checknext()

        checkboxstatus()

        agree_check.setOnClickListener {
            val intent = Intent(this@SignupCheckActivity,SignupInfoActivity::class.java)
            saveCheck1(true)
            checknext()
            showCustomToast("약관을 전부 읽어야 버튼이 활성화 됩니다.")
            startActivity(intent)
        }



        signup_btn.setOnClickListener {
            signUp()
        }

        with(binding){
            etNickname.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(p0: Editable?) {
                    if(etNickname.length()>2){
                        nicknamecheck = true
                        checknext()
                        SaveSignInNickname(etNickname.text.toString(),nicknamecheck)
                    }
                    else{
                        nicknamecheck = false
                        SaveSignInNickname(etNickname.text.toString(),nicknamecheck)
                        checknext()
                    }
                }
            })
        }

    }//onCreate

    private fun checknext(){

        if(nicknamecheck){
            if(checkbox1check){
                binding.signUpBtn.isEnabled = true
                binding.signUpBtn.setTextColor(Color.parseColor("#ffffff"))
            }
            else{
                binding.signUpBtn.isEnabled = false
                binding.signUpBtn.setTextColor(Color.parseColor("#000000"))
            }
        }
        else {
            binding.signUpBtn.isEnabled = false
            binding.signUpBtn.setTextColor(Color.parseColor("#000000"))
        }

    }
    private fun checkboxstatus(){
        binding.agreeCheck.isChecked = getCheck1()
        binding.etNickname.setText(getSignInNickname())
    }



    private fun signUp(){

        SignUpService().signUp(getUser(),object : SignUpView{

            override fun onSignUpSuccess(code: Int, result: String) {
                when(code){
                    200->{
                        showCustomToast(result)
                        startActivityLogin()
                    }
                }
            }

            override fun onSignUpFailure(message: String?) {
                Toast.makeText(this@SignupCheckActivity,message.toString(), Toast.LENGTH_SHORT)
                    .show()

                if("중복된 이메일입니다." == message){
                    Toast.makeText(this@SignupCheckActivity,message.toString(), Toast.LENGTH_SHORT)
                        .show()
                    backtoemail()
                }else if("이메일 형식을 확인해주세요." == message){
                    Toast.makeText(this@SignupCheckActivity,message.toString(), Toast.LENGTH_SHORT)
                        .show()
                    backtoemail()
                }
            }
        })
    }

    private fun getUser():SignUpForm {
//        var user = intent.getSerializableExtra("userData") as SignUpUser
        var id = getSignInId()!!
        var password = getSignInPw()!!
        var nickname = getSignInNickname()!!


        return SignUpForm(email = id, password = password, nickname = nickname)
    }

    private fun startActivityLogin(){
        val mintent = Intent(this,SignupSuccessActivity::class.java)
        finishAffinity()
        startActivity(mintent)
    }

    private fun backtoemail(){
        val intent = Intent(this,SignupActivity::class.java)
        startActivity(intent)
    }















}

