package com.softsquared.template.kotlin.src.signup

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivitySignupCheckBinding
import com.softsquared.template.kotlin.src.signup.DataFile.SignUpForm
import com.softsquared.template.kotlin.src.signup.DataSource.SignUpService
import com.softsquared.template.kotlin.util.*

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

                if("????????? ??????????????????." == message){
                    Toast.makeText(this@SignupCheckActivity,message.toString(), Toast.LENGTH_SHORT)
                        .show()
                    backtoemail()
                }else if("????????? ????????? ??????????????????." == message){
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
        startActivity(mintent)
        finish()
    }

    private fun backtoemail(){
        val intent = Intent(this,SignupActivity::class.java)
        startActivity(intent)
        finish()
    }















}

